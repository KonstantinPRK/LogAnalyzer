package application.core.analysis;

import application.aggregator.StatisticAggregator;
import application.factory.DateValidatorFactory;
import application.factory.LoaderFactory;
import application.factory.ReporterFactory;
import application.loader.Loader;
import application.parser.commandParser.Command;
import org.springframework.stereotype.Component;
import application.reporter.Reporter;
import application.validator.DateChecker;

@Component
public class AnalizatorBuilder {
    LoaderFactory loaderFactory = new LoaderFactory();
    DateValidatorFactory dateValidatorFactory = new DateValidatorFactory();
    ReporterFactory reporterFactory = new ReporterFactory();


    public Analizator createAnalizator(Command command) {
        Loader loader = loaderFactory.create(command.source());
        DateChecker dateChecker = dateValidatorFactory.create(command.fromDate(), command.toDate());
        Reporter reporter = reporterFactory.create(command.reportFormat());



        return new Analizator(loader, dateChecker, new StatisticAggregator(), reporter);
    }


}

/*
определение загрузчика
определение валидатора по дате на вход
определение валидатора по дате на выход
определение парсера логов через интерфейс логпарсер
определение пула коллекторов
определение аггрегатора
определение определение отчетмэйкера - Репортера возвращающего чистый стринг без мд

 */