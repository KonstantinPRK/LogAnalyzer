package application.validator;

import java.time.OffsetDateTime;

public class SomeDateChecker implements DateChecker {
    OffsetDateTime fromDate, toDate;

    public SomeDateChecker(OffsetDateTime fromDate, OffsetDateTime toDate){
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public Boolean validate(OffsetDateTime timestamp) {
        boolean isAfterOrEqualFrom = (fromDate == null) || timestamp.isAfter(fromDate) || timestamp.isEqual(fromDate);
        boolean isBeforeOrEqualTo = (toDate == null) || timestamp.isBefore(toDate) || timestamp.isEqual(toDate);

        return isAfterOrEqualFrom && isBeforeOrEqualTo;
    }
}
