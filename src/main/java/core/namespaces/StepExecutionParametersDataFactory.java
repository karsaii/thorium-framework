package core.namespaces;

import core.constants.CommandRangeDataConstants;
import core.constants.ExecutorConstants;
import core.extensions.interfaces.functional.boilers.DataSupplier;
import core.records.executor.ExecutionData;
import core.records.executor.StepExecutionParametersData;

import java.util.function.BiFunction;

public interface StepExecutionParametersDataFactory {
    static <ReturnType> StepExecutionParametersData<ReturnType> getWithDefaultRange(
        ExecutionData data,
        BiFunction<ExecutionData, DataSupplier<?>[], DataSupplier<ReturnType>> executor
    ) {
        return new StepExecutionParametersData<>(data, executor, CommandRangeDataConstants.DEFAULT_RANGE);
    }
}
