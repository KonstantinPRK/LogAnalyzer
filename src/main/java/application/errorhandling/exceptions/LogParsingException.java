package application.errorhandling.exceptions;

public final class LogParsingException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    public LogParsingException(String message) {
        super(message);
    }


    public LogParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
