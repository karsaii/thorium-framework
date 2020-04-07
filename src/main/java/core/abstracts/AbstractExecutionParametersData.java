package core.abstracts;

import core.records.Data;
import core.records.command.CommandRangeData;
import core.records.executor.ExecutionData;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractExecutionParametersData<DependencyType, ReturnType> {
    public final ExecutionData data;
    public final BiFunction<ExecutionData, Function<DependencyType, Data<?>>[], Function<DependencyType, Data<ReturnType>>> executor;
    public final CommandRangeData range;

    public AbstractExecutionParametersData(ExecutionData data, BiFunction<ExecutionData, Function<DependencyType, Data<?>>[], Function<DependencyType, Data<ReturnType>>> executor, CommandRangeData range) {
        this.data = data;
        this.executor = executor;
        this.range = range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (AbstractExecutionParametersData<?, ?>) o;
        return Objects.equals(data, that.data) && Objects.equals(executor, that.executor) && Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, executor, range);
    }
}