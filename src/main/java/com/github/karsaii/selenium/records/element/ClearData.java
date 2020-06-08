package com.github.karsaii.selenium.records.element;

import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import org.openqa.selenium.By;
import com.github.karsaii.selenium.namespaces.element.Element;
import com.github.karsaii.selenium.enums.SingleGetter;
import com.github.karsaii.selenium.records.lazy.LazyElement;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ClearData {
    public final Function<LazyElement, DriverFunction<Boolean>> clearLazy;
    public final BiFunction<By, SingleGetter, DriverFunction<Boolean>> clearByWithGetter;
    public final Function<By, DriverFunction<Boolean>> clearBy;

    public ClearData(
        Function<LazyElement, DriverFunction<Boolean>> clearLazy,
        BiFunction<By, SingleGetter, DriverFunction<Boolean>> clearByWithGetter,
        Function<By, DriverFunction<Boolean>> clearBy
    ) {
        this.clearLazy = clearLazy;
        this.clearByWithGetter = clearByWithGetter;
        this.clearBy = clearBy;
    }

    public ClearData() {
        this.clearLazy = Element::clear;
        this.clearByWithGetter = Element::clear;
        this.clearBy = Element::clear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var clearData = (ClearData) o;
        return Objects.equals(clearLazy, clearData.clearLazy) && Objects.equals(clearByWithGetter, clearData.clearByWithGetter) && Objects.equals(clearBy, clearData.clearBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clearLazy, clearByWithGetter, clearBy);
    }
}
