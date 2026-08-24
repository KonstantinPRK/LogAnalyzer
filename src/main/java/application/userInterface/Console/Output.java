package application.userInterface.Console;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Objects;

@Component
public final class Output {
    private final PrintStream output;


    public Output(@Qualifier("consoleOutput") PrintStream output) {
        this.output = Objects.requireNonNull(output, "output");
    }


    public void print(String text) {
        output.print(Objects.requireNonNull(text, "text"));
        output.flush();
    }


    public void println(String text) {
        output.println(Objects.requireNonNull(text, "text"));
        output.flush();
    }


    public void printlnError(String text) {
        output.println(Objects.requireNonNull(text, "text"));
        output.flush();
    }
}
