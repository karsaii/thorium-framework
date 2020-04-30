package core.namespaces.validators;

import core.records.WaitTimeData;
import data.constants.Strings;
import data.namespaces.Formatter;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface WaitValidators {
    static String validateWaitTimeData(WaitTimeData timeData) {
        var message = Formatter.isNullMessageWithName(timeData, "TimeData");
        if (isBlank(message)) {
            message += (
                Formatter.isNullMessageWithName(timeData.clock, "TimeData clock") +
                Formatter.isNullMessageWithName(timeData.interval, "TimeData interval") +
                Formatter.isNullMessageWithName(timeData.duration, "TimeData duration")
            );
        }

        return isBlank(message) ? message : (Strings.PARAMETER_ISSUES + message);
    }
    static <T, V> String validateUntilParameters(Function<T, V> condition, Predicate<V> continueCondition, WaitTimeData timeData) {
        final var message = (
            Formatter.isNullMessageWithName(condition, "Condition") +
            Formatter.isNullMessageWithName(continueCondition, "ContinueCondition") +
            validateWaitTimeData(timeData)
        );

        return isBlank(message) ? message : ("Wait.until: " + message);
    }
}
