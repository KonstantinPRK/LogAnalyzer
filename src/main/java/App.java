import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        /*
         * Всё решение специально находится в одном main, без отдельных классов
         * и методов. Так проще увидеть весь путь данных от команды до отчёта.
         */
        try {
            // 1. Получаем от пользователя всю команду одной строкой.
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите команду, например:");
            System.out.println("analyzer --path logs/*.log --from 2024-08-31 --format markdown");
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.isEmpty()) {
                System.out.println("Ошибка: команда не введена.");
                return;
            }

            // 2. Разбиваем команду на части. Пути в кавычках тоже поддерживаются.
            List<String> commandParts = new ArrayList<>();
            Matcher commandMatcher = Pattern.compile("\\\"([^\\\"]*)\\\"|'([^']*)'|(\\S+)").matcher(command);
            while (commandMatcher.find()) {
                if (commandMatcher.group(1) != null) {
                    commandParts.add(commandMatcher.group(1));
                } else if (commandMatcher.group(2) != null) {
                    commandParts.add(commandMatcher.group(2));
                } else {
                    commandParts.add(commandMatcher.group(3));
                }
            }

            // Первое слово analyzer является названием программы, поэтому пропускаем его.
            int firstOptionIndex = !commandParts.isEmpty() && commandParts.get(0).equalsIgnoreCase("analyzer") ? 1 : 0;

            String pathArgument = null;
            String fromArgument = null;
            String toArgument = null;
            String format = "markdown"; // Формат по умолчанию.

            // 3. Самый простой ручной разбор параметров команды.
            for (int i = firstOptionIndex; i < commandParts.size(); i++) {
                String part = commandParts.get(i);

                if (part.equals("--path") || part.equals("--from") || part.equals("--to") || part.equals("--format")) {
                    if (i + 1 >= commandParts.size() || commandParts.get(i + 1).startsWith("--")) {
                        System.out.println("Ошибка: после параметра " + part + " должно быть значение.");
                        return;
                    }

                    String value = commandParts.get(++i);
                    if (part.equals("--path")) {
                        pathArgument = value;
                    } else if (part.equals("--from")) {
                        fromArgument = value;
                    } else if (part.equals("--to")) {
                        toArgument = value;
                    } else {
                        format = value.toLowerCase(Locale.ROOT);
                    }
                } else {
                    System.out.println("Ошибка: неизвестная часть команды: " + part);
                    return;
                }
            }

            if (pathArgument == null || pathArgument.isBlank()) {
                System.out.println("Ошибка: обязательный параметр --path не указан.");
                return;
            }

            if (!format.equals("markdown") && !format.equals("adoc")) {
                System.out.println("Ошибка: --format может быть только markdown или adoc.");
                return;
            }

            // 4. Превращаем необязательные from и to в точные моменты времени.
            // Поддерживаются дата (2024-08-31) и полная ISO8601-дата со временем.
            Instant from = null;
            Instant to = null;

            try {
                if (fromArgument != null) {
                    if (fromArgument.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        from = LocalDate.parse(fromArgument).atStartOfDay(ZoneOffset.UTC).toInstant();
                    } else {
                        try {
                            from = OffsetDateTime.parse(fromArgument).toInstant();
                        } catch (Exception ignored) {
                            try {
                                from = Instant.parse(fromArgument);
                            } catch (Exception ignoredAgain) {
                                from = LocalDateTime.parse(fromArgument).toInstant(ZoneOffset.UTC);
                            }
                        }
                    }
                }

                if (toArgument != null) {
                    if (toArgument.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        // Если указана только дата, включаем её целиком: до начала следующего дня.
                        to = LocalDate.parse(toArgument).plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();
                    } else {
                        try {
                            to = OffsetDateTime.parse(toArgument).toInstant();
                        } catch (Exception ignored) {
                            try {
                                to = Instant.parse(toArgument);
                            } catch (Exception ignoredAgain) {
                                to = LocalDateTime.parse(toArgument).toInstant(ZoneOffset.UTC);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка: from и to должны быть датами в формате ISO8601.");
                return;
            }

            if (from != null && to != null && !from.isBefore(to)) {
                System.out.println("Ошибка: дата --from должна быть раньше даты --to.");
                return;
            }

            // 5. Готовим список источников: один URL, один файл или файлы по glob-шаблону.
            boolean sourceIsUrl = pathArgument.startsWith("http://") || pathArgument.startsWith("https://");
            List<Path> localFiles = new ArrayList<>();
            List<String> sourceNames = new ArrayList<>();

            if (sourceIsUrl) {
                sourceNames.add(pathArgument);
            } else {
                Path enteredPath = Path.of(pathArgument);
                boolean hasGlob = pathArgument.contains("*") || pathArgument.contains("?")
                    || pathArgument.contains("[") || pathArgument.contains("{");

                if (!hasGlob) {
                    Path file = enteredPath.toAbsolutePath().normalize();
                    if (!Files.isRegularFile(file)) {
                        System.out.println("Ошибка: файл не найден: " + file);
                        return;
                    }
                    localFiles.add(file);
                } else {
                    // Находим часть пути до первого символа glob — оттуда начинаем обход каталогов.
                    Path absolutePatternPath = enteredPath.isAbsolute()
                        ? enteredPath.normalize()
                        : Path.of("").toAbsolutePath().resolve(enteredPath).normalize();
                    String absolutePattern = absolutePatternPath.toString();
                    int firstGlob = absolutePattern.length();
                    for (char globCharacter : new char[]{'*', '?', '[', '{'}) {
                        int position = absolutePattern.indexOf(globCharacter);
                        if (position >= 0 && position < firstGlob) {
                            firstGlob = position;
                        }
                    }

                    int lastSeparator = absolutePattern.substring(0, firstGlob).lastIndexOf(java.io.File.separator);
                    Path searchDirectory = lastSeparator <= 0
                        ? Path.of(java.io.File.separator)
                        : Path.of(absolutePattern.substring(0, lastSeparator));

                    if (!Files.isDirectory(searchDirectory)) {
                        System.out.println("Ошибка: каталог для поиска не найден: " + searchDirectory);
                        return;
                    }

                    PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + absolutePattern);
                    try (Stream<Path> paths = Files.walk(searchDirectory)) {
                        paths.filter(Files::isRegularFile)
                            .map(path -> path.toAbsolutePath().normalize())
                            .filter(pathMatcher::matches)
                            .sorted()
                            .forEach(localFiles::add);
                    }

                    if (localFiles.isEmpty()) {
                        System.out.println("Ошибка: по шаблону не найдено ни одного файла: " + pathArgument);
                        return;
                    }
                }

                for (Path file : localFiles) {
                    sourceNames.add(file.toString());
                }
            }

            // 6. Это типизированное промежуточное представление одной строки NGINX-лога.
            // Локальный record объявлен прямо внутри main, чтобы не создавать отдельный класс.
            record LogRecord(Instant time, String resource, int status, long responseSize) {}

            // Стандартная строка combined-лога NGINX.
            Pattern logPattern = Pattern.compile(
                "^(\\S+)\\s+-\\s+(\\S+)\\s+\\[([^]]+)]\\s+\"([^\"]*)\"\\s+(\\d{3})\\s+(\\d+|-)\\s+\"([^\"]*)\"\\s+\"([^\"]*)\".*$"
            );
            DateTimeFormatter nginxDateFormat = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

            // 7. Переменные для всей статистики. Файлы целиком в память не загружаются.
            long totalRequests = 0;
            long totalResponseSize = 0;
            long skippedLines = 0;
            Map<String, Long> resourceCounts = new HashMap<>();
            Map<Integer, Long> statusCounts = new HashMap<>();
            List<Long> responseSizes = new ArrayList<>(); // Нужен для точного 95-го перцентиля.

            // Один URL представляем одним reader, а локальные файлы открываем по очереди.
            int sourceCount = sourceIsUrl ? 1 : localFiles.size();
            for (int sourceIndex = 0; sourceIndex < sourceCount; sourceIndex++) {
                BufferedReader reader;
                if (sourceIsUrl) {
                    reader = new BufferedReader(new InputStreamReader(
                        URI.create(pathArgument).toURL().openStream(), StandardCharsets.UTF_8
                    ));
                } else {
                    reader = Files.newBufferedReader(localFiles.get(sourceIndex), StandardCharsets.UTF_8);
                }

                // 8. Читаем и обрабатываем лог строго построчно.
                try (reader) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Matcher logMatcher = logPattern.matcher(line);
                        if (!logMatcher.matches()) {
                            skippedLines++;
                            continue;
                        }

                        try {
                            Instant recordTime = ZonedDateTime.parse(logMatcher.group(3), nginxDateFormat).toInstant();
                            String request = logMatcher.group(4);
                            String[] requestParts = request.split("\\s+");
                            String resource = requestParts.length >= 2 ? requestParts[1] : "-";
                            int status = Integer.parseInt(logMatcher.group(5));
                            long responseSize = logMatcher.group(6).equals("-") ? 0 : Long.parseLong(logMatcher.group(6));

                            LogRecord record = new LogRecord(recordTime, resource, status, responseSize);

                            // from включительно, to не включительно. Для одной даты to выше стал следующим днём.
                            if (from != null && record.time().isBefore(from)) {
                                continue;
                            }
                            if (to != null && !record.time().isBefore(to)) {
                                continue;
                            }

                            totalRequests++;
                            totalResponseSize += record.responseSize();
                            resourceCounts.merge(record.resource(), 1L, Long::sum);
                            statusCounts.merge(record.status(), 1L, Long::sum);
                            responseSizes.add(record.responseSize());
                        } catch (Exception e) {
                            // Одна испорченная строка не должна останавливать обработку остальных строк.
                            skippedLines++;
                        }
                    }
                }
            }

            // 9. Считаем среднее и 95-й перцентиль методом ближайшего ранга.
            double averageResponseSize = totalRequests == 0 ? 0.0 : (double) totalResponseSize / totalRequests;
            responseSizes.sort(Long::compareTo);
            long percentile95 = responseSizes.isEmpty()
                ? 0
                : responseSizes.get((int) Math.ceil(responseSizes.size() * 0.95) - 1);

            // Сортируем ресурсы и коды: сначала самые частые, затем по имени/коду.
            List<Map.Entry<String, Long>> sortedResources = new ArrayList<>(resourceCounts.entrySet());
            sortedResources.sort(
                Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                    .thenComparing(Map.Entry.comparingByKey())
            );

            List<Map.Entry<Integer, Long>> sortedStatuses = new ArrayList<>(statusCounts.entrySet());
            sortedStatuses.sort(
                Map.Entry.<Integer, Long>comparingByValue(Comparator.reverseOrder())
                    .thenComparing(Map.Entry.comparingByKey())
            );

            // Человеческие названия основных HTTP-кодов.
            Map<Integer, String> statusNames = Map.ofEntries(
                Map.entry(100, "Continue"), Map.entry(101, "Switching Protocols"),
                Map.entry(200, "OK"), Map.entry(201, "Created"), Map.entry(202, "Accepted"),
                Map.entry(204, "No Content"), Map.entry(206, "Partial Content"),
                Map.entry(301, "Moved Permanently"), Map.entry(302, "Found"),
                Map.entry(304, "Not Modified"), Map.entry(307, "Temporary Redirect"),
                Map.entry(308, "Permanent Redirect"), Map.entry(400, "Bad Request"),
                Map.entry(401, "Unauthorized"), Map.entry(403, "Forbidden"),
                Map.entry(404, "Not Found"), Map.entry(405, "Method Not Allowed"),
                Map.entry(408, "Request Timeout"), Map.entry(409, "Conflict"),
                Map.entry(413, "Content Too Large"), Map.entry(429, "Too Many Requests"),
                Map.entry(500, "Internal Server Error"), Map.entry(501, "Not Implemented"),
                Map.entry(502, "Bad Gateway"), Map.entry(503, "Service Unavailable"),
                Map.entry(504, "Gateway Timeout")
            );

            // 10. Собираем готовый текст отчёта в выбранном формате.
            StringBuilder report = new StringBuilder();
            String filesForReport = String.join(", ", sourceNames);
            String startDateForReport = fromArgument == null ? "-" : fromArgument;
            String endDateForReport = toArgument == null ? "-" : toArgument;

            if (format.equals("markdown")) {
                report.append("#### Общая информация\n\n");
                report.append("| Метрика | Значение |\n");
                report.append("|:--|--:|\n");
                report.append("| Файл(-ы) | `").append(filesForReport.replace("|", "\\|")).append("` |\n");
                report.append("| Начальная дата | ").append(startDateForReport).append(" |\n");
                report.append("| Конечная дата | ").append(endDateForReport).append(" |\n");
                report.append("| Количество запросов | ").append(totalRequests).append(" |\n");
                report.append("| Средний размер ответа | ").append(String.format(Locale.US, "%.2f", averageResponseSize)).append("b |\n");
                report.append("| 95p размера ответа | ").append(percentile95).append("b |\n");
                report.append("| Пропущено некорректных строк | ").append(skippedLines).append(" |\n\n");

                report.append("#### Запрашиваемые ресурсы\n\n");
                report.append("| Ресурс | Количество |\n");
                report.append("|:--|--:|\n");
                if (sortedResources.isEmpty()) {
                    report.append("| - | 0 |\n");
                } else {
                    for (Map.Entry<String, Long> entry : sortedResources) {
                        report.append("| `").append(entry.getKey().replace("|", "\\|"))
                            .append("` | ").append(entry.getValue()).append(" |\n");
                    }
                }

                report.append("\n#### Коды ответа\n\n");
                report.append("| Код | Имя | Количество |\n");
                report.append("|:--:|:--|--:|\n");
                if (sortedStatuses.isEmpty()) {
                    report.append("| - | - | 0 |\n");
                } else {
                    for (Map.Entry<Integer, Long> entry : sortedStatuses) {
                        report.append("| ").append(entry.getKey()).append(" | ")
                            .append(statusNames.getOrDefault(entry.getKey(), "Unknown"))
                            .append(" | ").append(entry.getValue()).append(" |\n");
                    }
                }
            } else {
                report.append("== Общая информация\n\n");
                report.append("[cols=\"2,1\", options=\"header\"]\n|===\n");
                report.append("|Метрика |Значение\n");
                report.append("|Файл(-ы) |").append(filesForReport.replace("|", "\\|")).append("\n");
                report.append("|Начальная дата |").append(startDateForReport).append("\n");
                report.append("|Конечная дата |").append(endDateForReport).append("\n");
                report.append("|Количество запросов |").append(totalRequests).append("\n");
                report.append("|Средний размер ответа |")
                    .append(String.format(Locale.US, "%.2f", averageResponseSize)).append("b\n");
                report.append("|95p размера ответа |").append(percentile95).append("b\n");
                report.append("|Пропущено некорректных строк |").append(skippedLines).append("\n|===\n\n");

                report.append("== Запрашиваемые ресурсы\n\n");
                report.append("[cols=\"3,1\", options=\"header\"]\n|===\n");
                report.append("|Ресурс |Количество\n");
                if (sortedResources.isEmpty()) {
                    report.append("|- |0\n");
                } else {
                    for (Map.Entry<String, Long> entry : sortedResources) {
                        report.append("|").append(entry.getKey().replace("|", "\\|"))
                            .append(" |").append(entry.getValue()).append("\n");
                    }
                }
                report.append("|===\n\n");

                report.append("== Коды ответа\n\n");
                report.append("[cols=\"1,3,1\", options=\"header\"]\n|===\n");
                report.append("|Код |Имя |Количество\n");
                if (sortedStatuses.isEmpty()) {
                    report.append("|- |- |0\n");
                } else {
                    for (Map.Entry<Integer, Long> entry : sortedStatuses) {
                        report.append("|").append(entry.getKey()).append(" |")
                            .append(statusNames.getOrDefault(entry.getKey(), "Unknown"))
                            .append(" |").append(entry.getValue()).append("\n");
                    }
                }
                report.append("|===\n");
            }

            // 11. Финальный результат работы программы — готовый отчёт в консоли.
            System.out.println();
            System.out.println(report);
        } catch (IllegalArgumentException e) {
            // Сюда попадут неправильный URL, путь или glob-шаблон.
            System.out.println("Ошибка во входных данных: " + e.getMessage());
        } catch (Exception e) {
            // Общая понятная ошибка вместо большого аварийного stack trace для пользователя.
            System.out.println("Не удалось обработать логи: " + e.getMessage());
        }
    }
}
