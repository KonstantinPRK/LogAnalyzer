package application.parser.sourceParser;

import application.errorhandling.exceptions.SourceParsingException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public final class FileSourceParser implements SourceParser<List<Path>> {
    private static final String GLOB_CHARACTERS = "*?[{";


    @Override
    public List<Path> parse(String source) {
        String normalizedSource = requireSource(source);

        try {
            Path sourcePath = Path.of(normalizedSource);
            if (!containsGlob(normalizedSource)) {
                return parseLiteralSource(sourcePath);
            }

            return parseGlobSource(sourcePath);
        } catch (InvalidPathException exception) {
            throw new SourceParsingException("Некорректный локальный источник: " + source, exception);
        } catch (SecurityException exception) {
            throw new SourceParsingException("Нет доступа к локальному источнику: " + source, exception);
        }
    }


    private List<Path> parseLiteralSource(Path sourcePath) {
        Path absolutePath = sourcePath.toAbsolutePath().normalize();

        if (Files.isRegularFile(absolutePath)) {
            return List.of(absolutePath);
        }

        if (Files.isDirectory(absolutePath)) {
            return walkRegularFiles(absolutePath, null, sourcePath.toString());
        }

        throw new SourceParsingException("Локальный источник не найден: " + sourcePath);
    }


    private List<Path> parseGlobSource(Path sourcePattern) {
        Path absolutePattern = sourcePattern.toAbsolutePath().normalize();
        Path searchRoot = findSearchRoot(absolutePattern);

        if (!Files.isDirectory(searchRoot)) {
            throw new SourceParsingException(
                    "Корневая директория шаблона не найдена: " + searchRoot
            );
        }

        PathMatcher matcher;
        try {
            matcher = FileSystems.getDefault().getPathMatcher("glob:" + absolutePattern);
        } catch (IllegalArgumentException exception) {
            throw new SourceParsingException(
                    "Некорректный glob-шаблон: " + sourcePattern,
                    exception
            );
        }

        return walkRegularFiles(searchRoot, matcher, sourcePattern.toString());
    }


    private List<Path> walkRegularFiles(
            Path searchRoot,
            PathMatcher matcher,
            String sourceDescription
    ) {
        try (Stream<Path> paths = Files.walk(searchRoot)) {
            List<Path> result = paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.toAbsolutePath().normalize())
                    .filter(path -> Objects.isNull(matcher) || matcher.matches(path))
                    .sorted()
                    .toList();

            if (result.isEmpty()) {
                throw new SourceParsingException(
                        "Не найдено ни одного лог-файла: " + sourceDescription
                );
            }

            return result;
        } catch (IOException exception) {
            throw new SourceParsingException(
                    "Не удалось просмотреть локальный источник: " + sourceDescription,
                    exception
            );
        }
    }


    private Path findSearchRoot(Path absolutePattern) {
        Path searchRoot = absolutePattern.getRoot();

        for (Path part : absolutePattern) {
            if (containsGlob(part.toString())) {
                break;
            }

            searchRoot = searchRoot.resolve(part);
        }

        return searchRoot;
    }


    private boolean containsGlob(String value) {
        for (int index = 0; index < value.length(); index++) {
            if (GLOB_CHARACTERS.indexOf(value.charAt(index)) >= 0) {
                return true;
            }
        }

        return false;
    }


    private String requireSource(String source) {
        if (Objects.isNull(source) || source.isBlank()) {
            throw new SourceParsingException("Источник локальных логов не задан");
        }

        return source.trim();
    }
}
