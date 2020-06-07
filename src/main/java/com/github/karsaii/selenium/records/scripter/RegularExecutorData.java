package com.github.karsaii.selenium.records.scripter;

import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.selenium.namespaces.extensions.boilers.ScriptHandlerFunction;
import com.github.karsaii.core.records.ExecuteCommonData;
import com.github.karsaii.core.records.HandleResultData;
import com.github.karsaii.core.records.caster.WrappedCastData;
import org.openqa.selenium.JavascriptExecutor;
import com.github.karsaii.selenium.abstracts.ExecuteBaseData;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class RegularExecutorData<ReturnType> extends ExecutorData<ScriptHandlerFunction, String, Boolean, ReturnType> {
    public RegularExecutorData(
        DriverFunction<JavascriptExecutor> getter,
        BiFunction<ExecuteCommonData<String>, ScriptHandlerFunction, ExecuteBaseData<String, Function<String, Object>>> constructor,
        Predicate<ScriptHandlerFunction> guard,
        WrappedCastData<ReturnType> castData,
        ExecutorWrappedResultFunctionsData<HandleResultData<String, ReturnType>, Boolean, ReturnType> resultHandlers
    ) {
        super(getter, constructor, guard, castData, resultHandlers);
    }
}
