package application.factory;

import application.errorhandling.exceptions.CommandParsingException;
import application.validator.DateRangeValidator;
import application.validator.DateValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Component
public final class DateValidatorFactory {
    public DateValidator create(String fromDate, String toDate) {
        LocalDate from = parseDate(fromDate, "--from");
        LocalDate to = parseDate(toDate, "--to");

        if (Objects.nonNull(from) && Objects.nonNull(to) && from.isAfter(to)) {
            throw new CommandParsingException(
                    "Дата --from не может быть позднее даты --to"
            );
        }

        return new DateRangeValidator(from, to);
    }


    private LocalDate parseDate(String value, String parameterName) {
        if (Objects.isNull(value) || value.isBlank()) {
            return null;
        }

        try {
            return LocalDate.parse(value.trim());
        } catch (DateTimeParseException exception) {
            throw new CommandParsingException(
                    "Некорректная дата " + parameterName + ": " + value
                            + ". Ожидается формат ISO-8601, например 2024-08-31",
                    exception
            );
        }
    }
}
