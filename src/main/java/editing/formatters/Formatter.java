package editing.formatters;

import collection.outputting.reporter.LogReport;

public interface Formatter {
    String format(LogReport report);
}