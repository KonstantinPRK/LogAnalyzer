package application.aggregator;

/**
 * Объединяет результаты нескольких сборщиков статистики
 * для одного типа логов.
 *
 * @param <LogType> тип обрабатываемой записи лога
 * @param <ResultType> тип итоговой статистики
 */
public interface Aggregator<LogType, ResultType> {
    /**
     * Передаёт запись лога всем связанным сборщикам статистики.
     *
     * @param log запись лога
     */
    void accept(LogType log);


    /**
     * Возвращает накопленный результат.
     *
     * @return итоговая статистика
     */
    ResultType getResult();
}
