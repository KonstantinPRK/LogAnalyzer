package application.aggregator;

import application.collector.Collector;
import application.parser.logParser.NGINXlog;
import application.report.LogStatistics;

import java.util.Map;

public final class StatisticAggregator implements Aggregator<NGINXlog, LogStatistics> {
    private final Collector<NGINXlog, Long> totalRequestCollector, percentileCollector;
    private final Collector<NGINXlog, Double> averageSizeCollector;
    private final Collector<NGINXlog, Map<String, Long>> topResourceCollector;
    private final Collector<NGINXlog, Map<Integer, Long>> topStatusCollector;


    public StatisticAggregator(Collector<NGINXlog, Long> totalRequestCollector, Collector<NGINXlog, Double> averageSizeCollector, Collector<NGINXlog, Long> percentileCollector, Collector<NGINXlog, Map<String, Long>> topResourceCollector, Collector<NGINXlog, Map<Integer, Long>> topStatusCollector) {
        this.totalRequestCollector = totalRequestCollector;
        this.averageSizeCollector = averageSizeCollector;
        this.percentileCollector = percentileCollector;
        this.topResourceCollector = topResourceCollector;
        this.topStatusCollector = topStatusCollector;
    }


    @Override
    public void accept(NGINXlog log) {
        totalRequestCollector.accept(log);
        averageSizeCollector.accept(log);
        percentileCollector.accept(log);
        topResourceCollector.accept(log);
        topStatusCollector.accept(log);
    }


    @Override
    public LogStatistics getResult() {
        return new LogStatistics(
                totalRequestCollector.getResult(),
                averageSizeCollector.getResult(),
                percentileCollector.getResult(),
                topResourceCollector.getResult(),
                topStatusCollector.getResult()
        );
    }
}
