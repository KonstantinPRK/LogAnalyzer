package application.collector;

public class CallCollector implements Collector<NGINXlog, Integer> {
    int countCall = 0;
    String name = "collector.CallCollector";

    @Override
    public void accept(NGINXlog log) {
        countCall++;
    }

    @Override
    public String getCollectorName(){
        return name;
    }

    @Override
    public Integer getSummary() {
        return countCall;
    }
}
