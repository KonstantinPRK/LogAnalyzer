package application.errorhandling;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class ErrorHandler {
    private static final String UNKNOWN_ERROR = "Внутренняя ошибка: причина не указана", UNEXPECTED_ERROR_PREFIX = "Внутренняя ошибка: ";


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


    private String format(ApplicationException exception) {
        return prefix(exception.type()) + exception.getMessage();
    }


    private String prefix(ErrorType type) {
        return switch (type) {
            case COMMAND -> "Ошибка команды: ";
            case SOURCE -> "Ошибка источника логов: ";
            case LOG_LOADING -> "Ошибка загрузки логов: ";
            case LOG_PARSING -> "Ошибка формата лога: ";
        };
    }


    private String unexpectedMessage(Exception exception) {
        String message = exception.getMessage();
        if (Objects.isNull(message) || message.isBlank()) {
            return UNKNOWN_ERROR;
        }

        return UNEXPECTED_ERROR_PREFIX + message;
    }
}
