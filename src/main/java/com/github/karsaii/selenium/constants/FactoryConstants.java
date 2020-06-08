package com.github.karsaii.selenium.constants;

import com.github.karsaii.core.namespaces.DataFactoryFunctions;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.github.karsaii.selenium.compatibility.NullJavascriptExecutor;
import com.github.karsaii.selenium.compatibility.NullSearchContext;
import com.github.karsaii.selenium.compatibility.NullTakesScreenshot;
import com.github.karsaii.selenium.compatibility.NullTargetLocator;

public abstract class FactoryConstants {
    public static final JavascriptExecutor NULL_JAVASCRIPT_EXECUTOR = new NullJavascriptExecutor();
    public static final TakesScreenshot NULL_TAKES_SCREENSHOT = new NullTakesScreenshot();
    public static final WebDriver.TargetLocator NULL_TARGET_LOCATOR = new NullTargetLocator();
    public static final SearchContext NULL_SEARCH_CONTEXT = new NullSearchContext();

    public static final Data<JavascriptExecutor> NULL_JAVASCRIPT_EXECUTOR_DATA = DataFactoryFunctions.getWithMessage(NULL_JAVASCRIPT_EXECUTOR, false, "Dependency for JavascriptExecutor" + CoreFormatterConstants.WAS_NULL);
    public static final Data<TakesScreenshot> NULL_TAKES_SCREENSHOT_DATA = DataFactoryFunctions.getWithMessage(NULL_TAKES_SCREENSHOT, false, "Dependency for TakesScreenshot" + CoreFormatterConstants.WAS_NULL);
    public static final Data<WebDriver.TargetLocator> NULL_TARGET_LOCATOR_DATA = DataFactoryFunctions.getWithMessage(NULL_TARGET_LOCATOR, false, "Dependency for TargetLocator" + CoreFormatterConstants.WAS_NULL);
    public static final Data<SearchContext> NULL_SEARCH_CONTEXT_DATA = DataFactoryFunctions.getWithMessage(NULL_SEARCH_CONTEXT, false, "Dependency for SearchContext" + CoreFormatterConstants.WAS_NULL);
}
