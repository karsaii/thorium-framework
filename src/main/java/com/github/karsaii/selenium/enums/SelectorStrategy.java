package com.github.karsaii.selenium.enums;

import java.util.HashMap;
import java.util.Map;

public enum SelectorStrategy {
    ID("id"),
    CSS_SELECTOR("cssSelector"),
    CLASS("class"),
    XPATH("xpath"),
    TAG_NAME("tagName"),
    NAME("name"),
    PARTIAL_LINK_TEXT("partialLinkText"),
    LINK_TEXT("linkText"),
    NONE("none"),
    DEFAULT("id");

    private static final Map<String, SelectorStrategy> VALUES = new HashMap<>();
    private String name;

    SelectorStrategy(String name) {
        this.name = name;
    }

    static {
        for(var getter : values()) {
            VALUES.putIfAbsent(getter.name, getter);
        }
    }
    public String getName() {
        return name;
    }

    public static SelectorStrategy getValueOf(String name) {
        return VALUES.getOrDefault(name, SelectorStrategy.DEFAULT);
    }

}
