package selenium.records;

import core.extensions.interfaces.functional.TriFunction;
import selenium.abstracts.AbstractElementFunctionParameters;
import selenium.namespaces.extensions.boilers.DriverFunction;

import java.util.Objects;
import java.util.function.Function;

public class ElementValueParameters<T> extends AbstractElementFunctionParameters<T> {
    public final Function<LazyElement, DriverFunction<T>> function;

    public ElementValueParameters(Function<LazyElement, DriverFunction<T>> function, TriFunction<String, String, T, String> formatter, String conditionName, String descriptor) {
        super(formatter, conditionName, descriptor);
        this.function = function;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        var that = (ElementValueParameters<?>) o;
        return Objects.equals(function, that.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), function);
    }
}
