package selenium.namespaces;

import core.extensions.namespaces.CoreUtilities;
import org.openqa.selenium.By;
import selenium.constants.SeleniumCoreConstants;
import selenium.enums.ManyGetter;
import selenium.enums.SingleGetter;
import selenium.records.FilterData;
import selenium.records.LazyElement;
import selenium.records.lazy.LazyIndexedElementParameters;
import selenium.records.lazy.LazyLocator;
import validators.ElementParameters;
import validators.Invalids;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.entry;
import static selenium.namespaces.SeleniumUtilities.getEntry;
import static selenium.namespaces.SeleniumUtilities.getEntryIndexed;

public interface LazyElementFactory {
    static <T> LazyElement getWithDefaultValidator(String name, Map<String, LazyIndexedElementParameters> parameters) {
        return new LazyElement(name, parameters, Invalids::defaultFalseValidator);
    }

    static <T> LazyElement getWithDefaultLocatorsAndValidator(String name) {
        return new LazyElement(name, new HashMap<>(), Invalids::defaultFalseValidator);
    }

    static LazyElement getWithIntegerFilterParameters(String name, boolean isIndexed, int index, LazyLocator locator, String getter) {
        final var lep = LazyIndexedElementFactory.getWithFilterParametersAndLocator(isIndexed, index, locator, getter);
        final var linkedHashMap = new LinkedHashMap<>(Map.ofEntries(entry(locator.strategy, lep)));
        final var synchronizedMap = Collections.synchronizedMap(linkedHashMap);

        return new LazyElement(name, synchronizedMap, ElementParameters::isValidLazyIndexedElement);
    }

    static LazyElement getWithIntegerFilterParameters(By locator, SingleGetter getter) {
        final var lep = getEntry(LazyIndexedElementFactory::getWithIntegerFilterParametersAndLocator, locator, getter.getName(), false);
        final var linkedHashMap = new LinkedHashMap<>(Map.ofEntries(lep));
        final var synchronizedMap = Collections.synchronizedMap(linkedHashMap);

        return new LazyElement(CoreUtilities.getIncrementalUUID(SeleniumCoreConstants.ATOMIC_COUNT), synchronizedMap, ElementParameters::isValidLazyIndexedElement);
    }

    static LazyElement getWithIntegerFilterParameters(String name, boolean isIndexed, LazyLocator locator, String getter) {
        return getWithIntegerFilterParameters(name, isIndexed, 0, locator, getter);
    }

    static LazyElement getWithIntegerFilterParameters(String name, int index, LazyLocator locator, String getter) {
        return getWithIntegerFilterParameters(name, true, index, locator, getter);
    }

    static LazyElement getWithIntegerFilterParameters(String name, boolean isIndexed, int index, LazyLocator locator) {
        return getWithIntegerFilterParameters(name, isIndexed, index, locator, SingleGetter.DEFAULT.getName());
    }

    static LazyElement getWithIntegerFilterParameters(String name, boolean isIndexed, LazyLocator locator) {
        return getWithIntegerFilterParameters(name, isIndexed, 0, locator, SingleGetter.DEFAULT.getName());
    }

    static LazyElement getWithIntegerFilterParameters(String name, int index, LazyLocator locator) {
        return getWithIntegerFilterParameters(name, true, index, locator, SingleGetter.DEFAULT.getName());
    }

    static LazyElement getWithIntegerFilterParameters(String name, LazyLocator locator) {
        return getWithIntegerFilterParameters(name, true, 0, locator, SingleGetter.DEFAULT.getName());
    }

    static LazyElement getWithIntegerFilterParameters(By locator) {
        return getWithIntegerFilterParameters(locator, SingleGetter.DEFAULT);
    }

    static LazyElement getWithIntegerFilterParameters(FilterData<Integer> filterData, By locator, ManyGetter getter) {
        final var lep = getEntryIndexed(LazyIndexedElementFactory::getWithIntegerFilterDataAndLocator, filterData, locator, getter.getName());
        final var linkedHashMap = new LinkedHashMap<>(Map.ofEntries(lep));
        final var synchronizedMap = Collections.synchronizedMap(linkedHashMap);

        return new LazyElement(CoreUtilities.getIncrementalUUID(SeleniumCoreConstants.ATOMIC_COUNT), synchronizedMap, ElementParameters::isValidLazyIndexedElement);
    }

    static LazyElement getWithStringFilterParameters(String name, boolean isFiltered, String message, LazyLocator locator, String getter) {
        final var lep = LazyIndexedElementFactory.getWithFilterParametersAndLocator(isFiltered, message, locator, getter);
        final var linkedHashMap = new LinkedHashMap<>(Map.ofEntries(entry(locator.strategy, lep)));
        final var synchronizedMap = Collections.synchronizedMap(linkedHashMap);

        return new LazyElement(name, synchronizedMap, ElementParameters::isValidLazyTextFilteredElement);
    }

    static LazyElement getWithStringFilterParameters(String name, String message, LazyLocator locator, String getter) {
        return getWithStringFilterParameters(name, true, message, locator, getter);
    }

    static LazyElement getWithStringFilterParameters(String name, boolean isFiltered, String message, LazyLocator locator) {
        return getWithStringFilterParameters(name, isFiltered, message, locator, SingleGetter.DEFAULT.getName());
    }

    static LazyElement getWithStringFilterParameters(String name, String message, LazyLocator locator) {
        return getWithStringFilterParameters(name, true, message, locator, SingleGetter.DEFAULT.getName());
    }

}
