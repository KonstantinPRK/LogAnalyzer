package application.collector;

public interface Collector<LogType, MetricType> {
    void accept(LogType log);
    MetricType getResult();
}
