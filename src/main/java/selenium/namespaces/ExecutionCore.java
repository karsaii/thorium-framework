package selenium.namespaces;

import core.namespaces.executor.ExecutionResultDataFactory;
import core.records.executor.ExecutionResultData;
import data.namespaces.Formatter;
import selenium.namespaces.extensions.boilers.DriverFunction;
import core.extensions.namespaces.NullableFunctions;
import core.namespaces.DataFactoryFunctions;
import core.namespaces.DependencyExecutionFunctions;
import core.records.Data;
import data.constants.Strings;
import org.openqa.selenium.WebDriver;
import selenium.constants.DriverFunctionConstants;
import core.constants.CoreConstants;

import java.util.function.Function;
import java.util.function.Predicate;

import static core.extensions.namespaces.CoreUtilities.areNotNull;
import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static core.extensions.namespaces.NullableFunctions.isNull;
import static core.namespaces.DataFactoryFunctions.prependMessage;
import static core.namespaces.DataFactoryFunctions.replaceMessage;
import static org.apache.commons.lang3.StringUtils.isBlank;

public interface ExecutionCore {
    static <T, U, V> Function<T, V> conditionalChain(Predicate<U> guard, Function<T, U> function, Function<U, V> positive, V negative) {
        return t -> {
            if (isNull(t)) {
                return negative;
            }

            final var dep = function.apply(t);
            return guard.test(dep) ? positive.apply(dep) : negative;
        };
    }

    static <T, U, V> Function<T, Data<V>> conditionalChain(Function<Data<U>, String> guard, Function<T, Data<U>> function, Function<Data<U>, Data<V>> positive, Data<V> negative) {
        return t -> {
            if (isNull(t)) {
                return replaceMessage(negative, "conditionalChain", "Base dependency" + Strings.WAS_NULL);
            }

            final var dep = function.apply(t);
            final var guardMessage = guard.apply(dep);
            return isBlank(guardMessage) ? positive.apply(dep) : prependMessage(negative, "conditionalChain", "Dependency parameter failed the guard" + Strings.COLON_SPACE + guardMessage);
        };
    }

    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, Data<ReturnType>> validChain(Function<DependencyType, Data<ParameterType>> function, Function<Data<ParameterType>, Data<ReturnType>> positive, Data<ReturnType> negative) {
        return conditionalChain(Formatter::isInvalidOrFalseMessage, function, positive, negative);
    }

    private static <T, U> DriverFunction<T> conditionalDataChain(Predicate<Data<U>> guard, Function<WebDriver, Data<U>> function, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return DriverFunctionFactory.getFunction(conditionalChain(guard, function, positive, replaceMessage(negative, "conditionalChain", "Dependency parameter failed the guard" + Strings.END_LINE)));
    }

    static <ParameterType, ReturnType> DriverFunction<ReturnType> validChain(DriverFunction<ParameterType> function, Function<Data<ParameterType>, Data<ReturnType>> positive, Data<ReturnType> negative) {
        return DriverFunctionFactory.getFunction(validChain(function.get(), positive, negative));
    }

    static <ParameterType, ReturnType> DriverFunction<ReturnType> nonNullChain(DriverFunction<ParameterType> function, Function<Data<ParameterType>, Data<ReturnType>> positive, Data<ReturnType> negative) {
        return DriverFunctionFactory.getFunction(conditionalChain(Formatter::isTrueMessage, function, positive, negative));
    }

    private static <T> Data<T> ifDriverAnyWrappedCore(WebDriver driver, String nameof, DriverFunction<T> function) {
        return isNotNull(driver) ? (
            DependencyExecutionFunctions.ifDependencyAnyCore(nameof, function.apply(driver))
        ) : DataFactoryFunctions.getWithNameAndMessage(null, false, nameof, Strings.DRIVER_WAS_NULL, CoreConstants.EXCEPTION);
    }

    private static <T> Function<WebDriver, Data<T>> ifDriverAnyWrappedCore(String nameof, DriverFunction<T> function) {
        return driver -> ifDriverAnyWrappedCore(driver, nameof, function);
    }

    static <T> DriverFunction<T> ifDriver(String nameof, boolean condition, DriverFunction<T> positive, Data<T> negative) {
        final var lNameof = isBlank(nameof) ? "ifDriver" : nameof;
        return condition && isNotNull(positive) ? (
            DriverFunctionFactory.getFunction(ifDriverAnyWrappedCore(lNameof, positive))
        ) : DriverFunctionFactory.get(DependencyExecutionFunctions.ifDependencyAnyCore(lNameof, negative));
    }

