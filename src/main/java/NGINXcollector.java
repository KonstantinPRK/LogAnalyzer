import org.apache.commons.logging.Log;

public class NGINXcollector {
    NGINXcollector callCollector = new CallCollector();

    public void accept(NGINXlog log) {
        callCollector.accept(log);
    }
}
