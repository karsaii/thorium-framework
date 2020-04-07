package environment;

import data.constants.Strings;

import java.nio.file.Paths;
import java.util.Objects;

public class EnvironmentProperties {
    public static final String PROJECT_DIR = Strings.PROJECT_BASE_DIRECTORY;
    public static final String DRIVER_PATH_BASE = PROJECT_DIR + Strings.DEFAULT_DRIVER_PATH;
    public static final String OPERATING_SYSTEM = System.getProperty("OS", Strings.WINDOWS);
    public static final String DRIVER_EXTENSION = (Objects.equals(OPERATING_SYSTEM, Strings.WINDOWS) ? ".exe" : "");
    public static final String FIREFOX_DRIVER_PATH = DRIVER_PATH_BASE + OPERATING_SYSTEM + "/marionette/64bit/geckodriver" + DRIVER_EXTENSION;
    public static final String CHROME_DRIVER_PATH = DRIVER_PATH_BASE + OPERATING_SYSTEM + "/googlechrome/64bit/chromedriver" + DRIVER_EXTENSION;
    public static final boolean IS_CI = Boolean.valueOf(System.getProperty("isCI", "false"));
    public static final boolean TAKE_SCREENSHOT_ON_FAILURE = Boolean.valueOf(System.getProperty("takeScreenshotOnFailure", "false"));

    public static final boolean isCI = Objects.equals(IS_CI, true);
    public static final String USER_DIR = System.getProperty("user.dir");

    public static String getUsersProjectRootDirectory() {
        var rootDir = Paths.get(".").normalize().toAbsolutePath().toString();
        if (!rootDir.startsWith(USER_DIR)) {
            throw new RuntimeException("Root directory not found in user directory.");
        }

        return rootDir;
    }
}
