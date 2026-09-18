package application.errorhandling.exceptions;

import application.errorhandling.ApplicationException;
import application.errorhandling.ErrorType;

/**
 * Сообщает об ошибке проверки
 * или преобразования источника логов.
 */
public final class SourceParsingException extends ApplicationException {
    private static final long serialVersionUID = 1L;


    /**
     * Создаёт исключение с описанием ошибки.
     *
     * @param message описание ошибки
     */
    public SourceParsingException(String message) {
        super(ErrorType.SOURCE, message);
    }

    /**
     * Создаёт исключение с описанием и исходной причиной.
     *
     * @param message описание ошибки
     * @param cause исходная причина
     */
    public SourceParsingException(String message, Throwable cause) {
        super(ErrorType.SOURCE, message, cause);
    }
}
