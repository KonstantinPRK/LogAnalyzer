import org.apache.commons.logging.Log;

public class CallCollector implements Collector<NGINXlog, Integer> {
    int countCall = 0;

    @Override
    public void accept(NGINXlog log) {
        countCall++;
    }

    @Override
    public Integer getSummary() {
        return countCall;
    }
}
