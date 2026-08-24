public class AnalizatorBuilder {
    LoaderFactory loaderFactory = new LoaderFactory();
    DateValidatorFactory dateValidatorFactory = new DateValidatorFactory();
    ReporterFactory reporterFactory = new ReporterFactory();
    AggregatorFactory aggregatorFactory = new AggregatorFactory();

    public Analizator createAnalizator(SeparatedCommand command) {
        Loader loader = loaderFactory.create(command.source());
        DateChecker dateChecker = dateValidatorFactory.create(command.fromDate(), command.toDate());
        Aggregator aggregator = aggregatorFactory.create();
        Reporter reporter = reporterFactory.create(command.reportFormat());

        return new Analizator(loader, dateChecker, aggregator, reporter);
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