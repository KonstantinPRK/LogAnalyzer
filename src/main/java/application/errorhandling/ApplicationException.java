package application.errorhandling;

/**
 * Базовое исключение приложения
 * с категорией пользовательской ошибки.
 */
public abstract class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Категория ошибки,
     * определяющая пользовательский префикс сообщения.
     */
    private final ErrorType type;


    /**
     * Создаёт исключение с категорией и сообщением.
     *
     * @param type категория ошибки
     * @param message описание ошибки
     */
    protected ApplicationException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }

    /**
     * Создаёт исключение с категорией, сообщением и причиной.
     *
     * @param type категория ошибки
     * @param message описание ошибки
     * @param cause исходная причина
     */
    protected ApplicationException(ErrorType type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }


    /**
     * Возвращает категорию ошибки.
     *
     * @return категория ошибки
     */
    public ErrorType type() {
        return type;
    }
}
