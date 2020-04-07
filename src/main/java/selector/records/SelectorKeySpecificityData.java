package selector.records;

import selectorSpecificity.tuples.SpecificityData;

import java.util.Objects;

public class SelectorKeySpecificityData {
    public final String selectorKey;
    public final SpecificityData specifics;

    public SelectorKeySpecificityData(String selectorKey, SpecificityData specifics) {
        this.selectorKey = selectorKey;
        this.specifics = specifics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (SelectorKeySpecificityData) o;
        return Objects.equals(selectorKey, that.selectorKey) && Objects.equals(specifics, that.specifics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectorKey, specifics);
    }

    @Override
    public String toString() {
        return "SelectorKeySpecificityData{selectorKey='" + selectorKey + "\', specifics=" + specifics + '}';
    }
}
