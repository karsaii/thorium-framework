package com.github.karsaii.selenium.namespaces;

import com.github.karsaii.core.constants.CoreDataConstants;
import com.github.karsaii.core.namespaces.wait.Wait;
import com.github.karsaii.core.namespaces.wait.WaitTimeDataFactory;
import com.github.karsaii.core.records.WaitData;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import org.openqa.selenium.By;
import com.github.karsaii.selenium.constants.DriverFunctionConstants;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.selenium.records.element.ElementWaitParameters;
import com.github.karsaii.selenium.records.lazy.LazyElement;
import com.github.karsaii.selenium.records.lazy.LazyElementWaitParameters;

import java.util.function.Function;

import static com.github.karsaii.core.extensions.namespaces.NullableFunctions.isNotNull;
import static com.github.karsaii.core.namespaces.DataFactoryFunctions.appendMessage;
import static com.github.karsaii.core.namespaces.DataFactoryFunctions.prependMessage;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static com.github.karsaii.selenium.namespaces.ExecutionCore.ifDriver;
import static com.github.karsaii.selenium.namespaces.utilities.SeleniumUtilities.isNotNullElementWaitParametersData;
import static com.github.karsaii.selenium.namespaces.utilities.SeleniumUtilities.isNotNullLazyElementWaitParametersData;

public interface WaitConditions {
    static DriverFunction<Boolean> waitWith(By locator, Function<By, DriverFunction<Boolean>> conditionGetter, String option, int interval, int timeout, String message) {
        return ifDriver(
            "waitConditionCore",
            isNotNull(conditionGetter),
            driver -> Wait.core(new WaitData<>(
                conditionGetter.apply(locator),
                isBlank(option) ? WaitPredicateFunctions::isTruthyData : WaitPredicateFunctions::isFalsyData,
                "Element located by: " + locator + " to be " + (isBlank(message) ? "clickable" : message) + CoreFormatterConstants.END_LINE,
                WaitTimeDataFactory.getWithDefaultClock(interval, timeout)
            )).apply(driver),
            appendMessage(CoreDataConstants.NULL_BOOLEAN, "Condition Getter" + CoreFormatterConstants.WAS_NULL)
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
                CoreFormatterConstants.ELEMENT + data.name
            ),
            prependMessage(CoreDataConstants.NULL_BOOLEAN, CoreFormatterConstants.ELEMENT + data.name)
        );
    }

    static DriverFunction<Boolean> waitWith(ElementWaitParameters data, Function<By, DriverFunction<Boolean>> conditionGetter, String option, String message) {
        return isNotNullElementWaitParametersData(data) ? waitWith(data.object, conditionGetter, option, data.interval, data.duration, message) : DriverFunctionConstants.NULL_BOOLEAN;
    }

    static DriverFunction<Boolean> waitWith(LazyElementWaitParameters data, Function<LazyElement, DriverFunction<Boolean>> conditionGetter, String option, String message) {
        return isNotNullLazyElementWaitParametersData(data) ? waitWith(data.object, conditionGetter, option, data.interval, data.duration, message) : DriverFunctionConstants.NULL_BOOLEAN;
    }
}
