package application.parser.logParser;

public interface LogParser<LogTypeRecord> {
    LogTypeRecord parse(String line);
}
