package application.core.control;

import application.factory.DateValidatorFactory;
import application.aggregator.Aggregator;
import application.collector.Collector;
import application.factory.LoaderFactory;
import application.factory.ReporterFactory;
import application.loader.Loader;
import org.springframework.stereotype.Component;
import application.reporter.Reporter;
import application.validator.DateChecker;

@Component
public class AnalizatorBuilder {
    LoaderFactory loaderFactory = new LoaderFactory();
    DateValidatorFactory dateValidatorFactory = new DateValidatorFactory();
    ReporterFactory reporterFactory = new ReporterFactory();


    public Analizator createAnalizator(UserParameters userParameters, SystemParameters systemParameters) {
        Loader loader = loaderFactory.create(userParameters.source());
        DateChecker dateChecker = dateValidatorFactory.create(userParameters.fromDate(), userParameters.toDate());
        Reporter reporter = reporterFactory.create(userParameters.reportFormat());

        Aggregator aggregator = systemParameters.aggregator();
        Collector collector = systemParameters.logCollector();

        return new Analizator(loader, dateChecker, aggregator, collector, reporter);
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