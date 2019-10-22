package wait;

import data.constants.Strings;
import data.constants.WaitDefaults;
import data.Data;
import data.DriverFunction;
import data.constants.DataDefaults;
import data.LazyElement;
import data.tuples.WaitData;
import data.ElementWaitParameters;
import data.lazy.tuples.LazyElementWaitParameters;
import drivers.Driver;
import formatter.Formatter;
import utilities.utils;
import validators.ElementParameters;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import exceptions.ArgumentNullException;
import org.openqa.selenium.WebElement;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static utilities.utils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface Wait {
    static <T, V> V untilCore(WaitData<T, V> waitData, Predicate<V> stopCondition) {
        final var condition = waitData.condition;
        final var errorMessage = ElementParameters.validateUntilParameters(condition, stopCondition);
        if (isNotBlank(errorMessage)) {
            throw new ArgumentNullException(errorMessage);
        }

        final var localTimeout = isNull(waitData.timeout) ? WaitDefaults.TIMEOUT : waitData.timeout;
        final var localInterval = (isNull(waitData.interval) ? WaitDefaults.INTERVAL : waitData.interval).toMillis();

        final var clock = isNull(waitData.clock) ? WaitDefaults.CLOCK : waitData.clock;
        final var start = clock.instant();
        final var end = start.plus(localTimeout);
        final var input = waitData.input;
        var message = "";
        V value = null;
        try {
            for(; end.isAfter(clock.instant()); Thread.sleep(localInterval)) {
                value = condition.apply(input);
                if (stopCondition.test(value)) {
                    return value;
                }
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            message = Formatter.getWaitInterruptMessage(ex.getMessage());
        }

        if (isBlank(message)) {
            message = Formatter.getWaitErrorMessage(
                isNotNull(value) ? getMessageFromData(value) : waitData.conditionMessage,
                localTimeout.getSeconds(),
                localInterval
            ) + "Endtime: " + end + Strings.END_LINE + "Start: " + start + Strings.END_LINE;
        }

        throw new TimeoutException(message + waitData.conditionMessage);
    }

    static <T, V> Data<Boolean> untilCoreStatus(WaitData<T, V> waitData, Predicate<V> stopCondition) {
        final var condition = waitData.condition;
        final var errorMessage = ElementParameters.validateUntilParameters(condition, stopCondition);
        if (isNotBlank(errorMessage)) {
            return DataDefaults.getBooleanData(false, errorMessage, new ArgumentNullException(errorMessage), errorMessage);
        }

        final var localTimeout = isNull(waitData.timeout) ? WaitDefaults.TIMEOUT : waitData.timeout;
        final var localInterval = (isNull(waitData.interval) ? WaitDefaults.INTERVAL : waitData.interval).toMillis();

        final var clock = isNull(waitData.clock) ? WaitDefaults.CLOCK : waitData.clock;
        final var start = clock.instant();
        final var end = start.plus(localTimeout);
        final var input = waitData.input;
        var message = "";
        V value = null;
        try {
            for(; end.isAfter(clock.instant()); Thread.sleep(localInterval)) {
                value = condition.apply(input);
                if (stopCondition.test(value)) {
                    return DataDefaults.getSimpleBooleanData(true, Strings.WAITING_WAS_SUCCESSFUL);
                }
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            message = Formatter.getWaitInterruptMessage(ex.getMessage());
        }

        if (isBlank(message)) {
            message = Formatter.getWaitErrorMessage(
                    isNotNull(value) ? getMessageFromData(value) : waitData.conditionMessage,
                    localTimeout.getSeconds(),
                    localInterval
            ) + "Endtime: " + end + Strings.END_LINE + "Start: " + start + Strings.END_LINE;
        }

        final var exception = new TimeoutException(message);
        return DataDefaults.getBooleanData(false, message, exception, exception.getMessage());
    }

    static <T, V> V until(WaitData<T, V> waitData) {
        return untilCore(waitData, utils::isTruthyData);
    }

    static <T, V> V untilNot(WaitData<T, V> waitData) {
        return untilCore(waitData, utils::isFalsyData);
    }

    static <T, V> Data<Boolean> untilStatus(WaitData<T, V> waitData) {
        return untilCoreStatus(waitData, utils::isTruthyData);
    }

    static <T, V> Data<Boolean> untilNotStatus(WaitData<T, V> waitData) {
        return untilCoreStatus(waitData, utils::isFalsyData);
    }

    static DriverFunction<Boolean> waitConditionCore(
        By locator,
        Function<By, DriverFunction<Boolean>> conditionGetter,
        String option,
        int interval,
        int timeout,
        String message
    ) {
        final var nameof = "waitConditionCore: ";
        if (isNull(conditionGetter)) {
            return new DriverFunction<Boolean>(appendMessage(DataDefaults.NULL_BOOLEAN_DATA, nameof + "Condition Getter was null."));
        }

        return new DriverFunction<Boolean>(driver -> {
            if (isNull(driver)) {
                return DataDefaults.getSimpleBooleanData(false, nameof + Strings.DRIVER_WAS_NULL);
            }

            final var localMessage = "Element located by: " + locator + " to be " + (isBlank(message) ? "clickable" : message) + "\n.";
            final var waitData = new WaitData<>(driver, conditionGetter.apply(locator), localMessage, interval, timeout);
            return isBlank(option) ? Wait.until(waitData) : Wait.untilNot(waitData);
        });
    }

    static DriverFunction<Boolean> waitConditionCoreF(
            By locator,
            Function<By, DriverFunction<Boolean>> conditionGetter,
            String option,
            int interval,
            int timeout,
            String message
    ) {
        final var nameof = "waitConditionCore: ";
        if (isNull(conditionGetter)) {
            return new DriverFunction<Boolean>(driver -> appendMessage(DataDefaults.NULL_BOOLEAN_DATA, nameof + "Condition Getter was null."));
        }

        return new DriverFunction<Boolean>(driver -> {
            if (isNull(driver)) {
                return DataDefaults.getSimpleBooleanData(false, nameof + Strings.DRIVER_WAS_NULL);
            }

            return waitConditionCore(locator, conditionGetter, option, interval, timeout, message).apply(driver);
        });
    }

    static DriverFunction<Boolean> waitConditionCoreF(ElementWaitParameters data, Function<By, DriverFunction<Boolean>> conditionGetter, String option, String message) {
        return isNotNullElementWaitParametersData(data) ? new DriverFunction<Boolean>(waitConditionCore(data.object, conditionGetter, option, data.interval, data.duration, message)) : DataDefaults.NULL_BOOLEAN_DF;

    }

    static DriverFunction<Boolean> waitConditionCoreF(LazyElement data, Function<LazyElement, DriverFunction<Boolean>> conditionGetter, String option, int interval, int timeout, String message) {
        if (isNull(conditionGetter)) {
            return new DriverFunction<Boolean>(prependMessage(DataDefaults.NULL_BOOLEAN_DATA, Strings.ELEMENT + data.name));
        }

        return new DriverFunction<Boolean>(driver -> {
            if (isNull(driver)) {
                return prependMessage(DataDefaults.NULL_BOOLEAN_DATA, Strings.ELEMENT + data.name);
            }

            final var waitData = new WaitData<>(driver, conditionGetter.apply(data), data.name + " " + message, interval, timeout);
            return prependMessage(isBlank(option) ? Wait.until(waitData) : Wait.untilNot(waitData), Strings.ELEMENT + data.name);
        });
    }

    static DriverFunction<Boolean> waitConditionCoreF(LazyElementWaitParameters data, Function<LazyElement, DriverFunction<Boolean>> conditionGetter, String option, String message) {
        return new DriverFunction<Boolean>(isNotNullLazyElementWaitParametersData(data) ? waitConditionCore(data.object, conditionGetter, option, data.interval, data.duration, message) : DataDefaults.NULL_BOOLEAN_DF);
    }

    static DriverFunction<Boolean> waitConditionCore(
        By locator,
        BiFunction<By, Function<By, DriverFunction<WebElement>>, DriverFunction<Boolean>> conditionGetter,
        String option,
        int interval,
        int timeout,
        String message
    ) {
        final var nameof = "waitConditionCore: ";
        if (isNull(conditionGetter)) {
            return new DriverFunction<Boolean>(appendMessage(DataDefaults.NULL_BOOLEAN_DATA, nameof + "Condition Getter was null."));
        }

        return new DriverFunction<Boolean> (driver -> {
            if (isNull(driver)) {
                return DataDefaults.getSimpleBooleanData(false, nameof + Strings.DRIVER_WAS_NULL);
            }

            final var localMessage = "Element located by: " + locator + " to be " + (isBlank(message) ? "clickable" : message) + Strings.END_LINE;
            final var waitData = new WaitData<>(driver, conditionGetter.apply(locator, Driver::getElement), localMessage, interval, timeout);
            return isBlank(option) ? Wait.until(waitData) : Wait.untilNot(waitData);
        });
    }

    static DriverFunction<Boolean> waitConditionCore(LazyElement data, Function<LazyElement, DriverFunction<Boolean>> conditionGetter, String option, int interval, int timeout, String message) {
        if (isNull(conditionGetter)) {
            return new DriverFunction<Boolean>(prependMessage(DataDefaults.NULL_BOOLEAN_DATA, Strings.ELEMENT + data.name));
        }

        return new DriverFunction<Boolean>(driver -> {
            if (isNull(driver)) {
                return prependMessage(DataDefaults.NULL_BOOLEAN_DATA, Strings.ELEMENT + data.name);
            }

            final var waitData = new WaitData<>(driver, conditionGetter.apply(data), data.name + " " + message, interval, timeout);
            return prependMessage(isBlank(option) ? Wait.until(waitData) : Wait.untilNot(waitData), Strings.ELEMENT + data.name);
        });
    }

    static DriverFunction<Boolean> waitConditionCoreStatus(LazyElement data, Function<LazyElement, DriverFunction<Boolean>> conditionGetter, String option, int interval, int timeout, String message) {
        if (isNull(conditionGetter)) {
            return new DriverFunction<Boolean>(prependMessage(DataDefaults.NULL_BOOLEAN_DATA, Strings.ELEMENT + data.name));
        }

        return new DriverFunction<Boolean>(driver -> {
            if (isNull(driver)) {
                return prependMessage(DataDefaults.NULL_BOOLEAN_DATA, Strings.ELEMENT + data.name);
            }

            final var waitData = new WaitData<>(driver, conditionGetter.apply(data), message, interval, timeout);
            return prependMessage(isBlank(option) ? Wait.untilStatus(waitData) : Wait.untilNotStatus(waitData), Strings.ELEMENT + data.name);
        });
    }

    static DriverFunction<Boolean> waitConditionCore(ElementWaitParameters data, Function<By, DriverFunction<Boolean>> conditionGetter, String option, String message) {
        return isNotNullElementWaitParametersData(data) ? waitConditionCore(data.object, conditionGetter, option, data.interval, data.duration, message) : DataDefaults.NULL_BOOLEAN_DF;
    }

    static DriverFunction<Boolean> waitConditionCore(LazyElementWaitParameters data, Function<LazyElement, DriverFunction<Boolean>> conditionGetter, String option, String message) {
        return isNotNullLazyElementWaitParametersData(data) ? waitConditionCore(data.object, conditionGetter, option, data.interval, data.duration, message) : DataDefaults.NULL_BOOLEAN_DF;
    }
}
