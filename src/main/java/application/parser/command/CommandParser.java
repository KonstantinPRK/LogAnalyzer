package application.parser.command;

import application.errorhandling.exceptions.CommandParsingException;
import application.reporter.Format;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Разбирает аргументы командной строки
 * в параметры запуска анализа.
 */
@Component
public final class CommandParser {
    private static final String PATH = "--path", FROM = "--from", TO = "--to", FORMAT = "--format";


    /**
     * Создает парсер аргументов командной строки.
     */
    public CommandParser() {
    }


    /**
     * Проверяет аргументы и преобразует их в команду анализа.
     *
     * @param arguments аргументы командной строки
     * @return разобранная команда
     */
    public Command parse(String[] arguments) {
        if (arguments.length == 0) throw new CommandParsingException("Команда анализа не задана");

        Map<String, String> options = parseOptions(arguments);

        String source = options.get(PATH);
        if (Objects.isNull(source) || source.isBlank()) {
            throw new CommandParsingException(
                    "Не задан обязательный параметр " + PATH
            );
        }

        LocalDate fromDate = parseDate(options.get(FROM), FROM);
        LocalDate toDate = parseDate(options.get(TO), TO);
        validateDateRange(fromDate, toDate);

        Format reportFormat = Format.fromString(options.get(FORMAT));

        return new Command(source.trim(), fromDate, toDate, reportFormat);
    }


    /**
     * Собирает пары «параметр — значение» из массива аргументов.
     *
     * @param arguments аргументы командной строки
     * @return значения переданных параметров
     */
    private Map<String, String> parseOptions(String[] arguments) {
        Map<String, String> options = new HashMap<>();

        for (int index = 0; index < arguments.length; index++) {
            String parameter = arguments[index];

            if (!isSupported(parameter)) throw new CommandParsingException("Неизвестный параметр: " + parameter);

            if (index + 1 >= arguments.length || arguments[index + 1].startsWith("--")) {
                throw new CommandParsingException("Не задано значение параметра " + parameter);
            }

            String value = arguments[++index];
            if (value.isBlank()) throw new CommandParsingException("Не задано значение параметра " + parameter);

            if (Objects.nonNull(options.putIfAbsent(parameter, value))) {
                throw new CommandParsingException(
                        "Параметр указан несколько раз: " + parameter
                );
            }
        }

        return options;
    }


    /**
     * Проверяет, поддерживается ли параметр анализатором.
     *
     * @param parameter имя параметра
     * @return {@code true}, если параметр поддерживается
     */
    private boolean isSupported(String parameter) {
        return switch (parameter) {
            case PATH, FROM, TO, FORMAT -> true;
            default -> false;
        };
    }


    /**
     * Разбирает необязательную дату в формате ISO-8601.
     *
     * @param value строковое значение даты или {@code null}
     * @param parameterName имя параметра для сообщения об ошибке
     * @return разобранная дата или {@code null}
     */
    private LocalDate parseDate(String value, String parameterName) {
        if (Objects.isNull(value)) return null;

        try {
            return LocalDate.parse(value);

        } catch (DateTimeParseException exception) {
            throw new CommandParsingException(
                    "Некорректная дата " + parameterName + ": " + value
                            + ". Ожидается формат ISO-8601, например 2024-08-31",
                    exception
            );

        }
    }


    /**
     * Проверяет взаимный порядок границ диапазона дат.
     *
     * @param fromDate начальная дата или {@code null}
     * @param toDate конечная дата или {@code null}
     */
    private void validateDateRange(LocalDate fromDate, LocalDate toDate) {
        if (Objects.nonNull(fromDate) && Objects.nonNull(toDate) && fromDate.isAfter(toDate)) {
            throw new CommandParsingException(
                    "Дата " + FROM + " не может быть позднее даты " + TO
            );
        }
    }
}
