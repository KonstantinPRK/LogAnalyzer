package application.report;

import java.util.Map;

public record LogStatistics(
        long totalRequests,
        double averageResponseSize,
        long responseSizePercentile,
        Map<String, Long> resources,
        Map<Integer, Long> statuses
) {
}
