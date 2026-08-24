package application.collector;

import application.parser.logParser.NGINXlog;

import java.util.Objects;

public final class AverageSizeCollector implements Collector<NGINXlog, Double> {
    private long totalSize, requestCount;


    @Override
    public void accept(NGINXlog log) {
        Objects.requireNonNull(log, "log");
        totalSize += log.bodyBytesSent();
        requestCount++;
    }


    @Override
    public Double getResult() {
        if (requestCount == 0) {
            return 0.0;
        }

        return (double) totalSize / requestCount;
    }
}
