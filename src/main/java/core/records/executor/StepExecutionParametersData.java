package core.records.executor;

import core.abstracts.AbstractStepExecutionParametersData;
import core.extensions.interfaces.functional.boilers.DataSupplier;
import core.records.command.CommandRangeData;

import java.util.function.BiFunction;

public class StepExecutionParametersData<ReturnType> extends AbstractStepExecutionParametersData<ReturnType> {
    public StepExecutionParametersData(
        ExecutionData data,
        BiFunction<ExecutionData, DataSupplier<?>[], DataSupplier<ReturnType>> executor,
        CommandRangeData range
    ) {
        super(data, executor, range);
    }
}
