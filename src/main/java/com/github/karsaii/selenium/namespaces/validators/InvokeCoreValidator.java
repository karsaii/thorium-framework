package com.github.karsaii.selenium.namespaces.validators;

import com.github.karsaii.core.abstracts.reflection.BaseInvokerDefaultsData;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.records.MethodData;
import com.github.karsaii.core.records.reflection.message.InvokeCommonMessageParametersData;
import com.github.karsaii.core.namespaces.validators.CoreFormatter;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;

import java.util.function.Function;

import static com.github.karsaii.core.namespaces.validators.CoreFormatter.isInvalidOrFalseMessage;
import static com.github.karsaii.selenium.namespaces.validators.ScriptExecutions.isInvalidInvokerDefaultsMessage;

public interface InvokeCoreValidator {
    private static <ParameterType, HandlerType, ReturnType> String isInvalidInvokeCoreParametersCommonMessage(
        Data<MethodData> data,
        BaseInvokerDefaultsData<ParameterType, HandlerType, ReturnType> defaults,
        Function<InvokeCommonMessageParametersData, Function<Exception, String>> messageHandler,
        HandlerType handler
    ) {
        return (
            isInvalidOrFalseMessage(data) +
            CoreFormatter.isNullMessageWithName(handler, "Handler") +
            CoreFormatter.isNullMessageWithName(messageHandler, "Message Handler") +
            isInvalidInvokerDefaultsMessage(defaults) +
            CoreFormatter.isFalseMessageWithName(defaults.guard.test(handler), "Guard tested handler")
        );
    }
    static <ParameterType, HandlerType, ReturnType> String isInvalidInvokeCoreParametersMessage(
        Data<MethodData> data,
        BaseInvokerDefaultsData<ParameterType, HandlerType, ReturnType> defaults,
        Function<InvokeCommonMessageParametersData, Function<Exception, String>> messageHandler,
        HandlerType handler,
        DriverFunction<ParameterType> getter
    ) {
        return isInvalidInvokeCoreParametersCommonMessage(data, defaults, messageHandler, handler) + CoreFormatter.isNullMessageWithName(getter, "Parameter Getter");
    }

    static <ParameterType, HandlerType, ReturnType> String isInvalidInvokeCoreParametersMessage(
        Data<MethodData> data,
        BaseInvokerDefaultsData<ParameterType, HandlerType, ReturnType> defaults,
        Function<InvokeCommonMessageParametersData, Function<Exception, String>> messageHandler,
        HandlerType handler,
        ParameterType parameter
    ) {
        return isInvalidInvokeCoreParametersCommonMessage(data, defaults, messageHandler, handler) + CoreFormatter.isNullMessageWithName(parameter, "Parameter");
    }
}
