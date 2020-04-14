package selenium.namespaces.validators;

import core.abstracts.reflection.BaseInvokerDefaultsData;
import core.records.Data;
import core.records.MethodData;
import core.records.reflection.message.InvokeCommonMessageParametersData;
import selenium.namespaces.extensions.boilers.DriverFunction;

import java.util.function.Function;

import static data.namespaces.Formatter.isFalseMessage;
import static data.namespaces.Formatter.isInvalidOrFalseMessage;
import static data.namespaces.Formatter.isNullMessage;
import static validators.ScriptExecutions.isInvalidInvokerDefaultsMessage;

public interface InvokeCoreValidator {
    private static <ParameterType, HandlerType, ReturnType> String isInvalidInvokeCoreParametersCommonMessage(
        Data<MethodData> data,
        BaseInvokerDefaultsData<ParameterType, HandlerType, ReturnType> defaults,
        Function<InvokeCommonMessageParametersData, Function<Exception, String>> messageHandler,
        HandlerType handler
    ) {
        return (
            isInvalidOrFalseMessage(data) +
            isNullMessage(handler, "Handler") +
            isNullMessage(messageHandler, "Message Handler") +
            isInvalidInvokerDefaultsMessage(defaults) +
            isFalseMessage(defaults.guard.test(handler), "Guard tested handler")
        );
    }
    static <ParameterType, HandlerType, ReturnType> String isInvalidInvokeCoreParametersMessage(
        Data<MethodData> data,
        BaseInvokerDefaultsData<ParameterType, HandlerType, ReturnType> defaults,
        Function<InvokeCommonMessageParametersData, Function<Exception, String>> messageHandler,
        HandlerType handler,
        DriverFunction<ParameterType> getter
    ) {
        return isInvalidInvokeCoreParametersCommonMessage(data, defaults, messageHandler, handler) + isNullMessage(getter, "Parameter Getter");
    }

    static <ParameterType, HandlerType, ReturnType> String isInvalidInvokeCoreParametersMessage(
        Data<MethodData> data,
        BaseInvokerDefaultsData<ParameterType, HandlerType, ReturnType> defaults,
        Function<InvokeCommonMessageParametersData, Function<Exception, String>> messageHandler,
        HandlerType handler,
        ParameterType parameter
    ) {
        return isInvalidInvokeCoreParametersCommonMessage(data, defaults, messageHandler, handler) + isNullMessage(parameter, "Parameter");
    }
}
