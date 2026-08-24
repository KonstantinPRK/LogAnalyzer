package application.errorhandling;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public final class ErrorHandler {
    private static final String UNKNOWN_ERROR = "Внутренняя ошибка: причина не указана", UNEXPECTED_ERROR_PREFIX = "Внутренняя ошибка: ";
    private final List<ErrorInterceptor> interceptors;


    public ErrorHandler(List<ErrorInterceptor> interceptors) {
        this.interceptors = List.copyOf(interceptors);
    }


    public String handle(Exception exception) {
        Throwable current = exception;
        while (current instanceof Exception currentException) {
            Optional<String> message = intercept(currentException);
            if (message.isPresent()) {
                return message.get();
            }

            current = current.getCause();
        }

        return unexpectedMessage(exception);
    }


    private Optional<String> intercept(Exception exception) {
        for (ErrorInterceptor interceptor : interceptors) {
            Optional<String> message = interceptor.intercept(exception);
            if (message.isPresent()) {
                return message;
            }
        }

        return Optional.empty();
    }


    private String unexpectedMessage(Exception exception) {
        String message = exception.getMessage();
        if (Objects.isNull(message) || message.isBlank()) {
            return UNKNOWN_ERROR;
        }

        return UNEXPECTED_ERROR_PREFIX + message;
    }
}
