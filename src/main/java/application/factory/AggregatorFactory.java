package application.factory;

import application.aggregator.Aggregator;
import application.aggregator.StatisticAggregator;
import application.collector.AverageSizeCollector;
import application.collector.PercentileCollector;
import application.collector.TopResourceCollector;
import application.collector.TopStatusCollector;
import application.collector.TotalRequestCollector;
import application.parser.logParser.NGINXlog;
import application.report.LogStatistics;
import org.springframework.stereotype.Component;

@Component
public final class AggregatorFactory {
    public Aggregator<NGINXlog, LogStatistics> create() {
        return new StatisticAggregator(
                new TotalRequestCollector(),
                new AverageSizeCollector(),
                new PercentileCollector(),
                new TopResourceCollector(),
                new TopStatusCollector()
        );
    }
}
