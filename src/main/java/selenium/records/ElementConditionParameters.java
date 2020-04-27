package selenium.records;

import selenium.namespaces.extensions.boilers.DriverFunction;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ElementConditionParameters {
    public final Function<LazyElement, DriverFunction<Boolean>> condition;
    public final UnaryOperator<Boolean> inverter;
    public final String conditionName;
    public final String descriptor;

    public ElementConditionParameters(Function<LazyElement, DriverFunction<Boolean>> condition, UnaryOperator<Boolean> inverter, String conditionName, String descriptor) {
        this.condition = condition;
        this.inverter = inverter;
        this.conditionName = conditionName;
        this.descriptor = descriptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ElementConditionParameters) o;
        return (
            Objects.equals(condition, that.condition) &&
            Objects.equals(inverter, that.inverter) &&
            Objects.equals(conditionName, that.conditionName) &&
            Objects.equals(descriptor, that.descriptor)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, inverter, conditionName, descriptor);
    }
}
