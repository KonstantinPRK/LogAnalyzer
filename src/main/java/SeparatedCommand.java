import java.nio.file.Path;
import java.time.LocalDate;

public record SeparatedCommand (
        String source,
        String fromDate,
        String toDate,
        String reportFormat
) {}
