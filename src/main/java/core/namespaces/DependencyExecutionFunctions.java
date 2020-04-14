package core.namespaces;

import core.records.Data;
import data.constants.Strings;
import core.constants.CoreConstants;

import java.util.function.Function;

import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static core.namespaces.DataFactoryFunctions.replaceMessage;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DependencyExecutionFunctions {
    static <T> Data<T> ifDependencyAnyCore(String nameof, Data<T> data) {
        final var name = isNotBlank(nameof) ? nameof : "ifDriverAnyCore";
        return isNotNull(data) ? (
            DataFactoryFunctions.getWithNameAndMessage(data.object, data.status, DataFunctions.getNameIfAbsent(data, name), data.message.message, data.exception)
        ) : DataFactoryFunctions.getWithNameAndMessage(null, false, name, "Data " + Strings.WAS_NULL, CoreConstants.EXCEPTION);
    }

    private static <T, U> Data<U> ifDependencyAnyWrappedCore(T dependency, String nameof, Function<T, Data<U>> function) {
        return isNotNull(dependency) ? (
            ifDependencyAnyCore(nameof, function.apply(dependency))
        ) : DataFactoryFunctions.getWithNameAndMessage(null, false, nameof, Strings.DRIVER_WAS_NULL, CoreConstants.EXCEPTION);
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
}
