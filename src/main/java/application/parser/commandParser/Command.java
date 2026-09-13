package application.parser.commandParser;

import application.reporter.Format;

import java.time.LocalDate;

public record Command(
        String source,
        LocalDate fromDate,
        LocalDate toDate,
        Format reportFormat
) {
}
