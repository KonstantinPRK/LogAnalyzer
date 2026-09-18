package application.loader;

import application.errorhandling.exceptions.LogLoadingException;
import application.parser.source.SourceParser;

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
import java.util.stream.Stream;

/**
 * Последовательно загружает строки лога по HTTP или HTTPS.
 */
public final class UrlLoader implements Loader {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);
    private final String source;
    private final SourceParser<URI> sourceParser;
    private final HttpClient httpClient;
    private final Charset charset;


    /**
     * Создает загрузчик с кодировкой UTF-8.
     *
     * @param source URL источника
     * @param sourceParser парсер URL
     * @param httpClient HTTP-клиент
     */
    public UrlLoader(String source, SourceParser<URI> sourceParser, HttpClient httpClient) {
        this(source, sourceParser, httpClient, StandardCharsets.UTF_8);
    }

    /**
     * Создает загрузчик с указанной кодировкой ответа.
     *
     * @param source URL источника
     * @param sourceParser парсер URL
     * @param httpClient HTTP-клиент
     * @param charset кодировка ответа
     */
    public UrlLoader(
            String source,
            SourceParser<URI> sourceParser,
            HttpClient httpClient,
            Charset charset
    ) {
        this.source = source;
        this.sourceParser = sourceParser;
        this.httpClient = httpClient;
        this.charset = charset;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<String> load() {
        URI sourceUri = sourceParser.parse(source);
        HttpRequest request = createRequest(sourceUri);
        HttpResponse<InputStream> response = sendRequest(request);
        InputStream responseBody = validateResponse(response);

        return readLines(responseBody);
    }


    /**
     * Создает HTTP-запрос к источнику логов.
     *
     * @param sourceUri адрес источника
     * @return подготовленный запрос
     */
    private HttpRequest createRequest(URI sourceUri) {
        try {
            return HttpRequest.newBuilder(sourceUri)
                    .timeout(REQUEST_TIMEOUT)
                    .header("User-Agent", "LogAnalyzer/1.0")
                    .GET()
                    .build();

        } catch (IllegalArgumentException exception) {
            throw new LogLoadingException("Некорректный HTTP URL: " + sourceUri, exception);

        }
    }


    /**
     * Выполняет HTTP-запрос и возвращает ответ с потоковым телом.
     *
     * @param request HTTP-запрос
     * @return ответ сервера
     */
    private HttpResponse<InputStream> sendRequest(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

        } catch (IOException exception) {
            throw new LogLoadingException(
                    "Не удалось загрузить лог по URL: " + source,
                    exception
            );

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new LogLoadingException("Загрузка лога была прервана: " + source, exception);

        }
    }


    /**
     * Проверяет успешность HTTP-ответа и возвращает его тело.
     *
     * @param response ответ сервера
     * @return поток тела успешного ответа
     */
    private InputStream validateResponse(HttpResponse<InputStream> response) {
        InputStream responseBody = response.body();
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            closeResponseBody(responseBody);
            throw new LogLoadingException(
                    "Сервер вернул HTTP " + response.statusCode() + " для " + source
            );
        }

        return responseBody;
    }


    /**
     * Преобразует тело HTTP-ответа в поток строк
     * с корректным закрытием ресурса.
     *
     * @param responseBody тело HTTP-ответа
     * @return поток строк лога
     */
    private Stream<String> readLines(InputStream responseBody) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(responseBody, charset)
        );

        return reader.lines().onClose(() -> closeReader(reader));
    }


    /**
     * Закрывает тело неуспешного HTTP-ответа.
     *
     * @param responseBody тело HTTP-ответа
     */
    private static void closeResponseBody(InputStream responseBody) {
        try {
            responseBody.close();

        } catch (IOException ignored) {

        }
    }


    /**
     * Закрывает средство чтения HTTP-ответа.
     *
     * @param reader средство чтения ответа
     */
    private static void closeReader(BufferedReader reader) {
        try {
            reader.close();

        } catch (IOException exception) {
            throw new LogLoadingException("Не удалось закрыть поток URL", exception);

        }
    }
}
