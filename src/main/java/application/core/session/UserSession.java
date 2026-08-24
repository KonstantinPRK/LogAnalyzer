package application.core.session;

import application.errorhandling.exceptions.InputClosedException;
import application.parser.commandParser.Command;
import application.reporter.Report;
import application.userInterface.UserInterface;

import java.util.concurrent.atomic.AtomicBoolean;

public final class UserSession {
    private final String id;
    private final UserInterface userInterface;
    private final AtomicBoolean open = new AtomicBoolean(true);


    public UserSession(String id, UserInterface userInterface) {
        if (id.isBlank()) {
            throw new IllegalArgumentException("Идентификатор сессии не задан");
        }

        this.id = id;
        this.userInterface = userInterface;
    }


    public String id() {
        return id;
    }


    public boolean isOpen() {
        return open.get();
    }


    public Command requestCommand() {
        ensureOpen();

        try {
            return userInterface.requestCommand();
        } catch (InputClosedException exception) {
            close();
            throw exception;
        }
    }


    public void displayReport(Report report) {
        userInterface.displayReport(report);
    }


    public void displayError(Exception exception) {
        userInterface.displayError(exception);
    }


    public boolean close() {
        return open.compareAndSet(true, false);
    }


    private void ensureOpen() {
        if (!isOpen()) {
            throw new IllegalStateException(
                    "Сессия " + id + " уже закрыта"
            );
        }
    }
}
