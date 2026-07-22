package collection.collectors;

import collection.Collector;
import collection.receiving.loader.output.LogEntry;

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