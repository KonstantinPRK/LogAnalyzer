package application.factory;

import application.validator.DateRangeValidator;
import application.validator.DateValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Создает валидаторы дат для параметров отдельной команды.
 */
@Component
public final class DateValidatorFactory {
    /**
     * Создает фабрику валидаторов дат.
     */
    public DateValidatorFactory() {
    }


    /**
     * Создает валидатор указанного диапазона дат.
     *
     * @param fromDate начальная дата включительно или {@code null}
     * @param toDate конечная дата включительно или {@code null}
     * @return валидатор диапазона дат
     */
    public DateValidator create(LocalDate fromDate, LocalDate toDate) {
        return new DateRangeValidator(fromDate, toDate);
    }
}
