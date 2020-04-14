package selenium.namespaces.validators;

import core.constants.CoreDataConstants;
import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import data.constants.Strings;
import data.namespaces.Formatter;
import org.openqa.selenium.WebElement;
import selenium.constants.SeleniumDataConstants;
import selenium.namespaces.extensions.boilers.DriverFunction;
import selenium.records.LazyElement;

import static core.extensions.namespaces.CoreUtilities.isEqual;
import static core.extensions.namespaces.NullableFunctions.isNull;
import static core.namespaces.validators.DataValidators.isInvalidOrFalse;
import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.ExecutionCore.validChain;

public interface SeleniumDataValidators {
    static boolean isNullWebElement(WebElement element) {
        return (
            isNull(element) ||
            isEqual(element, SeleniumDataConstants.NULL_ELEMENT.object) ||
            isEqual(element.getAttribute("id"), Strings.NULL_ELEMENT_ID)
        );
    }

    static boolean isNullWebElement(Data<WebElement> data) {
        return isInvalidOrFalse(data) || isEqual(data, SeleniumDataConstants.NULL_ELEMENT) || isNullWebElement(data.object);
    }

    static boolean isNotNullWebElement(Data<WebElement> data) {
        return !isNullWebElement(data);
    }

    static Data<Boolean> isValidWebElement(Data<WebElement> data) {
        final var status = isNotNullWebElement(data);
        final var message = status ? ("Element is okay" + Strings.END_LINE) : ("Element was null or false: " + data.message);
        return DataFactoryFunctions.getBoolean(status, "isValidWebElement", message);
    }

    static DriverFunction<Boolean> isValidWebElement(LazyElement element) {
        final var nameof = "isValidWebElement";
        return ifDriver(
            nameof,
            Formatter.isNullLazyElementMessage(element),
            validChain(element.get(), SeleniumDataValidators::isValidWebElement, CoreDataConstants.DATA_PARAMETER_WAS_NULL),
            CoreDataConstants.DATA_PARAMETER_WAS_NULL
        );
    }
}
