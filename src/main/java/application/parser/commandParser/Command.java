package application.parser.commandParser;

public record Command(
        String source,
        String fromDate,
        String toDate,
        String reportFormat
) {
}
