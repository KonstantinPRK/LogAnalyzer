package application.errorhandling.exceptions;

import application.errorhandling.ApplicationException;
import application.errorhandling.ErrorType;

public final class LogLoadingException extends ApplicationException {
    private static final long serialVersionUID = 1L;


    public LogLoadingException(String message) {
        super(ErrorType.LOG_LOADING, message);
    }


    public LogLoadingException(String message, Throwable cause) {
        super(ErrorType.LOG_LOADING, message, cause);
    }
}
