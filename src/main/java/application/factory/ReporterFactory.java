package application.factory;

import application.reporter.AsciiDocReporter;
import application.reporter.Format;
import application.reporter.MarkDownReporter;
import application.reporter.Reporter;
import org.springframework.stereotype.Component;

@Component
public final class ReporterFactory {
    private final Reporter markDownReporter, asciiDocReporter;


    public ReporterFactory(MarkDownReporter markDownReporter, AsciiDocReporter asciiDocReporter) {
        this.markDownReporter = markDownReporter;
        this.asciiDocReporter = asciiDocReporter;
    }


    public Reporter create(Format reportFormat) {
        return switch (reportFormat) {
            case MARKDOWN -> markDownReporter;
            case ADOC -> asciiDocReporter;
        };
    }
}
