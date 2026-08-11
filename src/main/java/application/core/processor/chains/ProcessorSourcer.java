package application.core.processor.chains;

import application.components.loading.Loader;
import application.core.processor.ProcessorContext;

public class ProcessorSourcer implements ProcessorComponent {
    Loader loader;

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void process(ProcessorContext context) {

    }
}
