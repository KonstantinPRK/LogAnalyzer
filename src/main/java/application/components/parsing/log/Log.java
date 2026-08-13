package application.components.parsing.log;

import java.time.ZonedDateTime;

public record Log(
        String remoteAddress,
        String remoteUser,
        ZonedDateTime timestamp,
        String method,
        String resource,
        String protocol,
        int status,
        long bodyBytesSent,
        String referer,
        String userAgent
) {}