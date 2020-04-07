package core.extensions.interfaces.functional.boilers;

import core.extensions.interfaces.DriverFunction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@FunctionalInterface
public interface IElement extends IContainedData<WebDriver, WebElement> {
    DriverFunction<WebElement> get();
}
