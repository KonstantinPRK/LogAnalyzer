package application.factory;

import application.reporter.AsciiDocReporter;
import application.reporter.Format;
import application.reporter.MarkDownReporter;
import application.reporter.Reporter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class ReporterFactory {
    private final Reporter markDownReporter, asciiDocReporter;


    public ReporterFactory(MarkDownReporter markDownReporter, AsciiDocReporter asciiDocReporter) {
        this.markDownReporter = Objects.requireNonNull(markDownReporter, "markDownReporter");
        this.asciiDocReporter = Objects.requireNonNull(asciiDocReporter, "asciiDocReporter");
    }


    public Reporter create(String reportFormat) {
        return switch (Format.fromString(reportFormat)) {
            case MARKDOWN -> markDownReporter;
            case ADOC -> asciiDocReporter;
        };
    }
}
