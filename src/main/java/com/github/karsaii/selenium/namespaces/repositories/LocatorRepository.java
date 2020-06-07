package com.github.karsaii.selenium.namespaces.repositories;

import com.github.karsaii.core.constants.CoreDataConstants;
import com.github.karsaii.core.namespaces.DataFactoryFunctions;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import com.github.karsaii.core.namespaces.validators.CoreFormatter;
import org.openqa.selenium.By;
import com.github.karsaii.selenium.constants.SeleniumDataConstants;
import com.github.karsaii.selenium.constants.RepositoryConstants;
import com.github.karsaii.selenium.enums.SingleGetter;
import com.github.karsaii.selenium.namespaces.lazy.LazyElementFactory;
import com.github.karsaii.selenium.records.lazy.LazyElement;
import com.github.karsaii.selenium.records.lazy.CachedLazyElementData;

import java.util.Map;

import static com.github.karsaii.core.namespaces.DataFactoryFunctions.replaceMessage;
import static org.apache.commons.lang3.StringUtils.isBlank;

public interface LocatorRepository {
    static Data<LazyElement> getIfContains(Map<By, String> locatorRepository, Map<String, CachedLazyElementData> elementRepository, By locator, SingleGetter getter) {
        final var nameof = "getIfContains";
        final var element = locatorRepository.containsKey(locator) ? ElementRepository.getElement(elementRepository, locatorRepository.get(locator)).object.element : LazyElementFactory.getWithFilterParameters(locator, getter);
        final var result = cacheLocator(locatorRepository, locator, element.name, CoreDataConstants.NULL_BOOLEAN);
        final var status = result.object;
        return status ?
            DataFactoryFunctions.getWithMethodMessage(element, result.status, nameof, result.message) :
            replaceMessage(SeleniumDataConstants.NULL_LAZY_ELEMENT, nameof, "Locator was " + CoreFormatter.getOptionMessage(status) + " found" + CoreFormatterConstants.END_LINE);
    }

    static Data<LazyElement> getIfContains(Map<By, String> locatorRepository, Map<String, CachedLazyElementData> elementRepository, By locator) {
        return getIfContains(locatorRepository, elementRepository, locator, SingleGetter.DEFAULT);
    }

    static Data<LazyElement> getIfContains(By locator, SingleGetter getter) {
        return getIfContains(RepositoryConstants.locatorElements, RepositoryConstants.elements, locator, getter);
    }

    static Data<LazyElement> getIfContains(By locator) {
        return getIfContains(RepositoryConstants.locatorElements, RepositoryConstants.elements, locator, SingleGetter.DEFAULT);
    }

    static Data<Boolean> cacheLocator(Map<By, String> locatorRepository, By locator, String name, Data<Boolean> defaultValue) {
        final var nameof = "cacheLocator";
        if (isBlank(name)) {
            return replaceMessage(defaultValue, nameof, SeleniumFormatterConstants.LOCATOR + "with name " + CoreFormatterConstants.WAS_NULL);
        }

        if (locatorRepository.containsKey(locator)) {
            return DataFactoryFunctions.getWithNameAndMessage(true, false, nameof, SeleniumFormatterConstants.LOCATOR + " with name(\"" + name + "\") was already stored" + CoreFormatterConstants.END_LINE);
        }

        locatorRepository.putIfAbsent(locator, name);
        final var status = locatorRepository.containsKey(locator);
        final var message = "New Lazy Element, by locator, with name(\"" + name + "\") " + CoreFormatter.getOptionMessage(status) + " added to cache" + CoreFormatterConstants.END_LINE;
        return DataFactoryFunctions.getBoolean(status, nameof, message);
    }

    static Data<Boolean> cacheLocator(Map<By, String> locatorRepository, By locator, String name) {
        return cacheLocator(locatorRepository, locator, name, CoreDataConstants.NULL_BOOLEAN);
    }

    static Data<Boolean> cacheLocator(By locator, String name, Data<Boolean> defaultValue) {
        return cacheLocator(RepositoryConstants.locatorElements, locator, name, defaultValue);
    }

    static Data<Boolean> cacheLocator(By locator, String name) {
        return cacheLocator(RepositoryConstants.locatorElements, locator, name, CoreDataConstants.NULL_BOOLEAN);
    }
}
