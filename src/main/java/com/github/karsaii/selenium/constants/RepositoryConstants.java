package com.github.karsaii.selenium.constants;

import org.openqa.selenium.By;
import com.github.karsaii.selenium.records.lazy.CachedLazyElementData;

import java.util.HashMap;

public abstract class RepositoryConstants {
    public static final HashMap<String, CachedLazyElementData> elements = new HashMap<>();
    public static final HashMap<By, String> locatorElements = new HashMap<>();
    public static final HashMap<By, String> namedLocators = new HashMap<>();
}
