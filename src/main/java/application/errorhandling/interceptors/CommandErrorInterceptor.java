package application.errorhandling.interceptors;

import application.errorhandling.ErrorInterceptor;
import application.errorhandling.exceptions.CommandParsingException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class CommandErrorInterceptor implements ErrorInterceptor {
    private static final String PREFIX = "Ошибка команды: ";


    @Override
    public Optional<String> intercept(Exception exception) {
        if (exception instanceof CommandParsingException) {
            return Optional.of(PREFIX + exception.getMessage());
        }

        return Optional.empty();
    }
}
