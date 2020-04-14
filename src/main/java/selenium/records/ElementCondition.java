package selenium.records;

import selenium.namespaces.extensions.boilers.DriverFunction;

import java.util.Objects;
import java.util.function.Function;

public class ElementCondition {
    public final LazyElement data;
    public final Function<LazyElement, DriverFunction<Boolean>> condition;
    public final String descriptor;

    public ElementCondition(LazyElement data, Function<LazyElement, DriverFunction<Boolean>> condition, String descriptor) {
        this.data = data;
        this.condition = condition;
        this.descriptor = descriptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ElementCondition) o;
        return Objects.equals(data, that.data) && Objects.equals(condition, that.condition) && Objects.equals(descriptor, that.descriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, condition, descriptor);
    }
}
