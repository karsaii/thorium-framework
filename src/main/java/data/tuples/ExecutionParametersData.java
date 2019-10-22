package data.tuples;

import data.DriverFunction;

import java.util.function.BiFunction;

public class ExecutionParametersData<Any> {
    public final ExecutionData data;
    public final BiFunction<ExecutionData, DriverFunction<?>[], DriverFunction<Any>> executor;

    public ExecutionParametersData(ExecutionData data, BiFunction<ExecutionData, DriverFunction<?>[], DriverFunction<Any>> executor) {
        this.data = data;
        this.executor = executor;
    }
}
