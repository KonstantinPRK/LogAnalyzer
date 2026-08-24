package application.aggregator;

import java.util.HashMap;
import java.util.Map;

public class StatisticAggregator implements Aggregator {
    Collector<NGINXlog, Integer> collector = new CallCollector();

    @Override
    public void accept(NGINXlog nginxLog) {
        collector.accept(nginxLog);
    }

    @Override
    public Map<String, String> getSummaryMap(Integer percentile) {
        HashMap<String, String> mapa = new HashMap<>();
        mapa.put(collector.getCollectorName(), collector.getSummary().toString());
        return mapa;
    }
}
