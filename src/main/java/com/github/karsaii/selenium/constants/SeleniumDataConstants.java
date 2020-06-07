package com.github.karsaii.selenium.constants;

import com.github.karsaii.core.constants.CoreDataConstants;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import com.github.karsaii.selenium.namespaces.extensions.boilers.WebElementList;
import com.github.karsaii.core.namespaces.DataFactoryFunctions;
import com.github.karsaii.core.records.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.github.karsaii.selenium.records.ExternalElementData;
import com.github.karsaii.selenium.records.lazy.LazyElement;
import com.github.karsaii.selenium.records.lazy.CachedLazyElementData;

import java.util.function.Function;

public abstract class SeleniumDataConstants {
    public static final Data<By> NULL_BY = DataFactoryFunctions.getWithNameAndMessage(By.cssSelector(CoreFormatterConstants.EMPTY), false, "nullBy", "Null By Data.");
    public static final Data<WebElement> NULL_ELEMENT = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, "defaultNullWebElementData", "Internal null element" + CoreFormatterConstants.END_LINE);
    public static final Data<WebElement> NULL_ELEMENT_NULL_DRIVER = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, "nullElementNullDriver", SeleniumFormatterConstants.DRIVER_WAS_NULL);
    public static final Data<WebElementList> NULL_LIST = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.NULL_ELEMENT_LIST, false, "nullList", "nullList data" + CoreFormatterConstants.END_LINE);
    public static final Data<WebElementList> DRIVER_WAS_NULL_LIST = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.NULL_ELEMENT_LIST, false, "driverWasNullList", SeleniumFormatterConstants.DRIVER_WAS_NULL);
    public static final Data<WebElementList> LOCATOR_WAS_NULL_LIST = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.NULL_ELEMENT_LIST, false, "locatorWasNullList", SeleniumFormatterConstants.LOCATOR_WAS_NULL);
    public static final Data<LazyElement> NULL_LAZY_ELEMENT = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.NULL_LAZY_ELEMENT, false, "nullLazyElement", "nullLazyElement data" + CoreFormatterConstants.END_LINE);
    public static final Data<CachedLazyElementData> NULL_CACHED_LAZY_ELEMENT = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.NULL_CACHED_LAZY_ELEMENT_DATA, false, "nullCachedLazyElement", "nullCachedLazyElement data" + CoreFormatterConstants.END_LINE);
    public static final Data<CachedLazyElementData> ELEMENT_WAS_NOT_CACHED = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.NULL_CACHED_LAZY_ELEMENT_DATA, false, "getIfContains", "Element wasn't cached" + CoreFormatterConstants.END_LINE);
    public static final Data<ExternalElementData> NULL_EXTERNAL_ELEMENT = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.NULL_EXTERNAL_ELEMENT_DATA, false, "nullExternalElement", "nullExternalElement data" + CoreFormatterConstants.END_LINE);
    public static final Data<Boolean> LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL = DataFactoryFunctions.getBoolean(false, SeleniumFormatterConstants.LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL);
    public static final Data<Boolean> LAZY_ELEMENT_WAS_NULL = DataFactoryFunctions.getBoolean(false, SeleniumFormatterConstants.LAZY_ELEMENT_WAS_NULL);

    public static final Data<WebDriver> NULL_DRIVER = DataFactoryFunctions.getWithNameAndMessage(null, false, "nullDriver", "No Drivers.Driver instance found" + CoreFormatterConstants.END_LINE);

    public static final Function<WebDriver.TargetLocator, Data<Boolean>> SWITCH_TO_NEGATIVE = targetLocator -> CoreDataConstants.NULL_BOOLEAN;
    public static final Data<Boolean> DRIVER_WAS_NULL = DataFactoryFunctions.getInvalidBooleanWithNameAndMessage("driverWasNull", SeleniumFormatterConstants.DRIVER_WAS_NULL);
    public static final Data<Integer> NULL_INTEGER_NULL_DRIVER = DataFactoryFunctions.getWithNameAndMessage(0, false, "nullIntegerNullDriver", SeleniumFormatterConstants.DRIVER_WAS_NULL);
    public static final Data<Integer> NO_ELEMENTS_FOUND = DataFactoryFunctions.getWithNameAndMessage(0, false, "noElementsFound", SeleniumFormatterConstants.ELEMENT_LIST_EMPTY_OR_NULL);
}
