package com.github.karsaii.selenium.abstracts;

import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.selenium.namespaces.extensions.boilers.IElement;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class AbstractLazyElement<T> implements IElement {
    public final String name;
    public final Map<String, T> parameters;
    public final Predicate<T> validator;

    public AbstractLazyElement(String name, Map<String, T> parameters, Predicate<T> validator) {
        this.name = name;
        this.parameters = parameters;
        this.validator = validator;
    }

    @Override
    public abstract DriverFunction<WebElement> get();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (AbstractLazyElement<?>) o;
        return Objects.equals(name, that.name) && Objects.equals(parameters, that.parameters) && Objects.equals(validator, that.validator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parameters, validator);
    }
}
