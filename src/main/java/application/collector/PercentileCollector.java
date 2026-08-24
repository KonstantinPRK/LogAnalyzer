package application.collector;

import application.parser.logParser.NGINXlog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class PercentileCollector implements Collector<NGINXlog, Long> {
    private static final double PERCENTILE = 0.95;
    private final List<Long> responseSizes = new ArrayList<>();


    @Override
    public void accept(NGINXlog log) {
        Objects.requireNonNull(log, "log");
        responseSizes.add(log.bodyBytesSent());
    }


    @Override
    public Long getResult() {
        if (responseSizes.isEmpty()) {
            return 0L;
        }

        List<Long> sortedSizes = new ArrayList<>(responseSizes);
        Collections.sort(sortedSizes);

        int index = (int) Math.ceil(PERCENTILE * sortedSizes.size()) - 1;
        return sortedSizes.get(Math.max(index, 0));
    }
}
