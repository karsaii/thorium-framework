package data;

import data.constants.ElementWaitDefaults;
import org.openqa.selenium.By;

public class ElementWaitParameters extends AbstractWaitParameters<By> {
    public ElementWaitParameters(By locator, int interval, int duration) {
        super(locator, interval, duration);
    }

    public ElementWaitParameters(By locator, int duration) {
        super(locator, ElementWaitDefaults.INTERVAL, duration);
    }

    public ElementWaitParameters(By locator) {
        super(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }
}
