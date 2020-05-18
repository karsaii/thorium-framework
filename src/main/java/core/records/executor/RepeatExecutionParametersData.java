package core.records.executor;

import core.extensions.interfaces.functional.TriFunction;
import core.records.Data;
import core.records.command.CommandRangeData;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class RepeatExecutionParametersData<DependencyType, ReturnType> {
    public final ExecutorFunctionData data;
    public final TriFunction<ExecutorFunctionData, Map<String, Data<?>>, Function<DependencyType, Data<?>>[], Function<DependencyType, ExecutionResultData<ReturnType>>> executor;
    public final CommandRangeData range;

    public RepeatExecutionParametersData(ExecutorFunctionData data, TriFunction<ExecutorFunctionData, Map<String, Data<?>>, Function<DependencyType, Data<?>>[], Function<DependencyType, ExecutionResultData<ReturnType>>> executor, CommandRangeData range) {
        this.data = data;
        this.executor = executor;
        this.range = range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (RepeatExecutionParametersData<?, ?>) o;
        return Objects.equals(data, that.data) && Objects.equals(executor, that.executor) && Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, executor, range);
    }
}
