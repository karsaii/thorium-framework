package selenium.drivers;

import core.extensions.namespaces.NullableFunctions;
import core.records.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import selenium.constants.SeleniumDataConstants;

import java.util.HashMap;


public class DriverFactory {
    private static final HashMap<String, HashMap<String, Data<WebDriver>>> driverMap = new HashMap<>();
    private static WebDriver driver = null;

    public static final String defaultBrowser = "FIREFOX",
        defaultId = "0";

    private static WebDriver getDriverObject(Data<WebDriver> data) {
        return (data.status ? data : SeleniumDataConstants.NULL_DRIVER).object;
    }

    /*public static WebDriver getDriver(String browserType, String id) {
        return getDriverObject(get(browserType, id));
    }*/

    /*public static WebDriver getDefaultDriver() {
        return getDriverObject(getDefault());
    }*/

    /*public static Data<WebDriver> getDefault() {
        return get(defaultBrowser, defaultId);
    }*/

    public static WebDriver get(/*String browserType, String id*/) {
        /*if (driverMap.containsKey(browserType))  {
            return driverMap.get(browserType).get(id)
        }*/
        System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
        if (NullableFunctions.isNull(driver)) {
            driver = new ChromeDriver();
        }

        return driver;
    }
}
