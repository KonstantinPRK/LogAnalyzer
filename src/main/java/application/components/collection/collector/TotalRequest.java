package application.components.collection.collector;

import application.components.parsing.log.Log;
import application.core.configuration.TableInfo;

import java.util.stream.Collector;

public class TotalRequest implements DataCollector {
    private long count = 0;


    @Override
    public void acceptLog(Log logEntry) {
        count++;
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