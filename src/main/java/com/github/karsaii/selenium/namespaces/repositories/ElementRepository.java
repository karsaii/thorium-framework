package com.github.karsaii.selenium.namespaces.repositories;

import com.github.karsaii.core.constants.CoreDataConstants;
import com.github.karsaii.core.extensions.DecoratedList;
import com.github.karsaii.core.extensions.namespaces.CoreUtilities;
import com.github.karsaii.core.extensions.namespaces.NullableFunctions;
import com.github.karsaii.core.namespaces.DataFactoryFunctions;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import com.github.karsaii.core.namespaces.validators.CoreFormatter;
import com.github.karsaii.selenium.selector.records.SelectorKeySpecificityData;
import selectorSpecificity.Specificity;
import selectorSpecificity.constants.Strategy;
import selectorSpecificity.tuples.SpecificityData;
import com.github.karsaii.selenium.constants.SeleniumDataConstants;
import com.github.karsaii.selenium.constants.ElementStrategyMapConstants;
import com.github.karsaii.selenium.constants.RepositoryConstants;
import com.github.karsaii.selenium.enums.SelectorStrategy;
import com.github.karsaii.selenium.namespaces.utilities.SeleniumUtilities;
import com.github.karsaii.selenium.namespaces.element.validators.ElementRepositoryValidators;
import com.github.karsaii.selenium.records.lazy.LazyElement;
import com.github.karsaii.selenium.namespaces.extensions.boilers.LazyLocatorList;
import com.github.karsaii.selenium.records.lazy.CachedLazyElementData;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.karsaii.core.namespaces.validators.DataValidators.isInvalidOrFalse;
import static com.github.karsaii.core.namespaces.validators.DataValidators.isValidNonFalse;
import static com.github.karsaii.core.namespaces.DataFactoryFunctions.prependMessage;
import static com.github.karsaii.core.namespaces.DataFactoryFunctions.replaceMessage;

