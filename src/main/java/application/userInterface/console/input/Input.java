package application.userInterface.console.input;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Input {
    private final Scanner scan;


    public Input(Scanner scan) {
        this.scan = scan;
    }


}
