package selenium.namespaces;

import data.constants.Strings;
import selenium.constants.EnvironmentPropertyConstants;

import java.nio.file.Paths;

public interface EnvironmentUtilities {
    static String getUsersProjectRootDirectory() {
        var rootDir = Paths.get(".").normalize().toAbsolutePath().toString();
        if (!rootDir.startsWith(EnvironmentPropertyConstants.USER_DIR)) {
            throw new RuntimeException("Root directory not found in user directory" + Strings.END_LINE);
        }

        return rootDir;
    }
}
