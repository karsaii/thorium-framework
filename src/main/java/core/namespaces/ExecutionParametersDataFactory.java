package core.namespaces;

import core.records.Data;
import core.records.executor.ExecutionData;
import core.records.executor.ExecutionParametersData;
import core.constants.ExecutorConstants;
import core.records.executor.ExecutionStepData;
import core.records.executor.StepExecutionParametersData;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ExecutionParametersDataFactory {
    static <DependencyType, ReturnType> ExecutionParametersData<DependencyType, ReturnType> getWithDefaultRange(
        ExecutionData data,
        BiFunction<ExecutionData, Function<DependencyType, Data<?>>[], Function<DependencyType, Data<ReturnType>>> executor
    ) {
        return new ExecutionParametersData<>(data, executor, ExecutorConstants.DEFAULT_RANGE);
    }
}
