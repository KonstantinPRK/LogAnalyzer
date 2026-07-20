package application.model;

import java.time.ZonedDateTime;

public record LogEntry(
        ZonedDateTime time,
        String uri,
        int status,
        long size
) {}