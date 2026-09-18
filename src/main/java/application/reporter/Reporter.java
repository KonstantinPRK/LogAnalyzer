package application.reporter;

import application.report.LogReport;

/**
 * Формирует текстовое представление отчёта.
 */
public interface Reporter {
    /**
     * Преобразует отчёт в строку выбранного формата.
     *
     * @param report отчёт с параметрами анализа и статистикой
     * @return форматированный текст
     */
    String format(LogReport report);
}
