package application.analyzer.collector;

import application.model.LogEntry;
import java.util.HashMap;
import java.util.Map;

public class TopStatusCollector implements Collector<Map<Integer, Long>> {
    private final Map<Integer, Long> statusCounts = new HashMap<>();

    @Override
    public void accept(LogEntry entry) {
        int status = entry.status();
        statusCounts.merge(status, 1L, Long::sum);
    }

    @Override
    public Map<Integer, Long> getResult() {
        return new HashMap<>(statusCounts);
    }
}