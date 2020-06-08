package com.github.karsaii.selenium.records.scripter;

import com.github.karsaii.core.records.Data;

import java.util.function.Function;

public class ExecutorWrappedResultFunctionsData<ParameterType, MessageParameterType, ReturnType> extends ExecutorResultFunctionsData<ParameterType, MessageParameterType, Data<ReturnType>> {
    public ExecutorWrappedResultFunctionsData(Function<MessageParameterType, String> messageHandler, Function<ParameterType, Data<ReturnType>> castHandler) {
        super(messageHandler, castHandler);
    }
}
