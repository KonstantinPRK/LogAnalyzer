package application;

import application.core.analysis.LogAnalyzer;
import application.errorhandling.ErrorHandler;
import application.factory.LogAnalyzerFactory;
import application.factory.ReporterFactory;
import application.parser.commandParser.Command;
import application.parser.commandParser.CommandParser;
import application.report.LogReport;
import application.report.LogStatistics;
import application.reporter.Reporter;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.io.PrintStream;

@Component
public final class LogAnalyzerService {
    private final ApplicationArguments arguments;
    private final CommandParser commandParser;
    private final LogAnalyzerFactory logAnalyzerFactory;
    private final ReporterFactory reporterFactory;
    private final ErrorHandler errorHandler;
    private final PrintStream output;


    public LogAnalyzerService(ApplicationArguments arguments, CommandParser commandParser, LogAnalyzerFactory logAnalyzerFactory, ReporterFactory reporterFactory, ErrorHandler errorHandler, @Qualifier("consoleOutput") PrintStream output) {
        this.arguments = arguments;
        this.commandParser = commandParser;
        this.logAnalyzerFactory = logAnalyzerFactory;
        this.reporterFactory = reporterFactory;
        this.errorHandler = errorHandler;
        this.output = output;
    }


    @PostConstruct
    public void start() {
        try {
            Command command = commandParser.parse(arguments.getSourceArgs());
            LogAnalyzer logAnalyzer = logAnalyzerFactory.create(command);
            LogStatistics statistics = logAnalyzer.analyze();
            LogReport report = new LogReport(
                    command.source(),
                    command.fromDate(),
                    command.toDate(),
                    statistics
            );
            Reporter reporter = reporterFactory.create(command.reportFormat());

            output.println(reporter.format(report));
        } catch (Exception exception) {
            output.println(errorHandler.handle(exception));
        } finally {
            output.flush();
        }
    }
}
