package core.namespaces.validators;

import core.records.Data;
import core.records.WaitTimeData;
import data.namespaces.Formatter;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface RepeatWaitValidators {
    static <T, V> String validateUntilParameters(Function<T, Data<?>>[] functions, BiPredicate<Predicate<Data<?>>, Data<?>[]> continueCondition, WaitTimeData timeData) {
        final var message = (
            Formatter.isNullMessageWithName(functions, "Functions") +
            Formatter.isNullMessageWithName(continueCondition, "ContinueCondition") +
            WaitValidators.validateWaitTimeData(timeData)
        );

        return isBlank(message) ? message : ("Wait.until: " + message);
    }
}
