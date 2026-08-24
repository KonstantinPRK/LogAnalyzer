import org.apache.commons.logging.Log;

public interface Collector<inputLogType, outputMetricType> {
    public void accept(inputLogType log);
    public outputMetricType getSummary();
}
