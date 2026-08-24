package application.core.analysis;

import application.aggregator.Aggregator;
import application.loader.Loader;
import application.parser.logParser.LogParser;
import application.parser.logParser.NGINXlog;
import application.reporter.Report;
import application.reporter.Reporter;
import application.validator.DateValidator;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public final class LogAnalysisTask implements Callable<Report> {
    private final Loader loader;
    private final LogParser<NGINXlog> logParser;
    private final DateValidator dateValidator;
    private final Aggregator<NGINXlog> aggregator;
    private final Reporter reporter;


    public LogAnalysisTask(Loader loader, LogParser<NGINXlog> logParser, DateValidator dateValidator, Aggregator<NGINXlog> aggregator, Reporter reporter) {
        this.loader = Objects.requireNonNull(loader, "loader");
        this.logParser = Objects.requireNonNull(logParser, "logParser");
        this.dateValidator = Objects.requireNonNull(dateValidator, "dateValidator");
        this.aggregator = Objects.requireNonNull(aggregator, "aggregator");
        this.reporter = Objects.requireNonNull(reporter, "reporter");
    }


    @Override
    public Report call() {
        try (Stream<String> lines = loader.load()) {
            lines.map(logParser::parse)
                    .filter(Objects::nonNull)
                    .filter(log -> dateValidator.validate(log.timestamp()))
                    .forEach(aggregator::accept);
        }

        return reporter.create(aggregator.getResult());
    }
}
