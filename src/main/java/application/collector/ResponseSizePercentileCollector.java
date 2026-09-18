package application.collector;

import application.parser.log.NGINXlog;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * Вычисляет 95-й перцентиль размера ответа
 * по частотам встречающихся значений.
 */
public final class ResponseSizePercentileCollector implements Collector<NGINXlog, Long> {
    private static final double PERCENTILE = 0.95;
    private static final Comparator<Map.Entry<Long, LongAdder>> RESPONSE_SIZE_COMPARATOR = Map.Entry.comparingByKey();
    private final ConcurrentMap<Long, LongAdder> responseSizeCounts = new ConcurrentHashMap<>();
    private final LongAdder responseCount = new LongAdder();


    /**
     * Создает пустой коллектор перцентиля размера ответа.
     */
    public ResponseSizePercentileCollector() {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(NGINXlog log) {
        responseSizeCounts
                .computeIfAbsent(log.bodyBytesSent(), ignored -> new LongAdder())
                .increment();
        responseCount.increment();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Long getResult() {
        long totalResponseCount = responseCount.sum();
        if (totalResponseCount == 0) return 0L;

        long percentileRank = calculatePercentileRank(totalResponseCount);
        return findResponseSize(percentileRank);
    }


    /**
     * Определяет порядковый номер значения,
     * соответствующего заданному перцентилю.
     *
     * @param totalResponseCount общее количество ответов
     * @return порядковый номер искомого значения
     */
    private long calculatePercentileRank(long totalResponseCount) {
        return (long) Math.ceil(PERCENTILE * totalResponseCount);
    }


    /**
     * Находит минимальный размер ответа,
     * накопленная частота которого достигает заданного ранга.
     *
     * @param percentileRank порядковый номер искомого значения
     * @return размер ответа для заданного перцентиля
     */
    private long findResponseSize(long percentileRank) {
        List<Map.Entry<Long, LongAdder>> sortedResponseSizeCounts = responseSizeCounts.entrySet().stream()
                .sorted(RESPONSE_SIZE_COMPARATOR)
                .toList();
        long cumulativeCount = 0;

        for (Map.Entry<Long, LongAdder> entry : sortedResponseSizeCounts) {
            cumulativeCount += entry.getValue().sum();
            if (cumulativeCount >= percentileRank) return entry.getKey();
        }

        throw new IllegalStateException("Не удалось вычислить перцентиль");
    }
}
