package application.factory;

import application.reporter.AsciiDocReporter;
import application.reporter.Format;
import application.reporter.MarkDownReporter;
import application.reporter.Reporter;
import org.springframework.stereotype.Component;

/**
 * Выбирает средство формирования отчета требуемого формата.
 */
@Component
public final class ReporterFactory {
    private final Reporter markDownReporter, asciiDocReporter;


    /**
     * Создает фабрику из доступных средств формирования отчетов.
     *
     * @param markDownReporter средство формирования Markdown-отчетов
     * @param asciiDocReporter средство формирования AsciiDoc-отчетов
     */
    public ReporterFactory(
            MarkDownReporter markDownReporter,
            AsciiDocReporter asciiDocReporter
    ) {
        this.markDownReporter = markDownReporter;
        this.asciiDocReporter = asciiDocReporter;
    }


    /**
     * Возвращает средство формирования указанного формата.
     *
     * @param reportFormat формат отчета
     * @return соответствующее средство формирования отчета
     */
    public Reporter create(Format reportFormat) {
        return switch (reportFormat) {
            case MARKDOWN -> markDownReporter;
            case ADOC -> asciiDocReporter;
        };
    }
}
