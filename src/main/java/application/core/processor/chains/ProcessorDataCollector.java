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
    // подкапотная валидация каждого лога и агрегация данных определение количества логов неизвестного формата и сколько из них проанализировано
}
