package drivers;

import data.constants.Strings;
import data.Data;
import data.DriverFunction;
import data.LazyElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface Switcher {
    static DriverFunction<Boolean> switchToFrame(WebElement element) {
        return switchToFrame(new Data<WebElement>(element, true, Strings.RAW_WEBELEMENT_PASSED));
    }

    static DriverFunction<Boolean> switchToFrame(LazyElement element) {
        return Driver.switchToFrame(element.get());
    }

    static DriverFunction<Boolean> switchToFrame(By locator) {
        return Driver.switchToFrame(locator);
    }

    static DriverFunction<Boolean> switchToFrame(Data<WebElement> element) {
        return Driver.switchToFrame(new DriverFunction<WebElement>(element));
    }

    static DriverFunction<Boolean> switchToFrame(int frameLocator) {
        return Driver.switchToFrame(frameLocator);
    }

    static DriverFunction<Boolean> switchToWindow(String windowHandle) {
        return Driver.switchToWindow(windowHandle);
    }

    static DriverFunction<Boolean> switchToParentFrame() {
        return Driver.switchToParentFrame();
    }

    static DriverFunction<Boolean> switchToAlert() {
        return Driver.switchToAlert();
    }

    static DriverFunction<Boolean> switchToDefaultContent() {
        return Driver.switchToDefaultContent();
    }
}
