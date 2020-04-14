package validators;

import data.constants.Strings;
import selenium.namespaces.extensions.boilers.ScriptHandlerFunction;
import core.records.Data;
import core.records.HandleResultData;
import core.records.caster.BasicCastData;
import core.records.caster.WrappedCastData;
import core.records.reflection.InvokerParameterizedParametersFieldData;
import core.abstracts.reflection.BaseInvokerDefaultsData;
import core.abstracts.reflection.InvokeResultDefaultsBaseData;
import core.abstracts.reflection.InvokerBaseFunctionalData;
import selenium.records.scripter.ExecutorData;
import selenium.records.scripter.ExecutorParametersFieldData;
import selenium.records.scripter.ExecutorResultFunctionsData;
import selenium.records.scripter.ScriptParametersData;

import java.lang.reflect.Method;
import java.util.function.BiFunction;

import static core.extensions.namespaces.AmountPredicatesFunctions.isSingle;
import static core.extensions.namespaces.CoreUtilities.areNotNull;
import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static data.namespaces.Formatter.isNullMessage;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ScriptExecutions {
    static boolean isValidExecutorParametersData(ExecutorParametersFieldData data) {
        return isNotNull(data) && areNotNull(data.handler, data.parameters, data.validator);
    }

    static <T> boolean isValidInvokerParameterizedData(InvokerParameterizedParametersFieldData<T> data) {
        return isNotNull(data) && areNotNull(data.handler, data.parameters, data.validator);
    }

    static <T, U> boolean isValidInvokerRegularData(BiFunction<Method, T, U> handler) {
        return isNotNull(handler);
    }

    static <T> boolean isValidExecutorRegularData(ScriptHandlerFunction handler) {
        return isNotNull(handler);
    }

    static <T> boolean isValidScriptParametersData(ScriptParametersData<T> data) {
        return isNotNull(data) && areNotNull(data.converter, data.parameters, data.validator);
    }

    static <T> boolean isValidCastData(WrappedCastData<T> data) {
        return isNotNull(data) && areNotNull(data.caster, data.defaultValue);
    }

    static <T> boolean isValidCastData(BasicCastData<T> data) {
        return isNotNull(data) && areNotNull(data.caster, data.defaultValue);
    }

    static <T> String isInvalidCastDataMessage(BasicCastData<T> data) {
        final var baseName = "Basic Cast Data";
        var message = isNullMessage(data, baseName);
        if (isBlank(message)) {
            message += (
                isNullMessage(data.caster, baseName + "Caster") +
                isNullMessage(data.defaultValue, baseName + "Default value")
            );
        }

        return isNotBlank(message) ? "isInvalidCastDataMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }

    static <T, U, V, W> boolean isValidConstructorData(ExecutorData<T, U, V, W> data) {
        return (
            isNotNull(data) &&
            areNotNull(data.constructor, data.getter, data.guard) &&
            isValidCastData(data.castData) &&
            isValidExecutorResultFunctionsData(data.resultHandlers)
        );
    }

    static <T, U, V> boolean isValidInvokerDefaults(BaseInvokerDefaultsData<T, U, V> data) {
        return isNotNull(data) && areNotNull(data.constructor, data.castData, data.guard) && isValidCastData(data.castData);
    }

    static <T, U, V> String isInvalidInvokerDefaultsMessage(BaseInvokerDefaultsData<T, U, V> data) {
        final var baseName = "Invoker Defaults Data";
        var message = isNullMessage(data, baseName);
        if (isBlank(message)) {
            message += (
                isNullMessage(data.constructor, baseName + " Constructor") +
                isInvalidCastDataMessage(data.castData) +
                isNullMessage(data.guard, baseName + " Guard")
            );
        }

        return isNotBlank(message) ? "isInvalidInvokerDefaultsMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }

    static <T, U> boolean isValidInvokerConstructorData(InvokerBaseFunctionalData<T, U> data) {
        return isNotNull(data) && areNotNull(data.constructor, data.guard);
    }

    static <U, Y> boolean isValidInvokerResultFunctionsData(InvokeResultDefaultsBaseData<U, Data<Y>> data) {
        return isNotNull(data) && areNotNull(data.castHandler, data.messageHandler);
    }

    static <T, U> boolean isValidHandlerResultData(HandleResultData<T, U> data) {
        return isNotNull(data) && areNotNull(data.caster, data.parameter, data.defaultValue);
    }

    static <T, U> String isInvalidHandlerResultDataMessage(HandleResultData<T, U> data) {
        final var baseName = "Handle Result Data";
        var message = isNullMessage(data, baseName);
        if (isBlank(message)) {
            message += (
                isNullMessage(data.caster, baseName + " Caster") +
                isNullMessage(data.parameter, baseName + " Parameter") +
                isNullMessage(data.defaultValue, baseName + " Default Value")
            );
        }
        return isNotBlank(message) ? "isInvalidHandlerResultDataMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }


    static <T, U, V> boolean isValidExecutorResultFunctionsData(ExecutorResultFunctionsData<T, U, V> data) {
        return isNotNull(data) && areNotNull(data.castHandler, data.messageHandler);
    }


}
