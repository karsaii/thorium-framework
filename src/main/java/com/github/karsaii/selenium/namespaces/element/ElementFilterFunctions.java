package com.github.karsaii.selenium.namespaces.element;

import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.namespaces.validators.CoreFormatter;
import com.github.karsaii.selenium.namespaces.validators.SeleniumFormatter;
import org.openqa.selenium.WebElement;
import com.github.karsaii.selenium.constants.SeleniumDataConstants;
import com.github.karsaii.selenium.enums.SingleGetter;
import com.github.karsaii.selenium.namespaces.Driver;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.selenium.namespaces.extensions.boilers.LazyLocatorList;
import com.github.karsaii.selenium.namespaces.extensions.boilers.WebElementList;
import com.github.karsaii.selenium.records.element.finder.ElementFilterParameters;

import java.util.Map;
import java.util.function.Function;

import static com.github.karsaii.selenium.namespaces.ExecutionCore.ifDriver;
import static com.github.karsaii.selenium.namespaces.ExecutionCore.validChain;
import static com.github.karsaii.selenium.namespaces.element.validators.ElementFilterParametersValidators.isInvalidElementFilterParametersMessage;

public interface ElementFilterFunctions {
    private static <T> Function<T, DriverFunction<WebElement>> getFilteredElement(String nameof, ElementFilterParameters data, Function<T, Function<Data<WebElementList>, Data<WebElement>>> filterFunction, Function<T, String> valueGuard) {
        return value -> ifDriver(
            nameof,
            isInvalidElementFilterParametersMessage(data) + valueGuard.apply(value),
            validChain(data.getterMap.get(data.getter).apply(data.locators), filterFunction.apply(value), SeleniumDataConstants.NULL_ELEMENT),
            SeleniumDataConstants.NULL_ELEMENT
        );
    }

    static Function<Integer, DriverFunction<WebElement>> getIndexedElement(ElementFilterParameters data) {
        return getFilteredElement("getIndexedElement", data, Driver::getElementByIndex, CoreFormatter::isNegativeMessage);
    }

    static Function<String, DriverFunction<WebElement>> getContainedTextElement(ElementFilterParameters data) {
        return getFilteredElement("getContainedTextElement", data, Driver::getElementByContainedText, CoreFormatter::isBlankMessage);
    }

    static DriverFunction<WebElement> getElement(LazyLocatorList locators, Map<SingleGetter, Function<LazyLocatorList, DriverFunction<WebElement>>> getterMap, SingleGetter getter) {
        return ifDriver("getElement via LazyElement parameters", SeleniumFormatter.getSingleGetterErrorMessage(getterMap, getter), getterMap.get(getter).apply(locators), SeleniumDataConstants.NULL_ELEMENT);
    }

}
