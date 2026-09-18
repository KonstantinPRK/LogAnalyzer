package application.parser.log;

import application.errorhandling.exceptions.LogParsingException;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Разбирает строки формата NGINX Combined Log.
 */
@Component
public final class NGINXparser implements LogParser<NGINXlog> {
    private static final int
            MAX_LINE_PREVIEW_LENGTH = 120,
            REMOTE_ADDRESS_GROUP = 1,
            REMOTE_USER_GROUP = 2,
            TIMESTAMP_GROUP = 3,
            METHOD_GROUP = 4,
            RESOURCE_GROUP = 5,
            PROTOCOL_GROUP = 6,
            STATUS_GROUP = 7,
            BODY_BYTES_SENT_GROUP = 8,
            REFERER_GROUP = 9,
            USER_AGENT_GROUP = 10;
    private static final Pattern LOG_PATTERN = Pattern.compile(
            "^(\\S+) \\S+ (\\S+) \\[([^\\]]+)\\] "
                    + "\"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+)"
                    + "(?: \"([^\"]*)\" \"([^\"]*)\")?$"
    );
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);


    /**
     * Создает парсер строк NGINX Combined Log.
     */
    public NGINXparser() {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public NGINXlog parse(String line) {
        if (Objects.isNull(line) || line.isBlank()) return null;

        Matcher matcher = LOG_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new LogParsingException(
                    "строка не соответствует формату NGINX Combined Log: "
                            + preview(line)
            );
        }

        try {
            String remoteAddress = matcher.group(REMOTE_ADDRESS_GROUP);
            String remoteUser = matcher.group(REMOTE_USER_GROUP);
            String timestampText = matcher.group(TIMESTAMP_GROUP);
            String method = matcher.group(METHOD_GROUP);
            String resource = matcher.group(RESOURCE_GROUP);
            String protocol = matcher.group(PROTOCOL_GROUP);
            int status = Integer.parseInt(matcher.group(STATUS_GROUP));
            long bodyBytesSent = Long.parseLong(matcher.group(BODY_BYTES_SENT_GROUP));
            String referer = matcher.group(REFERER_GROUP);
            String userAgent = matcher.group(USER_AGENT_GROUP);
            OffsetDateTime timestamp = OffsetDateTime.parse(
                    timestampText,
                    DATE_FORMATTER
            );

            return new NGINXlog(
                    remoteAddress,
                    remoteUser,
                    timestamp,
                    method,
                    resource,
                    protocol,
                    status,
                    bodyBytesSent,
                    referer,
                    userAgent
            );

        } catch (DateTimeParseException | NumberFormatException exception) {
            throw new LogParsingException(
                    "не удалось разобрать значения строки: " + preview(line),
                    exception
            );

        }
    }


    /**
     * Сокращает проблемную строку для безопасного отображения
     * в сообщении об ошибке.
     *
     * @param line исходная строка лога
     * @return однострочное сокращенное представление
     */
    private String preview(String line) {
        String singleLine = line.replace('\r', ' ').replace('\n', ' ');
        if (singleLine.length() <= MAX_LINE_PREVIEW_LENGTH) return singleLine;

        return singleLine.substring(0, MAX_LINE_PREVIEW_LENGTH) + "...";
    }
}
