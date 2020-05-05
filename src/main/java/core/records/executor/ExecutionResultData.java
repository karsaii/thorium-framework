package core.records.executor;

import core.records.Data;

import java.util.Map;
import java.util.Objects;

public class ExecutionResultData<T> {
    public final Map<String, Data<?>> executionResult;
    public final Data<T> result;

    public ExecutionResultData(Map<String, Data<?>> executionResult, Data<T> result) {
        this.executionResult = executionResult;
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ExecutionResultData<?>) o;
        return Objects.equals(executionResult, that.executionResult) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(executionResult, result);
    }
}
