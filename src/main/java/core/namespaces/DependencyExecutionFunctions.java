package core.namespaces;

import core.extensions.interfaces.functional.boilers.DataSupplier;
import core.records.Data;
import core.records.executor.ExecutionResultData;
import data.constants.Strings;
import core.constants.CoreConstants;
import data.namespaces.Formatter;

import java.util.function.Function;
import java.util.function.Supplier;

import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static core.namespaces.DataFactoryFunctions.prependMessage;
import static core.namespaces.DataFactoryFunctions.replaceMessage;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DependencyExecutionFunctions {
    static <T, U, V> DataSupplier<V> conditionalChain(Function<Data<U>, String> guard, Function<Void, Data<U>> dependency, Function<Data<U>, Data<V>> positive, Data<V> negative) {
        return (nothing) -> {
            final var dep = dependency.apply(null);
            final var guardMessage = guard.apply(dep);
            return isBlank(guardMessage) ? positive.apply(dep) : prependMessage(negative, "conditionalChain", "Dependency parameter failed the guard" + Strings.COLON_SPACE + guardMessage);
        };
    }

    static <ParameterType, ReturnType> DataSupplier<ReturnType> validChain(DataSupplier<ParameterType> dependency, Function<Data<ParameterType>, Data<ReturnType>> positive, Data<ReturnType> negative) {
        return conditionalChain(Formatter::isInvalidOrFalseMessage, dependency, positive, negative);
    }

    static <T> Data<T> ifDependencyAnyCore(String nameof, Data<T> data) {
        final var name = isNotBlank(nameof) ? nameof : "ifDependencyAnyCore";
        return isNotNull(data) ? (
            DataFactoryFunctions.getWithNameAndMessage(data.object, data.status, DataFunctions.getNameIfAbsent(data, name), data.message.message, data.exception)
        ) : DataFactoryFunctions.getWithNameAndMessage(null, false, name, "Data " + Strings.WAS_NULL, CoreConstants.EXCEPTION);
    }

    private static <T, U> Data<U> ifDependencyAnyWrappedCore(T dependency, String nameof, Function<T, Data<U>> function) {
        return ifDependencyAnyCore(nameof, function.apply(dependency));
    }

    private static <T, U> Function<T, Data<U>> ifDependencyAnyWrappedCore(String nameof, Function<T, Data<U>> function) {
        return dependency -> ifDependencyAnyWrappedCore(dependency, nameof, function);
    }

    static <T, U> Function<T, Data<U>> ifDependency(String nameof, boolean condition, Function<T, Data<U>> positive, Data<U> negative) {
        final var lNameof = isBlank(nameof) ? "ifDependency" : nameof;
        return condition && isNotNull(positive) ? (
            ifDependencyAnyWrappedCore(lNameof, positive)
        ) : dependency -> ifDependencyAnyCore(lNameof, negative);
    }

    static <T, U> Function<T, Data<U>> ifDependency(String nameof, String errorMessage, Function<T, Data<U>> positive, Data<U> negative) {
        return ifDependency(nameof, isBlank(errorMessage), positive, replaceMessage(negative, nameof, errorMessage));
    }

    private static <T> DataSupplier<T> ifDependencyAnyWrappedCore(String nameof, DataSupplier<T> function) {
        return (nothing) -> ifDependencyAnyCore(nameof, function.apply());
    }

    static <T> DataSupplier<T> ifDependency(String nameof, boolean condition, DataSupplier<T> positive, Data<T> negative) {
        final var lNameof = isBlank(nameof) ? "ifDependency" : nameof;
        return condition && isNotNull(positive) ? (
            ifDependencyAnyWrappedCore(lNameof, positive)
        ) : nothing -> ifDependencyAnyCore(lNameof, negative);
    }

    static <T> DataSupplier<T> ifDependency(String nameof, String errorMessage, DataSupplier<T> positive, Data<T> negative) {
        return ifDependency(nameof, isBlank(errorMessage), positive, replaceMessage(negative, nameof, errorMessage));
    }
}
