package application.collector;

import application.parser.logParser.NGINXlog;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

public final class PercentileCollector implements Collector<NGINXlog, Long> {
    private static final double PERCENTILE = 0.95;
    private final SortedMultiset<Long> responseSizes = TreeMultiset.create();


    private long responseCount;


    @Override
    public void accept(NGINXlog log) {
        responseSizes.add(log.bodyBytesSent());
        responseCount++;
    }


    @Override
    public Long getResult() {
        if (responseCount == 0) {
            return 0L;
        }

        long percentileRank = (long) Math.ceil(PERCENTILE * responseCount);
        long cumulativeCount = 0;

        for (Multiset.Entry<Long> entry : responseSizes.entrySet()) {
            cumulativeCount += entry.getCount();
            if (cumulativeCount >= percentileRank) {
                return entry.getElement();
            }
        }

        throw new IllegalStateException("Не удалось вычислить перцентиль");
    }
}
