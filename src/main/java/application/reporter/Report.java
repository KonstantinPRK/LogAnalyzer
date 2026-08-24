package application.reporter;

import java.util.Objects;

public record Report(String formattedReport) {
    public Report {
        Objects.requireNonNull(formattedReport, "formattedReport");
    }
}
