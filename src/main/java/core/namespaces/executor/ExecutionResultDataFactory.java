package core.namespaces.executor;

import core.records.Data;
import core.records.executor.ExecutionResultData;
import core.records.executor.ExecutionStateData;

import java.util.List;
import java.util.Map;

import static core.extensions.namespaces.NullableFunctions.isNotNull;

public interface ExecutionResultDataFactory {
    static <T> ExecutionResultData<T> getWith(ExecutionStateData data, T object) {
        final var lData = isNotNull(data) ? data : ExecutionStateDataFactory.getWithDefaults();
        return new ExecutionResultData<>(data, object);
    }

    static <T> ExecutionResultData<T> getWith(Map<String, Data<?>> map, List<Integer> indices, T object) {
        return getWith(ExecutionStateDataFactory.getWith(map, indices), object);
    }

    static <T> ExecutionResultData<T> getWithDefaultState(T object) {
        return getWith(ExecutionStateDataFactory.getWithDefaults(), object);
    }
}
