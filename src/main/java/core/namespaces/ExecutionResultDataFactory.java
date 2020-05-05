package core.namespaces;

import core.records.executor.ExecutionResultData;

public interface ExecutionResultDataFactory {
    static <T> ExecutionResultData<T> replaceMessage(ExecutionResultData<T> data, String message) {
        return new ExecutionResultData<>(data.executionResult, DataFactoryFunctions.replaceMessage(data.result, message));
    }

    static <T> ExecutionResultData<T> replaceMessage(ExecutionResultData<T> data, String nameof, String message) {
        return new ExecutionResultData<>(data.executionResult, DataFactoryFunctions.replaceMessage(data.result, nameof, message));
    }

    static <T> ExecutionResultData<T> prependMessage(ExecutionResultData<T> data, String nameof, String message) {
        return new ExecutionResultData<>(data.executionResult, DataFactoryFunctions.prependMessage(data.result, nameof, message));
    }
}
