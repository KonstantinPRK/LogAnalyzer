package application.analyzer.collector;

import application.model.LogEntry;

public class TotalRequestCollector implements Collector<Long> {
    private long count = 0;

    @Override
    public void accept(LogEntry entry) {
        count++;
    }

    @Override
    public Long getResult() {
        return count;
    }
}