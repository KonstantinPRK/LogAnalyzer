package application.errorhandling.exceptions;

import application.errorhandling.ApplicationException;
import application.errorhandling.ErrorType;

/**
 * Сообщает об ошибке преобразования строки NGINX-лога.
 */
public final class LogParsingException extends ApplicationException {
    private static final long serialVersionUID = 1L;


    /**
     * Создаёт исключение с описанием ошибки.
     *
     * @param message описание ошибки
     */
    public LogParsingException(String message) {
        super(ErrorType.LOG_PARSING, message);
    }

    /**
     * Создаёт исключение с описанием и исходной причиной.
     *
     * @param message описание ошибки
     * @param cause исходная причина
     */
    public LogParsingException(String message, Throwable cause) {
        super(ErrorType.LOG_PARSING, message, cause);
    }
}
