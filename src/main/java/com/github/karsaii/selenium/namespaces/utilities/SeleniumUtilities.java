package com.github.karsaii.selenium.namespaces.utilities;

import com.github.karsaii.core.extensions.DecoratedList;
import com.github.karsaii.core.extensions.interfaces.functional.TriFunction;
import com.github.karsaii.core.extensions.namespaces.BasicPredicateFunctions;
import com.github.karsaii.core.extensions.namespaces.EmptiableFunctions;
import com.github.karsaii.core.namespaces.DataFactoryFunctions;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.github.karsaii.selenium.selector.records.SelectorKeySpecificityData;
import com.github.karsaii.selenium.abstracts.AbstractLazyElement;
import com.github.karsaii.selenium.abstracts.AbstractWaitParameters;
import com.github.karsaii.selenium.constants.SeleniumDataConstants;
import com.github.karsaii.selenium.constants.ElementStrategyMapConstants;
import com.github.karsaii.selenium.enums.SelectorStrategy;
import com.github.karsaii.selenium.namespaces.lazy.LazyIndexedElementFactory;
import com.github.karsaii.selenium.records.element.ElementWaitParameters;
import com.github.karsaii.selenium.records.lazy.filtered.FilterData;
import com.github.karsaii.selenium.namespaces.extensions.boilers.LazyLocatorList;
import com.github.karsaii.selenium.records.lazy.LazyElementWaitParameters;
import com.github.karsaii.selenium.records.lazy.filtered.LazyFilteredElementParameters;
import com.github.karsaii.selenium.records.lazy.LazyLocator;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.github.karsaii.core.extensions.namespaces.CoreUtilities.areAll;
import static com.github.karsaii.core.extensions.namespaces.CoreUtilities.areAny;
import static com.github.karsaii.core.extensions.namespaces.CoreUtilities.areAnyNull;
import static com.github.karsaii.core.extensions.namespaces.CoreUtilities.isEqual;
import static com.github.karsaii.core.extensions.namespaces.NullableFunctions.isNull;
import static com.github.karsaii.core.namespaces.validators.DataValidators.isInvalidOrFalse;
import static java.util.Map.entry;
import static org.apache.commons.lang3.StringUtils.isBlank;

public interface SeleniumUtilities {
    static boolean isNullLazyData(LazyLocator data) {
        return isNull(data) || isBlank(data.locator) || isNull(data.strategy);
    }

    static boolean areNullLazyData(LazyLocator... data) {
        return areAll(SeleniumUtilities::isNullLazyData, data);
    }

    static boolean areNullLazyData(List<LazyLocator> data) {
        return areNullLazyData(data.toArray(new LazyLocator[0]));
    }

    static boolean isNullLazyDataList(LazyLocatorList list) {
        return EmptiableFunctions.isNullOrEmpty(list) || areNullLazyData(list.list);
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

    static <T> boolean isNullLazyElement(AbstractLazyElement<T> element) {
        return (
            isNull(element) ||
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
            isNull(element) ||
            Objects.equals(SeleniumDataConstants.NULL_ELEMENT.object, element) ||
            isEqual(element.getAttribute("id"), SeleniumFormatterConstants.NULL_ELEMENT_ID)
        );
    }

    static boolean isNotNullWebElement(WebElement element) {
        return !isNullWebElement(element);
    }

    static boolean isNullWebElement(Data<WebElement> element) {
        return isInvalidOrFalse(element) || Objects.equals(SeleniumDataConstants.NULL_ELEMENT, element) || isNullWebElement(element.object);
    }

    static boolean isNotNullWebElement(Data<WebElement> element) {
        return !isNullWebElement(element);
    }

    static <T> boolean isNullCommonWaitParametersData(AbstractWaitParameters<T> data) {
        return isNull(data) || areAny(BasicPredicateFunctions::isNegative, data.duration, data.interval);
    }

    static boolean isNullElementWaitParametersData(ElementWaitParameters data) {
        return isNullCommonWaitParametersData(data) || isNull(data.object);
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
            return CoreFormatterConstants.EMPTY;
        }

        final var colonIndex = locator.indexOf(CoreFormatterConstants.COLON);
        return locator.substring(3, colonIndex);
    }

    static String getLocator(String locator) {
        if (isBlank(locator) || locator.length() < 4) {
            return CoreFormatterConstants.EMPTY;
        }

        final var locatorStartIndex = locator.indexOf(CoreFormatterConstants.COLON) + 2;
        return locator.substring(locatorStartIndex);
    }

    static LazyLocator getLazyLocator(By locator) {
        if (isNull(locator)) {
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

    static <T, V> Map.Entry<String, LazyFilteredElementParameters> getEntryIndexed(TriFunction<FilterData, LazyLocator, String, LazyFilteredElementParameters> constructor, FilterData<?> filterData, By locator, String getter) {
        final var lazyLocator = getLazyLocator(locator);
        return entry(lazyLocator.strategy, constructor.apply(filterData, lazyLocator, getter));
    }

    static Data<By> getLocator(Map<SelectorStrategy, Function<String, By>> strategyMap, LazyLocator data) {
        if (isNullLazyData(data)) {
            return SeleniumDataConstants.NULL_BY;
        }

        final var locator = data.locator;
        final var strategy = data.strategy;
        return DataFactoryFunctions.getWithMessage(strategyMap.get(SelectorStrategy.getValueOf(strategy)).apply(locator), true, "Locator: By " + strategy + " with locator: " + locator);
    }

    static Data<By> getLocator(LazyLocator data) {
        return getLocator(ElementStrategyMapConstants.STRATEGY_MAP, data);
    }

    static <T> Map<T, LazyFilteredElementParameters> getParametersCopy(Map<T, LazyFilteredElementParameters> source) {
        final var keys = source.keySet().iterator();
        final var values = source.values().iterator();

        final var map = Collections.synchronizedMap(new LinkedHashMap<T, LazyFilteredElementParameters>());
        LazyFilteredElementParameters lep;
        while(keys.hasNext() && values.hasNext()) {
            lep = values.next();
            map.putIfAbsent(keys.next(), LazyIndexedElementFactory.getWithFilterDataAndLocatorList(lep.filterData, lep.probability, lep.lazyLocators, lep.getter));
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
