package application.core.processor.chains;

//один конкретный анализ внутри класса

import application.core.configuration.ProcessingStage;
import application.core.processor.ProcessorContext;


public interface ProcessorComponent {
    ProcessingStage stage();
    default int priority() {return stage().ordinal();}
    void process(ProcessorContext context);
}
