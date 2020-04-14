package selenium.abstracts;

import selenium.namespaces.extensions.boilers.DriverFunction;
import core.records.HandleResultData;
import core.records.caster.WrappedCastData;
import selenium.records.scripter.ExecutorWrappedResultFunctionsData;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public abstract class BaseFunctionalData<GetterType, HandlerType, ParameterType, MessageParameterType, ConstructorType, ConstructorResultType, ReturnType> {
    public final DriverFunction<GetterType> getter;
    public final BiFunction<ConstructorType, HandlerType, ConstructorResultType> constructor;
    public final Predicate<HandlerType> guard;
    public final WrappedCastData<ReturnType> castData;
    public final ExecutorWrappedResultFunctionsData<HandleResultData<ParameterType, ReturnType>, MessageParameterType, ReturnType> resultHandlers;

    public BaseFunctionalData(
        DriverFunction<GetterType> getter,
        BiFunction<ConstructorType, HandlerType, ConstructorResultType> constructor,
        Predicate<HandlerType> guard,
        WrappedCastData<ReturnType> castData,
        ExecutorWrappedResultFunctionsData<HandleResultData<ParameterType, ReturnType>, MessageParameterType, ReturnType> resultHandlers
    ) {
        this.getter = getter;
        this.constructor = constructor;
        this.guard = guard;
        this.castData = castData;
        this.resultHandlers = resultHandlers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (BaseFunctionalData<?, ?, ?, ?, ?, ?, ?>) o;
        return Objects.equals(getter, that.getter) &&
            Objects.equals(constructor, that.constructor) &&
            Objects.equals(guard, that.guard) &&
            Objects.equals(castData, that.castData) &&
            Objects.equals(resultHandlers, that.resultHandlers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getter, constructor, guard, castData, resultHandlers);
    }
}
