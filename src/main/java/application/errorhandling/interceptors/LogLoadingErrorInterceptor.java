package application.errorhandling.interceptors;

import application.errorhandling.ErrorInterceptor;
import application.errorhandling.exceptions.LogLoadingException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class LogLoadingErrorInterceptor implements ErrorInterceptor {
    private static final String PREFIX = "Ошибка загрузки логов: ";


    @Override
    public Optional<String> intercept(Exception exception) {
        if (exception instanceof LogLoadingException) {
            return Optional.of(PREFIX + exception.getMessage());
        }

        return Optional.empty();
    }
}
