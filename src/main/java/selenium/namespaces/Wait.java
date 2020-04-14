package selenium.namespaces;

import core.constants.CoreDataConstants;
import core.exceptions.ArgumentNullException;
import selenium.namespaces.extensions.boilers.DriverFunction;
import core.extensions.namespaces.CoreUtilities;
import core.namespaces.WaitTimeDataFactory;
import core.records.WaitData;
import data.constants.Strings;
import data.namespaces.Formatter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import selenium.constants.DriverFunctionConstants;
import selenium.records.ElementWaitParameters;
import selenium.records.LazyElement;
import selenium.records.lazy.LazyElementWaitParameters;
import validators.ElementParameters;

import java.util.function.Function;

import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static core.namespaces.DataFactoryFunctions.appendMessage;
import static core.namespaces.DataFactoryFunctions.prependMessage;
import static core.namespaces.DataFunctions.getMessageFromData;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static selenium.namespaces.ExecutionCore.ifDriver;

public interface Wait {
    static <T, V> V untilCore(T dependency, WaitData<T, V> waitData) {
        if (CoreUtilities.areAnyNull(dependency, waitData)) {
            throw new ArgumentNullException("Dependency or WaitData was wrong" + Strings.END_LINE);
        }

        final var condition = waitData.condition;
        final var exitCondition = waitData.exitCondition;
        final var timeData = waitData.timeData;
        final var errorMessage = ElementParameters.validateUntilParameters(condition, exitCondition, timeData);
        if (isNotBlank(errorMessage)) {
            throw new ArgumentNullException(errorMessage);
        }

        final var timeout = timeData.duration;
        final var interval = timeData.interval.toMillis();
        final var clock = timeData.clock;

        final var start = clock.instant();
        final var end = start.plus(timeout);
        var message = "";
        V value = null;
        try {
            for(; end.isAfter(clock.instant()); Thread.sleep(interval)) {
                value = condition.apply(dependency);
                if (exitCondition.test(value)) {
                    return value;
                }
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            message = Formatter.getWaitInterruptMessage(ex.getMessage());
        }

        final var conditionMessage = waitData.conditionMessage;
        if (isBlank(message)) {
            message = Formatter.getWaitErrorMessage(
                isNotNull(value) ? getMessageFromData(value) : conditionMessage, timeout.getSeconds(), interval
            ) + "End time: " + end + Strings.END_LINE + "Start: " + start + Strings.END_LINE;
        }

        throw new TimeoutException(message + conditionMessage);
    }

    static <T, V> Function<T, V> untilCore(WaitData<T, V> waitData) {
        return dependency -> untilCore(dependency, waitData);
    }

    static DriverFunction<Boolean> waitConditionCore(By locator, Function<By, DriverFunction<Boolean>> conditionGetter, String option, int interval, int timeout, String message) {
        return ifDriver(
            "waitConditionCore",
            isNotNull(conditionGetter),
            driver -> Wait.untilCore(new WaitData<>(
                conditionGetter.apply(locator),
                isBlank(option) ? WaitPredicateFunctions::isTruthyData : WaitPredicateFunctions::isFalsyData,
                "Element located by: " + locator + " to be " + (isBlank(message) ? "clickable" : message) + Strings.END_LINE,
                WaitTimeDataFactory.getWithDefaultClock(interval, timeout)
            )).apply(driver),
            appendMessage(CoreDataConstants.NULL_BOOLEAN, "Condition Getter" + Strings.WAS_NULL)
        );
    }

    static DriverFunction<Boolean> waitConditionCore(LazyElement data, Function<LazyElement, DriverFunction<Boolean>> conditionGetter, String option, int interval, int timeout, String message) {
        return ifDriver(
            "waitConditionCore",
            isNotNull(conditionGetter),
            driver -> prependMessage(
                Wait.untilCore(new WaitData<>(
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

    static DriverFunction<Boolean> waitConditionCore(ElementWaitParameters data, Function<By, DriverFunction<Boolean>> conditionGetter, String option, String message) {
        return SeleniumUtilities.isNotNullElementWaitParametersData(data) ? waitConditionCore(data.object, conditionGetter, option, data.interval, data.duration, message) : DriverFunctionConstants.NULL_BOOLEAN;
    }

    static DriverFunction<Boolean> waitConditionCore(LazyElementWaitParameters data, Function<LazyElement, DriverFunction<Boolean>> conditionGetter, String option, String message) {
        return SeleniumUtilities.isNotNullLazyElementWaitParametersData(data) ? waitConditionCore(data.object, conditionGetter, option, data.interval, data.duration, message) : DriverFunctionConstants.NULL_BOOLEAN;
    }
}
