package selenium.namespaces.repositories;

import core.constants.CoreDataConstants;
import core.extensions.DecoratedList;
import core.extensions.namespaces.CoreUtilities;
import core.extensions.namespaces.NullableFunctions;
import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import data.constants.Strings;
import data.namespaces.Formatter;
import selector.records.SelectorKeySpecificityData;
import selectorSpecificity.Specificity;
import selectorSpecificity.constants.Strategy;
import selectorSpecificity.tuples.SpecificityData;
import selenium.constants.SeleniumDataConstants;
import selenium.constants.ElementStrategyMapConstants;
import selenium.constants.RepositoryConstants;
import selenium.enums.SelectorStrategy;
import selenium.namespaces.SeleniumUtilities;
import selenium.records.LazyElement;
import selenium.records.LazyLocatorList;
import selenium.records.lazy.CachedLazyElementData;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static core.extensions.namespaces.CoreUtilities.areAnyNull;
import static core.namespaces.validators.DataValidators.isInvalidOrFalse;
import static core.namespaces.validators.DataValidators.isValidNonFalse;
import static core.namespaces.DataFactoryFunctions.prependMessage;
import static core.namespaces.DataFactoryFunctions.replaceMessage;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static selenium.namespaces.SeleniumUtilities.isNullLazyElement;

public interface ElementRepository {
    static Data<Boolean> cacheElement(Map<String, CachedLazyElementData> elementRepository, LazyElement element, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, Data<Boolean> defaultValue) {
        final var nameof = "cacheElement";
        if (isNullLazyElement(element)) {
            return replaceMessage(defaultValue, nameof, Strings.LAZY_ELEMENT + " " + Strings.WAS_NULL);
        }

        final var name = element.name;
        if (elementRepository.containsKey(name)) {
            return replaceMessage(defaultValue, nameof, Strings.LAZY_ELEMENT + " with name(\"" + name + "\") was already stored" + Strings.END_LINE);
        }

        elementRepository.put(name, new CachedLazyElementData(element, typeKeys));
        final var status = elementRepository.containsKey(name);
        final var message = (status ? "Lazy Element with name(\"" + name + "\") added to cache" : "Lazy Element with name(\"" + name + "\") not added to cache") + Strings.END_LINE;
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
        if (areAnyNull(elementRepository, defaultValue) || isBlank(name)) {
            return replaceMessage(defaultValue, nameof, "Parameters " + Strings.WAS_NULL);
        }

        final var defaultObject = defaultValue.object;
        final var object = elementRepository.containsKey(name);
        final var message = Strings.LAZY_ELEMENT + Formatter.getOptionMessage(object) + " found by name(\"" + name + "\")" + Strings.END_LINE;

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
        if (areAnyNull(elementRepository, defaultValue) || isBlank(name)) {
            return replaceMessage(defaultValue, nameof, "Passed name for lookup " + Strings.WAS_NULL);
        }

        final var defaultObject = defaultValue.object;
        final var object = elementRepository.getOrDefault(name, defaultObject);
        final var status = CoreUtilities.isNotEqual(object, defaultObject);
        final var message = Strings.LAZY_ELEMENT + Formatter.getOptionMessage(status) + " found by name(\"" + name + "\")" + Strings.END_LINE;

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
            return replaceMessage(CoreDataConstants.NULL_BOOLEAN, nameof, "Strategy passed" + Strings.WAS_NULL);
        }

        final var typeKey = types.stream().filter(key::contains).findFirst();
        if (typeKey.isEmpty()) {
            return replaceMessage(CoreDataConstants.NULL_BOOLEAN, nameof, "Types didn't contain type key(\"" + typeKey + "\")" + Strings.END_LINE);
        }

        final var type = typeKeys.get(typeKey.get());
        final var selectorKeySpecificityData = getSpecificityForSelector(locators, key);
        if (NullableFunctions.isNotNull(type)) {
            type.addNullSafe(selectorKeySpecificityData);
        }

        final var status = type.contains(selectorKeySpecificityData);
        final var message = "Key(\"" + key + "\") with specificity was " + Formatter.getOptionMessage(status) + " added" + Strings.END_LINE;
        return DataFactoryFunctions.getBoolean(status, message);
    }

    static Data<Boolean> updateTypeKeys(String name, LazyLocatorList locators, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, List<String> types, String key) {
        final var nameof = "updateTypeKeys";
        final var cached = containsElement(name);
        if (isInvalidOrFalse(cached)) {
            return prependMessage(cached, nameof, "There were parameter issues" + Strings.END_LINE);
        }

        return !cached.object ? (updateTypeKeys(locators, typeKeys, types, key)) : DataFactoryFunctions.getBoolean(true, nameof, "Element(\"" + name + "\") was already cached" + Strings.END_LINE);
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
