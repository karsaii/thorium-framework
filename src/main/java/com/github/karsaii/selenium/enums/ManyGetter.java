package com.github.karsaii.selenium.enums;

import java.util.HashMap;
import java.util.Map;

public enum ManyGetter {
    GET_ELEMENTS("getElements"),
    GET_NESTED_ELEMENTS_FROM_LAST("getNestedElementsFromLast"),
    GET_FRAME_NESTED_ELEMENTS("getFrameNestedElements"),
    GET_SHADOW_NESTED_ELEMENTS("getShadowNestedElements");

    private static final Map<String, ManyGetter> VALUES = new HashMap<>();
    private final String name;

    static {
        for(ManyGetter getter : values()) {
            VALUES.putIfAbsent(getter.name, getter);
        }
    }

    ManyGetter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ManyGetter getValueOf(String name) {
        return VALUES.getOrDefault(name, ManyGetter.GET_ELEMENTS);
    }
}
