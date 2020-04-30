package validators;

import core.constants.CastDataConstants;
import data.constants.Strings;
import data.namespaces.Formatter;
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
import java.util.Objects;
import java.util.function.BiFunction;

import static core.extensions.namespaces.AmountPredicatesFunctions.isSingle;
import static core.extensions.namespaces.CoreUtilities.areNotNull;
import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ScriptExecutions {
    static boolean isValidExecutorParametersData(ExecutorParametersFieldData data) {
        return isNotNull(data) && areNotNull(data.handler, data.parameters, data.validator);
    }

    static <T> boolean isValidInvokerParameterizedData(InvokerParameterizedParametersFieldData<T> data) {
        return isNotNull(data) && areNotNull(data.handler, data.parameters, data.validator);
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


    static <T> String isInvalidCastDataMessage(BasicCastData<T> data) {
        final var baseName = "Basic Cast Data";
        var message = Formatter.isNullMessageWithName(data, baseName);
        if (isBlank(message)) {
            message += (
                Formatter.isNullMessageWithName(data.caster, baseName + "Caster") +
                Formatter.isNullMessageWithName(data.defaultValue, baseName + "Default value")
            );
        }

        return isNotBlank(message) ? "isInvalidCastDataMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }

    static <T> String isInvalidVoidCastDataMessage(BasicCastData<T> data) {
        final var baseName = "Basic Cast Data(Void)";
        var message = Formatter.isNullMessageWithName(data, baseName);
        if (isBlank(message)) {
            message += Formatter.isNullMessageWithName(data.caster, baseName + "Caster");
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


    static <T, U, V> String isInvalidInvokerDefaultsMessage(BaseInvokerDefaultsData<T, U, V> data) {
        final var baseName = "Invoker Defaults Data";
        var message = Formatter.isNullMessageWithName(data, baseName);
        if (isBlank(message)) {
            final var castMessage = Objects.equals(CastDataConstants.VOID, data.castData) ? isInvalidVoidCastDataMessage(data.castData) : isInvalidCastDataMessage(data.castData);
            message += (
                Formatter.isNullMessageWithName(data.constructor, baseName + " Constructor") +
                castMessage +
                Formatter.isNullMessageWithName(data.guard, baseName + " Guard")
            );
        }

        return isNotBlank(message) ? "isInvalidInvokerDefaultsMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }


    static <T, U, V> boolean isValidExecutorResultFunctionsData(ExecutorResultFunctionsData<T, U, V> data) {
        return isNotNull(data) && areNotNull(data.castHandler, data.messageHandler);
    }


}
