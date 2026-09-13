package application.reporter;

import application.http.HttpStatusNameResolver;
import application.report.LogReport;
import application.report.LogStatistics;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@Component
public final class MarkDownReporter implements Reporter {
    private static final String EMPTY_VALUE = "-";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final HttpStatusNameResolver statusNameResolver;


    public MarkDownReporter(HttpStatusNameResolver statusNameResolver) {
        this.statusNameResolver = statusNameResolver;
    }


    @Override
    public String format(LogReport report) {
        StringBuilder result = new StringBuilder();
        appendGeneralInformation(result, report);
        appendResources(result, report.statistics());
        appendStatuses(result, report.statistics());
        return result.toString();
    }


    private void appendGeneralInformation(StringBuilder result, LogReport report) {
        LogStatistics statistics = report.statistics();
        result.append("#### Общая информация\n\n")
                .append("| Метрика | Значение |\n")
                .append("|:---------------------:|-------------:|\n")
                .append("| Файл(-ы) | `").append(escape(report.source())).append("` |\n")
                .append("| Начальная дата | ").append(formatDate(report.fromDate())).append(" |\n")
                .append("| Конечная дата | ").append(formatDate(report.toDate())).append(" |\n")
                .append("| Количество запросов | ").append(statistics.totalRequests()).append(" |\n")
                .append("| Средний размер ответа | ").append(formatBytes(statistics.averageResponseSize())).append(" |\n")
                .append("| 95p размера ответа | ").append(statistics.responseSizePercentile()).append("b |\n");
    }


    private void appendResources(StringBuilder result, LogStatistics statistics) {
        result.append("\n#### Запрашиваемые ресурсы\n\n")
                .append("| Ресурс | Количество |\n")
                .append("|:---------------:|-----------:|\n");

        if (statistics.resources().isEmpty()) {
            result.append("| ").append(EMPTY_VALUE).append(" | 0 |\n");
            return;
        }

        for (Map.Entry<String, Long> resource : statistics.resources().entrySet()) {
            result.append("| `").append(escape(resource.getKey())).append("` | ")
                    .append(resource.getValue()).append(" |\n");
        }
    }


    private void appendStatuses(StringBuilder result, LogStatistics statistics) {
        result.append("\n#### Коды ответа\n\n")
                .append("| Код | Имя | Количество |\n")
                .append("|:---:|:---------------------:|-----------:|\n");

        if (statistics.statuses().isEmpty()) {
            result.append("| ").append(EMPTY_VALUE).append(" | ")
                    .append(EMPTY_VALUE).append(" | 0 |\n");
            return;
        }

        for (Map.Entry<Integer, Long> status : statistics.statuses().entrySet()) {
            result.append("| ").append(status.getKey()).append(" | ")
                    .append(statusNameResolver.resolve(status.getKey())).append(" | ")
                    .append(status.getValue()).append(" |\n");
        }
    }


    private String formatDate(LocalDate date) {
        return Objects.isNull(date) ? EMPTY_VALUE : date.format(DATE_FORMATTER);
    }


    private String formatBytes(double bytes) {
        return BigDecimal.valueOf(bytes)
                .setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString() + "b";
    }


    private String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("|", "\\|")
                .replace("\r", " ")
                .replace("\n", " ");
    }
}
