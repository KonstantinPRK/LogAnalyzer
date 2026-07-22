package collection.collectors;

import collection.Collector;
import collection.receiving.loader.output.LogEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PercentileCollector implements Collector<Long> {
    private final List<Long> sizes = new ArrayList<>();

    @Override
    public void accept(LogEntry entry) {
        sizes.add(entry.size());
    }

    @Override
    public Long getResult() {
        if (sizes.isEmpty()) {
            return 0L;
        }

        List<Long> sorted = new ArrayList<>(sizes);
        Collections.sort(sorted);
        int index = (int) Math.ceil(0.95 * sorted.size()) - 1;


        if (index < 0) index = 0;
        if (index >= sorted.size()) index = sorted.size() - 1;
        return sorted.get(index);
    }
}