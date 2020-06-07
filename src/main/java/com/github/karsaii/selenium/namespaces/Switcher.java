package com.github.karsaii.selenium.namespaces;

import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.core.namespaces.DataFactoryFunctions;
import com.github.karsaii.core.records.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.github.karsaii.selenium.records.lazy.LazyElement;

public interface Switcher {
    static DriverFunction<Boolean> switchToFrame(WebElement element) {
        return switchToFrame(DataFactoryFunctions.getWithMessage(element, true, SeleniumFormatterConstants.RAW_WEBELEMENT_PASSED));
    }

    static DriverFunction<Boolean> switchToFrame(LazyElement element) {
        return Driver.switchToFrame(element.get());
    }

    static DriverFunction<Boolean> switchToFrame(By locator) {
        return Driver.switchToFrame(locator);
    }

    static DriverFunction<Boolean> switchToFrame(Data<WebElement> element) {
        return Driver.switchToFrame(DriverFunctionFactory.get(element));
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
