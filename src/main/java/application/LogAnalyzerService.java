package application;

import application.core.analysis.LogAnalyzer;
import application.errorhandling.ErrorHandler;
import application.factory.LogAnalyzerFactory;
import application.factory.ReporterFactory;
import application.parser.command.Command;
import application.parser.command.CommandParser;
import application.report.LogReport;
import application.report.LogStatistics;
import application.reporter.Reporter;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.io.PrintStream;

/**
 * Координирует однократный запуск анализа логов
 * после создания контекста Spring.
 */
@Component
public final class LogAnalyzerService {
    private final ApplicationArguments arguments;
    private final CommandParser commandParser;
    private final LogAnalyzerFactory logAnalyzerFactory;
    private final ReporterFactory reporterFactory;
    private final ErrorHandler errorHandler;
    private final PrintStream output;


    /**
     * Создает сервис с компонентами,
     * необходимыми для выполнения анализа
     * и вывода результата.
     *
     * @param arguments аргументы командной строки
     * @param commandParser парсер аргументов командной строки
     * @param logAnalyzerFactory фабрика анализаторов логов
     * @param reporterFactory фабрика средств формирования отчета
     * @param errorHandler обработчик ошибок приложения
     * @param output поток вывода результата
     */
    public LogAnalyzerService(
            ApplicationArguments arguments,
            CommandParser commandParser,
            LogAnalyzerFactory logAnalyzerFactory,
            ReporterFactory reporterFactory,
            ErrorHandler errorHandler,
            @Qualifier("consoleOutput") PrintStream output
    ) {
        this.arguments = arguments;
        this.commandParser = commandParser;
        this.logAnalyzerFactory = logAnalyzerFactory;
        this.reporterFactory = reporterFactory;
        this.errorHandler = errorHandler;
        this.output = output;
    }


    /**
     * Запускает анализ, выводит сформированный отчет
     * или понятное сообщение об ошибке.
     */
    @PostConstruct
    public void start() {
        try {
            output.println(createFormattedReport());

        } catch (Exception exception) {
            output.println(errorHandler.handle(exception));

        } finally {
            output.flush();

        }
    }


    /**
     * Выполняет полный сценарий анализа
     * и форматирует его результат.
     *
     * @return готовый к выводу отчет
     */
    private String createFormattedReport() {
        Command command = commandParser.parse(arguments.getSourceArgs());
        LogAnalyzer logAnalyzer = logAnalyzerFactory.create(command);
        LogStatistics statistics = logAnalyzer.analyze();
        LogReport report = createReport(command, statistics);
        Reporter reporter = reporterFactory.create(command.reportFormat());

        return reporter.format(report);
    }


    /**
     * Объединяет параметры команды
     * и собранную статистику в модель отчета.
     *
     * @param command исходная команда анализа
     * @param statistics рассчитанная статистика
     * @return модель отчета
     */
    private LogReport createReport(Command command, LogStatistics statistics) {
        return new LogReport(
                command.source(),
                command.fromDate(),
                command.toDate(),
                statistics
        );
    }
}
