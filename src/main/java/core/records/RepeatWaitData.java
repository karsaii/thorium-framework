package core.records;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class RepeatWaitData<T, V> {
    public final Function<T, Data<?>>[] functions;
    public final BiPredicate<Predicate<Data<?>>, Data<?>[]> exitCondition;
    public final String conditionMessage;
    public final WaitTimeData timeData;

    public RepeatWaitData(Function<T, Data<?>>[] functions, BiPredicate<Predicate<Data<?>>, Data<?>[]> exitCondition, String conditionMessage, WaitTimeData timeData) {
        this.functions = functions;
        this.exitCondition = exitCondition;
        this.conditionMessage = conditionMessage;
        this.timeData = timeData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (RepeatWaitData<?, ?>) o;
        return (
            Arrays.equals(functions, that.functions) &&
            Objects.equals(exitCondition, that.exitCondition) &&
            Objects.equals(conditionMessage, that.conditionMessage) &&
            Objects.equals(timeData, that.timeData)
        );
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(exitCondition, conditionMessage, timeData) + Arrays.hashCode(functions);
    }
}
