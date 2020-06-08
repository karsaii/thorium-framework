package com.github.karsaii.selenium.records.lazy;

import com.github.karsaii.core.extensions.DecoratedList;
import com.github.karsaii.selenium.constants.AdjusterConstants;
import com.github.karsaii.selenium.constants.GetOrderConstants;
import com.github.karsaii.selenium.constants.SelectorDataConstants;
import com.github.karsaii.selenium.records.ExternalSelectorData;
import com.github.karsaii.selenium.records.InternalSelectorData;
import com.github.karsaii.selenium.records.ProbabilityData;

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
        this(element, internalData, externalData, getOrder, AdjusterConstants.PROBABILITY_DATA);
    }

    public LazyElementWithOptionsData(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData, ProbabilityData probabilityData) {
        this(element, internalData, externalData, GetOrderConstants.DEFAULT, probabilityData);
    }

    public LazyElementWithOptionsData(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData) {
        this(element, internalData, externalData, GetOrderConstants.DEFAULT);
    }

    public LazyElementWithOptionsData(LazyElement element, ExternalSelectorData externalData) {
        this(element, SelectorDataConstants.INTERNAL_SELECTOR_DATA, externalData, GetOrderConstants.DEFAULT);
    }

    public LazyElementWithOptionsData(LazyElement element) {
        this(element, SelectorDataConstants.INTERNAL_SELECTOR_DATA, null, GetOrderConstants.DEFAULT);
    }

    public LazyElementWithOptionsData(LazyElement element, ExternalSelectorData externalData, DecoratedList<String> getOrder) {
        this(element, SelectorDataConstants.INTERNAL_SELECTOR_DATA, externalData, getOrder, AdjusterConstants.PROBABILITY_DATA);
    }

    public LazyElementWithOptionsData(LazyElement element, ExternalSelectorData externalData, ProbabilityData probabilityData) {
        this(element, SelectorDataConstants.INTERNAL_SELECTOR_DATA, externalData, GetOrderConstants.DEFAULT, probabilityData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (LazyElementWithOptionsData) o;
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
