package application.core.processor.chains;

import application.components.verification.Validator;
import application.core.processor.ProcessorContext;

public class ProcessorSetter implements ProcessorComponent {
    Validator validator; // все виды валидаторов

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void process(ProcessorContext context) {

    }
    // валидация путей и попытка загрузки - если что-то не так то работа с теми путями что есть или выдача исключения если хоть что нибудь не так // предложение ввести другой путь или начать все сначала - не удалось открыть следующие папки или файлы и перечислить от одной до бесконечности хотя насчет перечисления подумаю

}
