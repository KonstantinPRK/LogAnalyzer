package application.factory;

import application.aggregator.Aggregator;
import application.core.analysis.LogAnalysisTask;
import application.errorhandling.exceptions.CommandParsingException;
import application.loader.Loader;
import application.parser.commandParser.Command;
import application.parser.logParser.LogParser;
import application.parser.logParser.NGINXlog;
import application.reporter.Reporter;
import application.validator.DateValidator;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class LogAnalysisTaskFactory {
    private final LoaderFactory loaderFactory;
    private final DateValidatorFactory dateValidatorFactory;
    private final ReporterFactory reporterFactory;
    private final AggregatorFactory aggregatorFactory;
    private final LogParser<NGINXlog> logParser;


    public LogAnalysisTaskFactory(LoaderFactory loaderFactory, DateValidatorFactory dateValidatorFactory, ReporterFactory reporterFactory, AggregatorFactory aggregatorFactory, LogParser<NGINXlog> logParser) {
        this.loaderFactory = loaderFactory;
        this.dateValidatorFactory = dateValidatorFactory;
        this.reporterFactory = reporterFactory;
        this.aggregatorFactory = aggregatorFactory;
        this.logParser = logParser;
    }


    public LogAnalysisTask create(Command command) {
        if (Objects.isNull(command)) {
            throw new CommandParsingException("Команда анализа не задана");
        }

        Loader loader = loaderFactory.create(command.source());
        DateValidator dateValidator = dateValidatorFactory.create(command.fromDate(), command.toDate());
        Reporter reporter = reporterFactory.create(command.reportFormat());
        Aggregator<NGINXlog> aggregator = aggregatorFactory.create();

        return new LogAnalysisTask(
                loader,
                logParser,
                dateValidator,
                aggregator,
                reporter
        );
    }
}
