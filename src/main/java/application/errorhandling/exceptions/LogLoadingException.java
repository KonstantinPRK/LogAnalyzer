package application.errorhandling.exceptions;

import application.errorhandling.ApplicationException;
import application.errorhandling.ErrorType;

/**
 * Сообщает об ошибке чтения локального или удалённого лога.
 */
public final class LogLoadingException extends ApplicationException {
    private static final long serialVersionUID = 1L;


    /**
     * Создаёт исключение с описанием ошибки.
     *
     * @param message описание ошибки
     */
    public LogLoadingException(String message) {
        super(ErrorType.LOG_LOADING, message);
    }

    /**
     * Создаёт исключение с описанием и исходной причиной.
     *
     * @param message описание ошибки
     * @param cause исходная причина
     */
    public LogLoadingException(String message, Throwable cause) {
        super(ErrorType.LOG_LOADING, message, cause);
    }
}
