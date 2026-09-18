package application.report;

import java.time.LocalDate;

/**
 * Объединяет параметры анализа и рассчитанную статистику
 * для формирования отчёта.
 *
 * @param source исходное описание источника логов
 * @param fromDate начальная дата анализа включительно
 * @param toDate конечная дата анализа включительно
 * @param statistics рассчитанная статистика
 */
public record LogReport(
        String source,
        LocalDate fromDate,
        LocalDate toDate,
        LogStatistics statistics
) {
}
