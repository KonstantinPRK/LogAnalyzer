package application.parser.commandParser;

import application.errorhandling.exceptions.CommandParsingException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public final class CommandParser {
    public Command parse(String command) {
        if (Objects.isNull(command) || command.isBlank()) {
            throw new CommandParsingException("Команда анализа не задана");
        }

        String cleanCommand = command.trim();
        String source = extractParameter(cleanCommand, "--path");
        if (Objects.isNull(source) || source.isBlank()) {
            throw new CommandParsingException(
                    "Не задан обязательный параметр --path"
            );
        }

        String fromDate = extractParameter(cleanCommand, "--from");
        String toDate = extractParameter(cleanCommand, "--to");
        String reportFormat = extractParameter(cleanCommand, "--format");

        return new Command(source, fromDate, toDate, reportFormat);
    }


    private String extractParameter(String command, String paramName) {
        Pattern pattern = Pattern.compile(Pattern.quote(paramName) + "\\s+(\\S+)");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
