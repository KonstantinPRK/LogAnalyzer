package application.loader;

import application.errorhandling.exceptions.LogLoadingException;
import application.parser.sourceParser.SourceParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;
import java.util.stream.Stream;

public final class UrlLoader implements Loader {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);
    private final String source;
    private final SourceParser<URI> sourceParser;
    private final HttpClient httpClient;
    private final Charset charset;


    public UrlLoader(String source, SourceParser<URI> sourceParser, HttpClient httpClient) {
        this(source, sourceParser, httpClient, StandardCharsets.UTF_8);
    }


    public UrlLoader(String source, SourceParser<URI> sourceParser, HttpClient httpClient, Charset charset) {
        this.source = Objects.requireNonNull(source, "source");
        this.sourceParser = Objects.requireNonNull(sourceParser, "sourceParser");
        this.httpClient = Objects.requireNonNull(httpClient, "httpClient");
        this.charset = Objects.requireNonNull(charset, "charset");
    }


    @Override
    public Stream<String> load() {
        URI sourceUri = sourceParser.parse(source);

        HttpRequest request;
        try {
            request = HttpRequest.newBuilder(sourceUri)
                    .timeout(REQUEST_TIMEOUT)
                    .header("User-Agent", "LogAnalyzer/1.0")
                    .GET()
                    .build();
        } catch (IllegalArgumentException exception) {
            throw new LogLoadingException("Некорректный HTTP URL: " + sourceUri, exception);
        }

        HttpResponse<InputStream> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
        } catch (IOException exception) {
            throw new LogLoadingException("Не удалось загрузить лог по URL: " + source, exception);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new LogLoadingException("Загрузка лога была прервана: " + source, exception);
        }

        InputStream responseBody = response.body();
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            closeResponseBody(responseBody);
            throw new LogLoadingException(
                    "Сервер вернул HTTP " + response.statusCode() + " для " + source
            );
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(responseBody, charset)
        );

        return reader.lines().onClose(() -> closeReader(reader));
    }


    private static void closeResponseBody(InputStream responseBody) {
        try {
            responseBody.close();
        } catch (IOException ignored) {
        }
    }


    private static void closeReader(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException exception) {
            throw new LogLoadingException("Не удалось закрыть поток URL", exception);
        }
    }
}
