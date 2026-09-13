package application.factory;

import application.validator.DateRangeValidator;
import application.validator.DateValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public final class DateValidatorFactory {
    public DateValidator create(LocalDate fromDate, LocalDate toDate) {
        return new DateRangeValidator(fromDate, toDate);
    }
}
