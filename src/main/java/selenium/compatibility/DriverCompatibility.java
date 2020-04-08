package selenium.compatibility;

import core.extensions.boilers.WebElementList;
import core.extensions.interfaces.DriverFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.constants.SeleniumDataConstants;
import selenium.namespaces.Driver;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static selenium.namespaces.ExecutionCore.ifDriver;

public class DriverCompatibility {
    public static DriverFunction<WebElement> getElementByCssSelector(String selector, String nameof) {
        return ifDriver(isNotBlank(nameof) ? nameof : "getElementByCssSelector", isNotBlank(selector), Driver.getElement(By.cssSelector(selector)), SeleniumDataConstants.NULL_ELEMENT);
    }

    public static DriverFunction<WebElement> getElementByCssSelector(String selector) {
        return getElementByCssSelector(selector, null);
    }

    public static DriverFunction<WebElementList> getElementsByCssSelector(String selector) {
        return ifDriver("getElementsByCssSelector", isNotBlank(selector), Driver.getElements(By.cssSelector(selector)), SeleniumDataConstants.NULL_LIST);
    }

    public static DriverFunction<WebElement> getElementById(String id) {
        return ifDriver("getElementById", isNotBlank(id), Driver.getElement(By.id(id)), SeleniumDataConstants.NULL_ELEMENT);
    }

    public static DriverFunction<WebElementList> getElementsByClass(String className) {
        return ifDriver("getElementsByClass", isNotBlank(className), Driver.getElements(By.className(className)), SeleniumDataConstants.NULL_LIST);
    }

    public static DriverFunction<WebElement> getElementByClass(String className) {
        return ifDriver("getElementByClass", isNotBlank(className), Driver.getElement(By.className(className)), SeleniumDataConstants.NULL_ELEMENT);
    }

    public static DriverFunction<WebElement> getElementByXpath(String xpath) {
        return ifDriver("getElementByXpath", isNotBlank(xpath), Driver.getElement(By.xpath(xpath)), SeleniumDataConstants.NULL_ELEMENT);
    }
}
