package data;

import data.constants.Defaults;
import data.enums.ManyGetter;
import data.enums.SingleGetter;
import data.tuples.IndexedData;
import data.lazy.tuples.LazyIndexedElementParameters;
import drivers.Driver;
import utilities.utils;
import validators.ElementParameters;
import validators.Invalids;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.function.Predicate;

import static java.util.Map.entry;

public class LazyElement extends AbstractLazyElement<LazyIndexedElementParameters> {
    public LazyElement(String name, Map<String, LazyIndexedElementParameters> parameters, Predicate<LazyIndexedElementParameters> validator) {
        super(name, parameters, validator);
    }

    public LazyElement(String name, Map<String, LazyIndexedElementParameters> parameters) {
        this(name, parameters, Invalids::defaultFalseValidator);
    }

    public LazyElement(String name) {
        this(name, new HashMap<>(), Invalids::defaultFalseValidator);
    }

    public LazyElement(String name, boolean isIndexed, int index, LazyLocator locator, String getter) {
        this(
            name,
            Collections.synchronizedMap(
                new LinkedHashMap<>(
                    Map.ofEntries(entry(locator.strategy, new LazyIndexedElementParameters(isIndexed, index, locator, getter)))
                )
            ),
            ElementParameters::isValidLazyIndexedElement
        );
    }

    public LazyElement(String name, int index, LazyLocator locator, String getter) {
        this(
            name,
            Collections.synchronizedMap(
                new LinkedHashMap<>(
                    Map.ofEntries(entry(locator.strategy, new LazyIndexedElementParameters(index, locator, getter)))
                )
            ),
            ElementParameters::isValidLazyIndexedElement
        );
    }

    public LazyElement(String name, boolean isIndexed, LazyLocator locator, String getter) {
        this(
            name,
            Collections.synchronizedMap(
                new LinkedHashMap<>(
                    Map.ofEntries(entry(locator.strategy, new LazyIndexedElementParameters(isIndexed, locator, getter)))
                )
            ),
            ElementParameters::isValidLazyIndexedElement
        );
    }

    public LazyElement(String name, int index, LazyLocator locator) {
        this(
            name,
            Collections.synchronizedMap(
                new LinkedHashMap<>(
                    Map.ofEntries(entry(locator.strategy, new LazyIndexedElementParameters(index, locator)))
                )
            ),
            ElementParameters::isValidLazyIndexedElement
        );
    }

    public LazyElement(String name, LazyLocator locator) {
        this(
            name,
            Collections.synchronizedMap(
                new LinkedHashMap<>(
                    Map.ofEntries(entry(locator.strategy, new LazyIndexedElementParameters(false, 0, locator)))
                )
            ),
            ElementParameters::isValidLazyIndexedElement
        );
    }

    public LazyElement(By locator, SingleGetter getter) {
        this(
            utils.getIncrementalUUID(Defaults.atomicCount),
            Collections.synchronizedMap(
                new LinkedHashMap<>(
                    Map.ofEntries(utils.getEntry(LazyIndexedElementParameters::new, locator, getter.getName(), false))
                )
            ),
            ElementParameters::isValidLazyIndexedElement
        );
    }

    public LazyElement(By locator) {
        this(
            utils.getIncrementalUUID(Defaults.atomicCount),
            Collections.synchronizedMap(
                new LinkedHashMap<>(
                    Map.ofEntries(utils.getEntry(LazyIndexedElementParameters::new, locator, SingleGetter.DEFAULT.getName(), false))
                )
            ),
            ElementParameters::isValidLazyIndexedElement
        );
    }

    public LazyElement(IndexedData indexData, By locator, ManyGetter getter) {
        this(
            utils.getIncrementalUUID(Defaults.atomicCount),
            Collections.synchronizedMap(
                new LinkedHashMap<>(
                    Map.ofEntries(utils.getEntryIndexed(LazyIndexedElementParameters::new, indexData, locator, getter.getName()))
                )
            ),
            ElementParameters::isValidLazyIndexedElement
        );
    }

    @Override
    public DriverFunction<WebElement> get() {
        return Driver.getLazyElement(this);
    }
}
