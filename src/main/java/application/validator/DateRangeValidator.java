package application.validator;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Проверяет, входит ли дата записи
 * в заданный включительный диапазон.
 */
public final class DateRangeValidator implements DateValidator {
    private final LocalDate fromDate, toDate;


    /**
     * Создаёт проверку диапазона дат.
     *
     * @param fromDate начальная дата включительно либо {@code null}
     * @param toDate конечная дата включительно либо {@code null}
     */
    public DateRangeValidator(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(OffsetDateTime timestamp) {
        LocalDate logDate = timestamp.toLocalDate();
        boolean isAfterOrEqualFrom = Objects.isNull(fromDate) || !logDate.isBefore(fromDate);
        boolean isBeforeOrEqualTo = Objects.isNull(toDate) || !logDate.isAfter(toDate);

        return isAfterOrEqualFrom && isBeforeOrEqualTo;
    }
}
