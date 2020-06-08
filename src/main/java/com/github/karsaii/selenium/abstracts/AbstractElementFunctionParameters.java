package com.github.karsaii.selenium.abstracts;

import com.github.karsaii.core.extensions.interfaces.functional.TriFunction;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.selenium.records.element.is.ElementFormatData;

import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractElementFunctionParameters<ParameterType, ReturnType> {
    public final TriFunction<DriverFunction<ParameterType>, Function<Data<ParameterType>, Data<ReturnType>>, Data<ReturnType>, DriverFunction<ReturnType>> handler;
    public final ElementFormatData<ReturnType> formatData;

    public AbstractElementFunctionParameters(
        TriFunction<DriverFunction<ParameterType>, Function<Data<ParameterType>, Data<ReturnType>>, Data<ReturnType>, DriverFunction<ReturnType>> handler,
        ElementFormatData<ReturnType> formatData
    ) {
        this.handler = handler;
        this.formatData = formatData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (AbstractElementFunctionParameters<?, ?>) o;
        return Objects.equals(handler, that.handler) && Objects.equals(formatData, that.formatData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(handler, formatData);
    }
}
