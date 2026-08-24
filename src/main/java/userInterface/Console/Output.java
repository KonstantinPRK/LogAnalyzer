package userInterface.Console;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Objects;

@Component
public final class Output {
    private final PrintStream standardOutput;
    private final PrintStream errorOutput;

    public Output(
            @Qualifier("consoleStandardOutput") PrintStream standardOutput,
            @Qualifier("consoleErrorOutput") PrintStream errorOutput
    ) {
        this.standardOutput = Objects.requireNonNull(standardOutput, "standardOutput");
        this.errorOutput = Objects.requireNonNull(errorOutput, "errorOutput");
    }

    public void print(String text) {
        standardOutput.print(text);
        standardOutput.flush();
    }

    public void println(String text) {
        standardOutput.println(text);
        standardOutput.flush();
    }

    public void printlnError(String text) {
        errorOutput.println(text);
        errorOutput.flush();
    }
}
