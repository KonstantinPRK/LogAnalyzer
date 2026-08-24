package application.errorhandling.exceptions;

public final class InputClosedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InputClosedException(String message) {
        super(message);
    }
}
