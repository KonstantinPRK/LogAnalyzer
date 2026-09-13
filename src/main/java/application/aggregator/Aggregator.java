package application.aggregator;

public interface Aggregator<LogType, ResultType> {
    void accept(LogType log);
    ResultType getResult();
}
