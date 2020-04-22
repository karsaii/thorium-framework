package selenium.records.lazy;

import selenium.constants.LazyElementConstants;
import selenium.namespaces.extensions.boilers.LazyLocatorList;

import java.util.Objects;

public class LazyElementParameters {
    public final LazyLocatorList lazyLocators;
    public final String getter;
    public double probability;

    public LazyElementParameters(double probability, LazyLocatorList lazyLocators, String getter) {
        this.probability = probability;
        this.lazyLocators = lazyLocators;
        this.getter = getter;
    }

    public LazyElementParameters(double probability, LazyLocatorList lazyLocators) {
        this(probability, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyElementParameters(double probability, LazyLocator lazyLocator, String getter) {
        this(probability, new LazyLocatorList(lazyLocator), getter);
    }

    public LazyElementParameters(double probability, LazyLocator lazyLocator) {
        this(probability, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyElementParameters(LazyLocatorList lazyLocators) {
        this(100.0, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyElementParameters(LazyLocator lazyLocator, String getter) {
        this(100.0, new LazyLocatorList(lazyLocator), getter);
    }

    public LazyElementParameters(LazyLocator lazyLocator) {
        this(100.0, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (LazyElementParameters) o;
        return Double.compare(that.probability, probability) == 0 && Objects.equals(lazyLocators, that.lazyLocators) && Objects.equals(getter, that.getter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lazyLocators, getter, probability);
    }

    @Override
    public String toString() {
        return "LazyElementParameters{lazyLocators=" + lazyLocators + ", getter='" + getter + "\', probability=" + probability + '}';
    }
}
