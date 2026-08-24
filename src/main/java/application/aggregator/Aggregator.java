package application.aggregator;

import java.util.Map;

public interface Aggregator {
    void accept(NGINXlog nginxLog);
    Map<String, String> getSummaryMap(Integer percentile);
}
