package application.errorhandling.exceptions;

import application.errorhandling.ApplicationException;
import application.errorhandling.ErrorType;

/**
 * Сообщает об ошибке разбора аргументов командной строки.
 */
public final class CommandParsingException extends ApplicationException {
    private static final long serialVersionUID = 1L;


    /**
     * Создаёт исключение с описанием ошибки.
     *
     * @param message описание ошибки
     */
    public CommandParsingException(String message) {
        super(ErrorType.COMMAND, message);
    }

    /**
     * Создаёт исключение с описанием и исходной причиной.
     *
     * @param message описание ошибки
     * @param cause исходная причина
     */
    public CommandParsingException(String message, Throwable cause) {
        super(ErrorType.COMMAND, message, cause);
    }
}
