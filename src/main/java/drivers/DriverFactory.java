package drivers;

import data.Data;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;

public class DriverFactory {
    private static final HashMap<String, HashMap<String, Data<WebDriver>>> driverMap = new HashMap<>();
    public static final Data<WebDriver> nullDriver = new Data<WebDriver>(new RemoteWebDriver(new MutableCapabilities()), false, "No Drivers.Driver instance found.");
    public static final String defaultBrowser = "FIREFOX",
        defaultId = "0";


    private static WebDriver getDriverObject(Data<WebDriver> data) {
        return (data.status ? data : nullDriver).object;
    }

    public static WebDriver getDriver(String browserType, String id) {
        return getDriverObject(get(browserType, id));
    }

    public static WebDriver getDefaultDriver() {
        return getDriverObject(getDefault());
    }

    public static Data<WebDriver> getDefault() {
        return get(defaultBrowser, defaultId);
    }

    public static Data<WebDriver> get(String browserType, String id) {
        return driverMap.containsKey(browserType) ? driverMap.get(browserType).get(id) : nullDriver;
    }
}
