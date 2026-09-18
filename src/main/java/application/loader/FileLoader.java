package application.loader;

import application.errorhandling.exceptions.LogLoadingException;
import application.parser.source.SourceParser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * Загружает строки из одного или нескольких локальных лог-файлов.
 */
public final class FileLoader implements Loader {
    private final String source;
    private final SourceParser<List<Path>> sourceParser;
    private final Charset charset;


    /**
     * Создает загрузчик с кодировкой UTF-8.
     *
     * @param source локальный путь или glob-шаблон
     * @param sourceParser парсер локального источника
     */
    public FileLoader(String source, SourceParser<List<Path>> sourceParser) {
        this(source, sourceParser, StandardCharsets.UTF_8);
    }

    /**
     * Создает загрузчик с указанной кодировкой файлов.
     *
     * @param source локальный путь или glob-шаблон
     * @param sourceParser парсер локального источника
     * @param charset кодировка лог-файлов
     */
    public FileLoader(
            String source,
            SourceParser<List<Path>> sourceParser,
            Charset charset
    ) {
        this.source = source;
        this.sourceParser = sourceParser;
        this.charset = charset;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<String> load() {
        List<Path> paths = sourceParser.parse(source);
        return paths.parallelStream().flatMap(this::readLines);
    }


    /**
     * Открывает поток строк одного файла.
     *
     * @param path путь к лог-файлу
     * @return поток строк файла
     */
    private Stream<String> readLines(Path path) {
        try {
            return Files.lines(path, charset);

        } catch (IOException exception) {
            throw new LogLoadingException("Не удалось открыть лог-файл: " + path, exception);

        }
    }
}
