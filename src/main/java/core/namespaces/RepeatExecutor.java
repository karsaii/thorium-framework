package core.namespaces;

import core.constants.CommandRangeDataConstants;
import core.constants.CoreConstants;
import core.constants.CoreDataConstants;
import core.constants.ExecutorConstants;
import core.extensions.interfaces.functional.boilers.IGetMessage;
import core.records.Data;
import core.records.SimpleMessageData;
import core.records.executor.ExecutionData;
import core.records.executor.ExecutionResultData;
import core.records.executor.RepeatExecutionParametersData;
import data.constants.Strings;
import data.namespaces.Formatter;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static core.namespaces.DependencyExecutionFunctions.ifDependencyR;
import static core.namespaces.validators.DataValidators.isValidNonFalse;
import static selenium.namespaces.ExecutionCore.validChain;

public interface RepeatExecutor {
    private static Data<?> coreWithMessages(ExecutionData executionData, Data<?> stepResult, int index, String message) {
        return DataFactoryFunctions.replaceStatusAndMessage(
                stepResult,
                executionData.endStatus.test(stepResult.status),
                executionData.messageHandler.apply(message, Formatter.getExecutionStepMessage(index, stepResult.message.toString()))
        );
    }

    static <DependencyType, ReturnType> ExecutionResultData<ReturnType> executeCoreStepMessagesCore(
            DependencyType dependency,
            ExecutionData executionData,
            Map<String, Data<?>> executionResultMap,
            Function<DependencyType, Data<?>>[] steps
    ) {
        Data<?> data = CoreDataConstants.NO_STEPS;
        var index = 0;
        var previousMessage = "";
        final var length = steps.length;
        for(; index < length; ++index) {
            data = coreWithMessages(executionData, steps[index].apply(dependency), index, previousMessage);
            previousMessage = data.message.toString();
            if (isValidNonFalse(data)) {
                executionResultMap.put(Formatter.getExecutionResultKey(data.message.nameof, index), data);
            }
        }

        return new ExecutionResultData<>(
            Map.copyOf(executionResultMap),
            DataFactoryFunctions.getWithNameAndMessage((ReturnType)data.object, data.status, "execute", Formatter.getExecutionEndMessage(index, length, data.message.toString()))
        );
    }

    static <DependencyType, ReturnType> ExecutionResultData<ReturnType> executeCoreNoMessagesCore(
            DependencyType dependency,
            ExecutionData executionData,
            Map<String, Data<?>> executionResultMap,
            Function<DependencyType, Data<?>>[] steps
    ) {
        Data<?> data = CoreDataConstants.NO_STEPS;
        var index = 0;
        final var length = steps.length;
        for(; index < length; ++index) {
            data = core(executionData, steps[index].apply(dependency));
            if (isValidNonFalse(data)) {
                executionResultMap.put(Formatter.getExecutionResultKey(data.message.nameof, index), data);
            }
        }

        return new ExecutionResultData<>(
                Map.copyOf(executionResultMap),
                DataFactoryFunctions.getWithNameAndMessage((ReturnType)data.object, data.status, "execute", Formatter.getExecutionEndMessage(index, length, data.message.toString()))
        );
    }

    private static Data<?> core(ExecutionData executionData, Data<?> stepResult) {
        return DataFactoryFunctions.replaceStatus(stepResult, executionData.endStatus.test(stepResult.status));
    }

    @SafeVarargs
    static <DependencyType, Any> Function<DependencyType, ExecutionResultData<Any>> executeCoreStepMessages(ExecutionData executionData, Map<String, Data<?>> executionResultMap, Function<DependencyType, Data<?>>... steps) {
        return driver -> executeCoreStepMessagesCore(driver, executionData, executionResultMap, steps);
    }

    @SafeVarargs
    static <DependencyType, Any> Function<DependencyType, ExecutionResultData<Any>> executeCoreNoMessages(ExecutionData executionData, Map<String, Data<?>> executionResultMap, Function<DependencyType, Data<?>>... steps) {
        return driver -> executeCoreNoMessagesCore(driver, executionData, executionResultMap, steps);
    }

    static <Any> ExecutionResultData<Any> executeCore(ExecutionResultData<Any> data, ExecutionData executionData) {
        final var status = data.result.status;
        return new ExecutionResultData<Any>(
            data.executionResult,
            DataFactoryFunctions.getWithNameAndMessage(data.result.object, status, "executeCore", executionData.messageData.get().apply(status) + data.result.message)
        );
    }

    static <Any> Function<ExecutionResultData<Any>, ExecutionResultData<Any>> executeCore(ExecutionData executionData) {
        return data -> executeCore(data, executionData);
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, ExecutionResultData<ReturnType>> execute(RepeatExecutionParametersData<DependencyType, ReturnType> execution, Map<String, Data<?>> executionResultMap, Function<DependencyType, Data<?>>... steps) {
        final var data = execution.data;
        final var negative = new ExecutionResultData<>(null, DataFactoryFunctions.getWithMessage((ReturnType) CoreConstants.STOCK_OBJECT, false, Strings.EMPTY));
        return ifDependencyR(
                "execute",
                Formatter.getCommandAmountRangeErrorMessage(steps.length, execution.range),
                validChain(execution.executor.apply(data, executionResultMap, steps), executeCore(data), negative),
                negative
        );
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, ExecutionResultData<ReturnType>> execute(IGetMessage stepMessage, Map<String, Data<?>> executionResultMap, Function<DependencyType, Data<?>>... steps) {
        return execute(
                new RepeatExecutionParametersData<>(
                        ExecutionDataFactory.getWithSpecificMessageData(stepMessage),
                        RepeatExecutor::executeCoreStepMessages,
                        CommandRangeDataConstants.DEFAULT_RANGE
                ),
                executionResultMap,
                steps
        );
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, ExecutionResultData<ReturnType>> execute(String message, Map<String, Data<?>> executionResultMap, Function<DependencyType, Data<?>>... steps) {
        return execute(
                ExecutionParametersDataFactory.getWithDefaultRangeR(
                        ExecutionDataFactory.getWithSpecificMessageData(new SimpleMessageData(message)),
                        RepeatExecutor::executeCoreStepMessages
                ),
                executionResultMap,
                steps
        );
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, ExecutionResultData<ReturnType>> execute(BiFunction<String, String, String> messageHandler, Map<String, Data<?>> executionResultMap, Function<DependencyType, Data<?>>... steps) {
        return execute(
                ExecutionParametersDataFactory.getWithDefaultRangeR(
                        ExecutionDataFactory.getWithSpecificMessageHandler(messageHandler),
                        RepeatExecutor::executeCoreStepMessages
                ),
                executionResultMap,
                steps
        );
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, ExecutionResultData<ReturnType>> execute(Map<String, Data<?>> executionResultMap, Function<DependencyType, Data<?>>... steps) {
        return execute(ExecutionParametersDataFactory.getWithDefaultRangeR(ExecutorConstants.DEFAULT_EXECUTION_ENDED, RepeatExecutor::executeCoreStepMessages), executionResultMap, steps);
    }
}
