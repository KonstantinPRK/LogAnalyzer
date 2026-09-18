package application.collector;

/**
 * Собирает одну метрику из последовательности записей лога.
 *
 * @param <LogType> тип обрабатываемой записи лога
 * @param <MetricType> тип вычисляемой метрики
 */
public interface Collector<LogType, MetricType> {
    /**
     * Учитывает одну запись лога при вычислении метрики.
     *
     * @param log запись лога
     */
    void accept(LogType log);


    /**
     * Возвращает накопленное значение метрики.
     *
     * @return значение метрики
     */
    MetricType getResult();
}
