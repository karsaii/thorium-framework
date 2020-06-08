package com.github.karsaii.selenium.records.lazy;

import com.github.karsaii.core.constants.validators.CoreFormatterConstants;

import java.util.Objects;

public class LazyLocator {
    public final String locator;
    public final String strategy;

    public LazyLocator(String locator, String strategy) {
        this.locator = locator;
        this.strategy = strategy;
    }

    public LazyLocator(String locator) {
        this(locator, "cssSelector");
    }

    public LazyLocator() {
        this(CoreFormatterConstants.EMPTY, "cssSelector");
    }

    @Override
    public String toString() {
        return "LazyLocator - By." + strategy + ": " + locator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (LazyLocator) o;
        return Objects.equals(locator, that.locator) && Objects.equals(strategy, that.strategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locator, strategy);
    }
}
