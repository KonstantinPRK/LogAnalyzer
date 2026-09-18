package application.aggregator;

import application.collector.Collector;
import application.parser.log.NGINXlog;
import application.report.LogStatistics;

import java.util.Map;

/**
 * Передает записи лога специализированным коллекторам
 * и объединяет их результаты.
 */
public final class StatisticsAggregator implements Aggregator<NGINXlog, LogStatistics> {
    private final Collector<NGINXlog, Long> requestCountCollector, responseSizePercentileCollector;
    private final Collector<NGINXlog, Double> averageResponseSizeCollector;
    private final Collector<NGINXlog, Map<String, Long>> resourceFrequencyCollector;
    private final Collector<NGINXlog, Map<Integer, Long>> statusFrequencyCollector;


    /**
     * Создает агрегатор из коллекторов всех требуемых метрик.
     *
     * @param requestCountCollector коллектор количества запросов
     * @param averageResponseSizeCollector коллектор среднего размера ответа
     * @param responseSizePercentileCollector коллектор перцентиля размера ответа
     * @param resourceFrequencyCollector коллектор частоты запрашиваемых ресурсов
     * @param statusFrequencyCollector коллектор частоты кодов ответа
     */
    public StatisticsAggregator(
            Collector<NGINXlog, Long> requestCountCollector,
            Collector<NGINXlog, Double> averageResponseSizeCollector,
            Collector<NGINXlog, Long> responseSizePercentileCollector,
            Collector<NGINXlog, Map<String, Long>> resourceFrequencyCollector,
            Collector<NGINXlog, Map<Integer, Long>> statusFrequencyCollector
    ) {
        this.requestCountCollector = requestCountCollector;
        this.averageResponseSizeCollector = averageResponseSizeCollector;
        this.responseSizePercentileCollector = responseSizePercentileCollector;
        this.resourceFrequencyCollector = resourceFrequencyCollector;
        this.statusFrequencyCollector = statusFrequencyCollector;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(NGINXlog log) {
        requestCountCollector.accept(log);
        averageResponseSizeCollector.accept(log);
        responseSizePercentileCollector.accept(log);
        resourceFrequencyCollector.accept(log);
        statusFrequencyCollector.accept(log);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public LogStatistics getResult() {
        return new LogStatistics(
                requestCountCollector.getResult(),
                averageResponseSizeCollector.getResult(),
                responseSizePercentileCollector.getResult(),
                resourceFrequencyCollector.getResult(),
                statusFrequencyCollector.getResult()
        );
    }
}
