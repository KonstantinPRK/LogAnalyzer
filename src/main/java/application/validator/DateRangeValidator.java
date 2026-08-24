package application.validator;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;

public final class DateRangeValidator implements DateValidator {
    private final LocalDate fromDate, toDate;


    public DateRangeValidator(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }


    @Override
    public boolean validate(OffsetDateTime timestamp) {
        Objects.requireNonNull(timestamp, "timestamp");
        LocalDate logDate = timestamp.toLocalDate();
        boolean isAfterOrEqualFrom = Objects.isNull(fromDate) || !logDate.isBefore(fromDate);
        boolean isBeforeOrEqualTo = Objects.isNull(toDate) || !logDate.isAfter(toDate);

        return isAfterOrEqualFrom && isBeforeOrEqualTo;
    }
}
