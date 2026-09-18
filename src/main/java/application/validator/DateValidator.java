package application.validator;

import java.time.OffsetDateTime;

/**
 * Проверяет принадлежность временной метки
 * разрешённому диапазону дат.
 */
public interface DateValidator {
    /**
     * Проверяет временную метку записи лога.
     *
     * @param timestamp временная метка
     * @return {@code true}, если запись входит в диапазон
     */
    boolean validate(OffsetDateTime timestamp);
}
