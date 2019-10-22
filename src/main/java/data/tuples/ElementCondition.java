package data.tuples;

import data.DriverFunction;
import data.LazyElement;

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
}
