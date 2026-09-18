package application.factory;

import application.aggregator.Aggregator;
import application.core.analysis.LogAnalyzer;
import application.loader.Loader;
import application.parser.command.Command;
import application.parser.log.LogParser;
import application.parser.log.NGINXlog;
import application.report.LogStatistics;
import application.validator.DateValidator;
import org.springframework.stereotype.Component;

/**
 * Собирает анализатор логов из компонентов,
 * соответствующих команде пользователя.
 */
@Component
public final class LogAnalyzerFactory {
    private final LoaderFactory loaderFactory;
    private final DateValidatorFactory dateValidatorFactory;
    private final AggregatorFactory aggregatorFactory;
    private final LogParser<NGINXlog> logParser;


    /**
     * Создает фабрику анализаторов.
     *
     * @param loaderFactory фабрика загрузчиков
     * @param dateValidatorFactory фабрика валидаторов дат
     * @param aggregatorFactory фабрика агрегаторов
     * @param logParser парсер строк NGINX-лога
     */
    public LogAnalyzerFactory(
            LoaderFactory loaderFactory,
            DateValidatorFactory dateValidatorFactory,
            AggregatorFactory aggregatorFactory,
            LogParser<NGINXlog> logParser
    ) {
        this.loaderFactory = loaderFactory;
        this.dateValidatorFactory = dateValidatorFactory;
        this.aggregatorFactory = aggregatorFactory;
        this.logParser = logParser;
    }


    /**
     * Создает полностью настроенный анализатор
     * для указанной команды.
     *
     * @param command параметры запуска анализа
     * @return подготовленный анализатор
     */
    public LogAnalyzer create(Command command) {
        Loader loader = loaderFactory.create(command.source());
        DateValidator dateValidator = dateValidatorFactory.create(command.fromDate(), command.toDate());
        Aggregator<NGINXlog, LogStatistics> aggregator = aggregatorFactory.create();

        return new LogAnalyzer(
                loader,
                logParser,
                dateValidator,
                aggregator
        );
    }
}
