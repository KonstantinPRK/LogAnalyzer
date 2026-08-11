package application.userInterface;

import application.components.reporting.logReport.Report;

public interface UserInterface {
    void displayMessage(MessageType msgType, String message);
    void displayReport(Report report);
    void displayError(Exception exception);
    String requestCommandLine();
}
