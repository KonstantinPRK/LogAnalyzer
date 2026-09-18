package application.report;

import java.util.Map;

/**
 * Содержит итоговые показатели анализа логов.
 *
 * @param totalRequests общее количество запросов
 * @param averageResponseSize средний размер ответа в байтах
 * @param responseSizePercentile 95-й перцентиль размера ответа
 * @param resources частота обращения к ресурсам
 * @param statuses частота кодов ответа
 */
public record LogStatistics(
        long totalRequests,
        double averageResponseSize,
        long responseSizePercentile,
        Map<String, Long> resources,
        Map<Integer, Long> statuses
) {
}
