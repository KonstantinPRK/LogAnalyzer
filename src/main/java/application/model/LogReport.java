package application.model;

import java.time.ZonedDateTime;
import java.util.Map;

public record LogReport(
        long totalRequests,
        Map<String, Long> topResources,
        Map<Integer, Long> topStatuses,
        double averageSize,
        long percentile95,
        ZonedDateTime fromDate,
        ZonedDateTime toDate
) {}