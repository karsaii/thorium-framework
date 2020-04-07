package core.namespaces;

import core.extensions.interfaces.functional.TriPredicate;
import core.extensions.interfaces.functional.boilers.IGetMessage;
import core.records.Data;
import core.records.executor.ExecuteParametersData;
import core.records.executor.ExecutionData;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface ExecutionDataFactory {
    static ExecutionData getWithDefaultExitCondition(IGetMessage messageData, Predicate<Boolean> endStatus, BiFunction<String, String, String> messageHandler) {
        return new ExecutionData(messageData, Executor::isExecuting, endStatus, messageHandler);
    }

    static ExecutionData getWithExecuteParametersData(IGetMessage messageData, TriPredicate<Data<?>, Integer, Integer> exitCondition, ExecuteParametersData data) {
        return new ExecutionData(messageData, exitCondition, data.endStatus, data.messageHandler);
    }

    static ExecutionData getWithExecuteParametersDataAndDefaultExitCondition(IGetMessage messageData, ExecuteParametersData data) {
        return new ExecutionData(messageData, Executor::isExecuting, data.endStatus, data.messageHandler);
    }
}
