package com.github.karsaii.selenium.namespaces.element;

import com.github.karsaii.core.constants.CoreDataConstants;
import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import com.github.karsaii.selenium.namespaces.scripter.Execute;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.core.records.Data;
import org.openqa.selenium.By;
import com.github.karsaii.selenium.enums.SingleGetter;
import com.github.karsaii.selenium.namespaces.repositories.LocatorRepository;
import com.github.karsaii.selenium.records.lazy.LazyElement;


import static com.github.karsaii.core.namespaces.validators.DataValidators.isValidNonFalse;
import static com.github.karsaii.selenium.namespaces.ExecutionCore.ifDriver;
import static com.github.karsaii.selenium.namespaces.utilities.SeleniumUtilities.isNotNullLazyElement;

public interface ElementAlternatives {
    static DriverFunction<Boolean> clearWithSelectAll(LazyElement element) {
        return ifDriver("clearWithSelectAll", isNotNullLazyElement(element), Element.sendKeys(element, SeleniumFormatterConstants.SELECT_ALL), CoreDataConstants.NULL_BOOLEAN);
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
