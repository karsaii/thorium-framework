package core.namespaces;

import core.constants.CommandRangeDataConstants;
import core.extensions.interfaces.functional.TriFunction;
import core.records.Data;
import core.records.command.CommandRangeData;
import core.records.executor.ExecutionData;
import core.records.executor.ExecutionParametersData;
import core.constants.ExecutorConstants;
import core.records.executor.ExecutionResultData;
import core.records.executor.RepeatExecutionParametersData;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ExecutionParametersDataFactory {
    static <DependencyType, ReturnType> ExecutionParametersData<DependencyType, ReturnType> getWithDefaultRange(
        ExecutionData data,
        BiFunction<ExecutionData, Function<DependencyType, Data<?>>[], Function<DependencyType, Data<ReturnType>>> executor
    ) {
        return new ExecutionParametersData<>(data, executor, CommandRangeDataConstants.DEFAULT_RANGE);
    }

    static <DependencyType, ReturnType> ExecutionParametersData<DependencyType, ReturnType> getWithDefaultRangeAndMessagesExecutor(ExecutionData data) {
        return getWithDefaultRange(data, Executor::executeCoreStepMessages);
    }

    static <DependencyType, ReturnType> ExecutionParametersData<DependencyType, ReturnType> getWithDefaultRangeAndNoMessagesExecutor(ExecutionData data) {
        return getWithDefaultRange(data, Executor::executeCoreNoMessages);
    }

    static <DependencyType, ReturnType> ExecutionParametersData<DependencyType, ReturnType> getWithDefaultDataRangeAndMessagesExecutor() {
        return getWithDefaultRange(ExecutorConstants.DEFAULT_EXECUTION_ENDED, Executor::executeCoreStepMessages);
    }

    static <DependencyType, ReturnType> ExecutionParametersData<DependencyType, ReturnType> getWithDefaultDataRangeAndNoMessagesExecutor() {
        return getWithDefaultRange(ExecutorConstants.DEFAULT_EXECUTION_ENDED, Executor::executeCoreNoMessages);
    }

    static <DependencyType, ReturnType> ExecutionParametersData<DependencyType, ReturnType> getWithMessagesExecutor(ExecutionData data, CommandRangeData range) {
        return new ExecutionParametersData<>(data, Executor::executeCoreStepMessages, range);
    }

    static <DependencyType, ReturnType> ExecutionParametersData<DependencyType, ReturnType> getWithNoMessagesExecutor(ExecutionData data, CommandRangeData range) {
        return new ExecutionParametersData<>(data, Executor::executeCoreNoMessages, range);
    }

    static <DependencyType, ReturnType> ExecutionParametersData<DependencyType, ReturnType> getWithMessagesAndSpecificRange(CommandRangeData range) {
        return new ExecutionParametersData<>(ExecutorConstants.DEFAULT_EXECUTION_ENDED, Executor::executeCoreStepMessages, range);
    }

    static <DependencyType, ReturnType> ExecutionParametersData<DependencyType, ReturnType> getWithNoMessagesAndSpecificRange(CommandRangeData range) {
        return new ExecutionParametersData<>(ExecutorConstants.DEFAULT_EXECUTION_ENDED, Executor::executeCoreNoMessages, range);
    }

    static <DependencyType, ReturnType> RepeatExecutionParametersData<DependencyType, ReturnType> getWithDefaultRangeR(
            ExecutionData data,
            TriFunction<ExecutionData, Map<String, Data<?>>, Function<DependencyType, Data<?>>[], Function<DependencyType, ExecutionResultData<ReturnType>>> executor
    ) {
        return new RepeatExecutionParametersData<>(data, executor, CommandRangeDataConstants.DEFAULT_RANGE);
    }

    static <DependencyType, ReturnType> RepeatExecutionParametersData<DependencyType, ReturnType> getWithDefaultRangeAndMessagesExecutorR(ExecutionData data) {
        return getWithDefaultRangeR(data, RepeatExecutor::executeCoreStepMessages);
    }

    static <DependencyType, ReturnType> RepeatExecutionParametersData<DependencyType, ReturnType> getWithDefaultRangeAndNoMessagesExecutorR(ExecutionData data) {
        return getWithDefaultRangeR(data, RepeatExecutor::executeCoreNoMessages);
    }

    static <DependencyType, ReturnType> RepeatExecutionParametersData<DependencyType, ReturnType> getWithDefaultDataRangeAndMessagesExecutorR() {
        return getWithDefaultRangeR(ExecutorConstants.DEFAULT_EXECUTION_ENDED, RepeatExecutor::executeCoreStepMessages);
    }

    static <DependencyType, ReturnType> RepeatExecutionParametersData<DependencyType, ReturnType> getWithDefaultDataRangeAndNoMessagesExecutorR() {
        return getWithDefaultRangeR(ExecutorConstants.DEFAULT_EXECUTION_ENDED, RepeatExecutor::executeCoreNoMessages);
    }
}
