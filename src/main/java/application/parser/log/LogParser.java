package application.parser.log;

/**
 * Преобразует строковое представление лога
 * в типизированную запись.
 *
 * @param <LogTypeRecord> тип результата разбора
 */
public interface LogParser<LogTypeRecord> {
    /**
     * Разбирает одну строку лога.
     *
     * @param line строка лога
     * @return типизированная запись либо {@code null} для пустой строки
     */
    LogTypeRecord parse(String line);
}
