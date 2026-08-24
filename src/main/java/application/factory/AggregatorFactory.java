package application.factory;

import application.aggregator.Aggregator;
import application.aggregator.StatisticAggregator;
import application.collector.AverageSizeCollector;
import application.collector.Collector;
import application.collector.PercentileCollector;
import application.collector.TopResourceCollector;
import application.collector.TopStatusCollector;
import application.collector.TotalRequestCollector;
import application.parser.logParser.NGINXlog;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public final class AggregatorFactory {
    public Aggregator<NGINXlog> create() {
        Map<String, Collector<NGINXlog, ?>> collectors = new LinkedHashMap<>();
        collectors.put("Количество запросов", new TotalRequestCollector());
        collectors.put("Средний размер ответа", new AverageSizeCollector());
        collectors.put("95p размера ответа", new PercentileCollector());
        collectors.put("Запрашиваемые ресурсы", new TopResourceCollector());
        collectors.put("Коды ответа", new TopStatusCollector());

        return new StatisticAggregator(collectors);
    }
}
