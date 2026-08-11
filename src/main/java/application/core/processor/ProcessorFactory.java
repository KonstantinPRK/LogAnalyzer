package application.core.processor;

import application.core.processor.chains.ProcessorComponent;
import application.core.session.Session;
import org.springframework.stereotype.Component;
import application.userInterface.UserInterface;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Component
public class ProcessorFactory {
    PriorityQueue<ProcessorComponent> components;

    public ProcessorFactory(List<ProcessorComponent> unsortedComponents){
        this.components = new PriorityQueue<>(Comparator.comparingInt(ProcessorComponent::priority));
        this.components.addAll(unsortedComponents);
    }


    public CommandProcessor newCommand(Session session) {

        return new CommandProcessor(session.getUI(), components);
    }
}