package com.github.karsaii.selenium.records.lazy;

import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import org.openqa.selenium.WebElement;
import com.github.karsaii.selenium.abstracts.AbstractLazyElement;
import com.github.karsaii.selenium.namespaces.Driver;
import com.github.karsaii.selenium.records.lazy.filtered.LazyFilteredElementParameters;

import java.util.Map;
import java.util.function.Predicate;


public class LazyElement extends AbstractLazyElement<LazyFilteredElementParameters> {
    public LazyElement(String name, Map<String, LazyFilteredElementParameters> parameters, Predicate<LazyFilteredElementParameters> validator) {
        super(name, parameters, validator);
    }

    @Override
    public DriverFunction<WebElement> get() {
        return Driver.getLazyElement(this);
    }
}
