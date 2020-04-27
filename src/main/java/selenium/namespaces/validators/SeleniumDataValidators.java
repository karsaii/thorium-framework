package selenium.namespaces.validators;

import core.constants.CoreDataConstants;
import core.extensions.namespaces.NullableFunctions;
import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import data.constants.Strings;
import data.namespaces.Formatter;
import org.openqa.selenium.WebElement;
import selenium.constants.SeleniumDataConstants;
import selenium.namespaces.extensions.boilers.DriverFunction;
import selenium.records.LazyElement;

import java.util.function.Function;

import static core.extensions.namespaces.CoreUtilities.isEqual;
import static core.extensions.namespaces.NullableFunctions.isNull;
import static core.namespaces.validators.DataValidators.isInvalidOrFalse;
import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.ExecutionCore.validChain;

public interface SeleniumDataValidators {
    private static DriverFunction<Boolean> isConditionCore(LazyElement element, String nameof, Function<Data<WebElement>, Data<Boolean>> condition) {
        return ifDriver(
            nameof,
            Formatter.isNullLazyElementMessage(element),
            validChain(element.get(), condition, CoreDataConstants.DATA_PARAMETER_WAS_NULL),
            CoreDataConstants.DATA_PARAMETER_WAS_NULL
        );
    }
    static DriverFunction<Boolean> isNotNull(LazyElement element) {
        return isConditionCore(element, "isNotNull", WebElementValidators::isInitialized);
    }

    static DriverFunction<Boolean> isValidLazyElement(LazyElement element) {
        return isConditionCore(element, "isValidLazyElement", WebElementValidators::isValidWebElement);
    }
}
