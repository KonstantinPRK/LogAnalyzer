package application.loader;

import java.util.stream.Stream;

/**
 * Предоставляет поток строк из источника логов.
 */
@FunctionalInterface
public interface Loader {
    /**
     * Открывает источник и возвращает поток его строк.
     *
     * @return поток строк лога
     */
    Stream<String> load();
}
