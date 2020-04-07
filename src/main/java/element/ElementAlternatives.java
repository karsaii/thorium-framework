package element;

import data.constants.Strings;
import data.enums.SingleGetter;
import data.LazyElement;
import data.DriverFunction;
import data.constants.DataDefaults;
import org.openqa.selenium.*;

import static data.ExecutionCore.ifDriver;
import static utilities.utils.*;

public interface ElementAlternatives {
    static DriverFunction<Boolean> clearWithSelectAll(DriverFunction<WebElement> getter) {
        return ifDriver("clearWithSelectAll", isNotNull(getter), Element.sendKeys(getter, Strings.SELECT_ALL), DataDefaults.NULL_BOOLEAN_DATA);
    }

    static DriverFunction<Boolean> clearWithSelectAll(LazyElement element) {
        return isNotNullLazyElement(element) ? clearWithSelectAll(element.get()) : DataDefaults.NULL_BOOLEAN_DF;
    }

    static DriverFunction<Boolean> clearWithSelectAll(By locator, SingleGetter getter) {
        return clearWithSelectAll(new LazyElement(locator, getter));
    }

    static DriverFunction<Boolean> clearWithSelectAll(By locator) {
        return clearWithSelectAll(new LazyElement(locator, SingleGetter.DEFAULT));
    }
}
