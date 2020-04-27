package selenium.namespaces.validators;

import core.extensions.namespaces.NullableFunctions;
import core.namespaces.DataFactoryFunctions;
import core.namespaces.validators.DataValidators;
import core.records.Data;
import data.constants.Strings;
import org.openqa.selenium.WebElement;
import selenium.constants.SeleniumDataConstants;

import static core.extensions.namespaces.CoreUtilities.isEqual;
import static core.namespaces.validators.DataValidators.isInvalidOrFalse;

public interface WebElementValidators {
    static boolean isNull(WebElement element) {
        return (
            NullableFunctions.isNull(element) ||
            isEqual(element, SeleniumDataConstants.NULL_ELEMENT.object) ||
            isEqual(element.getAttribute("id"), Strings.NULL_ELEMENT_ID)
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
        final var message = status ? ("Element is okay" + Strings.END_LINE) : ("Element was null or false: " + data.message);
        return DataFactoryFunctions.getBoolean(status, "isValidWebElement", message);
    }

    static Data<Boolean> isInitialized(Data<WebElement> data) {
        final var status = DataValidators.isInitialized(data);
        final var message = "Element container was" + (status ? "" : "n't") + "initialized" + Strings.END_LINE;
        return DataFactoryFunctions.getBoolean(status, "isInitialized", message);
    }
}
