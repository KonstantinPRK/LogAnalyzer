

import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LogAnalyzer {
    Scanner scan = new Scanner(System.in);
    CommandParser commandParser = new CommandParser();
    AnalizatorBuilder analizatorBuilder = new AnalizatorBuilder();



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
