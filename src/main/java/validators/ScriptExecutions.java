package validators;

import core.extensions.interfaces.functional.boilers.ScriptHandlerFunction;
import core.extensions.namespaces.NullableFunctions;
import core.records.Data;
import core.records.HandleResultData;
import core.records.caster.BasicCastData;
import core.records.caster.WrappedCastData;
import core.reflection.InvokerParameterizedParametersFieldData;
import core.reflection.abstracts.BaseInvokerDefaultsData;
import core.reflection.abstracts.InvokeResultDefaultsBaseData;
import core.reflection.abstracts.InvokerBaseFunctionalData;
import selenium.records.scripter.ExecutorData;
import selenium.records.scripter.ExecutorParametersFieldData;
import selenium.records.scripter.ExecutorResultFunctionsData;
import selenium.records.scripter.ScriptParametersData;

import java.lang.reflect.Method;
import java.util.function.BiFunction;

import static core.extensions.namespaces.CoreUtilities.areAnyNull;
import static core.extensions.namespaces.CoreUtilities.areNotNull;

public interface ScriptExecutions {
    static boolean isInvalidExecutorParametersData(ExecutorParametersFieldData data) {
        return NullableFunctions.isNull(data) || areAnyNull(data.handler, data.parameters, data.validator);
    }

    static <T> boolean isInvalidInvokerParameterizedData(InvokerParameterizedParametersFieldData<T> data) {
        return NullableFunctions.isNull(data) || areAnyNull(data.validator, data.handler, data.parameters);
    }

    static <T, U> boolean isInvalidInvokerRegularData(BiFunction<Method, T, U> handler) {
        return NullableFunctions.isNull(handler);
    }

    static <T> boolean isInvalidExecutorRegularData(ScriptHandlerFunction handler) {
        return NullableFunctions.isNull(handler);
    }

    static <T> boolean isInvalidScriptParametersData(ScriptParametersData<T> data) {
        return NullableFunctions.isNull(data) || areAnyNull(data.converter, data.parameters, data.validator);
    }

    static <T> boolean isInvalidCastData(WrappedCastData<T> data) {
        return NullableFunctions.isNull(data) || areAnyNull(data.caster, data.defaultValue);
    }

    static <T> boolean isValidCastData(WrappedCastData<T> data) {
        return NullableFunctions.isNotNull(data) && areNotNull(data.caster, data.defaultValue);
    }

    static <T> boolean isValidCastData(BasicCastData<T> data) {
        return NullableFunctions.isNotNull(data) && areNotNull(data.caster, data.defaultValue);
    }


    static <T, U, V, W> boolean isValidConstructorData(ExecutorData<T, U, V, W> data) {
        return (
            NullableFunctions.isNotNull(data) &&
            areNotNull(data.constructor, data.getter, data.guard) &&
            isValidCastData(data.castData) &&
            isValidExecutorResultFunctionsData(data.resultHandlers)
        );
    }

    static <T, U, V> boolean isValidInvokerDefaults(BaseInvokerDefaultsData<T, U, V> data) {
        return NullableFunctions.isNotNull(data) && areNotNull(data.constructor, data.castData, data.guard) && isValidCastData(data.castData);
    }

    static <T, U> boolean isValidInvokerConstructorData(InvokerBaseFunctionalData<T, U> data) {
        return NullableFunctions.isNotNull(data) && areNotNull(data.constructor, data.guard);
    }

    static <U, Y> boolean isValidInvokerResultFunctionsData(InvokeResultDefaultsBaseData<U, Data<Y>> data) {
        return NullableFunctions.isNotNull(data) && areNotNull(data.castHandler, data.messageHandler);
    }

    static boolean isNonZeroAmountOfParameters(Object[] data) {
        return NullableFunctions.isNotNull(data) && (data.length > 0);
    }

    static boolean isSingleParameter(Object[] data) {
        return NullableFunctions.isNotNull(data) && (data.length == 1);
    }

    static <T, U> boolean isValidHandlerResultData(HandleResultData<T, U> data) {
        return NullableFunctions.isNotNull(data) && areNotNull(data.caster, data.parameter, data.defaultValue);
    }

    static <T, U, V> boolean isValidExecutorResultFunctionsData(ExecutorResultFunctionsData<T, U, V> data) {
        return NullableFunctions.isNotNull(data) && areNotNull(data.castHandler, data.messageHandler);
    }


}
