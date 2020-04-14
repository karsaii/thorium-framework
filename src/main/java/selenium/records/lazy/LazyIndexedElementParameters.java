package selenium.records.lazy;

import selenium.namespaces.extensions.boilers.WebElementList;
import org.openqa.selenium.WebElement;
import selenium.constants.LazyElementConstants;
import selenium.records.IndexedData;
import selenium.records.LazyLocatorList;

import java.util.Objects;

public class LazyIndexedElementParameters extends LazyElementParameters {
    public final IndexedData indexData;
    public final Class clazz;

    public LazyIndexedElementParameters(IndexedData data, double probability, LazyLocatorList lazyLocators, String getter) {
        super(probability, lazyLocators, getter);
        this.indexData = data;
        this.clazz = data.isIndexed ? WebElement.class : WebElementList.class;
    }

    public LazyIndexedElementParameters(IndexedData data, double probability, LazyLocatorList lazyLocators) {
        this(data, probability, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(IndexedData data, double probability, LazyLocator lazyLocator) {
        this(data, probability, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(IndexedData data, double probability, LazyLocator lazyLocator, String getter) {
        this(data, probability, new LazyLocatorList(lazyLocator), getter);
    }

    public LazyIndexedElementParameters(IndexedData data, LazyLocatorList lazyLocators) {
        this(data, 100.0, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(IndexedData data, LazyLocator lazyLocator) {
        this(data, 100.0, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(IndexedData data, LazyLocator lazyLocator, String getter) {
        this(data, 100.0, new LazyLocatorList(lazyLocator), getter);
    }

    public LazyIndexedElementParameters(boolean isIndexed, int index, double probability, LazyLocatorList lazyLocators, String getter) {
        this(new IndexedData(isIndexed, index), probability, lazyLocators, getter);
    }

    public LazyIndexedElementParameters(boolean isIndexed, int index, double probability, LazyLocatorList lazyLocators) {
        this(isIndexed, index, probability, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(boolean isIndexed, int index, double probability, LazyLocator lazyLocator) {
        this(isIndexed, index, probability, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(boolean isIndexed, int index, double probability, LazyLocator lazyLocator, String getter) {
        this(isIndexed, index, probability, new LazyLocatorList(lazyLocator), getter);
    }

    public LazyIndexedElementParameters(boolean isIndexed, int index, LazyLocatorList lazyLocators) {
        this(isIndexed, index, 100.0, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(boolean isIndexed, int index, LazyLocator lazyLocator) {
        this(isIndexed, index, 100.0, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(boolean isIndexed, int index, LazyLocator lazyLocator, String getter) {
        this(isIndexed, index, 100.0, new LazyLocatorList(lazyLocator), getter);
    }

    public LazyIndexedElementParameters(int index, double probability, LazyLocatorList lazyLocators, String getter) {
        this(new IndexedData(index), probability, lazyLocators, getter);
    }

    public LazyIndexedElementParameters(int index, double probability, LazyLocatorList lazyLocators) {
        this(index, probability, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(int index, double probability, LazyLocator lazyLocator) {
        this(index, probability, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(int index, double probability, LazyLocator lazyLocator, String getter) {
        this(index, probability, new LazyLocatorList(lazyLocator), getter);
    }

    public LazyIndexedElementParameters(int index, LazyLocatorList lazyLocators) {
        this(index, 100.0, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(int index, LazyLocator lazyLocator) {
        this(index, 100.0, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(int index, LazyLocator lazyLocator, String getter) {
        this(index, 100.0, new LazyLocatorList(lazyLocator), getter);
    }

    public LazyIndexedElementParameters(boolean isIndexed, double probability, LazyLocatorList lazyLocators, String getter) {
        this(new IndexedData(isIndexed), probability, lazyLocators, getter);
    }

    public LazyIndexedElementParameters(boolean isIndexed, double probability, LazyLocatorList lazyLocators) {
        this(isIndexed, probability, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(boolean isIndexed, double probability, LazyLocator lazyLocator) {
        this(isIndexed, probability, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(boolean isIndexed, double probability, LazyLocator lazyLocator, String getter) {
        this(isIndexed, probability, new LazyLocatorList(lazyLocator), getter);
    }

    public LazyIndexedElementParameters(boolean isIndexed, LazyLocatorList lazyLocators) {
        this(isIndexed, 100.0, lazyLocators, LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(boolean isIndexed, LazyLocator lazyLocator) {
        this(isIndexed, 100.0, new LazyLocatorList(lazyLocator), LazyElementConstants.DEFAULT_GETTER_ELEMENT);
    }

    public LazyIndexedElementParameters(boolean isIndexed, LazyLocator lazyLocator, String getter) {
        this(isIndexed, 100.0, new LazyLocatorList(lazyLocator), getter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        var that = (LazyIndexedElementParameters) o;
        return Objects.equals(indexData, that.indexData) &&
                Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), indexData, clazz);
    }

    @Override
    public String toString() {
        return "LazyIndexedElementParameters{" +
            "indexData=" + indexData +
            ", clazz=" + clazz +
            ", lazyLocators=" + lazyLocators +
            ", getter='" + getter + '\'' +
            ", probability=" + probability +
            '}';
    }
}
