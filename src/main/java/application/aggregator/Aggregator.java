package application.aggregator;

import java.util.Map;

public interface Aggregator<LogType> {
    void accept(LogType log);
    Map<String, ?> getResult();
}
