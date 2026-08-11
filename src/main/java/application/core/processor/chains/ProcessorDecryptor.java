package application.core.processor.chains;

import application.components.parsing.Parser;
import application.core.configuration.ProcessingStage;
import application.core.processor.ProcessorContext;
import org.springframework.stereotype.Component;

import static application.core.configuration.ProcessingStage.DECRYPTOR;

@Component
public class ProcessorDecryptor implements ProcessorComponent{
    Parser parser; // все парсеры дешифровка в читаемый для программы вид

// тоже цепочкой, нужно придумать едины алгоритм для всех компонентов и доя главного проуессора 

/*
итеративный лист илм связаный список нужных элементов, парсеров, валидаторов и тд 

заткм их поэтапное прохождение 

и логику возвратов надо придумать 
то есть отражения ошибок и возврата в самое начало

логично тогда проверку доступа к файлам проверять волбще на самом начале и спрашивать пользователя, готов ли он продолжить если доступно не все
*/

    public ProcessorDecryptor(){

    }

    @Override
    public ProcessingStage stage() {
        return DECRYPTOR;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void process(ProcessorContext context) {

    }
}
