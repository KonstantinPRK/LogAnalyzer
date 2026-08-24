package application.core.control;

import application.aggregator.Aggregator;
import application.collector.Collector;
import application.parser.LogParser;

public record SystemParameters(Aggregator aggregator, Collector logCollector, LogParser logParser) {
}
