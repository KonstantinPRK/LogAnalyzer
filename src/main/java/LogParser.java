public interface LogParser<LogTypeRecord> {
    LogTypeRecord parse(String line);
}
