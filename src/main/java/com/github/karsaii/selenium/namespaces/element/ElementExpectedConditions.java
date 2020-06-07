package com.github.karsaii.selenium.namespaces.element;

import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.selenium.namespaces.ExpectedConditions;
import com.github.karsaii.selenium.records.lazy.LazyElement;

public interface ElementExpectedConditions {
    static DriverFunction<Boolean> isTextEquals(LazyElement element, String expected) {
        return ExpectedConditions.isValuesEqualData(Element.getText(element), expected, SeleniumFormatterConstants.ELEMENT_TEXT);
    }

    static DriverFunction<Boolean> isTextEmpty(LazyElement element) {
        return isTextEquals(element, CoreFormatterConstants.EMPTY);
    }

    static DriverFunction<Boolean> isTextContains(LazyElement element, String expected) {
        return ExpectedConditions.isContainsExpectedData(Element.getText(element), expected, SeleniumFormatterConstants.ELEMENT_TEXT);
    }

    static DriverFunction<Boolean> isAttributeValueTextEqual(LazyElement element, String expected) {
        return ExpectedConditions.isValuesEqualData(Element.getAttributeValue(element), expected, SeleniumFormatterConstants.ELEMENT_ATTRIBUTE_VALUE);
    }

    static DriverFunction<Boolean> isAttributeEqual(LazyElement element, String attribute, String expected) {
        return ExpectedConditions.isValuesEqualData(Element.getAttribute(element, attribute), expected, SeleniumFormatterConstants.ELEMENT_ATTRIBUTE + attribute);
    }

    static DriverFunction<Boolean> isAttributeContains(LazyElement element, String attribute, String expected) {
        return ExpectedConditions.isContainsExpectedData(Element.getAttribute(element, attribute), expected, SeleniumFormatterConstants.ELEMENT_ATTRIBUTE + attribute);
    }

    static DriverFunction<Boolean> isAttributeValueContains(LazyElement element, String expected) {
        return ExpectedConditions.isContainsExpectedData(Element.getAttributeValue(element), expected, SeleniumFormatterConstants.ELEMENT_ATTRIBUTE_VALUE);
    }

    static DriverFunction<Boolean> isAttributeUnequal(LazyElement element, String attribute, String expected) {
        return ExpectedConditions.isValuesNotEqualData(Element.getAttribute(element, attribute), expected, SeleniumFormatterConstants.ELEMENT_ATTRIBUTE + attribute);
    }

    static DriverFunction<Boolean> isAttributeNotContains(LazyElement element, String attribute, String expected) {
        return ExpectedConditions.isStringNotContainsExpectedData(Element.getAttribute(element, attribute), expected, SeleniumFormatterConstants.ELEMENT_ATTRIBUTE + attribute);
    }

    static DriverFunction<Boolean> isTextUnequals(LazyElement element, String expected) {
        return ExpectedConditions.isValuesNotEqualData(Element.getText(element), expected, SeleniumFormatterConstants.ELEMENT_TEXT);
    }

    static DriverFunction<Boolean> isTextNotContain(LazyElement element, String expected) {
        return ExpectedConditions.isStringNotContainsExpectedData(Element.getText(element), expected, SeleniumFormatterConstants.ELEMENT_TEXT);
    }

    static DriverFunction<Boolean> isAttributeValueTextUnequal(LazyElement element, String expected) {
        return ExpectedConditions.isValuesNotEqualData(Element.getAttributeValue(element), expected, SeleniumFormatterConstants.ELEMENT_ATTRIBUTE_VALUE);
    }

    static DriverFunction<Boolean> isAttributeValueNotContains(LazyElement element, String expected) {
        return ExpectedConditions.isStringNotContainsExpectedData(Element.getAttributeValue(element), expected, SeleniumFormatterConstants.ELEMENT_TEXT);
    }

    static DriverFunction<Boolean> isPresent(LazyElement element) {
        return ExpectedConditions.isElementPresent(element);
    }

    static DriverFunction<Boolean> isDisplayed(LazyElement element) {
        return ExpectedConditions.isElementDisplayed(element);
    }

    static DriverFunction<Boolean> isSelected(LazyElement element) {
        return ExpectedConditions.isElementSelected(element);
    }

    static DriverFunction<Boolean> isEnabled(LazyElement element) {
        return ExpectedConditions.isElementEnabled(element);
    }

    static DriverFunction<Boolean> isClickable(LazyElement element) {
        return ExpectedConditions.isElementClickable(element);
    }

    static DriverFunction<Boolean> isAbsent(LazyElement element) {
        return ExpectedConditions.isElementAbsent(element);
    }

    static DriverFunction<Boolean> isHidden(LazyElement element) {
        return ExpectedConditions.isElementHidden(element);
    }

    static DriverFunction<Boolean> isUnselected(LazyElement element) {
        return ExpectedConditions.isElementUnselected(element);
    }

    static DriverFunction<Boolean> isDisabled(LazyElement element) {
        return ExpectedConditions.isElementDisabled(element);
    }

    static DriverFunction<Boolean> isUnclickable(LazyElement element) {
        return ExpectedConditions.isElementUnclickable(element);
    }
}
