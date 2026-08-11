package application.core.processor.chains;

//один конкретный анализ внутри класса

import application.core.configuration.ProcessingStage;
import application.core.processor.ProcessorContext;

//а сам команд по сути будет интерфейсом, но что может быть общего между такими разными обработчиками
public interface ProcessorComponent {
    ProcessingStage stage();
    default int priority() {return stage().ordinal();}
    void process(ProcessorContext context);
}
