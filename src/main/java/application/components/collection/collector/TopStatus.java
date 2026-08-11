package application.components.collection.collector;

import application.components.parsing.log.Log;
import application.core.configuration.TableInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;

public class TopStatus implements DataCollector {
    private final Map<Integer, Long> statusCounts = new HashMap<>();



    @Override
    public void acceptLog(Log logEntry) {
       // int status = entry.status();
        //statusCounts.merge(status, 1L, Long::sum);
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