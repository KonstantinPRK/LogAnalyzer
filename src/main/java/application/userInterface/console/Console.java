package application.userInterface.console;

import application.userInterface.UserInterface;
import application.userInterface.console.input.Input;
import application.userInterface.console.output.Editor;
import application.userInterface.console.output.Output;
import org.springframework.stereotype.Component;

@Component
public class Console implements UserInterface {
    private Input input;
    private Output output;
    private Editor edit;


}
