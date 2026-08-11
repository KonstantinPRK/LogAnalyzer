package application.userInterface;

public enum MessageType {
    INFO,
    RESULT,
    SUCCESS,        // успешное завершение
    WARNING,        // предупреждение
    ERROR,          // ошибка (но не исключение)
    INTRODUCTION,   // приветствие
    TEMPLATE,       // пример команды
    PROGRESS,       // статус выполнения
}
