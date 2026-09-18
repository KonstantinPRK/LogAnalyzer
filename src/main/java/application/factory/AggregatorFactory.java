package application.factory;

import application.aggregator.Aggregator;
import application.aggregator.StatisticsAggregator;
import application.collector.AverageResponseSizeCollector;
import application.collector.RequestCountCollector;
import application.collector.ResourceFrequencyCollector;
import application.collector.ResponseSizePercentileCollector;
import application.collector.StatusFrequencyCollector;
import application.parser.log.NGINXlog;
import application.report.LogStatistics;
import org.springframework.stereotype.Component;

/**
 * Создает агрегатор и новый набор коллекторов
 * для отдельного анализа.
 */
@Component
public final class AggregatorFactory {
    /**
     * Создает фабрику агрегаторов статистики.
     */
    public AggregatorFactory() {
    }


    /**
     * Создает агрегатор всех поддерживаемых
     * статистических показателей.
     *
     * @return новый агрегатор статистики
     */
    public Aggregator<NGINXlog, LogStatistics> create() {
        return new StatisticsAggregator(
                new RequestCountCollector(),
                new AverageResponseSizeCollector(),
                new ResponseSizePercentileCollector(),
                new ResourceFrequencyCollector(),
                new StatusFrequencyCollector()
        );
    }
}
