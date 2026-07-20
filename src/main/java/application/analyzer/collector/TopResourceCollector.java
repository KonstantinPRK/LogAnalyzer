package application.analyzer.collector;

import application.model.LogEntry;
import java.util.HashMap;
import java.util.Map;

public class TopResourceCollector implements Collector<Map<String, Long>> {
    private final Map<String, Long> resourceCounts = new HashMap<>();

    @Override
    public void accept(LogEntry entry) {
        String uri = entry.uri();
        resourceCounts.merge(uri, 1L, Long::sum);
    }

    @Override
    public Map<String, Long> getResult() {
        return new HashMap<>(resourceCounts);
    }
}