    static <T> DriverFunction<T> ifDriver(String nameof, DriverFunction<T> positive, Data<T> negative) {
        return ifDriver(nameof, areNotNull(positive, negative), positive, negative);
    }

    static <T> DriverFunction<T> ifDriver(String nameof, boolean condition, DriverFunction<T> positive, DriverFunction<T> negative) {
        final var lNameof = isBlank(nameof) ? "ifDriver" : nameof;
        final var function = condition && isNotNull(positive) ? positive : negative;
        return DriverFunctionFactory.getFunction(ifDriverAnyWrappedCore(lNameof, function));
    }

    static <T> DriverFunction<T> ifDriver(String nameof, String errorMessage, DriverFunction<T> positive, Data<T> negative) {
        return ifDriver(nameof, isBlank(errorMessage), positive, replaceMessage(negative, nameof, errorMessage));
    }

    static <T, U> DriverFunction<U> ifDriver(String nameof, String errorMessage, DriverFunction<T> function, Function<Data<T>, Data<U>> positive, Data<U> negative) {
        return ifDriver(nameof, isBlank(errorMessage) && areNotNull(function, positive, negative), DriverFunctionFactory.getFunction(function.andThen(positive)), replaceMessage(negative, nameof, errorMessage));
    }

    static <T, U> DriverFunction<T> ifDriverGuardData(String nameof, Predicate<Data<U>> guard, Function<WebDriver, Data<U>> function, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return ifDriver(nameof, areNotNull(guard, function, positive, negative), conditionalDataChain(guard, function, positive, negative), negative);
    }

    static <T, U> DriverFunction<T> ifDriverGuardData(String nameof, Function<WebDriver, Data<U>> function, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return ifDriverGuardData(nameof, NullableFunctions::isNotNull, function, positive, negative);
    }

    static <T, U> DriverFunction<T> ifDriver(String nameof, boolean status, Function<WebDriver, Data<U>> function, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return ifDriver(nameof, status && areNotNull(function, positive, negative), ifDriverGuardData(nameof, function, positive, negative), negative);
    }

    static <T, U> DriverFunction<T> ifDriver(String nameof, Predicate<Data<U>> guard, DriverFunction<U> function, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return ifDriver(nameof, areNotNull(guard, function, positive, negative), ifDriverGuardData(nameof, guard, function, positive, negative), negative);
    }

    static <T, U> DriverFunction<U> ifDriverFunction(String nameof, Predicate<Data<?>> guard, DriverFunction<T> function, Function<Data<T>, DriverFunction<U>> positive, Data<U> negative) {
        return ifDriver(
            nameof,
            areNotNull(guard, function, positive, negative),
            DriverFunctionFactory.getFunction(driver -> {
                final var dep = function.apply(driver);
                return guard.test(dep) ? positive.apply(dep).apply(driver) : negative;
            }),
            negative
        );
    }

    static <T, U> Data<U> ifData(String nameof, Predicate<T> guard, T object, Function<T, Data<U>> positive, Data<U> negative) {
        return prependMessage((isNotNull(guard) && guard.test(object) ? positive.apply(object) : negative), nameof, "");
    }

    static <T, U> DriverFunction<T> ifDriver(String nameof, boolean status, Predicate<Data<U>> guard, DriverFunction<U> function, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return ifDriver(nameof, status && areNotNull(guard, function, positive), conditionalDataChain(guard, function, positive, negative), negative);
    }

    static <T, U> DriverFunction<T> ifDriver(String nameof, String errorMessage, Predicate<Data<U>> guard, DriverFunction<U> function, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return ifDriver(nameof, isBlank(errorMessage) && areNotNull(guard, function, positive), conditionalDataChain(guard, function, positive, negative), replaceMessage(negative, errorMessage));
    }

    static <T> DriverFunction<T> ifDriver(String nameof, String errorMessage, DriverFunction<Boolean> function, DriverFunction<T> positive, Data<T> negative) {
        return ifDriver(nameof, isBlank(errorMessage) && areNotNull(function, positive, negative), SeleniumExecutor.execute(function, positive), negative);
    }

    static DriverFunction<Boolean> ifDriver(String message, DriverFunction<Boolean> positive) {
        return isBlank(message) ? positive : DriverFunctionConstants.NULL_BOOLEAN;
    }
}
