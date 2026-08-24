package application.core.setting;

import application.aggregator.Aggregator;
import application.collector.Collector;
import application.parser.logParser.LogParser;

import java.util.Objects;

public class SystemParameters {
    private final Aggregator aggregator;
    private final Collector logCollector;
    private final LogParser logParser;

    public SystemParameters(Aggregator aggregator, Collector logCollector, LogParser logParser) {
        this.aggregator = aggregator;
        this.logCollector = logCollector;
        this.logParser = logParser;
    }

    public Aggregator aggregator() {
        return aggregator;
    }

    public Collector logCollector() {
        return logCollector;
    }

    public LogParser logParser() {
        return logParser;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SystemParameters) obj;
        return Objects.equals(this.aggregator, that.aggregator) &&
                Objects.equals(this.logCollector, that.logCollector) &&
                Objects.equals(this.logParser, that.logParser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregator, logCollector, logParser);
    }

    @Override
    public String toString() {
        return "SystemParameters[" +
                "aggregator=" + aggregator + ", " +
                "logCollector=" + logCollector + ", " +
                "logParser=" + logParser + ']';
    }

}
