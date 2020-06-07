package com.github.karsaii.selenium.records.scripter;

import java.util.Objects;
import java.util.function.Function;

public class ExecutorResultData<ParameterType, HandlerType, ReturnType> extends ExecutorResultFunctionsData<ParameterType, HandlerType, ReturnType> {
    public final ParameterType parameter;
    public final ReturnType defaultValue;

    public ExecutorResultData(Function<HandlerType, String> messageHandler, Function<ParameterType, ReturnType> castHandler, ParameterType parameter, ReturnType defaultValue) {
        super(messageHandler, castHandler);
        this.parameter = parameter;
        this.defaultValue = defaultValue;
    }

    public ExecutorResultData(ExecutorResultFunctionsData<ParameterType, HandlerType, ReturnType> data, ParameterType parameter, ReturnType defaultValue) {
        this(data.messageHandler, data.castHandler, parameter, defaultValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass()) || !super.equals(o)) return false;
        var that = (ExecutorResultData<?, ?, ?>) o;
        return Objects.equals(parameter, that.parameter) && Objects.equals(defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parameter, defaultValue);
    }
}
