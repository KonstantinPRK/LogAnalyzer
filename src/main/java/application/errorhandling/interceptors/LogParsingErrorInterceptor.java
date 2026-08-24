package application.errorhandling.interceptors;

import application.errorhandling.ErrorInterceptor;
import application.errorhandling.exceptions.LogParsingException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class LogParsingErrorInterceptor implements ErrorInterceptor {
    private static final String PREFIX = "Ошибка формата лога: ";


    @Override
    public Optional<String> intercept(Exception exception) {
        if (exception instanceof LogParsingException) {
            return Optional.of(PREFIX + exception.getMessage());
        }

        return Optional.empty();
    }
}
