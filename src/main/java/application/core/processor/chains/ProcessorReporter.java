package application.core.processor.chains;

import application.components.reporting.Reporter;
import application.core.processor.ProcessorContext;

public class ProcessorReporter implements ProcessorComponent{
    Reporter reporter;

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void process(ProcessorContext context) {

    }

}
