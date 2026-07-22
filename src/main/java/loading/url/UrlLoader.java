package loading.url;

import loading.Loader;

import java.io.IOException;
import java.util.stream.Stream;

public class UrlLoader implements Loader {
    @Override
    public Stream<String> load(String path) throws IOException {
        return Stream.empty();
    }
}
