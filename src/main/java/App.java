
import application.components.parsing.Parser;
import application.components.parsing.log.NGINXparser;
import application.core.LogAnalyzer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Path;


@SpringBootApplication
public class App {
    public static void main(String[] args) throws IOException {
            Path path = Path.of(args[0]);

            Parser parser = new NGINXparser();
            LogAnalyzer analyzer = new LogAnalyzer(parser);

            long requests = analyzer.analyze(path);

            System.out.println("Requests: " + requests);
    }
}
