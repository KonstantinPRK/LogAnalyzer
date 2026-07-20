package application.analyzer.collector;

import application.model.LogEntry;

public interface Collector<MetricType> {
    void accept(LogEntry entry);
    MetricType getResult();
}