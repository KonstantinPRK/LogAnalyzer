package application.components.loading;

import java.io.IOException;
import java.util.stream.Stream;

public interface Loader {
    Stream<String> load(String path) throws IOException;
}