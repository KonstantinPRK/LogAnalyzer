

import java.net.URI;
import java.nio.file.Path;
import java.util.Scanner;

public class LogAnalyzer {
    Scanner scan = new Scanner(System.in);
    CommandParser commandParser = new CommandParser();
    AnalizatorFactory analizatorFactory = new AnalizatorFactory();


    public void start(){
       String line = request();
       SeparatedCommand separatedCommandParameters = commandParser.parse(line);
       Analizator analizator = analizatorFactory.createAnalizator(separatedCommandParameters);
    }

    private String request() {
        System.out.println("Введите команду, например:");
        System.out.println("analyzer --path logs/*.log --from 2024-08-31 --format markdown");
        System.out.print("> ");
        return scan.nextLine().trim(); // с удалением пробелов
    }
}
