package application.components.reporting.logReport;


import application.components.collection.collector.DataCollector;
import application.components.reporting.Reporter;

import java.util.Set;


public class LogReporter implements Reporter {
    private String format;

    @Override
    public Report getReport(Set<DataCollector> collectedInfo) {
        return null;
    }
}