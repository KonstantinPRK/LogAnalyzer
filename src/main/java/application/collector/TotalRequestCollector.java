package application.collector;

import application.parser.logParser.NGINXlog;

import java.util.Objects;

public final class TotalRequestCollector implements Collector<NGINXlog, Long> {
    private long requestCount;


    @Override
    public void accept(NGINXlog log) {
        Objects.requireNonNull(log, "log");
        requestCount++;
    }


    @Override
    public Long getResult() {
        return requestCount;
    }
}
