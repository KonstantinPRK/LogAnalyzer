package application.analyzer.collector;

import application.model.LogEntry;

public class AverageSizeCollector implements Collector<Double> {
    private long sum = 0;
    private long count = 0;

    @Override
    public void accept(LogEntry entry) {
        sum += entry.size();
        count++;
    }

    @Override
    public Double getResult() {
        if (count == 0) {
            return 0.0;
        }
        return (double) sum / count;
    }
}