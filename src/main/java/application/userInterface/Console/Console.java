package application.userInterface.Console;

import application.errorhandling.ErrorHandler;
import application.parser.commandParser.Command;
import application.parser.commandParser.CommandParser;
import application.reporter.Report;
import org.springframework.stereotype.Component;
import application.userInterface.UserInterface;

import java.util.Objects;

@Component
public final class Console implements UserInterface {
    private final Terminal terminal;
    private final CommandParser commandParser;
    private final ErrorHandler errorHandler;


    public Console(Terminal terminal, CommandParser commandParser, ErrorHandler errorHandler) {
        this.terminal = Objects.requireNonNull(terminal, "terminal");
        this.commandParser = Objects.requireNonNull(commandParser, "commandParser");
        this.errorHandler = Objects.requireNonNull(errorHandler, "errorHandler");
    }


    @Override
    public Command requestCommand() {
        String commandLine = terminal.requestCommandLine();
        return commandParser.parse(commandLine);
    }


    @Override
    public void displayReport(Report report) {
        Objects.requireNonNull(report, "report");
        terminal.printReport(report.formattedReport());
    }


    @Override
    public void displayError(Exception exception) {
        Objects.requireNonNull(exception, "exception");
        terminal.printError(errorHandler.handle(exception));
    }
}
