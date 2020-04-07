package core.reflection;

import core.extensions.interfaces.functional.boilers.MethodFunction;
import core.extensions.namespaces.NullableFunctions;
import core.namespaces.InvokeFunctions;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class InvokerRegularData<ParameterType> implements MethodFunction<Function<ParameterType, Object>> {
    public final BiFunction<Method, ParameterType, Object> handler;

    public InvokerRegularData(BiFunction<Method, ParameterType, Object> handler) {
        this.handler = handler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (InvokerRegularData<?>) o;
        return Objects.equals(handler, that.handler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(handler);
    }

    @Override
    public Function<ParameterType, Object> apply(Method method) {
        return NullableFunctions.isNotNull(method) ? base -> handler.apply(method, base) : InvokeFunctions.regularDefault();
    }
}
