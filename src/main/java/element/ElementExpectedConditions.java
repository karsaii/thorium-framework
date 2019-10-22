package element;

import data.constants.Strings;
import data.Data;
import data.DriverFunction;
import data.LazyElement;
import expectedConditions.EC;
import org.openqa.selenium.WebDriver;

import java.util.function.Function;

public interface ElementExpectedConditions {
    static DriverFunction<Boolean> isTextEquals(LazyElement element, String expected) {
        return EC.isValuesEqualData(Strings.ELEMENT_TEXT, expected, Element.getText(element));
    }

    static DriverFunction<Boolean> isTextEmpty(LazyElement element) {
        return isTextEquals(element, Strings.EMPTY);
    }

    static DriverFunction<Boolean> isTextContains(LazyElement element, String expected) {
        return EC.isStringContainsExpectedData(Strings.ELEMENT_TEXT, expected, Element.getText(element));
    }

    static DriverFunction<Boolean> isAttributeValueTextEqual(LazyElement element, String expected) {
        return EC.isValuesEqualData(Strings.ELEMENT_ATTRIBUTE_VALUE, expected, Element.getAttributeValue(element));
    }

    static DriverFunction<Boolean> isAttributeEqual(LazyElement element, String attribute, String expected) {
        return EC.isValuesEqualData(Strings.ELEMENT_ATTRIBUTE + attribute, expected, Element.getAttribute(element, attribute));
    }

    static DriverFunction<Boolean> isAttributeContains(LazyElement element, String attribute, String expected) {
        return EC.isStringContainsExpectedData(Strings.ELEMENT_ATTRIBUTE + attribute, expected, Element.getAttribute(element, attribute));
    }

    static DriverFunction<Boolean> isAttributeValueContains(LazyElement element, String expected) {
        return EC.isStringContainsExpectedData(Strings.ELEMENT_ATTRIBUTE_VALUE, expected, Element.getAttributeValue(element));
    }

    static DriverFunction<Boolean> isAttributeUnequal(LazyElement element, String attribute, String expected) {
        return EC.isValuesNotEqualData(Strings.ELEMENT_ATTRIBUTE + attribute, expected, Element.getAttribute(element, attribute));
    }

    static DriverFunction<Boolean> isAttributeNotContains(LazyElement element, String attribute, String expected) {
        return EC.isStringNotContainsExpectedData(Strings.ELEMENT_ATTRIBUTE + attribute, expected, Element.getAttribute(element, attribute));
    }

    static DriverFunction<Boolean> isTextUnequals(LazyElement element, String expected) {
        return EC.isValuesNotEqualData(Strings.ELEMENT_TEXT, expected, Element.getText(element));
    }

    static DriverFunction<Boolean> isTextNotContain(LazyElement element, String expected) {
        return EC.isStringNotContainsExpectedData(Strings.ELEMENT_TEXT, expected, Element.getText(element));
    }

    static DriverFunction<Boolean> isAttributeValueTextUnequal(LazyElement element, String expected) {
        return EC.isValuesNotEqualData(Strings.ELEMENT_ATTRIBUTE_VALUE, expected, Element.getAttributeValue(element));
    }

    static DriverFunction<Boolean> isAttributeValueNotContains(LazyElement element, String expected) {
        return EC.isStringNotContainsExpectedData(Strings.ELEMENT_TEXT, expected, Element.getAttributeValue(element));
    }

    static DriverFunction<Boolean> isPresent(LazyElement element) {
        return EC.isElementPresent(element);
    }

    static DriverFunction<Boolean> isDisplayed(LazyElement element) {
        return EC.isElementDisplayed(element);
    }

    static DriverFunction<Boolean> isSelected(LazyElement element) {
        return EC.isElementSelected(element);
    }

    static DriverFunction<Boolean> isEnabled(LazyElement element) {
        return EC.isElementEnabled(element);
    }

    static DriverFunction<Boolean> isClickable(LazyElement element) {
        return EC.isElementClickable(element);
    }

    static DriverFunction<Boolean> isAbsent(LazyElement element) {
        return EC.isElementAbsent(element);
    }

    static Function<WebDriver, Data<Boolean>> isHidden(LazyElement element) {
        return EC.isElementHidden(element);
    }

    static Function<WebDriver, Data<Boolean>> isUnselected(LazyElement element) {
        return EC.isElementUnselected(element);
    }

    static Function<WebDriver, Data<Boolean>> isDisabled(LazyElement element) {
        return EC.isElementDisabled(element);
    }

    static Function<WebDriver, Data<Boolean>> isUnclickable(LazyElement element) {
        return EC.isElementUnclickable(element);
    }
}
