package com.github.karsaii.selenium.namespaces.element.validators;

import com.github.karsaii.core.extensions.namespaces.NullableFunctions;
import com.github.karsaii.core.namespaces.DataFactoryFunctions;
import com.github.karsaii.core.namespaces.validators.DataValidators;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import org.openqa.selenium.WebElement;
import com.github.karsaii.selenium.constants.SeleniumDataConstants;

import static com.github.karsaii.core.extensions.namespaces.CoreUtilities.isEqual;
import static com.github.karsaii.core.namespaces.validators.DataValidators.isInvalidOrFalse;

public interface WebElementValidators {
    static boolean isNull(WebElement element) {
        return (
            NullableFunctions.isNull(element) ||
            isEqual(element, SeleniumDataConstants.NULL_ELEMENT.object) ||
            isEqual(element.getAttribute("id"), SeleniumFormatterConstants.NULL_ELEMENT_ID)
        );
    }

    static boolean isNotNull(WebElement element) {
        return !isNull(element);
    }

    static boolean isNullWebElement(Data<WebElement> data) {
        return isInvalidOrFalse(data) || isEqual(data, SeleniumDataConstants.NULL_ELEMENT) || isNull(data.object);
    }

    static boolean isNotNullWebElement(Data<WebElement> data) {
        return !isNullWebElement(data);
    }

    static Data<Boolean> isValidWebElement(Data<WebElement> data) {
        final var status = isNotNullWebElement(data);
        final var message = status ? ("Element is okay" + CoreFormatterConstants.END_LINE) : ("Element was null or false: " + data.message);
        return DataFactoryFunctions.getBoolean(status, "isValidWebElement", message);
    }

    static Data<Boolean> isInitialized(Data<WebElement> data) {
        final var status = DataValidators.isInitialized(data);
        final var message = "Element container was" + (status ? "" : "n't") + "initialized" + CoreFormatterConstants.END_LINE;
        return DataFactoryFunctions.getBoolean(status, "isInitialized", message);
    }
}
