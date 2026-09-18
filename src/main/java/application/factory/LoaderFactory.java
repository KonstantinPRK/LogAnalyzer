package application.factory;

import application.errorhandling.exceptions.CommandParsingException;
import application.loader.FileLoader;
import application.loader.Loader;
import application.loader.UrlLoader;
import application.parser.source.FileSourceParser;
import application.parser.source.UrlSourceParser;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.util.Locale;

/**
 * Выбирает загрузчик в зависимости от типа
 * указанного источника логов.
 */
@Component
public final class LoaderFactory {
    private final FileSourceParser fileSourceParser;
    private final UrlSourceParser urlSourceParser;
    private final HttpClient httpClient;


    /**
     * Создает фабрику с парсерами источников
     * и HTTP-клиентом приложения.
     *
     * @param fileSourceParser парсер файловых источников
     * @param urlSourceParser парсер сетевых источников
     * @param httpClient клиент для сетевых запросов
     */
    public LoaderFactory(
            FileSourceParser fileSourceParser,
            UrlSourceParser urlSourceParser,
            HttpClient httpClient
    ) {
        this.fileSourceParser = fileSourceParser;
        this.urlSourceParser = urlSourceParser;
        this.httpClient = httpClient;
    }


    /**
     * Создает загрузчик для локального пути либо HTTP-адреса.
     *
     * @param source строковое представление источника
     * @return подходящий загрузчик
     */
    public Loader create(String source) {
        String normalizedSource = source.trim();
        int schemeSeparator = normalizedSource.indexOf("://");
        if (schemeSeparator < 0) return new FileLoader(normalizedSource, fileSourceParser);

        String scheme = normalizedSource
                .substring(0, schemeSeparator)
                .toLowerCase(Locale.ROOT);

        if (scheme.equals("http") || scheme.equals("https")) {
            return new UrlLoader(normalizedSource, urlSourceParser, httpClient);
        }

        throw new CommandParsingException("Неподдерживаемая схема источника: " + scheme);
    }
}
