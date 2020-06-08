package com.github.karsaii.selenium.namespaces.lazy;

import com.github.karsaii.selenium.namespaces.element.ElementFilterFunctions;
import com.github.karsaii.selenium.namespaces.extensions.boilers.LazyLocatorList;
import com.github.karsaii.selenium.records.lazy.filtered.FilterData;
import com.github.karsaii.selenium.records.lazy.filtered.LazyFilteredElementParameters;
import com.github.karsaii.selenium.records.lazy.LazyLocator;

import static com.github.karsaii.selenium.constants.LazyIndexedElementFactoryConstants.FIRST_INDEX;
import static com.github.karsaii.selenium.constants.LazyIndexedElementFactoryConstants.GETTER;
import static com.github.karsaii.selenium.constants.LazyIndexedElementFactoryConstants.PROBABILITY;

public interface LazyIndexedElementFactory {
    static LazyFilteredElementParameters getWithFilterDataAndLocatorList(FilterData<?> data, double probability, LazyLocatorList lazyLocators, String getter) {
        return new LazyFilteredElementParameters(data, probability, lazyLocators, getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(boolean isFiltered, String message, double probability, LazyLocatorList lazyLocators, String getter) {
        return getWithFilterDataAndLocatorList(new FilterData<>(isFiltered, ElementFilterFunctions::getContainedTextElement, message), probability, lazyLocators, getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(boolean isFiltered, int index, double probability, LazyLocatorList lazyLocators, String getter) {
        return getWithFilterDataAndLocatorList(new FilterData<>(isFiltered, ElementFilterFunctions::getIndexedElement, index), probability, lazyLocators, getter);
    }

    static LazyFilteredElementParameters getWithFilterDataAndLocatorList(FilterData<?> data, double probability, LazyLocatorList lazyLocators) {
        return getWithFilterDataAndLocatorList(data, probability, lazyLocators, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterDataAndLocatorList(FilterData<?> data, LazyLocatorList lazyLocators, String getter) {
        return getWithFilterDataAndLocatorList(data, PROBABILITY, lazyLocators, getter);
    }

    static LazyFilteredElementParameters getWithFilterDataAndLocatorList(FilterData<?> data, LazyLocatorList lazyLocators) {
        return getWithFilterDataAndLocatorList(data, PROBABILITY, lazyLocators, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterDataAndLocator(FilterData<?> data, double probability, LazyLocator lazyLocator) {
        return getWithFilterDataAndLocatorList(data, probability, new LazyLocatorList(lazyLocator), GETTER);
    }

    static LazyFilteredElementParameters getWithFilterDataAndLocator(FilterData<?> data, double probability, LazyLocator lazyLocator, String getter) {
        return getWithFilterDataAndLocatorList(data, probability, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyFilteredElementParameters getWithFilterDataAndLocator(FilterData<?> data, LazyLocator lazyLocator, String getter) {
        return getWithFilterDataAndLocatorList(data, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyFilteredElementParameters getWithFilterDataAndLocator(FilterData<?> data, LazyLocator lazyLocator) {
        return getWithFilterDataAndLocatorList(data, new LazyLocatorList(lazyLocator), GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(boolean isFiltered, int index, double probability, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(isFiltered, index, probability, lazyLocators, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(int index, double probability, LazyLocatorList lazyLocators, String getter) {
        return getWithFilterParametersAndLocatorList(true, index, probability, lazyLocators, getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(boolean isFiltered, double probability, LazyLocatorList lazyLocators, String getter) {
        return getWithFilterParametersAndLocatorList(isFiltered, FIRST_INDEX, probability, lazyLocators, getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(boolean isFiltered, int index, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(isFiltered, index, PROBABILITY, lazyLocators, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(boolean isFiltered, double probability, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(isFiltered, FIRST_INDEX, probability, lazyLocators, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(int index, double probability, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(true, index, probability, lazyLocators, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(boolean isFiltered, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(isFiltered, FIRST_INDEX, PROBABILITY, lazyLocators, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(int index, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(true, index, PROBABILITY, lazyLocators, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(boolean isFiltered, int index, double probability, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(isFiltered, index, probability, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(boolean isFiltered, int index, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(isFiltered, index, PROBABILITY, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(boolean isFiltered, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(isFiltered, FIRST_INDEX, PROBABILITY, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(boolean isFiltered, int index, double probability, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocatorList(isFiltered, index, probability, new LazyLocatorList(lazyLocator), GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(boolean isFiltered, int index, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocatorList(isFiltered, index, PROBABILITY, new LazyLocatorList(lazyLocator), GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(int index, double probability, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(index, probability, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(boolean isFiltered, double probability, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(isFiltered, probability, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(int index, double probability, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocator(index, probability, lazyLocator, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(boolean isFiltered, double probability, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocator(isFiltered, FIRST_INDEX, probability, lazyLocator, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(int index, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocator(index, PROBABILITY, lazyLocator, getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(boolean isFiltered, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocator(isFiltered, FIRST_INDEX, PROBABILITY, lazyLocator, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(int index, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocator(index, PROBABILITY, lazyLocator, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(boolean isFiltered, String message, double probability, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(isFiltered, message, probability, lazyLocators, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(String message, double probability, LazyLocatorList lazyLocators, String getter) {
        return getWithFilterParametersAndLocatorList(true, message, probability, lazyLocators, getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(boolean isFiltered, String message, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(isFiltered, message, PROBABILITY, lazyLocators, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(String message, double probability, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(true, message, probability, lazyLocators, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocatorList(String message, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(true, message, PROBABILITY, lazyLocators, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(boolean isFiltered, String message, double probability, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(isFiltered, message, probability, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(boolean isFiltered, String message, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(isFiltered, message, PROBABILITY, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(boolean isFiltered, String message, double probability, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocatorList(isFiltered, message, probability, new LazyLocatorList(lazyLocator), GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(boolean isFiltered, String message, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocatorList(isFiltered, message, PROBABILITY, new LazyLocatorList(lazyLocator), GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(String message, double probability, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(message, probability, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(String message, double probability, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocator(message, probability, lazyLocator, GETTER);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(String message, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocator(message, PROBABILITY, lazyLocator, getter);
    }

    static LazyFilteredElementParameters getWithFilterParametersAndLocator(String message, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocator(message, PROBABILITY, lazyLocator, GETTER);
    }
}