package application.report;

import java.time.LocalDate;

public record LogReport(
        String source,
        LocalDate fromDate,
        LocalDate toDate,
        LogStatistics statistics
) {
}
