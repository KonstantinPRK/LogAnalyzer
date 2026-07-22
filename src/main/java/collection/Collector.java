package collection;

import editing.parsers.log.Log;

public interface Collector<MetricType> {
    void accept(Log entry);
    MetricType getResult();
}