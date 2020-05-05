package core.namespaces;

import core.exceptions.ArgumentNullException;
import core.exceptions.WaitTimeoutException;
import core.extensions.namespaces.CoreUtilities;
import core.namespaces.validators.DataValidators;
import core.namespaces.validators.RepeatWaitValidators;
import core.records.Data;
import core.records.RepeatWaitData;
import core.records.executor.ExecutionResultData;
import data.constants.Strings;
import data.namespaces.Formatter;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.function.Function;

import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static core.namespaces.DataFunctions.getMessageFromData;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface WaitRepeat {
    private static <T, V> Data<V> core(T dependency, RepeatWaitData<T, V> waitData) {
        if (CoreUtilities.areAnyNull(dependency, waitData)) {
            throw new ArgumentNullException("Dependency or WaitData was wrong" + Strings.END_LINE);
        }

        final var functions = waitData.functions;
        final var exitCondition = waitData.exitCondition;
        final var timeData = waitData.timeData;
        final var errorMessage = RepeatWaitValidators.validateUntilParameters(functions, waitData.exitCondition, timeData);
        if (isNotBlank(errorMessage)) {
            throw new ArgumentNullException(errorMessage);
        }

        final var timeout = timeData.duration;
        final var interval = timeData.interval.toMillis();
        final var clock = timeData.clock;

        final var start = clock.instant();
        final var end = start.plus(timeout);
        var message = "";
        Data<?> value = null;
        ExecutionResultData<?> innerValue;
        final var executionMap = new LinkedHashMap<String, Data<?>>();
        try {
            for(; end.isAfter(clock.instant()); Thread.sleep(interval)) {
                innerValue = RepeatExecutor.execute(executionMap, functions).apply(dependency);
                value = innerValue.result;
                if (
                    Objects.equals(innerValue.executionResult.size(), functions.length) &&
                    exitCondition.test(
                        DataValidators::isValidNonFalse,
                        innerValue.executionResult.values().toArray(new Data<?>[0])
                    )
                ) {
                    return DataFactoryFunctions.getWithMethodMessage((V)value.object, value.status, value.message, value.exception, value.exceptionMessage);
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

    static <T, V> Function<T, Data<V>> core(RepeatWaitData<T, V> waitData) {
        return dependency -> core(dependency, waitData);
    }
}
