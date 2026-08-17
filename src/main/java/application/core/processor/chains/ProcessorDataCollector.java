package application.core.processor.chains;

import application.components.collection.collector.DataCollector;
import application.core.processor.ProcessorContext;

public class ProcessorDataCollector implements ProcessorComponent {
    DataCollector dataCollector;

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void process(ProcessorContext context) {

    }

}
