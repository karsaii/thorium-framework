package com.github.karsaii.selenium.records.scripter;

import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.core.records.ExecuteCommonData;
import com.github.karsaii.core.records.HandleResultData;
import com.github.karsaii.core.records.caster.WrappedCastData;
import org.openqa.selenium.JavascriptExecutor;
import com.github.karsaii.selenium.abstracts.BaseFunctionalData;
import com.github.karsaii.selenium.abstracts.ExecuteBaseData;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class ExecutorData<HandlerType, ParameterType, MessageParameterType, ReturnType> extends
        BaseFunctionalData<JavascriptExecutor, HandlerType, ParameterType, MessageParameterType, ExecuteCommonData<ParameterType>, ExecuteBaseData<ParameterType, Function<String, Object>>, ReturnType> {
    public ExecutorData(
            DriverFunction<JavascriptExecutor> getter,
            BiFunction<ExecuteCommonData<ParameterType>, HandlerType, ExecuteBaseData<ParameterType, Function<String, Object>>> constructor,
            Predicate<HandlerType> guard,
            WrappedCastData<ReturnType> castData,
            ExecutorWrappedResultFunctionsData<HandleResultData<ParameterType, ReturnType>, MessageParameterType, ReturnType> resultHandlers
    ) {
        super(getter, constructor, guard, castData, resultHandlers);
    }
}