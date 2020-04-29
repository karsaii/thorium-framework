package selenium.namespaces;

import core.namespaces.validators.DataValidators;
import data.namespaces.Formatter;
import org.openqa.selenium.WebElement;
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
import static core.namespaces.validators.DataValidators.isInvalidOrFalse;
import static org.apache.commons.lang3.StringUtils.isBlank;

public interface ExecutionCore {
    static <T, U> Function<WebDriver, U> conditionalChain(Predicate<T> guard, DriverFunction<T> dependency, Function<T, U> positive, U negative) {
        return t -> {
            if (isNull(t)) {
                return negative;
            }

            final var dep = dependency.apply(t);
            if (isInvalidOrFalse(dep)) {
                return negative;
            }

            final var object = dep.object;
            return guard.test(object) ? positive.apply(object) : negative;
        };
    }

    static <T, U, V> Function<T, V> conditionalChain(Predicate<U> guard, Function<T, U> dependency, Function<U, V> positive, V negative) {
        return t -> {
            if (isNull(t)) {
                return negative;
            }

            final var dep = dependency.apply(t);
            return guard.test(dep) ? positive.apply(dep) : negative;
        };
    }

    static <T, U, V> Function<T, Data<V>> conditionalChain(Function<Data<U>, String> guard, Function<T, Data<U>> dependency, Function<Data<U>, Data<V>> positive, Data<V> negative) {
        return t -> {
            if (isNull(t)) {
                return replaceMessage(negative, "conditionalChain", "Base dependency" + Strings.WAS_NULL);
            }

            final var dep = dependency.apply(t);
            final var guardMessage = guard.apply(dep);
            return isBlank(guardMessage) ? positive.apply(dep) : prependMessage(negative, "conditionalChain", "Dependency parameter failed the guard" + Strings.COLON_SPACE + guardMessage);
        };
    }

    private static <T, U> DriverFunction<T> conditionalDataChain(Predicate<Data<U>> guard, Function<WebDriver, Data<U>> dependency, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return DriverFunctionFactory.getFunction(conditionalChain(guard, dependency, positive, replaceMessage(negative, "conditionalChain", "Dependency parameter failed the guard" + Strings.END_LINE)));
    }

    static <T, U> DriverFunction<T> conditionalChain(Predicate<Data<U>> guard, DriverFunction<U> dependency, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return conditionalDataChain(guard, dependency, positive, negative);
    }

    static <ParameterType, ReturnType> DriverFunction<ReturnType> validChain(DriverFunction<ParameterType> dependency, Function<Data<ParameterType>, Data<ReturnType>> positive, Data<ReturnType> negative) {
        return DriverFunctionFactory.getFunction(conditionalChain(Formatter::isInvalidOrFalseMessage, dependency, positive, negative));
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

    static <T, U> DriverFunction<U> ifDriver(String nameof, String errorMessage, DriverFunction<T> dependency, Function<Data<T>, Data<U>> positive, Data<U> negative) {
        return ifDriver(nameof, isBlank(errorMessage) && areNotNull(dependency, positive, negative), DriverFunctionFactory.getFunction(dependency.andThen(positive)), replaceMessage(negative, nameof, errorMessage));
    }

    static <T, U> DriverFunction<T> ifDriverGuardData(String nameof, Predicate<Data<U>> guard, Function<WebDriver, Data<U>> dependency, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return ifDriver(nameof, areNotNull(guard, dependency, positive, negative), conditionalDataChain(guard, dependency, positive, negative), negative);
    }

    static <T, U> DriverFunction<T> ifDriverGuardData(String nameof, Function<WebDriver, Data<U>> dependency, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return ifDriverGuardData(nameof, NullableFunctions::isNotNull, dependency, positive, negative);
    }

    static <T, U> DriverFunction<T> ifDriver(String nameof, boolean status, Function<WebDriver, Data<U>> dependency, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return ifDriver(nameof, status && areNotNull(dependency, positive, negative), ifDriverGuardData(nameof, dependency, positive, negative), negative);
    }

    static <T, U> DriverFunction<T> ifDriver(String nameof, Predicate<Data<U>> guard, DriverFunction<U> dependency, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return ifDriver(nameof, areNotNull(guard, dependency, positive, negative), ifDriverGuardData(nameof, guard, dependency, positive, negative), negative);
    }

    static <T, U> DriverFunction<U> ifDriverFunction(String nameof, Predicate<Data<?>> guard, DriverFunction<T> dependency, Function<Data<T>, DriverFunction<U>> positive, Data<U> negative) {
        return ifDriver(
            nameof,
            areNotNull(guard, dependency, positive, negative),
            DriverFunctionFactory.getFunction(driver -> {
                final var dep = dependency.apply(driver);
                return guard.test(dep) ? positive.apply(dep).apply(driver) : negative;
            }),
            negative
        );
    }

    static <T, U> Data<U> ifData(String nameof, Predicate<T> guard, T dependency, Function<T, Data<U>> positive, Data<U> negative) {
        return prependMessage((isNotNull(guard) && guard.test(dependency) ? positive.apply(dependency) : negative), nameof, "");
    }

    static <T, U> DriverFunction<T> ifDriver(String nameof, boolean status, Predicate<Data<U>> guard, DriverFunction<U> dependency, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return ifDriver(nameof, status && areNotNull(guard, dependency, positive), conditionalChain(guard, dependency, positive, negative), negative);
    }

    static <T, U> DriverFunction<T> ifDriver(String nameof, String errorMessage, Predicate<Data<U>> guard, DriverFunction<U> dependency, Function<Data<U>, Data<T>> positive, Data<T> negative) {
        return ifDriver(nameof, isBlank(errorMessage) && areNotNull(guard, dependency, positive), conditionalChain(guard, dependency, positive, negative), replaceMessage(negative, errorMessage));
    }

    static <T> DriverFunction<T> ifDriver(String nameof, String errorMessage, DriverFunction<Boolean> dependency, DriverFunction<T> positive, Data<T> negative) {
        return ifDriver(nameof, isBlank(errorMessage) && areNotNull(dependency, positive, negative), SeleniumExecutor.execute(dependency, positive), negative);
    }

    static DriverFunction<Boolean> ifDriver(String message, DriverFunction<Boolean> positive) {
        return isBlank(message) ? positive : DriverFunctionConstants.NULL_BOOLEAN;
    }
}
