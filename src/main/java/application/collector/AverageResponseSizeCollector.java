package application.collector;

import application.parser.log.NGINXlog;

import java.util.concurrent.atomic.LongAdder;

/**
 * Вычисляет средний размер тела ответа
 * для обработанных записей лога.
 */
public final class AverageResponseSizeCollector implements Collector<NGINXlog, Double> {
    private final LongAdder totalSize = new LongAdder(), requestCount = new LongAdder();


    /**
     * Создает пустой коллектор среднего размера ответа.
     */
    public AverageResponseSizeCollector() {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(NGINXlog log) {
        totalSize.add(log.bodyBytesSent());
        requestCount.increment();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Double getResult() {
        long count = requestCount.sum();
        if (count == 0) return 0.0;

        return (double) totalSize.sum() / count;
    }
}
