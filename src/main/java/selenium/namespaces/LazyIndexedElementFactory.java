package selenium.namespaces;

import selenium.constants.LazyElementConstants;
import selenium.namespaces.extensions.boilers.LazyLocatorList;
import selenium.records.FilterData;
import selenium.records.lazy.LazyIndexedElementParameters;
import selenium.records.lazy.LazyLocator;

public interface LazyIndexedElementFactory {
    static LazyIndexedElementParameters getWithFilterDataAndLocatorList(FilterData<?> data, double probability, LazyLocatorList lazyLocators, String getter) {
        return new LazyIndexedElementParameters(data, probability, lazyLocators, getter);
    }

    static LazyIndexedElementParameters getWithFilterDataAndLocatorList(FilterData<?> data, double probability, LazyLocatorList lazyLocators) {
        return getWithFilterDataAndLocatorList(data, probability, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterDataAndLocatorList(FilterData<?> data, LazyLocatorList lazyLocators, String getter) {
        return getWithFilterDataAndLocatorList(data, 100.0, lazyLocators, getter);
    }

    static LazyIndexedElementParameters getWithFilterDataAndLocatorList(FilterData<?> data, LazyLocatorList lazyLocators) {
        return getWithFilterDataAndLocatorList(data, 100.0, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterDataAndLocator(FilterData<?> data, double probability, LazyLocator lazyLocator) {
        return getWithFilterDataAndLocatorList(data, probability, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterDataAndLocator(FilterData<?> data, double probability, LazyLocator lazyLocator, String getter) {
        return getWithFilterDataAndLocatorList(data, probability, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyIndexedElementParameters getWithFilterDataAndLocator(FilterData<?> data, LazyLocator lazyLocator, String getter) {
        return getWithFilterDataAndLocatorList(data, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyIndexedElementParameters getWithFilterDataAndLocator(FilterData<?> data, LazyLocator lazyLocator) {
        return getWithFilterDataAndLocatorList(data, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithIntegerFilterDataAndLocator(FilterData<Integer> data, LazyLocator lazyLocator, String getter) {
        return getWithFilterDataAndLocatorList(data, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(boolean isIndexed, int index, double probability, LazyLocatorList lazyLocators, String getter) {
        return getWithFilterDataAndLocatorList(new FilterData<>(isIndexed, ElementFilterFunctions::getIndexedElement, index), probability, lazyLocators, getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(boolean isIndexed, int index, double probability, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(isIndexed, index, probability, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(int index, double probability, LazyLocatorList lazyLocators, String getter) {
        return getWithFilterParametersAndLocatorList(true, index, probability, lazyLocators, getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(boolean isIndexed, double probability, LazyLocatorList lazyLocators, String getter) {
        return getWithFilterParametersAndLocatorList(isIndexed, 0, probability, lazyLocators, getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(boolean isIndexed, int index, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(isIndexed, index, 100.0, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(boolean isIndexed, double probability, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(isIndexed, 0, probability, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(int index, double probability, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(true, index, probability, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(boolean isIndexed, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(isIndexed, 0, 100.0, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(int index, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(true, index, 100.0, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(boolean isIndexed, int index, double probability, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(isIndexed, index, probability, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(boolean isIndexed, int index, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(isIndexed, index, 100.0, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyIndexedElementParameters getWithIntegerFilterParametersAndLocator(boolean isIndexed, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(isIndexed, 0, 100.0, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(boolean isIndexed, int index, double probability, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocatorList(isIndexed, index, probability, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(boolean isIndexed, int index, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocatorList(isIndexed, index, 100.0, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(int index, double probability, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(index, probability, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(boolean isIndexed, double probability, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(isIndexed, probability, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(int index, double probability, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocator(index, probability, lazyLocator, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(boolean isIndexed, double probability, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocator(isIndexed, 0, probability, lazyLocator, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(int index, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocator(index, 100.0, lazyLocator, getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(boolean isIndexed, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocator(isIndexed, 0, 100.0, lazyLocator, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(int index, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocator(index, 100.0, lazyLocator, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(boolean isFiltered, String message, double probability, LazyLocatorList lazyLocators, String getter) {
        return getWithFilterDataAndLocatorList(new FilterData<>(isFiltered, ElementFilterFunctions::getContainedTextElement, message), probability, lazyLocators, getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(boolean isFiltered, String message, double probability, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(isFiltered, message, probability, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(String message, double probability, LazyLocatorList lazyLocators, String getter) {
        return getWithFilterParametersAndLocatorList(true, message, probability, lazyLocators, getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(boolean isFiltered, String message, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(isFiltered, message, 100.0, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(String message, double probability, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(true, message, probability, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocatorList(String message, LazyLocatorList lazyLocators) {
        return getWithFilterParametersAndLocatorList(true, message, 100.0, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(boolean isFiltered, String message, double probability, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(isFiltered, message, probability, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(boolean isFiltered, String message, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(isFiltered, message, 100.0, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(boolean isFiltered, String message, double probability, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocatorList(isFiltered, message, probability, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(boolean isFiltered, String message, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocatorList(isFiltered, message, 100.0, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(String message, double probability, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocatorList(message, probability, new LazyLocatorList(lazyLocator), getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(String message, double probability, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocator(message, probability, lazyLocator, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(String message, LazyLocator lazyLocator, String getter) {
        return getWithFilterParametersAndLocator(message, 100.0, lazyLocator, getter);
    }

    static LazyIndexedElementParameters getWithFilterParametersAndLocator(String message, LazyLocator lazyLocator) {
        return getWithFilterParametersAndLocator(message, 100.0, lazyLocator, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    static LazyIndexedElementParameters getWithStringFilterDataAndLocator(FilterData<String> data, LazyLocator lazyLocator, String getter) {
        return getWithFilterDataAndLocatorList(data, new LazyLocatorList(lazyLocator), getter);
    }
}