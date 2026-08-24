package application.parser;

import application.core.control.UserParameters;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    public UserParameters parse(String command) {
        String cleanCommand = command.trim();

        String source = extractParameter(cleanCommand, "--path");
        String fromDate = extractParameter(cleanCommand, "--from");
        String toDate = extractParameter(cleanCommand, "--to");
        String reportFormat = extractParameter(cleanCommand, "--format");

        return new UserParameters(source, fromDate, toDate, reportFormat);
    }

    private String extractParameter(String command, String paramName) {
        Pattern pattern = Pattern.compile(paramName + "\\s+([^\\s]+)");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private Path parsePath(String pathStr) {
        if (pathStr == null) return null;
        try {
            // Если это URL, сначала переводим в URI, а затем в Path
            if (pathStr.startsWith("http://") || pathStr.startsWith("https://")) {
                return Paths.get(new URI(pathStr));
            }
            // Если это обычный локальный путь или маска (например, logs/2024*)
            return Path.of(pathStr);
        } catch (URISyntaxException | IllegalArgumentException e) {
            // На случай, если URL или путь составлен некорректно
            return Path.of(pathStr);
        }
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null) return null;
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private Format parseFormat(String formatStr) {
        if (formatStr == null) return null;
        try {
            return Format.valueOf(formatStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
