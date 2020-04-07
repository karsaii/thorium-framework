package selenium.namespaces;

import core.extensions.DecoratedList;
import core.extensions.interfaces.functional.TriFunction;
import core.extensions.namespaces.NullableFunctions;
import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import data.constants.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selector.records.SelectorKeySpecificityData;
import selenium.abstracts.AbstractLazyElement;
import selenium.abstracts.AbstractWaitParameters;
import selenium.constants.DataConstants;
import selenium.constants.ElementStrategyMapConstants;
import selenium.enums.SelectorStrategy;
import selenium.records.ElementWaitParameters;
import selenium.records.IndexedData;
import selenium.records.LazyLocatorList;
import selenium.records.lazy.LazyElementWaitParameters;
import selenium.records.lazy.LazyIndexedElementParameters;
import selenium.records.lazy.LazyLocator;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static core.extensions.namespaces.CoreUtilities.areAll;
import static core.extensions.namespaces.CoreUtilities.areAnyNull;
import static core.extensions.namespaces.CoreUtilities.isNotEqual;
import static core.extensions.namespaces.CoreUtilities.isNullOrEmptyList;
import static java.util.Map.entry;
import static org.apache.commons.lang3.StringUtils.isBlank;

public interface SeleniumUtilities {
    static boolean isNullLazyData(LazyLocator data) {
        return NullableFunctions.isNull(data) || isBlank(data.locator) || NullableFunctions.isNull(data.strategy);
    }

    static boolean areNullLazyData(LazyLocator... data) {
        return areAll(SeleniumUtilities::isNullLazyData, data);
    }

    static boolean areNullLazyData(List<LazyLocator> data) {
        return areNullLazyData(data.toArray(new LazyLocator[0]));
    }

    static boolean isNullLazyDataList(LazyLocatorList list) {
        return isNullOrEmptyList(list) || areNullLazyData(list.list);
    }

    static boolean isNotNullLazyData(LazyLocator data) {
        return !isNullLazyData(data);
    }

    static <T> boolean isNullAbstractLazyElementParametersList(Collection<T> data, Predicate<T> validator) {
        for(T params : data) {
            if (validator.test(params)) {
                return false;
            }
        }

        return true;
    }

    static <T> boolean isNotNullAbstractLazyElementParametersList(Collection<T> data, Predicate<T> validator) {
        return !isNullAbstractLazyElementParametersList(data, validator);
    }

    static <T> boolean isNullLazyElement(AbstractLazyElement<T> element) {
        return (
            NullableFunctions.isNull(element) ||
            isBlank(element.name) ||
            areAnyNull(element.parameters, element.validator) ||
            element.parameters.isEmpty() ||
            isNullAbstractLazyElementParametersList(element.parameters.values(), element.validator)
        );
    }

    static <T> boolean isNotNullLazyElement(AbstractLazyElement<T> data) {
        return !isNullLazyElement(data);
    }


    static boolean isNullWebElement(WebElement element) {
        return (
            NullableFunctions.isNull(element) ||
            Objects.equals(DataConstants.NULL_ELEMENT.object, element) ||
            isNotEqual(element.getAttribute("id"), Strings.NULL_ELEMENT_ID)
        );
    }

    static <T> boolean isNullCommonWaitParametersData(AbstractWaitParameters<T> data) {
        return NullableFunctions.isNull(data) || (data.duration < 0) || (data.interval < 0);
    }

    static boolean isNullElementWaitParametersData(ElementWaitParameters data) {
        return isNullCommonWaitParametersData(data) || NullableFunctions.isNull(data.object);
    }

    static boolean isNotNullElementWaitParametersData(ElementWaitParameters data) {
        return !isNullElementWaitParametersData(data);
    }

    static boolean isNullLazyElementWaitParametersData(LazyElementWaitParameters data) {
        return isNullCommonWaitParametersData(data) || isNullLazyElement(data.object);
    }

    static boolean isNotNullLazyElementWaitParametersData(LazyElementWaitParameters data) {
        return !isNullLazyElementWaitParametersData(data);
    }

    static String getStrategy(String locator) {
        if (isBlank(locator) || locator.length() < 4) {
            return Strings.EMPTY;
        }

        final var colonIndex = locator.indexOf(':');
        return locator.substring(3, colonIndex);
    }

    static String getLocator(String locator) {
        if (isBlank(locator) || locator.length() < 4) {
            return Strings.EMPTY;
        }

        final var locatorStartIndex = locator.indexOf(':') + 2;
        return locator.substring(locatorStartIndex);
    }

    static LazyLocator getLazyLocator(By locator) {
        if (NullableFunctions.isNull(locator)) {
            return new LazyLocator();
        }

        final var locatorString = locator.toString();
        return new LazyLocator(getLocator(locatorString), getStrategy(locatorString));
    }

    static LazyLocatorList getLazyLocatorList(By locator) {
        return new LazyLocatorList(getLazyLocator(locator));
    }

    static <T> Map.Entry<String, T> getEntry(TriFunction<Boolean, LazyLocator, String, T> constructor, By locator, String getter, boolean isIndexed) {
        final var lazyLocator = getLazyLocator(locator);
        return entry(lazyLocator.strategy, constructor.apply(isIndexed, lazyLocator, getter));
    }

    static <T> Map.Entry<String, T> getEntryIndexed(TriFunction<IndexedData, LazyLocator, String, T> constructor, IndexedData indexedData, By locator, String getter) {
        final var lazyLocator = getLazyLocator(locator);
        return entry(lazyLocator.strategy, constructor.apply(indexedData, lazyLocator, getter));
    }



    static Data<By> getLocator(Map<SelectorStrategy, Function<String, By>> strategyMap, LazyLocator data) {
        if (isNullLazyData(data)) {
            return DataConstants.NULL_BY;
        }

        final var locator = data.locator;
        final var strategy = data.strategy;
        return DataFactoryFunctions.getWithMessage(strategyMap.get(SelectorStrategy.getValueOf(strategy)).apply(locator), true, "Locator: By " + strategy + " with locator: " + locator);
    }

    static Data<By> getLocator(LazyLocator data) {
        return getLocator(ElementStrategyMapConstants.STRATEGY_MAP, data);
    }

    static <T> Map<T, LazyIndexedElementParameters> getParametersCopy(Map<T, LazyIndexedElementParameters> source) {
        final var keys = source.keySet().iterator();
        final var values = source.values().iterator();

        final var map = Collections.synchronizedMap(new LinkedHashMap<T, LazyIndexedElementParameters>());
        LazyIndexedElementParameters lep;
        while(keys.hasNext() && values.hasNext()) {
            lep = values.next();
            map.putIfAbsent(keys.next(), new LazyIndexedElementParameters(lep.indexData, lep.probability, lep.lazyLocators, lep.getter));
        }

        return map;
    }

    static Map<String, DecoratedList<SelectorKeySpecificityData>> getKeysCopy(Map<String, DecoratedList<SelectorKeySpecificityData>> source) {
        final var keys = source.keySet().iterator();
        final var values = source.values().iterator();

        final var map = Collections.synchronizedMap(new LinkedHashMap<String, DecoratedList<SelectorKeySpecificityData>>());
        while(keys.hasNext() && values.hasNext()) {
            map.putIfAbsent(keys.next(), new DecoratedList<>(values.next(), SelectorKeySpecificityData.class));
        }

        return map;
    }
}