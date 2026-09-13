package application.errorhandling.exceptions;

import application.errorhandling.ApplicationException;
import application.errorhandling.ErrorType;

public final class CommandParsingException extends ApplicationException {
    private static final long serialVersionUID = 1L;


    public CommandParsingException(String message) {
        super(ErrorType.COMMAND, message);
    }


    public CommandParsingException(String message, Throwable cause) {
        super(ErrorType.COMMAND, message, cause);
    }
}
