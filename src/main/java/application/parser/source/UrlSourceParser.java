package application.parser.source;

import application.errorhandling.exceptions.SourceParsingException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Locale;
import java.util.Objects;

/**
 * Проверяет и преобразует HTTP-адрес источника логов.
 */
@Component
public final class UrlSourceParser implements SourceParser<URI> {
    /**
     * Создает парсер URL-источников логов.
     */
    public UrlSourceParser() {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public URI parse(String source) {
        if (Objects.isNull(source) || source.isBlank()) {
            throw new SourceParsingException("URL источника логов не задан");
        }

        URI uri;
        try {
            uri = URI.create(source.trim());

        } catch (IllegalArgumentException exception) {
            throw new SourceParsingException("Некорректный URL: " + source, exception);

        }

        validate(uri);
        return uri;
    }


    /**
     * Проверяет схему и наличие адреса сервера в URL.
     *
     * @param uri проверяемый адрес
     */
    private void validate(URI uri) {
        if (Objects.isNull(uri.getScheme())) {
            throw new SourceParsingException("URL должен содержать схему http или https");
        }

        String scheme = uri.getScheme().toLowerCase(Locale.ROOT);
        if (!scheme.equals("http") && !scheme.equals("https")) {
            throw new SourceParsingException("Неподдерживаемая схема URL: " + uri.getScheme());
        }

        if (Objects.isNull(uri.getRawAuthority()) || uri.getRawAuthority().isBlank()) {
            throw new SourceParsingException("URL должен содержать адрес сервера: " + uri);
        }
    }
}
