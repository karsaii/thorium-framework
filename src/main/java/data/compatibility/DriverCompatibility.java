package data.compatibility;

import data.constants.DataDefaults;
import data.DriverFunction;
import data.WebElementList;
import drivers.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static utilities.utils.ifLambda;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DriverCompatibility {
    public static DriverFunction<WebElement> getElementByCssSelector(String selector, String nameof) {
        return ifLambda(isNotBlank(selector), Driver.getElement(By.cssSelector(selector)), DataDefaults.NULL_ELEMENT_DATA, isNotBlank(nameof) ? nameof : "getElementByCssSelector: ");
    }

    public static DriverFunction<WebElement> getElementByCssSelector(String selector) {
        return getElementByCssSelector(selector, null);
    }

    public static DriverFunction<WebElementList> getElementsByCssSelector(String selector) {
        return ifLambda(isNotBlank(selector), Driver.getElements(By.cssSelector(selector)), DataDefaults.NULL_LIST_DATA, "getElementsByCssSelector: ");
    }

    public static DriverFunction<WebElement> getElementById(String id) {
        return ifLambda(isNotBlank(id), Driver.getElement(By.id(id)), DataDefaults.NULL_ELEMENT_DATA, "getElementById: ");
    }

    public static DriverFunction<WebElementList> getElementsByClass(String className) {
        return ifLambda(isNotBlank(className), Driver.getElements(By.className(className)), DataDefaults.NULL_LIST_DATA, "getElementsByClass: ");
    }

    public static DriverFunction<WebElement> getElementByClass(String className) {
        return ifLambda(isNotBlank(className), Driver.getElement(By.className(className)), DataDefaults.NULL_ELEMENT_DATA, "getElementByClass: ");
    }

    public static DriverFunction<WebElement> getElementByXpath(String xpath) {
        return ifLambda(isNotBlank(xpath), Driver.getElement(By.xpath(xpath)), DataDefaults.NULL_ELEMENT_DATA, "getElementByXpath: ");
    }
}
