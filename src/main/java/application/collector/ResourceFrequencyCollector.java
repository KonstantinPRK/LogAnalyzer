package application.collector;

import application.parser.log.NGINXlog;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * Подсчитывает частоту запросов к каждому ресурсу.
 */
public final class ResourceFrequencyCollector implements Collector<NGINXlog, Map<String, Long>> {
    private static final Comparator<Map.Entry<String, LongAdder>> RESOURCE_FREQUENCY_COMPARATOR =
            Comparator.<Map.Entry<String, LongAdder>>comparingLong(entry -> entry.getValue().sum())
                    .reversed()
                    .thenComparing(Map.Entry::getKey);
    private final ConcurrentMap<String, LongAdder> resourceCounts = new ConcurrentHashMap<>();


    /**
     * Создает пустой коллектор частоты ресурсов.
     */
    public ResourceFrequencyCollector() {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(NGINXlog log) {
        resourceCounts
                .computeIfAbsent(log.resource(), ignored -> new LongAdder())
                .increment();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Long> getResult() {
        Map<String, Long> sortedCounts = new LinkedHashMap<>();
        resourceCounts.entrySet().stream()
                .sorted(RESOURCE_FREQUENCY_COMPARATOR)
                .forEach(entry -> sortedCounts.put(entry.getKey(), entry.getValue().sum()));

        return Collections.unmodifiableMap(sortedCounts);
    }
}
