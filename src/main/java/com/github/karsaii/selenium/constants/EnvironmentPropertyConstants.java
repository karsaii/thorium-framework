package com.github.karsaii.selenium.constants;

import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;

import java.nio.file.Paths;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isBlank;

public abstract class EnvironmentPropertyConstants {
    public static final String PROJECT_WORKING_DIRECTORY = System.getProperty("projectdirectory");
    public static final String PROJECT_BASE_DIRECTORY = isBlank(PROJECT_WORKING_DIRECTORY) ? Paths.get(".").toAbsolutePath().normalize().toString() : PROJECT_WORKING_DIRECTORY;
    public static final String OPERATING_SYSTEM = System.getProperty("OS", SeleniumFormatterConstants.WINDOWS);
    public static final String DRIVER_EXTENSION = (Objects.equals(OPERATING_SYSTEM, SeleniumFormatterConstants.WINDOWS) ? ".exe" : "");

    public static final String USER_DIR = System.getProperty("user.dir");

    public static final boolean IS_CI = Boolean.parseBoolean(System.getProperty("isCI", "false"));
    public static final boolean TAKE_SCREENSHOT_ON_FAILURE = Boolean.parseBoolean(System.getProperty("takeScreenshotOnFailure", "false"));

    public static final boolean isCI = Objects.equals(IS_CI, true);
}
