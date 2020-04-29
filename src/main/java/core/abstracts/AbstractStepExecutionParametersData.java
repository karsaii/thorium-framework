package core.abstracts;

import core.extensions.interfaces.functional.boilers.DataSupplier;
import core.records.command.CommandRangeData;
import core.records.executor.ExecutionData;

import java.util.Objects;
import java.util.function.BiFunction;

public abstract class AbstractStepExecutionParametersData<ReturnType> {
    public final ExecutionData data;
    public final BiFunction<ExecutionData, DataSupplier<?>[], DataSupplier<ReturnType>> executor;
    public final CommandRangeData range;

    public AbstractStepExecutionParametersData(ExecutionData data, BiFunction<ExecutionData, DataSupplier<?>[], DataSupplier<ReturnType>> executor, CommandRangeData range) {
        this.data = data;
        this.executor = executor;
        this.range = range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (AbstractStepExecutionParametersData<?>) o;
        return Objects.equals(data, that.data) && Objects.equals(executor, that.executor) && Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, executor, range);
    }
}
