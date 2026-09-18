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
 * Подсчитывает частоту каждого кода ответа сервера.
 */
public final class StatusFrequencyCollector implements Collector<NGINXlog, Map<Integer, Long>> {
    private static final Comparator<Map.Entry<Integer, LongAdder>> STATUS_FREQUENCY_COMPARATOR =
            Comparator.<Map.Entry<Integer, LongAdder>>comparingLong(entry -> entry.getValue().sum())
                    .reversed()
                    .thenComparing(Map.Entry::getKey);
    private final ConcurrentMap<Integer, LongAdder> statusCounts = new ConcurrentHashMap<>();


    /**
     * Создает пустой коллектор частоты HTTP-статусов.
     */
    public StatusFrequencyCollector() {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(NGINXlog log) {
        statusCounts
                .computeIfAbsent(log.status(), ignored -> new LongAdder())
                .increment();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, Long> getResult() {
        Map<Integer, Long> sortedCounts = new LinkedHashMap<>();
        statusCounts.entrySet().stream()
                .sorted(STATUS_FREQUENCY_COMPARATOR)
                .forEach(entry -> sortedCounts.put(entry.getKey(), entry.getValue().sum()));

        return Collections.unmodifiableMap(sortedCounts);
    }
}
