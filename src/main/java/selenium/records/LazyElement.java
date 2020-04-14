package selenium.records;

import selenium.namespaces.extensions.boilers.DriverFunction;
import core.extensions.namespaces.CoreUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.abstracts.AbstractLazyElement;
import selenium.constants.SeleniumCoreConstants;
import selenium.enums.ManyGetter;
import selenium.enums.SingleGetter;
import selenium.namespaces.Driver;
import selenium.records.lazy.LazyIndexedElementParameters;
import selenium.records.lazy.LazyLocator;
import validators.ElementParameters;
import validators.Invalids;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.Map.entry;
import static selenium.namespaces.SeleniumUtilities.getEntry;
import static selenium.namespaces.SeleniumUtilities.getEntryIndexed;

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
            CoreUtilities.getIncrementalUUID(SeleniumCoreConstants.ATOMIC_COUNT),
            Collections.synchronizedMap(
                new LinkedHashMap<>(
                    Map.ofEntries(getEntry(LazyIndexedElementParameters::new, locator, getter.getName(), false))
                )
            ),
            ElementParameters::isValidLazyIndexedElement
        );
    }

    public LazyElement(By locator) {
        this(
                CoreUtilities.getIncrementalUUID(SeleniumCoreConstants.ATOMIC_COUNT),
            Collections.synchronizedMap(
                new LinkedHashMap<>(
                    Map.ofEntries(getEntry(LazyIndexedElementParameters::new, locator, SingleGetter.DEFAULT.getName(), false))
                )
            ),
            ElementParameters::isValidLazyIndexedElement
        );
    }

    public LazyElement(IndexedData indexData, By locator, ManyGetter getter) {
        this(
            CoreUtilities.getIncrementalUUID(SeleniumCoreConstants.ATOMIC_COUNT),
            Collections.synchronizedMap(
                new LinkedHashMap<>(
                    Map.ofEntries(getEntryIndexed(LazyIndexedElementParameters::new, indexData, locator, getter.getName()))
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
