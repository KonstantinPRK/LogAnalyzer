package application.errorhandling.exceptions;

public final class CommandParsingException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    public CommandParsingException(String message) {
        super(message);
    }


    public CommandParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
