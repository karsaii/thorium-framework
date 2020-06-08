package com.github.karsaii.selenium.records.element.is;

import com.github.karsaii.core.extensions.interfaces.functional.TriFunction;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.selenium.abstracts.AbstractElementFunctionParameters;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.selenium.records.lazy.LazyElement;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ElementParameterizedValueParameters<ReturnType> extends AbstractElementFunctionParameters<String, ReturnType> {
    public final BiFunction<LazyElement, String, DriverFunction<ReturnType>> function;

    public ElementParameterizedValueParameters(
        TriFunction<DriverFunction<String>, Function<Data<String>, Data<ReturnType>>, Data<ReturnType>, DriverFunction<ReturnType>> handler,
        ElementFormatData<ReturnType> formatData,
        BiFunction<LazyElement, String, DriverFunction<ReturnType>> function
    ) {
        super(handler, formatData);
        this.function = function;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass()) || !super.equals(o)) return false;
        final var that = (ElementParameterizedValueParameters<?>) o;
        return Objects.equals(function, that.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), function);
    }
}
