package application.collector;

import application.parser.logParser.NGINXlog;

public final class TotalRequestCollector implements Collector<NGINXlog, Long> {
    private long requestCount;


    @Override
    public void accept(NGINXlog log) {
        requestCount++;
    }


    @Override
    public Long getResult() {
        return requestCount;
    }
}
