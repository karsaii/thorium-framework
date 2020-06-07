package com.github.karsaii.selenium.records.scripter;

import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.core.records.ExecuteCommonData;
import com.github.karsaii.core.records.HandleResultData;
import com.github.karsaii.core.records.caster.WrappedCastData;
import org.openqa.selenium.JavascriptExecutor;
import com.github.karsaii.selenium.abstracts.ExecuteBaseData;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class ParameterizedExecutorData<ReturnType> extends ExecutorData<ExecutorParametersFieldData, String, Boolean, ReturnType> {
    public ParameterizedExecutorData(
        DriverFunction<JavascriptExecutor> getter,
        BiFunction<ExecuteCommonData<String>, ExecutorParametersFieldData, ExecuteBaseData<String, Function<String, Object>>> constructor,
        Predicate<ExecutorParametersFieldData> guard,
        WrappedCastData<ReturnType> castData,
        ExecutorWrappedResultFunctionsData<HandleResultData<String, ReturnType>, Boolean, ReturnType> resultHandlers
    ) {
        super(getter, constructor, guard, castData, resultHandlers);
    }
}
