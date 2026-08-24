package application.errorhandling.interceptors;

import application.errorhandling.ErrorInterceptor;
import application.errorhandling.exceptions.InputClosedException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class InputErrorInterceptor implements ErrorInterceptor {
    private static final String PREFIX = "Ввод завершён: ";


    @Override
    public Optional<String> intercept(Exception exception) {
        if (exception instanceof InputClosedException) {
            return Optional.of(PREFIX + exception.getMessage());
        }

        return Optional.empty();
    }
}
