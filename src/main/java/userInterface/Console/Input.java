package userInterface.Console;

import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;


@Component
public final class Input {
    private static final String CLOSED_INPUT_MESSAGE = "Поток ввода закрыт";

    private final Scanner scanner;

    public Input(Scanner scanner) {
        this.scanner = Objects.requireNonNull(scanner, "scanner");
    }

    public String readLine() {
        if (!scanner.hasNextLine()) {
            throw new NoSuchElementException(CLOSED_INPUT_MESSAGE);
        }

        return scanner.nextLine();
    }
}
