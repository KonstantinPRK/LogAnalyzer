package application.collector;

import application.parser.log.NGINXlog;

import java.util.concurrent.atomic.LongAdder;

/**
 * Подсчитывает общее количество обработанных запросов.
 */
public final class RequestCountCollector implements Collector<NGINXlog, Long> {
    private final LongAdder requestCount = new LongAdder();


    /**
     * Создает пустой счетчик запросов.
     */
    public RequestCountCollector() {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(NGINXlog log) {
        requestCount.increment();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Long getResult() {
        return requestCount.sum();
    }
}
