package application.parser.source;

/**
 * Проверяет и преобразует строковое описание источника логов.
 *
 * @param <ParsedSource> тип подготовленного источника
 */
@FunctionalInterface
public interface SourceParser<ParsedSource> {
    /**
     * Разбирает строковое описание источника.
     *
     * @param source исходное описание
     * @return подготовленный источник
     */
    ParsedSource parse(String source);
}
