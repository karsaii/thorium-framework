package selenium.records.lazy;

import core.extensions.DecoratedList;
import selector.records.SelectorKeySpecificityData;
import selenium.records.LazyElement;

import java.util.Map;
import java.util.Objects;

public class CachedLazyElementData {
    public final LazyElement element;
    public final Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys;


    public CachedLazyElementData(LazyElement element, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys) {
        this.element = element;
        this.typeKeys = typeKeys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (CachedLazyElementData) o;
        return Objects.equals(element, that.element) && Objects.equals(typeKeys, that.typeKeys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, typeKeys);
    }

    @Override
    public String toString() {
        return "CachedLazyElementData{" +
                "selenium.element=" + element +
                ", typeKeys=" + typeKeys +
                '}';
    }
}
