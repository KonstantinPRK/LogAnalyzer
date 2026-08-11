package application.userInterface.console;

import application.components.reporting.logReport.Report;

import application.userInterface.MessageType;
import application.userInterface.UserInterface;
import org.springframework.stereotype.Component;
import application.userInterface.console.input.Input;
import application.userInterface.console.output.Output;


@Component
public class Console implements UserInterface {
    Input input;
    Output output;
    TextModifier modifier;

    @Override
    public void displayMessage(MessageType msgType, String message) {

    }

    @Override
    public void displayReport(Report report) {

    }

    @Override
    public void displayError(Exception exception) {

    }

    @Override
    public String requestCommandLine() {
        return "";
    }
}
