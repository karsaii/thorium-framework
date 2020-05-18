package core.records;

import core.extensions.interfaces.functional.boilers.DataSupplier;
import core.records.executor.ExecutionResultData;
import core.records.executor.ExecutionStateData;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class StepWaitData<T> {
    public final Function<ExecutionStateData, DataSupplier<ExecutionResultData<T>>> function;
    public final Predicate<Data<ExecutionResultData<T>>> exitCondition;
    public final String conditionMessage;
    public final WaitTimeData timeData;

    public StepWaitData(Function<ExecutionStateData, DataSupplier<ExecutionResultData<T>>> function, Predicate<Data<ExecutionResultData<T>>> exitCondition, String conditionMessage, WaitTimeData timeData) {
        this.function = function;
        this.exitCondition = exitCondition;
        this.conditionMessage = conditionMessage;
        this.timeData = timeData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (StepWaitData<?>) o;
        return Objects.equals(function, that.function) &&
                Objects.equals(exitCondition, that.exitCondition) &&
                Objects.equals(conditionMessage, that.conditionMessage) &&
                Objects.equals(timeData, that.timeData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(function, exitCondition, conditionMessage, timeData);
    }
}
