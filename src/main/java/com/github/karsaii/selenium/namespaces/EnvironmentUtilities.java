package com.github.karsaii.selenium.namespaces;

import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.selenium.constants.EnvironmentPropertyConstants;

import java.nio.file.Paths;

public interface EnvironmentUtilities {
    static String getUsersProjectRootDirectory() {
        var rootDir = Paths.get(".").normalize().toAbsolutePath().toString();
        if (!rootDir.startsWith(EnvironmentPropertyConstants.USER_DIR)) {
            throw new RuntimeException("Root directory not found in user directory" + CoreFormatterConstants.END_LINE);
        }

        return rootDir;
    }
}
