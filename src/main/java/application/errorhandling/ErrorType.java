package application.errorhandling;

/**
 * Категории ошибок, отображаемые пользователю.
 */
public enum ErrorType {
    /** Ошибка аргументов команды. */
    COMMAND,

    /** Ошибка описания источника логов. */
    SOURCE,

    /** Ошибка загрузки данных из источника. */
    LOG_LOADING,

    /** Ошибка разбора строки лога. */
    LOG_PARSING
}
