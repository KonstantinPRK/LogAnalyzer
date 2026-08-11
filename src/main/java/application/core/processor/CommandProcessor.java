package application.core.processor;

import application.components.reporting.logReport.Report;
import application.core.processor.chains.ProcessorComponent;
import application.core.session.Session;
import application.userInterface.UserInterface;

import java.util.PriorityQueue;
import java.util.concurrent.Callable;

public class CommandProcessor implements Callable<Report> {
    PriorityQueue<ProcessorComponent> components;
    UserInterface UI;

    public CommandProcessor(UserInterface UI, PriorityQueue<ProcessorComponent> components) {

    }

    @Override
    public Report call() throws Exception {
        return null;
    }
}
