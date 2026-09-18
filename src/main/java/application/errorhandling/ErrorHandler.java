package application.errorhandling;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Преобразует исключения приложения
 * в сообщения для пользователя.
 */
@Component
public class ErrorHandler {
    private static final String
            UNKNOWN_ERROR = "Внутренняя ошибка: причина не указана",
            UNEXPECTED_ERROR_PREFIX = "Внутренняя ошибка: ";


    /**
     * Создает обработчик ошибок приложения.
     */
    public ErrorHandler() {
    }


    /**
     * Ищет прикладное исключение в цепочке причин
     * и формирует итоговое сообщение.
     *
     * @param exception перехваченное исключение
     * @return сообщение для пользователя
     */
    public String handle(Exception exception) {
        Throwable current = exception;
        while (current instanceof Exception currentException) {
            if (currentException instanceof ApplicationException applicationException) {
                return format(applicationException);
            }

            current = current.getCause();
        }

        return unexpectedMessage(exception);
    }


    /**
     * Добавляет к сообщению прикладного исключения
     * префикс его категории.
     *
     * @param exception прикладное исключение
     * @return отформатированное сообщение
     */
    private String format(ApplicationException exception) {
        return prefix(exception.type()) + exception.getMessage();
    }


    /**
     * Возвращает пользовательский префикс для категории ошибки.
     *
     * @param type категория ошибки
     * @return префикс сообщения
     */
    private String prefix(ErrorType type) {
        return switch (type) {
            case COMMAND -> "Ошибка команды: ";
            case SOURCE -> "Ошибка источника логов: ";
            case LOG_LOADING -> "Ошибка загрузки логов: ";
            case LOG_PARSING -> "Ошибка формата лога: ";
        };
    }


    /**
     * Формирует сообщение для исключения,
     * не относящегося к ошибкам приложения.
     *
     * @param exception неожиданное исключение
     * @return сообщение для пользователя
     */
    private String unexpectedMessage(Exception exception) {
        String message = exception.getMessage();
        if (Objects.isNull(message) || message.isBlank()) return UNKNOWN_ERROR;

        return UNEXPECTED_ERROR_PREFIX + message;
    }
}
