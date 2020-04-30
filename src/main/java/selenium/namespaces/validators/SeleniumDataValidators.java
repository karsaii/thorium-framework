package selenium.namespaces.validators;

import core.constants.CoreDataConstants;
import core.records.Data;
import data.namespaces.Formatter;
import org.openqa.selenium.WebElement;
import selenium.namespaces.element.validators.WebElementValidators;
import selenium.namespaces.extensions.boilers.DriverFunction;
import selenium.records.lazy.LazyElement;

import java.util.function.Function;

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
