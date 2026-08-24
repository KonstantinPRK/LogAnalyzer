package application.parser.logParser;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class NGINXparser implements LogParser {

        // Регулярное выражение для формата NGINX Combined Log
        private static final Pattern LOG_PATTERN = Pattern.compile(
                "^(\\S+) \\S+ \\S+ \\[([^\\]]+)\\] \"(\\S+) (\\S+) \\S+\" (\\d{3}) (\\d+)(?: \"([^\"]*)\" \"([^\"]*)\")?$"
        );

        // Форматтер для даты вида "17/May/2015:08:05:06 +0000"
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
                .ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

        @Override
        public NGINXlog parse(String line) {
            if (line == null || line.isBlank()) {
                return null; // Или выбросить исключение, зависит от ваших требований
            }

            Matcher matcher = LOG_PATTERN.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid log line format: " + line);
            }

            // Извлекаем группы из регулярного выражения
            String ip = matcher.group(1);
            String timestampStr = matcher.group(2);
            String method = matcher.group(3);
            String resource = matcher.group(4);
            int status = Integer.parseInt(matcher.group(5));
            long bodyBytesSent = Long.parseLong(matcher.group(6));
            String referer = matcher.group(7);
            String userAgent = matcher.group(8);

            // Парсим дату с учетом таймзоны
            OffsetDateTime timestamp = OffsetDateTime.parse(timestampStr, DATE_FORMATTER);

            // Возвращаем заполненный record
            return new NGINXlog(
                    ip,
                    timestamp,
                    method,
                    resource,
                    status,
                    bodyBytesSent,
                    referer,
                    userAgent
            );
        }
    }

