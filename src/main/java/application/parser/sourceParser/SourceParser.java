package application.parser.sourceParser;

@FunctionalInterface
public interface SourceParser<ParsedSource> {
    ParsedSource parse(String source);
}
