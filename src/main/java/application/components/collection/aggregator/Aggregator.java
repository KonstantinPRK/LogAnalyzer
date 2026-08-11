package application.components.collection.aggregator;

import application.components.collection.collector.DataCollector;
import application.components.parsing.log.Log;
import application.core.configuration.TableInfo;

public interface Aggregator {
    void acceptDataCollector(DataCollector collector);
    TableInfo getTable ();
}
