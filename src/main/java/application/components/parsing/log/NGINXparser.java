package application.components.parsing.log;

import application.components.parsing.Parser;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NGINXparser implements Parser {

    private static final Pattern LOG_PATTERN = Pattern.compile(
            "^(\\S+)\\s+" +                 // IP
                    "(\\S+)\\s+" +                  // ident
                    "(\\S+)\\s+" +                  // user
                    "\\[([^]]+)]\\s+" +             // date
                    "\"(\\S+)\\s+(\\S+)\\s+(\\S+)\"\\s+" + // request
                    "(\\d{3})\\s+" +                // status
                    "(\\d+|-)\\s+" +                // size
                    "\"([^\"]*)\"\\s+" +            // referer
                    "\"([^\"]*)\"$"                  // user-agent
    );

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");

    @Override
    public Log parse(String unparsedLine) {
        Matcher matcher = LOG_PATTERN.matcher(unparsedLine);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid NGINX log line: " + unparsedLine
            );
        }

        return new Log(
                matcher.group(1),                           // remote address
                matcher.group(3),                           // remote user
                ZonedDateTime.parse(
                        matcher.group(4),
                        DATE_FORMATTER
                ),                                          // timestamp
                matcher.group(5),                           // method
                matcher.group(6),                           // resource
                matcher.group(7),                           // protocol
                Integer.parseInt(matcher.group(8)),         // status
                parseSize(matcher.group(9)),                // bytes
                matcher.group(10),                          // referer
                matcher.group(11)                           // user-agent
        );
    }

    private long parseSize(String size) {
        return "-".equals(size)
                ? 0L
                : Long.parseLong(size);
    }
}