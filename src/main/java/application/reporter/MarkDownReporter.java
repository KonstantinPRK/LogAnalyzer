package application.reporter;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public final class MarkDownReporter implements Reporter {
    private static final String EMPTY_VALUE = "-";


    @Override
    public Report create(Map<String, ?> statistics) {
        Objects.requireNonNull(statistics, "statistics");
        StringBuilder report = new StringBuilder();
        appendScalarMetrics(report, statistics);
        appendMapMetrics(report, statistics);
        return new Report(report.toString());
    }


    private void appendScalarMetrics(StringBuilder report, Map<String, ?> statistics) {
        report.append("#### Общая информация\n\n")
                .append("| Метрика | Значение |\n")
                .append("| :--- | ---: |\n");

        boolean hasScalarMetrics = false;
        for (Map.Entry<String, ?> entry : statistics.entrySet()) {
            if (entry.getValue() instanceof Map<?, ?>) {
                continue;
            }

            hasScalarMetrics = true;
            report.append("| ")
                    .append(escape(entry.getKey()))
                    .append(" | ")
                    .append(escape(valueOf(entry.getValue())))
                    .append(" |\n");
        }

        if (!hasScalarMetrics) {
            report.append("| ").append(EMPTY_VALUE).append(" | ").append(EMPTY_VALUE).append(" |\n");
        }
    }


    private void appendMapMetrics(StringBuilder report, Map<String, ?> statistics) {
        for (Map.Entry<String, ?> metric : statistics.entrySet()) {
            if (!(metric.getValue() instanceof Map<?, ?> values)) {
                continue;
            }

            report.append("\n#### ")
                    .append(escape(metric.getKey()))
                    .append("\n\n")
                    .append("| Значение | Количество |\n")
                    .append("| :--- | ---: |\n");

            if (values.isEmpty()) {
                report.append("| ").append(EMPTY_VALUE).append(" | 0 |\n");
                continue;
            }

            for (Map.Entry<?, ?> value : values.entrySet()) {
                report.append("| ")
                        .append(escape(valueOf(value.getKey())))
                        .append(" | ")
                        .append(escape(valueOf(value.getValue())))
                        .append(" |\n");
            }
        }
    }


    private String valueOf(Object value) {
        return Objects.isNull(value) ? EMPTY_VALUE : String.valueOf(value);
    }


    private String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("|", "\\|")
                .replace("\r", " ")
                .replace("\n", " ");
    }
}
