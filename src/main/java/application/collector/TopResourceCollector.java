package application.collector;

import application.parser.logParser.NGINXlog;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class TopResourceCollector implements Collector<NGINXlog, Map<String, Long>> {
    private final Map<String, Long> resourceCounts = new HashMap<>();


    @Override
    public void accept(NGINXlog log) {
        Objects.requireNonNull(log, "log");
        resourceCounts.merge(log.resource(), 1L, Long::sum);
    }


    @Override
    public Map<String, Long> getResult() {
        Map<String, Long> sortedCounts = new LinkedHashMap<>();
        resourceCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(entry -> sortedCounts.put(entry.getKey(), entry.getValue()));

        return Collections.unmodifiableMap(sortedCounts);
    }
}
