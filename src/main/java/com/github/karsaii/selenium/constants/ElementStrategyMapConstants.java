package com.github.karsaii.selenium.constants;

import org.openqa.selenium.By;
import com.github.karsaii.selenium.enums.SelectorStrategy;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.Map.entry;

public abstract class ElementStrategyMapConstants {
    public static final Map<SelectorStrategy, Function<String, By>> STRATEGY_MAP = Collections.unmodifiableMap(
        new EnumMap<>(
            Map.ofEntries(
                entry(SelectorStrategy.ID, By::id),
                entry(SelectorStrategy.CSS_SELECTOR, By::cssSelector),
                entry(SelectorStrategy.CLASS, By::className),
                entry(SelectorStrategy.XPATH, By::xpath),
                entry(SelectorStrategy.TAG_NAME, By::tagName),
                entry(SelectorStrategy.NAME, By::name),
                entry(SelectorStrategy.PARTIAL_LINK_TEXT, By::partialLinkText),
                entry(SelectorStrategy.LINK_TEXT, By::linkText)
            )
        )
    );

    public static final Set<SelectorStrategy> STRATEGY_MAP_KEY_SET = EnumSet.allOf(SelectorStrategy.class);
}
