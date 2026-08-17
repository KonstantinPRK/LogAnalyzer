package application.core.processor.chains;

import application.components.parsing.Parser;
import application.core.configuration.ProcessingStage;
import application.core.processor.ProcessorContext;
import org.springframework.stereotype.Component;

import static application.core.configuration.ProcessingStage.DECRYPTOR;

@Component
public class ProcessorDecryptor implements ProcessorComponent{
    Parser parser;

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