import static com.github.karsaii.selenium.namespaces.validators.SeleniumFormatter.isNullLazyElementMessage;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ElementRepository {
    static Data<Boolean> cacheElement(Map<String, CachedLazyElementData> elementRepository, LazyElement element, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, Data<Boolean> defaultValue) {
        final var nameof = "cacheElement";
        final var errorMessage = isNullLazyElementMessage(element);
        if (isNotBlank(errorMessage)) {
            return replaceMessage(defaultValue, nameof, SeleniumFormatterConstants.LAZY_ELEMENT + " " + CoreFormatterConstants.WAS_NULL);
        }

        final var name = element.name;
        if (elementRepository.containsKey(name)) {
            return replaceMessage(defaultValue, nameof, SeleniumFormatterConstants.LAZY_ELEMENT + " with name(\"" + name + "\") was already stored" + CoreFormatterConstants.END_LINE);
        }

        elementRepository.put(name, new CachedLazyElementData(element, typeKeys));
        final var status = elementRepository.containsKey(name);
        final var message = (status ? "Lazy Element with name(\"" + name + "\") added to cache" : "Lazy Element with name(\"" + name + "\") not added to cache") + CoreFormatterConstants.END_LINE;
        return DataFactoryFunctions.getBoolean(status, nameof, message);
    }

    static Data<Boolean> cacheElement(LazyElement element, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, Data<Boolean> defaultValue) {
        return cacheElement(RepositoryConstants.elements, element, typeKeys, defaultValue);
    }

    static Data<Boolean> cacheElement(LazyElement element, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys) {
        return cacheElement(RepositoryConstants.elements, element, typeKeys, CoreDataConstants.NULL_BOOLEAN);
    }

    static Data<Boolean> containsElement(Map<String, CachedLazyElementData> elementRepository, String name, Data<Boolean> defaultValue) {
        final var nameof = "containsElement";
        final var errorMessage = ElementRepositoryValidators.isInvalidContainsElementMessage(elementRepository, name, defaultValue);
        if (isNotBlank(errorMessage)) {
            return replaceMessage(defaultValue, nameof, errorMessage);
        }

        final var defaultObject = defaultValue.object;
        final var object = elementRepository.containsKey(name);
        final var message = SeleniumFormatterConstants.LAZY_ELEMENT + CoreFormatter.getOptionMessage(object) + " found by name(\"" + name + "\")" + CoreFormatterConstants.END_LINE;

        return DataFactoryFunctions.getWithNameAndMessage(object, true, nameof, message);
    }

    static Data<Boolean> containsElement(String name, Data<Boolean> defaultValue) {
        return containsElement(RepositoryConstants.elements, name, defaultValue);
    }

    static Data<Boolean> containsElement(String name) {
        return containsElement(RepositoryConstants.elements, name, CoreDataConstants.NULL_BOOLEAN);
    }

    static Data<CachedLazyElementData> getElement(Map<String, CachedLazyElementData> elementRepository, String name, Data<CachedLazyElementData> defaultValue) {
        final var nameof = "getElement";
        final var errorMessage = ElementRepositoryValidators.isInvalidContainsElementMessage(elementRepository, name, defaultValue);
        if (isNotBlank(errorMessage)) {
            return replaceMessage(defaultValue, nameof, errorMessage);
        }

        final var defaultObject = defaultValue.object;
        final var object = elementRepository.getOrDefault(name, defaultObject);
        final var status = CoreUtilities.isNotEqual(object, defaultObject);
        final var message = SeleniumFormatterConstants.LAZY_ELEMENT + CoreFormatter.getOptionMessage(status) + " found by name(\"" + name + "\")" + CoreFormatterConstants.END_LINE;

        return DataFactoryFunctions.getWithNameAndMessage(object, status, nameof, message);
    }

    static Data<CachedLazyElementData> getElement(String name, Data<CachedLazyElementData> defaultValue) {
        return getElement(RepositoryConstants.elements, name, defaultValue);
    }

    static Data<CachedLazyElementData> getElement(Map<String, CachedLazyElementData> elementRepository, String name) {
        return getElement(elementRepository, name, SeleniumDataConstants.NULL_CACHED_LAZY_ELEMENT);
    }

    static Data<CachedLazyElementData> getElement(String name) {
        return getElement(RepositoryConstants.elements, name);
    }

    static String cacheIfAbsent(LazyElement element, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys) {
        final var cached = ElementRepository.containsElement(element.name);

        var message = "";
        if (isValidNonFalse(cached) && !cached.object) {
            final var cachedElement = new LazyElement(element.name, SeleniumUtilities.getParametersCopy(element.parameters), element.validator);
            message = ElementRepository.cacheElement(cachedElement, typeKeys).message.toString();
        }

        return message;
    }

    static Map<String, DecoratedList<SelectorKeySpecificityData>> getInitializedTypeKeysMap() {
        final var typeKeys = Collections.synchronizedMap(new LinkedHashMap<String, DecoratedList<SelectorKeySpecificityData>>());
        final var types = new DecoratedList<>(ElementStrategyMapConstants.STRATEGY_MAP_KEY_SET, SelectorStrategy.class);
        for (var type : types) {
            typeKeys.put(type.getName(), new DecoratedList<>());
        }

        return typeKeys;
    }

    static Data<CachedLazyElementData> getIfContains(LazyElement element) {
        final var name = element.name;
        return isValidNonFalse(ElementRepository.containsElement(name)) ? ElementRepository.getElement(name) : SeleniumDataConstants.ELEMENT_WAS_NOT_CACHED;
    }

    static Data<Boolean> updateTypeKeys(LazyLocatorList locators, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, List<String> types, String key) {
        final var nameof = "updateTypeKeys";
        if (NullableFunctions.isNull(key)) {
            return replaceMessage(CoreDataConstants.NULL_BOOLEAN, nameof, "Strategy passed" + CoreFormatterConstants.WAS_NULL);
        }

        final var typeKey = types.stream().filter(key::contains).findFirst();
        if (typeKey.isEmpty()) {
            return replaceMessage(CoreDataConstants.NULL_BOOLEAN, nameof, "Types didn't contain type key(\"" + typeKey + "\")" + CoreFormatterConstants.END_LINE);
        }

        final var type = typeKeys.get(typeKey.get());
        final var selectorKeySpecificityData = getSpecificityForSelector(locators, key);
        if (NullableFunctions.isNotNull(type)) {
            type.addNullSafe(selectorKeySpecificityData);
        }

        final var status = type.contains(selectorKeySpecificityData);
        final var message = "Key(\"" + key + "\") with specificity was " + CoreFormatter.getOptionMessage(status) + " added" + CoreFormatterConstants.END_LINE;
        return DataFactoryFunctions.getBoolean(status, message);
    }

    static Data<Boolean> updateTypeKeys(String name, LazyLocatorList locators, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, List<String> types, String key) {
        final var nameof = "updateTypeKeys";
        final var cached = containsElement(name);
        if (isInvalidOrFalse(cached)) {
            return prependMessage(cached, nameof, "There were parameter issues" + CoreFormatterConstants.END_LINE);
        }

        return !cached.object ? (updateTypeKeys(locators, typeKeys, types, key)) : DataFactoryFunctions.getBoolean(true, nameof, "Element(\"" + name + "\") was already cached" + CoreFormatterConstants.END_LINE);
    }

    static SelectorKeySpecificityData getSpecificityForSelector(LazyLocatorList list, String key) {
        return new SelectorKeySpecificityData(
            key,
            Specificity.reduce(
                list.stream().map(
                    locator -> Specificity.getSelectorSpecificity(locator.locator, (CoreUtilities.isEqual(locator.strategy, SelectorStrategy.CSS_SELECTOR) ? Strategy.CSS_SELECTOR : Strategy.XPATH)).specifics
                ).toArray(SpecificityData[]::new)
            )
        );
    }
}
