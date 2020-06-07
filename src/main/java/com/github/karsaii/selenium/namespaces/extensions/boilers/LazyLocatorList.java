package com.github.karsaii.selenium.namespaces.extensions.boilers;

import com.github.karsaii.core.extensions.DecoratedList;
import com.github.karsaii.selenium.records.lazy.LazyLocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LazyLocatorList extends DecoratedList<LazyLocator> {
    public LazyLocatorList() {
        super(new ArrayList<>(), LazyLocator.class);
    }

    public LazyLocatorList(List<LazyLocator> list) {
        super(list, LazyLocator.class);
    }

    public LazyLocatorList(LazyLocator element) {
        super(Collections.singletonList(element), LazyLocator.class);
    }

    public LazyLocatorList subList(int fromIndex, int toIndex) {
        return subList(LazyLocatorList.class, fromIndex, toIndex);
    }

    public LazyLocatorList tail() {
        return tail(LazyLocatorList.class);
    }

    public LazyLocatorList initials() {
        return initials(LazyLocatorList.class);
    }
}
