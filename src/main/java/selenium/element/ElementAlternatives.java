package selenium.element;

import core.constants.CoreDataConstants;
import selenium.javascriptCommands.Execute;
import selenium.namespaces.extensions.boilers.DriverFunction;
import core.records.Data;
import data.constants.Strings;
import org.openqa.selenium.By;
import selenium.enums.SingleGetter;
import selenium.namespaces.repositories.LocatorRepository;
import selenium.records.LazyElement;


import static core.namespaces.validators.DataValidators.isValidNonFalse;
import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.SeleniumUtilities.isNotNullLazyElement;

public interface ElementAlternatives {
    static DriverFunction<Boolean> clearWithSelectAll(LazyElement element) {
        return ifDriver("clearWithSelectAll", isNotNullLazyElement(element), Element.sendKeys(element, Strings.SELECT_ALL), CoreDataConstants.NULL_BOOLEAN);
    }

    static DriverFunction<Boolean> clearWithSelectAll(Data<LazyElement> data) {
        return ifDriver("clearWithSelectAll", isValidNonFalse(data), clearWithSelectAll(data.object), CoreDataConstants.NULL_BOOLEAN);
    }

    static DriverFunction<Boolean> clearWithSelectAll(By locator, SingleGetter getter) {
        return clearWithSelectAll(LocatorRepository.getIfContains(locator, getter));
    }

    static DriverFunction<Boolean> clearWithSelectAll(By locator) {
        return clearWithSelectAll(LocatorRepository.getIfContains(locator));
    }

    static DriverFunction<Boolean> clickWithEventDispatcher(LazyElement element) {
        return ifDriver("clickWithEventDispatcher", isNotNullLazyElement(element), Execute.clickEventDispatcher(element), CoreDataConstants.NULL_BOOLEAN);
    }

    static DriverFunction<Boolean> clickWithEventDispatcher(Data<LazyElement> data) {
        return ifDriver("clickWithEventDispatcher", isValidNonFalse(data), clickWithEventDispatcher(data.object), CoreDataConstants.NULL_BOOLEAN);
    }

    static DriverFunction<Boolean> clickWithEventDispatcher(By locator, SingleGetter getter) {
        return clickWithEventDispatcher(LocatorRepository.getIfContains(locator, getter));
    }

    static DriverFunction<Boolean> clickWithEventDispatcher(By locator) {
        return clickWithEventDispatcher(LocatorRepository.getIfContains(locator));
    }
}
