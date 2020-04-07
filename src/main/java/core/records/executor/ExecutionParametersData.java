package core.records.executor;

import core.abstracts.AbstractExecutionParametersData;
import core.records.Data;
import core.records.command.CommandRangeData;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ExecutionParametersData<DependencyType, ReturnType> extends AbstractExecutionParametersData<DependencyType, ReturnType> {
    public ExecutionParametersData(
        ExecutionData data,
        BiFunction<ExecutionData, Function<DependencyType, Data<?>>[], Function<DependencyType, Data<ReturnType>>> executor,
        CommandRangeData range
    ) {
        super(data, executor, range);
    }
}
