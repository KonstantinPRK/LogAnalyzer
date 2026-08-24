package application.reporter;

import application.errorhandling.exceptions.CommandParsingException;

import java.util.Objects;

public enum Format {
    MARKDOWN,
    ADOC;

    public static Format fromString(String format) {
        if (Objects.isNull(format) || format.isBlank()) {
            return MARKDOWN;
        }

        return switch (format.trim().toLowerCase()) {
            case "markdown", "md" -> MARKDOWN;
            case "adoc", "asciidoc" -> ADOC;
            default -> throw new CommandParsingException(
                    "Неподдерживаемый формат отчёта: " + format
            );
        };
    }
}
