package data;

import data.constants.Defaults;

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
        this(Defaults.EMPTY_STRING, Defaults.EMPTY_STRING);
    }
    @Override
    public String toString() {
        return "LazyLocator - By." + strategy + ": " + locator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LazyLocator lazyLocator = (LazyLocator) o;
        return (
            Objects.equals(locator, lazyLocator.locator) &&
            Objects.equals(strategy, lazyLocator.strategy)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(locator, strategy);
    }

}
