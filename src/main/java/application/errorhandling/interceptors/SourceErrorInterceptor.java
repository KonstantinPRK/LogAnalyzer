package application.errorhandling.interceptors;

import application.errorhandling.ErrorInterceptor;
import application.errorhandling.exceptions.SourceParsingException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class SourceErrorInterceptor implements ErrorInterceptor {
    private static final String PREFIX = "Ошибка источника логов: ";


    @Override
    public Optional<String> intercept(Exception exception) {
        if (exception instanceof SourceParsingException) {
            return Optional.of(PREFIX + exception.getMessage());
        }

        return Optional.empty();
    }
}
