package core.namespaces;

import core.exceptions.ArgumentNullException;
import core.exceptions.WaitTimeoutException;
import core.namespaces.validators.WaitValidators;
import core.extensions.namespaces.CoreUtilities;
import core.records.WaitData;
import data.constants.Strings;
import data.namespaces.Formatter;

import java.util.function.Function;

import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static core.namespaces.DataFunctions.getMessageFromData;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface Wait {
    private static <T, V> V untilCore(T dependency, WaitData<T, V> waitData) {
        if (CoreUtilities.areAnyNull(dependency, waitData)) {
            throw new ArgumentNullException("Dependency or WaitData was wrong" + Strings.END_LINE);
        }

        final var condition = waitData.condition;
        final var exitCondition = waitData.exitCondition;
        final var timeData = waitData.timeData;
        final var errorMessage = WaitValidators.validateUntilParameters(condition, exitCondition, timeData);
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

        throw new WaitTimeoutException(message + conditionMessage);
    }

    static <T, V> Function<T, V> untilCore(WaitData<T, V> waitData) {
        return dependency -> untilCore(dependency, waitData);
    }
}
