package application.core.analysis;

import application.aggregator.Aggregator;
import application.loader.Loader;
import application.parser.log.LogParser;
import application.parser.log.NGINXlog;
import application.report.LogStatistics;
import application.validator.DateValidator;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Загружает, разбирает и фильтрует строки логов,
 * после чего собирает статистику.
 */
public final class LogAnalyzer {
    private final Loader loader;
    private final LogParser<NGINXlog> logParser;
    private final DateValidator dateValidator;
    private final Aggregator<NGINXlog, LogStatistics> aggregator;


    /**
     * Создает анализатор с подготовленными зависимостями
     * для одного запуска.
     *
     * @param loader загрузчик строк логов
     * @param logParser парсер строк логов
     * @param dateValidator фильтр по диапазону дат
     * @param aggregator агрегатор статистики
     */
    public LogAnalyzer(
            Loader loader,
            LogParser<NGINXlog> logParser,
            DateValidator dateValidator,
            Aggregator<NGINXlog, LogStatistics> aggregator
    ) {
        this.loader = loader;
        this.logParser = logParser;
        this.dateValidator = dateValidator;
        this.aggregator = aggregator;
    }


    /**
     * Выполняет анализ всех строк источника.
     *
     * @return собранная статистика
     */
    public LogStatistics analyze() {
        try (Stream<String> lines = loader.load()) {
            lines.map(logParser::parse)
                    .filter(Objects::nonNull)
                    .filter(log -> dateValidator.validate(log.timestamp()))
                    .forEach(aggregator::accept);

        }

        return aggregator.getResult();
    }
}
