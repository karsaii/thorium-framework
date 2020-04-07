package core.namespaces;

import core.records.Data;
import core.records.executor.ExecutionData;
import core.records.executor.ExecutionParametersData;
import selenium.constants.ExecutorConstants;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface ExecutionParametersDataFactory {
    static <T, Any> ExecutionParametersData<T, Any> getWithDefaultRange(ExecutionData data, BiFunction<ExecutionData, Function<T, Data<?>>[], Function<T, Data<Any>>> executor) {
        return new ExecutionParametersData<>(data, executor, ExecutorConstants.DEFAULT_RANGE);
    }
}
