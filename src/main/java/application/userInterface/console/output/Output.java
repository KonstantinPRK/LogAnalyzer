package application.userInterface.console.output;

import org.springframework.stereotype.Component;
import java.io.PrintStream;

@Component
public class Output {
    private final PrintStream printer;


    public Output(PrintStream printer) {
        this.printer = printer;
    }


    public void print(String... str) {
        for(String line : str) printer.print(line);
    }


    public void printEmptyLines(int count) {
        if(count == 0) printer.println();
        for(int i = 0; i < count; i++) printer.println();
    }
}
