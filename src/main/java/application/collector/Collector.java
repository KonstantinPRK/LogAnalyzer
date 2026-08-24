package application.collector;

public interface Collector<inputLogType, outputMetricType> {
    public void accept(inputLogType log);
    public outputMetricType getSummary();
    public String getCollectorName();
}
