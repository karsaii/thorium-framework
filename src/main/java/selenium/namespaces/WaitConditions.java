package selenium.namespaces;

import core.constants.CoreDataConstants;
import core.namespaces.Wait;
import core.namespaces.WaitTimeDataFactory;
import core.records.WaitData;
import data.constants.Strings;
import org.openqa.selenium.By;
import selenium.constants.DriverFunctionConstants;
import selenium.namespaces.extensions.boilers.DriverFunction;
import selenium.records.element.ElementWaitParameters;
import selenium.records.lazy.LazyElement;
import selenium.records.lazy.LazyElementWaitParameters;

import java.util.function.Function;

import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static core.namespaces.DataFactoryFunctions.appendMessage;
import static core.namespaces.DataFactoryFunctions.prependMessage;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.utilities.SeleniumUtilities.isNotNullElementWaitParametersData;
import static selenium.namespaces.utilities.SeleniumUtilities.isNotNullLazyElementWaitParametersData;

public interface WaitConditions {
    static DriverFunction<Boolean> waitWith(By locator, Function<By, DriverFunction<Boolean>> conditionGetter, String option, int interval, int timeout, String message) {
        return ifDriver(
            "waitConditionCore",
            isNotNull(conditionGetter),
            driver -> Wait.core(new WaitData<>(
                conditionGetter.apply(locator),
                isBlank(option) ? WaitPredicateFunctions::isTruthyData : WaitPredicateFunctions::isFalsyData,
                "Element located by: " + locator + " to be " + (isBlank(message) ? "clickable" : message) + Strings.END_LINE,
                WaitTimeDataFactory.getWithDefaultClock(interval, timeout)
            )).apply(driver),
            appendMessage(CoreDataConstants.NULL_BOOLEAN, "Condition Getter" + Strings.WAS_NULL)
        );
    }

    static DriverFunction<Boolean> waitWith(LazyElement data, Function<LazyElement, DriverFunction<Boolean>> conditionGetter, String option, int interval, int timeout, String message) {
        return ifDriver(
            "waitConditionCore",
            isNotNull(conditionGetter),
            driver -> prependMessage(
                Wait.core(new WaitData<>(
                    conditionGetter.apply(data),
                    isBlank(option) ? WaitPredicateFunctions::isTruthyData : WaitPredicateFunctions::isFalsyData,
                    data.name + " " + message,
                    WaitTimeDataFactory.getWithDefaultClock(interval, timeout)
                )).apply(driver),
                Strings.ELEMENT + data.name
            ),
            prependMessage(CoreDataConstants.NULL_BOOLEAN, Strings.ELEMENT + data.name)
        );
    }

    static DriverFunction<Boolean> waitWith(ElementWaitParameters data, Function<By, DriverFunction<Boolean>> conditionGetter, String option, String message) {
        return isNotNullElementWaitParametersData(data) ? waitWith(data.object, conditionGetter, option, data.interval, data.duration, message) : DriverFunctionConstants.NULL_BOOLEAN;
    }

    static DriverFunction<Boolean> waitWith(LazyElementWaitParameters data, Function<LazyElement, DriverFunction<Boolean>> conditionGetter, String option, String message) {
        return isNotNullLazyElementWaitParametersData(data) ? waitWith(data.object, conditionGetter, option, data.interval, data.duration, message) : DriverFunctionConstants.NULL_BOOLEAN;
    }
}
