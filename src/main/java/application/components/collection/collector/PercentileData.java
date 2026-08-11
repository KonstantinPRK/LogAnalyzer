package application.components.collection.collector;

import application.components.parsing.log.Log;
import application.core.configuration.TableInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

public class PercentileData implements DataCollector {
    private final List<Long> sizes = new ArrayList<>();



    @Override
    public void acceptLog(Log logEntry) {
        sizes.add(Long.valueOf(logEntry.size()));
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