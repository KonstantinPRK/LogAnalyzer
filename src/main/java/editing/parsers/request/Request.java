package editing.parsers.request;

import editing.formatters.Formatter;

import java.time.LocalDateTime;

public record Request(
        String path,
        LocalDateTime from,
        LocalDateTime to,
        Formatter formatter
) {
}
