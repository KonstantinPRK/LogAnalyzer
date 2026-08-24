package application.loader;

import application.errorhandling.exceptions.LogLoadingException;
import application.parser.sourceParser.SourceParser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public final class FileLoader implements Loader {
    private final String source;
    private final SourceParser<List<Path>> sourceParser;
    private final Charset charset;


    public FileLoader(String source, SourceParser<List<Path>> sourceParser) {
        this(source, sourceParser, StandardCharsets.UTF_8);
    }


    public FileLoader(String source, SourceParser<List<Path>> sourceParser, Charset charset) {
        this.source = source;
        this.sourceParser = sourceParser;
        this.charset = charset;
    }


    @Override
    public Stream<String> load() {
        List<Path> paths = sourceParser.parse(source);
        return paths.stream().flatMap(this::readLines);
    }


    private Stream<String> readLines(Path path) {
        try {
            return Files.lines(path, charset);
        } catch (IOException exception) {
            throw new LogLoadingException("Не удалось открыть лог-файл: " + path, exception);
        }
    }
}
