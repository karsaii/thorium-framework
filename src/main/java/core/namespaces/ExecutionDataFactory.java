package core.namespaces;

import core.constants.ExecutorConstants;
import core.extensions.interfaces.functional.TriFunction;
import core.extensions.interfaces.functional.TriPredicate;
import core.extensions.interfaces.functional.boilers.IGetMessage;
import core.records.Data;
import core.records.SimpleMessageData;
import core.records.executor.ExecuteParametersData;
import core.records.executor.ExecutionData;
import data.namespaces.Formatter;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface ExecutionDataFactory {
    static ExecutionData getWithDefaultExecuteParametersData(IGetMessage messageData, TriPredicate<Data<?>, Integer, Integer> exitCondition) {
        final var data = ExecutorConstants.DEFAULT_EXECUTION_DATA;
        return new ExecutionData(messageData, exitCondition, data.endStatus, data.messageHandler, data.endMessageHandler);
    }

    static ExecutionData getWithExecuteParametersData(IGetMessage messageData, TriPredicate<Data<?>, Integer, Integer> exitCondition, ExecuteParametersData data) {
        return new ExecutionData(messageData, exitCondition, data.endStatus, data.messageHandler, data.endMessageHandler);
    }

    static ExecutionData getWithExecuteParametersDataAndDefaultExitCondition(IGetMessage messageData, ExecuteParametersData data) {
        return new ExecutionData(messageData, Executor::isExecuting, data.endStatus, data.messageHandler, data.endMessageHandler);
    }

    static ExecutionData getWithDefaultExitCondition(
        IGetMessage messageData,
        Predicate<Boolean> endStatus,
        BiFunction<String, String, String> messageHandler,
        TriFunction<Integer, Integer, String, String> endMessageHandler
    ) {
        return new ExecutionData(messageData, Executor::isExecuting, endStatus, messageHandler, endMessageHandler);
    }

    static ExecutionData getWithDefaultExitConditionAndMessageData(
        Predicate<Boolean> endStatus,
        BiFunction<String, String, String> messageHandler,
        TriFunction<Integer, Integer, String, String> endMessageHandler
    ) {
        return getWithDefaultExitCondition(new SimpleMessageData(), endStatus, messageHandler, endMessageHandler);
    }

    static ExecutionData getWithDefaultExitConditionAndDefaultMessageHandlerAndData(
        BiFunction<String, String, String> messageHandler,
        TriFunction<Integer, Integer, String, String> endMessageHandler
    ) {
        return getWithDefaultExitConditionAndMessageData(Executor::returnStatus, messageHandler, endMessageHandler);
    }

    static ExecutionData getWithSpecificMessageHandler(
        BiFunction<String, String, String> messageHandler
    ) {
        return getWithDefaultExitConditionAndDefaultMessageHandlerAndData(messageHandler, Formatter::getExecutionEndMessage);
    }

    static ExecutionData getWithSpecificEndMessageHandler(
        TriFunction<Integer, Integer, String, String> endMessageHandler
    ) {
        return getWithDefaultExitConditionAndMessageData(Executor::returnStatus, Executor::reduceMessage, endMessageHandler);
    }

    static ExecutionData getWithSpecificMessageData(IGetMessage messageData) {
        return getWithExecuteParametersDataAndDefaultExitCondition(messageData, ExecutorConstants.DEFAULT_EXECUTION_DATA);
    }
}
