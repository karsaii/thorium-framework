package com.github.karsaii.selenium.namespaces.extensions.boilers;

import com.github.karsaii.core.extensions.interfaces.functional.boilers.IContainedData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@FunctionalInterface
public interface IElement extends IContainedData<WebDriver, WebElement> {
    DriverFunction<WebElement> get();
}
