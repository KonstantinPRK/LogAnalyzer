package application.core.session;

import application.core.analysis.Analizator;
import application.parser.commandParser.Command;
import application.reporter.Report;
import userInterface.UserInterface;

public class UserSession {
    private final String sessionID;
    private final UserInterface UI;

    private Command lastCommand;
    private Analizator lastAnalizator;
    private Report lastReport;


    public UserSession(String sessionID, UserInterface ui) {
        this.sessionID = sessionID;
        UI = ui;
    }

    public void setAnalizator(Analizator analizator){
        lastAnalizator = analizator;
    }

    public Analizator getAnalizator(){
        return lastAnalizator;
    }

    public Command getCommand(){
        return lastCommand;
    }

    public Command requestCommand(){
        lastCommand = UI.requestCommand();
        return lastCommand;
    }

    public void displayReport(Report report){
        UI.displayReport(report);
    }

    public void displayError(Exception exception){
        UI.displayError(exception);
    }

    public boolean isOpen() {
        return true; // пока так
    }
}
