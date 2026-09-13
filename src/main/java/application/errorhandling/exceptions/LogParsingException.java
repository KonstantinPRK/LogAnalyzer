package application.errorhandling.exceptions;

import application.errorhandling.ApplicationException;
import application.errorhandling.ErrorType;

public final class LogParsingException extends ApplicationException {
    private static final long serialVersionUID = 1L;


    public LogParsingException(String message) {
        super(ErrorType.LOG_PARSING, message);
    }


    public LogParsingException(String message, Throwable cause) {
        super(ErrorType.LOG_PARSING, message, cause);
    }
}
