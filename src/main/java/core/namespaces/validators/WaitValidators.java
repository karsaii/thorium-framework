package core.namespaces.validators;

import core.records.WaitTimeData;
import data.constants.Strings;
import data.namespaces.Formatter;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface WaitValidators {
    static String validateWaitTimeData(WaitTimeData timeData) {
        var message = Formatter.isNullMessage(timeData, "TimeData");
        if (isBlank(message)) {
            message += (
                Formatter.isNullMessage(timeData.clock, "TimeData clock") +
                Formatter.isNullMessage(timeData.interval, "TimeData interval") +
                Formatter.isNullMessage(timeData.duration, "TimeData duration")
            );
        }

        return isBlank(message) ? message : (Strings.PARAMETER_ISSUES + message);
    }
    static <T, V> String validateUntilParameters(Function<T, V> condition, Predicate<V> continueCondition, WaitTimeData timeData) {
        final var message = (
            Formatter.isNullMessage(condition, "Condition") +
            Formatter.isNullMessage(continueCondition, "ContinueCondition") +
            validateWaitTimeData(timeData)
        );

        return isBlank(message) ? message : ("Wait.until: " + message);
    }
}
