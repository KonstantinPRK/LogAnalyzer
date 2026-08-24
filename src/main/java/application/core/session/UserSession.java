package application.core.session;

import application.errorhandling.exceptions.InputClosedException;
import application.parser.commandParser.Command;
import application.reporter.Report;
import application.userInterface.UserInterface;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public final class UserSession {
    private final String id;
    private final UserInterface userInterface;
    private final AtomicBoolean open = new AtomicBoolean(true);


    public UserSession(String id, UserInterface userInterface) {
        Objects.requireNonNull(id, "id");
        if (id.isBlank()) {
            throw new IllegalArgumentException("Идентификатор сессии не задан");
        }

        this.id = id;
        this.userInterface = Objects.requireNonNull(
                userInterface,
                "userInterface"
        );
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
            return Objects.requireNonNull(
                    userInterface.requestCommand(),
                    "Пользовательский интерфейс вернул null вместо команды"
            );
        } catch (InputClosedException exception) {
            close();
            throw exception;
        }
    }


    public void displayReport(Report report) {
        userInterface.displayReport(
                Objects.requireNonNull(report, "report")
        );
    }


    public void displayError(Exception exception) {
        userInterface.displayError(
                Objects.requireNonNull(exception, "exception")
        );
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
