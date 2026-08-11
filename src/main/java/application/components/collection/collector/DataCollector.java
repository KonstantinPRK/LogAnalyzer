package application.components.collection.collector;

import application.components.parsing.log.Log;
import application.core.configuration.TableInfo;

import java.util.stream.Collector;


public interface DataCollector {
    void acceptLog(Log logEntry);
    String[][] getGeneralInfo();
    TableInfo getTable ();
}