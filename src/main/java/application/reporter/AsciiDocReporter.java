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
public final class AsciiDocReporter implements Reporter {
    private static final String EMPTY_VALUE = "-";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final HttpStatusNameResolver statusNameResolver;


    public AsciiDocReporter(HttpStatusNameResolver statusNameResolver) {
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
        result.append("=== Общая информация\n\n")
                .append("[cols=\"1,1\", options=\"header\"]\n")
                .append("|===\n")
                .append("|Метрика |Значение\n")
                .append("|Файл(-ы)\n|").append(escape(report.source())).append('\n')
                .append("|Начальная дата\n|").append(formatDate(report.fromDate())).append('\n')
                .append("|Конечная дата\n|").append(formatDate(report.toDate())).append('\n')
                .append("|Количество запросов\n|").append(statistics.totalRequests()).append('\n')
                .append("|Средний размер ответа\n|").append(formatBytes(statistics.averageResponseSize())).append('\n')
                .append("|95p размера ответа\n|").append(statistics.responseSizePercentile()).append("b\n")
                .append("|===\n");
    }


    private void appendResources(StringBuilder result, LogStatistics statistics) {
        result.append("\n=== Запрашиваемые ресурсы\n\n")
                .append("[cols=\"1,1\", options=\"header\"]\n")
                .append("|===\n")
                .append("|Ресурс |Количество\n");

        if (statistics.resources().isEmpty()) {
            result.append('|').append(EMPTY_VALUE).append("\n|0\n");
        } else {
            for (Map.Entry<String, Long> resource : statistics.resources().entrySet()) {
                result.append('|').append(escape(resource.getKey())).append('\n')
                        .append('|').append(resource.getValue()).append('\n');
            }
        }

        result.append("|===\n");
    }


    private void appendStatuses(StringBuilder result, LogStatistics statistics) {
        result.append("\n=== Коды ответа\n\n")
                .append("[cols=\"1,2,1\", options=\"header\"]\n")
                .append("|===\n")
                .append("|Код |Имя |Количество\n");

        if (statistics.statuses().isEmpty()) {
            result.append('|').append(EMPTY_VALUE).append('\n')
                    .append('|').append(EMPTY_VALUE).append("\n|0\n");
        } else {
            for (Map.Entry<Integer, Long> status : statistics.statuses().entrySet()) {
                result.append('|').append(status.getKey()).append('\n')
                        .append('|').append(statusNameResolver.resolve(status.getKey())).append('\n')
                        .append('|').append(status.getValue()).append('\n');
            }
        }

        result.append("|===\n");
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
        return value.replace("|", "\\|")
                .replace("\r", " ")
                .replace("\n", " ");
    }
}
