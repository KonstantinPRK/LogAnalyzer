package application.components.reporting;

import application.components.reporting.logReport.Report;

import java.util.Set;
import application.components.collection.collector.DataCollector;

public interface Reporter {
    public Report getReport(Set<DataCollector> collectedInfo);
}
