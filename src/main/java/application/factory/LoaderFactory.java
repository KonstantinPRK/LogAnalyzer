package application.factory;

import application.errorhandling.exceptions.CommandParsingException;
import application.loader.FileLoader;
import application.loader.Loader;
import application.loader.UrlLoader;
import application.parser.sourceParser.FileSourceParser;
import application.parser.sourceParser.UrlSourceParser;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.util.Locale;
import java.util.Objects;

@Component
public final class LoaderFactory {
    private final FileSourceParser fileSourceParser;
    private final UrlSourceParser urlSourceParser;
    private final HttpClient httpClient;


    public LoaderFactory(FileSourceParser fileSourceParser, UrlSourceParser urlSourceParser, HttpClient httpClient) {
        this.fileSourceParser = fileSourceParser;
        this.urlSourceParser = urlSourceParser;
        this.httpClient = httpClient;
    }


    public Loader create(String source) {
        if (Objects.isNull(source) || source.isBlank()) {
            throw new CommandParsingException("Источник логов не задан");
        }

        String normalizedSource = source.trim();
        int schemeSeparator = normalizedSource.indexOf("://");
        if (schemeSeparator < 0) {
            return new FileLoader(normalizedSource, fileSourceParser);
        }

        String scheme = normalizedSource
                .substring(0, schemeSeparator)
                .toLowerCase(Locale.ROOT);

        if (scheme.equals("http") || scheme.equals("https")) {
            return new UrlLoader(normalizedSource, urlSourceParser, httpClient);
        }

        throw new CommandParsingException("Неподдерживаемая схема источника: " + scheme);
    }
}
