package application.collector;

public class NGINXcollector {
    NGINXcollector callCollector = new NGINXcollector();

    public void accept(NGINXlog log) {
        callCollector.accept(log);
    }
}
