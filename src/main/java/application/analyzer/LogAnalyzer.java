package application.analyzer;

import application.analyzer.collector.*;
import application.model.LogEntry;
import application.model.LogReport;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.stream.Stream;

public class LogAnalyzer implements Analyzer {

    private final ZonedDateTime from;
    private final ZonedDateTime to;

    public LogAnalyzer(ZonedDateTime from, ZonedDateTime to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public LogReport analyze(Stream<LogEntry> entries) {
        Collector<Long> totalReq = new TotalRequestCollector();
        Collector<Map<String, Long>> topRes = new TopResourceCollector();
        Collector<Map<Integer, Long>> topStatus = new TopStatusCollector();
        Collector<Double> avgSize = new AverageSizeCollector();
        Collector<Long> percentile = new PercentileCollector();


        entries.forEach(entry -> {
            totalReq.accept(entry);
            topRes.accept(entry);
            topStatus.accept(entry);
            avgSize.accept(entry);
            percentile.accept(entry);
        });


        long totalRequests = totalReq.getResult();
        Map<String, Long> topResources = topRes.getResult();
        Map<Integer, Long> topStatuses = topStatus.getResult();
        double averageSize = avgSize.getResult();
        long percentileValue = percentile.getResult();

        return new LogReport(
                totalRequests,
                topResources,
                topStatuses,
                averageSize,
                percentileValue,
                from,
                to
        );
    }
}
