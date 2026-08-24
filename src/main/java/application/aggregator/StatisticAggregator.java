package application.aggregator;

import application.collector.Collector;
import application.parser.logParser.NGINXlog;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class StatisticAggregator implements Aggregator<NGINXlog> {
    private final Map<String, Collector<NGINXlog, ?>> collectors;


    public StatisticAggregator(Map<String, Collector<NGINXlog, ?>> collectors) {
        if (collectors.isEmpty()) {
            throw new IllegalArgumentException("Коллекторы агрегатора не заданы");
        }

        this.collectors = Collections.unmodifiableMap(new LinkedHashMap<>(collectors));
    }


    @Override
    public void accept(NGINXlog log) {
        collectors.values().forEach(collector -> collector.accept(log));
    }


    @Override
    public Map<String, ?> getResult() {
        Map<String, Object> result = new LinkedHashMap<>();
        collectors.forEach((name, collector) -> result.put(name, collector.getResult()));
        return Collections.unmodifiableMap(result);
    }
}
