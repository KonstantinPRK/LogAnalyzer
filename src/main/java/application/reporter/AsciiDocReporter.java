package application.reporter;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public final class AsciiDocReporter implements Reporter {
    private static final String EMPTY_VALUE = "-";


    @Override
    public Report create(Map<String, ?> statistics) {
        StringBuilder report = new StringBuilder();
        appendScalarMetrics(report, statistics);
        appendMapMetrics(report, statistics);
        return new Report(report.toString());
    }


    private void appendScalarMetrics(StringBuilder report, Map<String, ?> statistics) {
        report.append("=== Общая информация\n\n")
                .append("[cols=\"1,1\", options=\"header\"]\n")
                .append("|===\n")
                .append("|Метрика |Значение\n");

        boolean hasScalarMetrics = false;
        for (Map.Entry<String, ?> entry : statistics.entrySet()) {
            if (entry.getValue() instanceof Map<?, ?>) {
                continue;
            }

            hasScalarMetrics = true;
            report.append('|').append(escape(entry.getKey()))
                    .append("\n|").append(escape(valueOf(entry.getValue())))
                    .append('\n');
        }

        if (!hasScalarMetrics) {
            report.append('|').append(EMPTY_VALUE).append("\n|").append(EMPTY_VALUE).append('\n');
        }

        report.append("|===\n");
    }


    private void appendMapMetrics(StringBuilder report, Map<String, ?> statistics) {
        for (Map.Entry<String, ?> metric : statistics.entrySet()) {
            if (!(metric.getValue() instanceof Map<?, ?> values)) {
                continue;
            }

            report.append("\n=== ").append(escape(metric.getKey())).append("\n\n")
                    .append("[cols=\"1,1\", options=\"header\"]\n")
                    .append("|===\n")
                    .append("|Значение |Количество\n");

            if (values.isEmpty()) {
                report.append('|').append(EMPTY_VALUE).append("\n|0\n");
            } else {
                for (Map.Entry<?, ?> value : values.entrySet()) {
                    report.append('|').append(escape(valueOf(value.getKey())))
                            .append("\n|").append(escape(valueOf(value.getValue())))
                            .append('\n');
                }
            }

            report.append("|===\n");
        }
    }


    private String valueOf(Object value) {
        return Objects.isNull(value) ? EMPTY_VALUE : String.valueOf(value);
    }


    private String escape(String value) {
        return value.replace("|", "\\|")
                .replace("\r", " ")
                .replace("\n", " ");
    }
}
