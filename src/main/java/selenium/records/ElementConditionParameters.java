package selenium.records;

import core.extensions.interfaces.functional.TriFunction;
import selenium.namespaces.extensions.boilers.DriverFunction;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ElementConditionParameters<T>  extends ElementValueParameters<T> {
    public final UnaryOperator<Boolean> inverter;

    public ElementConditionParameters(Function<LazyElement, DriverFunction<T>> function, TriFunction<String, String, T, String> formatter, UnaryOperator<Boolean> inverter, String conditionName, String descriptor) {
        super(function, formatter, conditionName, descriptor);
        this.inverter = inverter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        var that = (ElementConditionParameters<?>) o;
        return Objects.equals(inverter, that.inverter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), inverter);
    }
}
