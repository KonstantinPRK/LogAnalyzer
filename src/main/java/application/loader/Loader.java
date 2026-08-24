package application.loader;

import java.nio.file.Path;
import java.util.List;

public interface Loader {
    List<Path> getPaths(String idealPath, String s);
}
