package application.parser.logParser;

import java.time.OffsetDateTime;

public record NGINXlog(
        String remoteAddress,
        String remoteUser,
        OffsetDateTime timestamp,
        String method,
        String resource,
        String protocol,
        int status,
        long bodyBytesSent,
        String referer,
        String userAgent
) {
}
