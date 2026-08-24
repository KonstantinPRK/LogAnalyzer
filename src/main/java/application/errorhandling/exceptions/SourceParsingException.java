package application.errorhandling.exceptions;

public final class SourceParsingException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    public SourceParsingException(String message) {
        super(message);
    }


    public SourceParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
