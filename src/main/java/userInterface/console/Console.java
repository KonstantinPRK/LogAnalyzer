package userInterface.console;

import collection.receiving.RequestConfig;
import userInterface.UserInterface;
import collection.outputting.userInterface.console.input.Input;
import org.springframework.stereotype.Component;
import userInterface.console.output.Editor;
import userInterface.console.output.Output;

@Component
public class Console implements UserInterface {
    private Input input;
    private Output output;
    private Editor edit;


    @Override
    public RequestConfig getConfiguration(String[] args) {
        return null;
    }

    @Override
    public void displayReport(String report) {

    }

    @Override
    public void displayError(String message) {

    }
}
