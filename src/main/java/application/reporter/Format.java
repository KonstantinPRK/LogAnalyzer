package application.reporter;

import application.errorhandling.exceptions.CommandParsingException;

import java.util.Locale;
import java.util.Objects;

/**
 * Поддерживаемые форматы итогового отчета.
 */
public enum Format {
    /** Формат Markdown. */
    MARKDOWN,

    /** Формат AsciiDoc. */
    ADOC;


    /**
     * Преобразует пользовательское имя формата
     * в значение перечисления.
     *
     * @param format имя формата или {@code null}
     * @return выбранный формат; по умолчанию Markdown
     */
    public static Format fromString(String format) {
        if (Objects.isNull(format) || format.isBlank()) return MARKDOWN;

        return switch (format.trim().toLowerCase(Locale.ROOT)) {
            case "markdown", "md" -> MARKDOWN;
            case "adoc", "asciidoc" -> ADOC;
            default -> throw new CommandParsingException(
                    "Неподдерживаемый формат отчёта: " + format
            );
        };
    }
}
