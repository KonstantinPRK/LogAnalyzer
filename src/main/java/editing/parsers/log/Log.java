package editing.parsers.log;

import java.time.ZonedDateTime;

public record Log(
        ZonedDateTime time,
        String uri,
        int status,
        long size
) {}