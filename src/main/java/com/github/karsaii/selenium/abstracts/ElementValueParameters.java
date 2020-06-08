package com.github.karsaii.selenium.abstracts;

import com.github.karsaii.core.extensions.interfaces.functional.TriFunction;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.selenium.records.element.is.ElementFormatData;
import com.github.karsaii.selenium.records.lazy.LazyElement;

import java.util.Objects;
import java.util.function.Function;

public abstract class ElementValueParameters<ParameterType, ReturnType> extends AbstractElementFunctionParameters<ParameterType, ReturnType> {
    public final Function<LazyElement, DriverFunction<ReturnType>> function;

    public ElementValueParameters(
        TriFunction<DriverFunction<ParameterType>, Function<Data<ParameterType>, Data<ReturnType>>, Data<ReturnType>, DriverFunction<ReturnType>> handler,
        ElementFormatData<ReturnType> formatData,
        Function<LazyElement, DriverFunction<ReturnType>> function
    ) {
        super(handler, formatData);
        this.function = function;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final var that = (ElementValueParameters<?, ?>) o;
        return Objects.equals(function, that.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), function);
    }
}
