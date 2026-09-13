package application.errorhandling;

public abstract class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final ErrorType type;


    protected ApplicationException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }


    protected ApplicationException(ErrorType type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }


    public ErrorType type() {
        return type;
    }
}
