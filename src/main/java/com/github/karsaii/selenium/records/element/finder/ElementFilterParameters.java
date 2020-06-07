package com.github.karsaii.selenium.records.element.finder;

import com.github.karsaii.selenium.enums.ManyGetter;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.selenium.namespaces.extensions.boilers.LazyLocatorList;
import com.github.karsaii.selenium.namespaces.extensions.boilers.WebElementList;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class ElementFilterParameters {
    public final LazyLocatorList locators;
    public final Map<ManyGetter, Function<LazyLocatorList, DriverFunction<WebElementList>>> getterMap;
    public final ManyGetter getter;

    public ElementFilterParameters(LazyLocatorList locators, Map<ManyGetter, Function<LazyLocatorList, DriverFunction<WebElementList>>> getterMap, ManyGetter getter) {
        this.locators = locators;
        this.getterMap = getterMap;
        this.getter = getter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ElementFilterParameters) o;
        return Objects.equals(locators, that.locators) && Objects.equals(getterMap, that.getterMap) && Objects.equals(getter, that.getter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locators, getterMap, getter);
    }
}
