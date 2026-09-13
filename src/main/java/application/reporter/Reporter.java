package application.reporter;

import application.report.LogReport;

public interface Reporter {
    String format(LogReport report);
}
