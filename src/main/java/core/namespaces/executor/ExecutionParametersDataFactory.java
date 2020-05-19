package core.namespaces.executor;

import core.constants.CommandRangeDataConstants;
import core.extensions.interfaces.functional.TriFunction;
import core.records.command.CommandRangeData;
import core.records.executor.ExecutionStateData;
import core.records.executor.ExecutorFunctionData;
import core.records.executor.ExecutionParametersData;
import core.constants.ExecutorConstants;

public interface ExecutionParametersDataFactory {
    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWith(
        ExecutorFunctionData data,
        TriFunction<ExecutorFunctionData, ExecutionStateData, ArrayType[], ReturnType> executor,
        CommandRangeData range
    ) {
        return new ExecutionParametersData<>(data, executor, range);
    }

    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWithTwoCommandsRange(
        ExecutorFunctionData data,
        TriFunction<ExecutorFunctionData, ExecutionStateData, ArrayType[], ReturnType> executor
    ) {
        return getWith(data, executor, CommandRangeDataConstants.TWO_COMMANDS_RANGE);
    }

    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWithDefaultRange(
        ExecutorFunctionData data,
        TriFunction<ExecutorFunctionData, ExecutionStateData, ArrayType[], ReturnType> executor
    ) {
        return getWith(data, executor, CommandRangeDataConstants.DEFAULT_RANGE);
    }

    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWithDefaultFunctionData(
        TriFunction<ExecutorFunctionData, ExecutionStateData, ArrayType[], ReturnType> executor,
        CommandRangeData range
    ) {
        return getWith(ExecutorConstants.DEFAULT_EXECUTION_ENDED, executor, range);
    }

    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWithDefaultFunctionDataAndDefaultRange(
        TriFunction<ExecutorFunctionData, ExecutionStateData, ArrayType[], ReturnType> executor
    ) {
        return getWithDefaultFunctionData(executor, CommandRangeDataConstants.DEFAULT_RANGE);
    }

    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWithDefaultFunctionDataAndTwoCommandRange(
        TriFunction<ExecutorFunctionData, ExecutionStateData, ArrayType[], ReturnType> executor
    ) {
        return getWithDefaultFunctionData(executor, CommandRangeDataConstants.TWO_COMMANDS_RANGE);
    }
}
