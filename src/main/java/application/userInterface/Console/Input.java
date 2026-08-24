package application.userInterface.Console;

import application.errorhandling.exceptions.InputClosedException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public final class Input {
    private static final String CLOSED_INPUT_MESSAGE = "Поток ввода закрыт";
    private final Scanner scanner;


    public Input(Scanner scanner) {
        this.scanner = scanner;
    }


    public String readLine() {
        if (!scanner.hasNextLine()) {
            throw new InputClosedException(CLOSED_INPUT_MESSAGE);
        }

        return scanner.nextLine();
    }
}
