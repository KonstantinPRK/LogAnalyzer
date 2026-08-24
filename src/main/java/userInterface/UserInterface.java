package userInterface;

import application.parser.commandParser.Command;
import application.reporter.Report;

public interface UserInterface {
    Command requestCommand();

    void displayReport(Report report);

    void displayError(Exception exception);
}
