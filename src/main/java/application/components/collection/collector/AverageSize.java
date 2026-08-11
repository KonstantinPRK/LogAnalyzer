package application.components.collection.collector;

import application.components.parsing.log.Log;
import application.core.configuration.TableInfo;

import java.util.List;
import java.util.stream.Collector;


public class AverageSize implements DataCollector {
    private long sum = 0;
    private long count = 0;


    @Override
    public void acceptLog(Log logEntry) {
        sum += Long.parseLong(logEntry.size());
        count++;
    }

    @Override
    public String[][] getGeneralInfo() {
    return null;
    }

    @Override
    public TableInfo getTable() {
        return null;
    }
}