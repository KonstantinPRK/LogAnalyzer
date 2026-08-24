package application.validator;

import java.time.OffsetDateTime;

public interface DateValidator {
    boolean validate(OffsetDateTime timestamp);
}
