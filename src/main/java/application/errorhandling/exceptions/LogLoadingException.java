package application.errorhandling.exceptions;

public final class LogLoadingException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    public LogLoadingException(String message) {
        super(message);
    }


    public LogLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
