package application.loader;

import java.util.stream.Stream;

@FunctionalInterface
public interface Loader {
    Stream<String> load();
}
