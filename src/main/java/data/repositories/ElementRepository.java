package data.repositories;

import data.LazyLocatorList;
import data.constants.DataDefaults;
import data.constants.Defaults;
import data.constants.Strings;
import data.Data;
import data.LazyElement;
import data.extensions.DecoratedList;
import data.lazy.SelectorKeySpecificityData;
import data.lazy.tuples.CachedLazyElementData;
import formatter.Formatter;
import selectorSpecificity.Specificity;
import selectorSpecificity.constants.Strategy;
import selectorSpecificity.tuples.SpecificityData;
import utilities.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static utilities.utils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

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
        return new Data<Boolean>(status, status, nameof, message);
    }

    static Data<Boolean> cacheElement(LazyElement element, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, Data<Boolean> defaultValue) {
        return cacheElement(Defaults.elements, element, typeKeys, defaultValue);
    }

    static Data<Boolean> cacheElement(LazyElement element, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys) {
        return cacheElement(Defaults.elements, element, typeKeys, DataDefaults.NULL_BOOLEAN_DATA);
    }

    static Data<Boolean> containsElement(Map<String, CachedLazyElementData> elementRepository, String name, Data<Boolean> defaultValue) {
        final var nameof = "containsElement";
        if (areAnyNull(elementRepository, defaultValue) || isBlank(name)) {
            return replaceMessage(defaultValue, nameof, "Parameters " + Strings.WAS_NULL);
        }

        final var defaultObject = defaultValue.object;
        final var object = elementRepository.containsKey(name);
        final var message = Strings.LAZY_ELEMENT + Formatter.getOptionMessage(object) + " found by name(\"" + name + "\")" + Strings.END_LINE;

        return new Data<Boolean>(object, true, nameof, message);
    }

    static Data<Boolean> containsElement(String name, Data<Boolean> defaultValue) {
        return containsElement(Defaults.elements, name, defaultValue);
    }

    static Data<Boolean> containsElement(String name) {
        return containsElement(Defaults.elements, name, DataDefaults.NULL_BOOLEAN_DATA);
    }

    static Data<CachedLazyElementData> getElement(Map<String, CachedLazyElementData> elementRepository, String name, Data<CachedLazyElementData> defaultValue) {
        final var nameof = "getElement";
        if (areAnyNull(elementRepository, defaultValue) || isBlank(name)) {
            return replaceMessage(defaultValue, nameof, "Passed name for lookup " + Strings.WAS_NULL);
        }

        final var defaultObject = defaultValue.object;
        final var object = elementRepository.getOrDefault(name, defaultObject);
        final var status = isNotEqual(object, defaultObject);
        final var message = Strings.LAZY_ELEMENT + Formatter.getOptionMessage(status) + " found by name(\"" + name + "\")" + Strings.END_LINE;

        return new Data<CachedLazyElementData>(object, status, nameof, message);
    }

    static Data<CachedLazyElementData> getElement(String name, Data<CachedLazyElementData> defaultValue) {
        return getElement(Defaults.elements, name, defaultValue);
    }

    static Data<CachedLazyElementData> getElement(String name) {
        return getElement(Defaults.elements, name, DataDefaults.NULL_CACHED_LAZY_ELEMENT_DATA);
    }

    static String cacheIfAbsent(LazyElement element, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys) {
        final var cached = ElementRepository.containsElement(element.name);

        var message = "";
        if (isNotNullOrFalseDataOrDataObject(cached) && !cached.object) {
            final var cachedElement = new LazyElement(element.name, utils.getParametersCopy(element.parameters), element.validator);
            message = ElementRepository.cacheElement(cachedElement, typeKeys).message.toString();
        }

        return message;
    }

    static Map<String, DecoratedList<SelectorKeySpecificityData>> getInitializedTypeKeysMap() {
        final var typeKeys = Collections.synchronizedMap(new LinkedHashMap<String, DecoratedList<SelectorKeySpecificityData>>());
        final var types = new DecoratedList<String>(Defaults.strategyMap.keySet(), String.class);
        for (var type : types) {
            typeKeys.put(type, new DecoratedList<>());
        }

        return typeKeys;
    }

    static Data<CachedLazyElementData> getIfContains(LazyElement element) {
        final var name = element.name;
        return isNotNullOrFalseDataOrDataObject(ElementRepository.containsElement(name)) ? (
                ElementRepository.getElement(name)
        ) : replaceMessage(DataDefaults.NULL_CACHED_LAZY_ELEMENT_DATA, "getIfContains", "Element wasn't cached" + Strings.END_LINE);
    }

    static Data<Boolean> updateTypeKeys(LazyLocatorList locators, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, List<String> types, String key) {
        final var nameof = "updateTypeKeys";
        if (isNull(key)) {
            return replaceMessage(DataDefaults.NULL_BOOLEAN_DATA, nameof, "Strategy passed" + Strings.WAS_NULL);
        }

        final var typeKey = types.stream().filter(key::contains).findFirst();
        if (typeKey.isEmpty()) {
            return replaceMessage(DataDefaults.NULL_BOOLEAN_DATA, nameof, "Types didn't contain type key(\"" + typeKey + "\")" + Strings.END_LINE);
        }

        final var type = typeKeys.get(typeKey.get());
        final var selectorKeySpecificityData = getSpecificityForSelector(locators, key);
        if (isNotNull(type)) {
            type.addNullSafe(selectorKeySpecificityData);
        }

        final var status = type.contains(selectorKeySpecificityData);
        final var message = "Key(\"" + key + "\") with specificity was " + Formatter.getOptionMessage(status) + " added" + Strings.END_LINE;
        return new Data<Boolean>(status, status, message);
    }

    static Data<Boolean> updateTypeKeys(String name, LazyLocatorList locators, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, List<String> types, String key) {
        final var nameof = "updateTypeKeys";
        final var cached = containsElement(name);
        if (isNullOrFalseDataOrDataObject(cached)) {
            return prependMessage(cached, nameof, "There were parameter issues" + Strings.END_LINE);
        }

        return !cached.object ? (
            updateTypeKeys(locators, typeKeys, types, key)
        ) : new Data<Boolean>(true, true, nameof, "Element(\"" + name + "\") was already cached" + Strings.END_LINE);
    }

    static SelectorKeySpecificityData getSpecificityForSelector(LazyLocatorList list, String key) {
        return new SelectorKeySpecificityData(
            key,
            Specificity.reduce(
                list.stream().map(
                    locator -> Specificity.getSelectorSpecificity(locator.locator, (isEqual(locator.strategy, "cssSelector") ? Strategy.CSS_SELECTOR : Strategy.XPATH)).specifics
                ).toArray(SpecificityData[]::new)
            )
        );
    }
}
