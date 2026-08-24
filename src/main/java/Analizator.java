import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Analizator implements Runnable {
    Loader loader = new FileLoader();
    DateChecker dateChecker = new SomeDateChecker(null, null);
    Aggregator aggregator = new StatisticAggregator();
    Reporter reporter = new MarkDownReporter();

    LogParser logParser = new NGINXparser();

    public Analizator(Loader loader, DateChecker dateChecker, Aggregator aggregator, Collector collector, Reporter reporter) {

    }



    public void run(){
        List<Path> finalTextPaths = loader.getPaths();

        for(Path path : finalTextPaths) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                // Читаем по одной строке, пока файл не закончится
                while ((line = reader.readLine()) != null) {
                    // Обработка строки line
                    NGINXlog nginxLog = (NGINXlog) logParser.parse(line);

                    dateChecker.validate(nginxLog.timestamp());
                    aggregator.accept(nginxLog);


                }
            } catch (IOException e) {
                System.err.println("Ошибка чтения файла " + path + ": " + e.getMessage());
            }
        }

    }
}
