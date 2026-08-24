package application.reporter;

import java.util.Map;

public interface Reporter {
    Report create(Map<String, ?> statistics);
}
