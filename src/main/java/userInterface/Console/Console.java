package userInterface.Console;

import application.parser.commandParser.Command;
import application.parser.commandParser.CommandParser;
import application.reporter.Report;
import org.springframework.stereotype.Component;
import userInterface.UserInterface;

import java.util.Objects;

@Component
public final class Console implements UserInterface {
    private final Terminal terminal;
    private final CommandParser commandParser;

    public Console(Terminal terminal, CommandParser commandParser) {
        this.terminal = Objects.requireNonNull(terminal, "terminal");
        this.commandParser = Objects.requireNonNull(commandParser, "commandParser");
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
        terminal.printError(exception);
    }
}
