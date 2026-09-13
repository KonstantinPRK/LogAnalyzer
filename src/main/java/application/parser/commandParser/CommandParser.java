package application.parser.commandParser;

import application.errorhandling.exceptions.CommandParsingException;
import application.reporter.Format;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public final class CommandParser {
    private static final Set<String> SUPPORTED_PARAMETERS = Set.of("--path", "--from", "--to", "--format");


    public Command parse(String[] arguments) {
        if (arguments.length == 0) {
            throw new CommandParsingException("Команда анализа не задана");
        }

        Map<String, String> options = parseOptions(arguments);
        String source = options.get("--path");
        if (Objects.isNull(source) || source.isBlank()) {
            throw new CommandParsingException(
                    "Не задан обязательный параметр --path"
            );
        }

        LocalDate fromDate = parseDate(options.get("--from"), "--from");
        LocalDate toDate = parseDate(options.get("--to"), "--to");
        validateDateRange(fromDate, toDate);

        Format reportFormat = Format.fromString(options.get("--format"));

        return new Command(source.trim(), fromDate, toDate, reportFormat);
    }


    private Map<String, String> parseOptions(String[] arguments) {
        Map<String, String> options = new HashMap<>();

        for (int index = 0; index < arguments.length; index++) {
            String argument = arguments[index];
            int separatorIndex = argument.indexOf('=');
            String parameter = separatorIndex < 0
                    ? argument
                    : argument.substring(0, separatorIndex);

            if (!SUPPORTED_PARAMETERS.contains(parameter)) {
                throw new CommandParsingException("Неизвестный параметр: " + parameter);
            }

            String value;
            if (separatorIndex >= 0) {
                value = argument.substring(separatorIndex + 1);
            } else {
                if (index + 1 >= arguments.length || arguments[index + 1].startsWith("--")) {
                    throw new CommandParsingException("Не задано значение параметра " + parameter);
                }

                value = arguments[++index];
            }

            if (value.isBlank()) {
                throw new CommandParsingException("Не задано значение параметра " + parameter);
            }

            if (Objects.nonNull(options.putIfAbsent(parameter, value))) {
                throw new CommandParsingException("Параметр указан несколько раз: " + parameter);
            }
        }

        return options;
    }


    private LocalDate parseDate(String value, String parameterName) {
        if (Objects.isNull(value)) {
            return null;
        }

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


    private void validateDateRange(LocalDate fromDate, LocalDate toDate) {
        if (Objects.nonNull(fromDate) && Objects.nonNull(toDate) && fromDate.isAfter(toDate)) {
            throw new CommandParsingException(
                    "Дата --from не может быть позднее даты --to"
            );
        }
    }
}
