package selenium.element;

import core.extensions.interfaces.DriverFunction;
import core.records.Data;
import data.constants.Strings;
import org.openqa.selenium.By;
import selenium.constants.DataConstants;
import selenium.enums.SingleGetter;
import selenium.namespaces.repositories.LocatorRepository;
import selenium.records.LazyElement;

import static core.namespaces.DataFunctions.isValidNonFalse;
import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.SeleniumUtilities.isNotNullLazyElement;

public interface ElementAlternatives {
    static DriverFunction<Boolean> clearWithSelectAll(LazyElement element) {
        return ifDriver("clearWithSelectAll", isNotNullLazyElement(element), Element.sendKeys(element, Strings.SELECT_ALL), DataConstants.NULL_BOOLEAN);
    }

    static DriverFunction<Boolean> clearWithSelectAll(Data<LazyElement> data) {
        return ifDriver("clearWithSelectAll", isValidNonFalse(data), clearWithSelectAll(data.object), DataConstants.NULL_BOOLEAN);
    }

    static DriverFunction<Boolean> clearWithSelectAll(By locator, SingleGetter getter) {
        return clearWithSelectAll(LocatorRepository.getIfContains(locator, getter));
    }

    static DriverFunction<Boolean> clearWithSelectAll(By locator) {
        return clearWithSelectAll(LocatorRepository.getIfContains(locator));
    }
}
