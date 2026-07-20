package application.model;

import application.format.Format;

import java.time.LocalDateTime;

public record RequestConfig(
        String path,
        LocalDateTime from,
        LocalDateTime to,
        Format format
) {
}
