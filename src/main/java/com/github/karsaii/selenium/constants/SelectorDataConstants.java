package com.github.karsaii.selenium.constants;

import com.github.karsaii.selenium.records.ExternalSelectorData;
import com.github.karsaii.selenium.records.InternalSelectorData;

public abstract class SelectorDataConstants {
    public static final InternalSelectorData INTERNAL_SELECTOR_DATA = new InternalSelectorData();
    public static final ExternalSelectorData EXTERNAL_SELECTOR_DATA = new ExternalSelectorData(null, null, null, null, -1, null);
}
