package selenium.namespaces;

import data.namespaces.Formatter;
import org.openqa.selenium.WebElement;
import selenium.constants.SeleniumDataConstants;
import selenium.enums.SingleGetter;
import selenium.namespaces.extensions.boilers.DriverFunction;
import selenium.namespaces.extensions.boilers.LazyLocatorList;
import selenium.namespaces.validators.ElementFilterParametersValidators;
import selenium.records.ElementFilterParameters;

import java.util.Map;
import java.util.function.Function;

import static selenium.namespaces.ExecutionCore.ifDriver;

public interface ElementFilterFunctions {
    static Function<Integer, DriverFunction<WebElement>> getIndexedElement(ElementFilterParameters data) {
        return value -> ifDriver(
            "getIndexedElement",
            ElementFilterParametersValidators.isInvalidElementFilterParametersMessage(data),
            data.getterMap.get(data.getter).apply(data.locators),
            Driver.getElementByIndex(value),
            SeleniumDataConstants.NULL_ELEMENT
        );
    }

    static Function<String, DriverFunction<WebElement>> getContainedTextElement(ElementFilterParameters data) {
        return value -> ifDriver(
            "getContainedTextElement",
            ElementFilterParametersValidators.isInvalidElementFilterParametersMessage(data),
            data.getterMap.get(data.getter).apply(data.locators),
            Driver.getElementByContainedText(value),
            SeleniumDataConstants.NULL_ELEMENT
        );
    }

    static DriverFunction<WebElement> getElement(LazyLocatorList locators, Map<SingleGetter, Function<LazyLocatorList, DriverFunction<WebElement>>> getterMap, SingleGetter getter) {
        return ifDriver("getElement via LazyElement parameters", Formatter.getSingleGetterErrorMessage(getterMap, getter), getterMap.get(getter).apply(locators), SeleniumDataConstants.NULL_ELEMENT);
    }

}
