package application.core.control;

public record UserParameters (
        String source,
        String fromDate,
        String toDate,
        String reportFormat
) {}
