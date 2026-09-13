package application.core.analysis;

import application.aggregator.Aggregator;
import application.loader.Loader;
import application.parser.logParser.LogParser;
import application.parser.logParser.NGINXlog;
import application.report.LogStatistics;
import application.validator.DateValidator;

import java.util.Objects;
import java.util.stream.Stream;

public final class LogAnalyzer {
    private final Loader loader;
    private final LogParser<NGINXlog> logParser;
    private final DateValidator dateValidator;
    private final Aggregator<NGINXlog, LogStatistics> aggregator;


    public LogAnalyzer(Loader loader, LogParser<NGINXlog> logParser, DateValidator dateValidator, Aggregator<NGINXlog, LogStatistics> aggregator) {
        this.loader = loader;
        this.logParser = logParser;
        this.dateValidator = dateValidator;
        this.aggregator = aggregator;
    }


    public LogStatistics analyze() {
        try (Stream<String> lines = loader.load()) {
            lines.map(logParser::parse)
                    .filter(Objects::nonNull)
                    .filter(log -> dateValidator.validate(log.timestamp()))
                    .forEach(aggregator::accept);
        }

        return aggregator.getResult();
    }
}
