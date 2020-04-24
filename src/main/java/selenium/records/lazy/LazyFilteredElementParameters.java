package selenium.records.lazy;

import selenium.namespaces.extensions.boilers.WebElementList;
import org.openqa.selenium.WebElement;
import selenium.records.FilterData;
import selenium.namespaces.extensions.boilers.LazyLocatorList;

import java.util.Objects;

public class LazyFilteredElementParameters extends LazyElementParameters {
    public final FilterData<?> filterData;
    public final Class clazz;

    public LazyFilteredElementParameters(FilterData<?> data, double probability, LazyLocatorList lazyLocators, String getter) {
        super(probability, lazyLocators, getter);
        this.filterData = data;
        this.clazz = data.isFiltered ? WebElement.class : WebElementList.class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        var that = (LazyFilteredElementParameters) o;
        return Objects.equals(filterData, that.filterData) && Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), filterData, clazz);
    }

    @Override
    public String toString() {
        return "LazyIndexedElementParameters{" +
            "indexData=" + filterData +
            ", clazz=" + clazz +
            ", lazyLocators=" + lazyLocators +
            ", getter='" + getter + '\'' +
            ", probability=" + probability +
            '}';
    }
}
