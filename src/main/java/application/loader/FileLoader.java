package application.loader;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileLoader implements Loader {
    @Override
    public List<Path> getPaths(String idealPath, String s) {
        Path startPath = Paths.get(idealPath);

        // Проверяем, существует ли путь и является ли он папкой
        if (!Files.exists(startPath) || !Files.isDirectory(startPath)) {
            return null;
        }

        try (Stream<Path> stream = Files.walk(startPath)) {
            List<Path> result = stream
                    // Оставляем только файлы (игнорируем папки)
                    .filter(Files::isRegularFile)
                    // Проверяем, заканчивается ли имя файла на нужное расширение (например, .txt)
                    .filter(path -> path.toString().endsWith(s))
                    .collect(Collectors.toList());

            // Если ничего не нашли, возвращаем null, как в условии
            return result.isEmpty() ? null : result;

        } catch (IOException e) {
            // В случае ошибки ввода-вывода возвращаем null
            return null;
        }
    }
}
