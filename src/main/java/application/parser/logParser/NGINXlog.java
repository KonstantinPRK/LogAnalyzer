package application.parser.logParser;

import java.time.OffsetDateTime;

public record NGINXlog(
        String ip,
        OffsetDateTime timestamp,
        String method,
        String resource,
        int status,
        long bodyBytesSent,
        String referer,
        String userAgent
) {
}
