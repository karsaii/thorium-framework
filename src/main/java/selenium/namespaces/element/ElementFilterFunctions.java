package selenium.namespaces.element;

import core.records.Data;
import data.namespaces.Formatter;
import org.openqa.selenium.WebElement;
import selenium.constants.SeleniumDataConstants;
import selenium.enums.SingleGetter;
import selenium.namespaces.Driver;
import selenium.namespaces.extensions.boilers.DriverFunction;
import selenium.namespaces.extensions.boilers.LazyLocatorList;
import selenium.namespaces.extensions.boilers.WebElementList;
import selenium.records.element.finder.ElementFilterParameters;

import java.util.Map;
import java.util.function.Function;

import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.ExecutionCore.validChain;
import static selenium.namespaces.element.validators.ElementFilterParametersValidators.isInvalidElementFilterParametersMessage;

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
        return getFilteredElement("getIndexedElement", data, Driver::getElementByIndex, Formatter::isNegativeMessage);
    }

    static Function<String, DriverFunction<WebElement>> getContainedTextElement(ElementFilterParameters data) {
        return getFilteredElement("getContainedTextElement", data, Driver::getElementByContainedText, Formatter::isBlankMessage);
    }

    static DriverFunction<WebElement> getElement(LazyLocatorList locators, Map<SingleGetter, Function<LazyLocatorList, DriverFunction<WebElement>>> getterMap, SingleGetter getter) {
        return ifDriver("getElement via LazyElement parameters", Formatter.getSingleGetterErrorMessage(getterMap, getter), getterMap.get(getter).apply(locators), SeleniumDataConstants.NULL_ELEMENT);
    }

}
