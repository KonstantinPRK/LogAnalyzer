package application.errorhandling.exceptions;

import application.errorhandling.ApplicationException;
import application.errorhandling.ErrorType;

public final class SourceParsingException extends ApplicationException {
    private static final long serialVersionUID = 1L;


    public SourceParsingException(String message) {
        super(ErrorType.SOURCE, message);
    }


    public SourceParsingException(String message, Throwable cause) {
        super(ErrorType.SOURCE, message, cause);
    }
}
