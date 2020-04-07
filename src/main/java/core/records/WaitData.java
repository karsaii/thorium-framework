package core.records;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class WaitData<T, V> {
    public final Function<T, V> condition;
    public final Predicate<V> exitCondition;
    public final String conditionMessage;
    public final WaitTimeData timeData;

    public WaitData(Function<T, V> condition, Predicate<V> exitCondition, String conditionMessage, WaitTimeData timeData) {
        this.condition = condition;
        this.exitCondition = exitCondition;
        this.conditionMessage = conditionMessage;
        this.timeData = timeData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var waitData = (WaitData<?, ?>) o;
        return (
            Objects.equals(condition, waitData.condition) &&
            Objects.equals(exitCondition, waitData.exitCondition) &&
            Objects.equals(conditionMessage, waitData.conditionMessage) &&
            Objects.equals(timeData, waitData.timeData)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, exitCondition, conditionMessage, timeData);
    }
}
