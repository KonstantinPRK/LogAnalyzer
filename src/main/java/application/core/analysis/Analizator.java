package application.core.analysis;

import application.aggregator.Aggregator;
import application.aggregator.StatisticAggregator;
import application.loader.FileLoader;
import application.loader.Loader;
import application.parser.logParser.LogParser;
import application.parser.logParser.NGINXlog;
import application.parser.logParser.NGINXparser;
import application.reporter.MarkDownReporter;
import application.reporter.Reporter;
import application.validator.DateChecker;
import application.validator.SomeDateChecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Analizator implements Runnable {
    Loader loader = new FileLoader();
    DateChecker dateChecker = new SomeDateChecker(null, null);
    Aggregator aggregator = new StatisticAggregator();
    Reporter reporter = new MarkDownReporter();

    LogParser logParser = new NGINXparser();

    public Analizator(Loader loader, DateChecker dateChecker, Aggregator aggregator, Reporter reporter) {

    }



    public void run(){
        System.out.println("Введи путь");
        Scanner scan = new Scanner(System.in);
        String idealPath = scan.nextLine();


        List<Path> finalTextPaths = loader.getPaths(idealPath, ".txt");

        for(Path path : finalTextPaths) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                // Читаем по одной строке, пока файл не закончится
                while ((line = reader.readLine()) != null) {
                    // Обработка строки line
                    NGINXlog nginxLog = (NGINXlog) logParser.parse(line);

                    boolean inDateRange = dateChecker.validate(nginxLog.timestamp());
                    if(inDateRange) aggregator.accept(nginxLog);

                }
            } catch (IOException e) {
                System.err.println("Ошибка чтения файла " + path + ": " + e.getMessage());
            }
        }

        Map<String, String> summaryMap = aggregator.getSummaryMap(100);

        for(String key : summaryMap.keySet()) System.out.println(summaryMap.get(key));


    }
}
