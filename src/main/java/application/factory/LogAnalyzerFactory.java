package application.factory;

import application.aggregator.Aggregator;
import application.core.analysis.LogAnalyzer;
import application.loader.Loader;
import application.parser.commandParser.Command;
import application.parser.logParser.LogParser;
import application.parser.logParser.NGINXlog;
import application.report.LogStatistics;
import application.validator.DateValidator;
import org.springframework.stereotype.Component;

@Component
public final class LogAnalyzerFactory {
    private final LoaderFactory loaderFactory;
    private final DateValidatorFactory dateValidatorFactory;
    private final AggregatorFactory aggregatorFactory;
    private final LogParser<NGINXlog> logParser;


    public LogAnalyzerFactory(LoaderFactory loaderFactory, DateValidatorFactory dateValidatorFactory, AggregatorFactory aggregatorFactory, LogParser<NGINXlog> logParser) {
        this.loaderFactory = loaderFactory;
        this.dateValidatorFactory = dateValidatorFactory;
        this.aggregatorFactory = aggregatorFactory;
        this.logParser = logParser;
    }


    public LogAnalyzer create(Command command) {
        Loader loader = loaderFactory.create(command.source());
        DateValidator dateValidator = dateValidatorFactory.create(command.fromDate(), command.toDate());
        Aggregator<NGINXlog, LogStatistics> aggregator = aggregatorFactory.create();

        return new LogAnalyzer(
                loader,
                logParser,
                dateValidator,
                aggregator
        );
    }
}
