package com.github.karsaii.selenium.records.scripter;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class ScriptParametersData<T> {
    public final T parameters;
    public final Predicate<T> validator;
    public final Function<T, Object[]> converter;

    public ScriptParametersData(T parameters, Predicate<T> validator, Function<T, Object[]> converter) {
        this.parameters = parameters;
        this.validator = validator;
        this.converter = converter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ScriptParametersData<?>) o;
        return Objects.equals(parameters, that.parameters) &&
                Objects.equals(validator, that.validator) &&
                Objects.equals(converter, that.converter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters, validator, converter);
    }
}
