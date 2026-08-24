package application;

import application.aggregator.StatisticAggregator;
import application.collector.CallCollector;
import application.core.control.Analizator;
import application.core.control.AnalizatorBuilder;
import application.core.control.SystemParameters;
import application.core.control.UserParameters;
import application.parser.CommandParser;
import application.parser.NGINXparser;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class LogAnalyzerService {
    Scanner scan = new Scanner(System.in);
    CommandParser commandParser = new CommandParser();
    AnalizatorBuilder analizatorBuilder = new AnalizatorBuilder();


    @PostConstruct
    public void start(){
       String command = request();
       UserParameters userParameters = commandParser.parse(command);
       SystemParameters systemParameters = new SystemParameters(new StatisticAggregator(), new CallCollector(), new NGINXparser());
       Analizator analizator = analizatorBuilder.createAnalizator(userParameters, systemParameters);

        analizator.run();
    }

    private String request() {
        System.out.println("Введите команду, например:");
        System.out.println("analyzer --path logs/*.log --from 2024-08-31 --format markdown");
        System.out.print("> ");
        return scan.nextLine().trim(); // с удалением пробелов
    }


}
