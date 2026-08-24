package application.parser.logParser;

import application.errorhandling.exceptions.LogParsingException;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public final class NGINXparser implements LogParser<NGINXlog> {
    private static final int MAX_LINE_PREVIEW_LENGTH = 120;
    private static final Pattern LOG_PATTERN = Pattern.compile(
            "^(\\S+) \\S+ \\S+ \\[([^\\]]+)\\] \"(\\S+) (\\S+) \\S+\" (\\d{3}) (\\d+)(?: \"([^\"]*)\" \"([^\"]*)\")?$"
    );
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);


    @Override
    public NGINXlog parse(String line) {
        if (Objects.isNull(line) || line.isBlank()) {
            return null;
        }

        Matcher matcher = LOG_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new LogParsingException(
                    "строка не соответствует формату NGINX Combined Log: "
                            + preview(line)
            );
        }

        try {
            String ip = matcher.group(1);
            String timestampText = matcher.group(2);
            String method = matcher.group(3);
            String resource = matcher.group(4);
            int status = Integer.parseInt(matcher.group(5));
            long bodyBytesSent = Long.parseLong(matcher.group(6));
            String referer = matcher.group(7);
            String userAgent = matcher.group(8);
            OffsetDateTime timestamp = OffsetDateTime.parse(
                    timestampText,
                    DATE_FORMATTER
            );

            return new NGINXlog(
                    ip,
                    timestamp,
                    method,
                    resource,
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


    private String preview(String line) {
        String singleLine = line.replace('\r', ' ').replace('\n', ' ');
        if (singleLine.length() <= MAX_LINE_PREVIEW_LENGTH) {
            return singleLine;
        }

        return singleLine.substring(0, MAX_LINE_PREVIEW_LENGTH) + "...";
    }
}
