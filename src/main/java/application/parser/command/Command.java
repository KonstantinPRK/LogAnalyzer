package application.parser.command;

import application.reporter.Format;

import java.time.LocalDate;

/**
 * Содержит разобранные параметры одного запуска анализатора.
 *
 * @param source путь, glob-шаблон или URL источника логов
 * @param fromDate начальная дата анализа включительно
 * @param toDate конечная дата анализа включительно
 * @param reportFormat формат итогового отчёта
 */
public record Command(
        String source,
        LocalDate fromDate,
        LocalDate toDate,
        Format reportFormat
) {
}
