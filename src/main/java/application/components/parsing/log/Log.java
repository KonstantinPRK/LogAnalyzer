package application.components.parsing.log;

import java.time.ZonedDateTime;

public record Log(
        ZonedDateTime time,
        String source,
        String status,
        String size
) {}