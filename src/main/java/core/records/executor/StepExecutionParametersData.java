package core.records.executor;

import core.abstracts.AbstractStepExecutionParametersData;
import core.extensions.interfaces.functional.TriFunction;
import core.extensions.interfaces.functional.boilers.DataSupplier;
import core.records.Data;
import core.records.command.CommandRangeData;

import java.util.function.BiFunction;
import java.util.function.Function;

public class StepExecutionParametersData<ReturnType> extends AbstractStepExecutionParametersData<ReturnType> {
    public StepExecutionParametersData(
        ExecutorFunctionData data,
        TriFunction<ExecutorFunctionData, ExecutionStateData, Function<Void, Data<?>>[], DataSupplier<ExecutionResultData<ReturnType>>> executor,
        CommandRangeData range
    ) {
        super(data, executor, range);
    }
}
