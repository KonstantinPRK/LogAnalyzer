package application.errorhandling;

import java.util.Optional;

@FunctionalInterface
public interface ErrorInterceptor {
    Optional<String> intercept(Exception exception);
}
