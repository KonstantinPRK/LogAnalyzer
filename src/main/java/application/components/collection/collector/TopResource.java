package application.components.collection.collector;

import application.components.parsing.log.Log;
import application.core.configuration.TableInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;

public class TopResource implements DataCollector {
    private final Map<String, Long> resourceCounts = new HashMap<>();


    @Override
    public void acceptLog(Log logEntry) {
        //String uri = logEntry.uri();
        //resourceCounts.merge(uri, 1L, Long::sum);
    }

    @Override
    public String[][] getGeneralInfo() {
        return new String[0][];
    }

    @Override
    public TableInfo getTable() {
        return null;
    }
}