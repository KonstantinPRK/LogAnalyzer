package application.parser.commandParser;


import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommandParser {

    public Command parse(String command) {
        String cleanCommand = command.trim();

        String source = extractParameter(cleanCommand, "--path");
        String fromDate = extractParameter(cleanCommand, "--from");
        String toDate = extractParameter(cleanCommand, "--to");
        String reportFormat = extractParameter(cleanCommand, "--format");

        return new Command(source, fromDate, toDate, reportFormat);
    }

    private String extractParameter(String command, String paramName) {
        Pattern pattern = Pattern.compile(paramName + "\\s+([^\\s]+)");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
