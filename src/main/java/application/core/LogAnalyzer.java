package application.core;

import application.components.parsing.Parser;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class LogAnalyzer {

    private final Parser parser;

    public LogAnalyzer(Parser parser) {
        this.parser = parser;
    }

    public long analyze(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .map(parser::parse)
                    .count();
        }
    }
}