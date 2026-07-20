package application.analyzer;

import application.model.LogEntry;
import application.model.LogReport;
import java.util.stream.Stream;

public interface Analyzer {
    LogReport analyze(Stream<LogEntry> entries);
}