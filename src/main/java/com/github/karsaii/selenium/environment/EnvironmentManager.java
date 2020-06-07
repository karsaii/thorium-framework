package com.github.karsaii.selenium.environment;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.github.karsaii.selenium.constants.EnvironmentPropertyConstants;
import com.github.karsaii.selenium.namespaces.Driver;

public class EnvironmentManager {
    private static final String chromeDriver = "webdriver.chrome.driver";
    public static WebDriver driver;

    public static WebDriver getChromeDriver(ChromeOptions options) {
        return new ChromeDriver(options);
    }

    public static ChromeOptions getChromeOptions() {
        var options = new ChromeOptions();

        options.addArguments("--disable-translate");
        options.addArguments("--disable-infobars");

        if (EnvironmentPropertyConstants.isCI) {
            options.addArguments("--headless");
            options.addArguments("--verbose");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--whitelisted-ips=''");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        options.addArguments("--lang=en");

        return options;
    }

    public static WebDriver getNewDriver() {
        if (StringUtils.isBlank(System.getProperty(chromeDriver))) {
            System.setProperty(chromeDriver, System.getProperty("projectdirectory") + "/src/test/resources/selenium_standalone_binaries/windows/googlechrome/64bit/chromedriver.exe");
        }

        return getChromeDriver(getChromeOptions());
    }

    public static void initWebDriver() {
        driver = getNewDriver();
    }

    public static void shutDownDriver() {
        shutDownDriverInstance(driver);
    }

    public static void shutDownDriverInstance(WebDriver driver) {
        Driver.quitDriver().apply(driver);
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
