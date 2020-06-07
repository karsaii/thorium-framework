package com.github.karsaii.selenium.records.element.is;

import com.github.karsaii.core.extensions.interfaces.functional.TriFunction;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.selenium.records.lazy.LazyElement;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ElementConditionParameters<ReturnType> extends ElementBooleanValueParameters<ReturnType> {
    public final UnaryOperator<Boolean> inverter;

    public ElementConditionParameters(
        TriFunction<DriverFunction<Boolean>, Function<Data<Boolean>, Data<ReturnType>>, Data<ReturnType>, DriverFunction<ReturnType>> handler,
        ElementFormatData<ReturnType> formatData,
        Function<LazyElement, DriverFunction<ReturnType>> function,
        UnaryOperator<Boolean> inverter
    ) {
        super(handler, formatData, function);
        this.inverter = inverter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass()) || !super.equals(o)) return false;
        final var that = (ElementConditionParameters<?>) o;
        return Objects.equals(inverter, that.inverter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), inverter);
    }
}
