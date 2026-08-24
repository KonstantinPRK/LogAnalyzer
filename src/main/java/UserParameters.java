import java.nio.file.Path;
import java.time.LocalDate;

public record UserParameters (
        String source,
        String fromDate,
        String toDate,
        String reportFormat
) {}
