package application.reporter;

import application.http.HttpStatusNameResolver;
import application.report.LogReport;
import application.report.LogStatistics;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

/**
 * Формирует отчет в формате Markdown.
 */
@Component
public final class MarkDownReporter implements Reporter {
    private static final String EMPTY_VALUE = "-";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final HttpStatusNameResolver statusNameResolver;


    /**
     * Создает средство формирования отчетов
     * с преобразователем HTTP-статусов.
     *
     * @param statusNameResolver преобразователь HTTP-статусов в названия
     */
    public MarkDownReporter(HttpStatusNameResolver statusNameResolver) {
        this.statusNameResolver = statusNameResolver;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String format(LogReport report) {
        StringBuilder result = new StringBuilder();
        appendGeneralInformation(result, report);
        appendResources(result, report.statistics());
        appendStatuses(result, report.statistics());
        return result.toString();
    }


    /**
     * Добавляет раздел с параметрами запуска
     * и общими показателями.
     *
     * @param result создаваемый текст отчета
     * @param report модель отчета
     */
    private void appendGeneralInformation(StringBuilder result, LogReport report) {
        LogStatistics statistics = report.statistics();
        String fromDate = Objects.isNull(report.fromDate())
                ? EMPTY_VALUE
                : report.fromDate().format(DATE_FORMATTER);
        String toDate = Objects.isNull(report.toDate())
                ? EMPTY_VALUE
                : report.toDate().format(DATE_FORMATTER);

        result.append("#### Общая информация\n\n")
                .append("| Метрика | Значение |\n")
                .append("|:---------------------:|-------------:|\n")
                .append("| Файл(-ы) | `").append(escape(report.source())).append("` |\n")
                .append("| Начальная дата | ").append(fromDate).append(" |\n")
                .append("| Конечная дата | ").append(toDate).append(" |\n")
                .append("| Количество запросов | ").append(statistics.totalRequests()).append(" |\n")
                .append("| Средний размер ответа | ")
                .append(formatBytes(statistics.averageResponseSize())).append(" |\n")
                .append("| 95p размера ответа | ")
                .append(statistics.responseSizePercentile()).append("b |\n");
    }


    /**
     * Добавляет раздел с частотой запрашиваемых ресурсов.
     *
     * @param result создаваемый текст отчета
     * @param statistics рассчитанная статистика
     */
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


    /**
     * Добавляет раздел с частотой кодов ответа.
     *
     * @param result создаваемый текст отчета
     * @param statistics рассчитанная статистика
     */
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


    /**
     * Округляет количество байтов до двух знаков
     * и добавляет единицу измерения.
     *
     * @param bytes количество байтов
     * @return форматированное значение
     */
    private String formatBytes(double bytes) {
        return BigDecimal.valueOf(bytes)
                .setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString() + "b";
    }


    /**
     * Экранирует управляющие символы Markdown в табличном значении.
     *
     * @param value исходное значение
     * @return безопасное значение для таблицы
     */
    private String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("|", "\\|")
                .replace("\r", " ")
                .replace("\n", " ");
    }
}
