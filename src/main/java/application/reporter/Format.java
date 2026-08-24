package application.reporter;

public enum Format {
    MARKDOWN, ADOC, TEXT;

    public static Format fromString(String format) {
        if (format == null) return null;
        return Format.valueOf(format.trim().toUpperCase());
    }
}
