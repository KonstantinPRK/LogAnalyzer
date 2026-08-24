public class StatisticAggregator implements Aggregator{
    Collector<NGINXlog, Integer> collector = new CallCollector();

    @Override
    public void accept(NGINXlog nginxLog) {
        collector.accept(nginxLog);
    }
}
