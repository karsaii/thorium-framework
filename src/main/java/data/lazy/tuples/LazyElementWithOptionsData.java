package data.lazy.tuples;

import data.LazyElement;
import data.constants.Defaults;
import data.constants.GetOrder;
import data.extensions.DecoratedList;
import data.tuples.ExternalSelectorData;
import data.tuples.InternalSelectorData;
import data.tuples.ProbabilityData;

import java.util.Objects;

public class LazyElementWithOptionsData {
    public final LazyElement element;
    public final InternalSelectorData internalData;
    public final ExternalSelectorData externalData;
    public final DecoratedList<String> getOrder;
    public final ProbabilityData probabilityData;

    public LazyElementWithOptionsData(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData, DecoratedList<String> getOrder, ProbabilityData probabilityData) {
        this.element = element;
        this.internalData = internalData;
        this.externalData = externalData;
        this.getOrder = getOrder;
        this.probabilityData = probabilityData;
    }

    public LazyElementWithOptionsData(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData, DecoratedList<String> getOrder) {
        this(element, internalData, externalData, getOrder, Defaults.PROBABILITY_DATA);
    }

    public LazyElementWithOptionsData(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData, ProbabilityData probabilityData) {
        this(element, internalData, externalData, GetOrder.DEFAULT, probabilityData);
    }

    public LazyElementWithOptionsData(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData) {
        this(element, internalData, externalData, GetOrder.DEFAULT);
    }

    public LazyElementWithOptionsData(LazyElement element, ExternalSelectorData externalData) {
        this(element, Defaults.INTERNAL_SELECTOR_DATA, externalData, GetOrder.DEFAULT);
    }

    public LazyElementWithOptionsData(LazyElement element) {
        this(element, Defaults.INTERNAL_SELECTOR_DATA, null, GetOrder.DEFAULT);
    }

    public LazyElementWithOptionsData(LazyElement element, ExternalSelectorData externalData, DecoratedList<String> getOrder) {
        this(element, Defaults.INTERNAL_SELECTOR_DATA, externalData, getOrder, Defaults.PROBABILITY_DATA);
    }

    public LazyElementWithOptionsData(LazyElement element, ExternalSelectorData externalData, ProbabilityData probabilityData) {
        this(element, Defaults.INTERNAL_SELECTOR_DATA, externalData, GetOrder.DEFAULT, probabilityData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LazyElementWithOptionsData that = (LazyElementWithOptionsData) o;
        return Objects.equals(element, that.element) &&
            Objects.equals(internalData, that.internalData) &&
            Objects.equals(externalData, that.externalData) &&
            Objects.equals(getOrder, that.getOrder) &&
            Objects.equals(probabilityData, that.probabilityData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, internalData, externalData, getOrder, probabilityData);
    }
}
