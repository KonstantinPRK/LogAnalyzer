package application.core.processor.chains;

import application.components.verification.Validator;
import application.core.processor.ProcessorContext;

public class ProcessorSetter implements ProcessorComponent {
    Validator validator;

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void process(ProcessorContext context) {

    }

}
