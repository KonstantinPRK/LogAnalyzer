package application.components.collection.aggregator;

import application.components.collection.collector.DataCollector;
import application.core.configuration.TableInfo;

public class GeneralInfo implements Aggregator{
    @Override
    public void acceptDataCollector(DataCollector collector) {

    }

    @Override
    public TableInfo getTable() {
        return null;
    }
}
