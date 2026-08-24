package application.collector;

import application.parser.logParser.NGINXlog;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class TopStatusCollector implements Collector<NGINXlog, Map<Integer, Long>> {
    private final Map<Integer, Long> statusCounts = new HashMap<>();


    @Override
    public void accept(NGINXlog log) {
        statusCounts.merge(log.status(), 1L, Long::sum);
    }


    @Override
    public Map<Integer, Long> getResult() {
        Map<Integer, Long> sortedCounts = new LinkedHashMap<>();
        statusCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue()
                        .reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(entry -> sortedCounts.put(entry.getKey(), entry.getValue()));

        return Collections.unmodifiableMap(sortedCounts);
    }
}
