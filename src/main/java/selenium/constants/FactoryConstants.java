package selenium.constants;

import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import data.constants.Strings;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import selenium.compatibility.NullJavascriptExecutor;
import selenium.compatibility.NullSearchContext;
import selenium.compatibility.NullTakesScreenshot;
import selenium.compatibility.NullTargetLocator;

public abstract class FactoryConstants {
    public static final JavascriptExecutor NULL_JAVASCRIPT_EXECUTOR = new NullJavascriptExecutor();
    public static final TakesScreenshot NULL_TAKES_SCREENSHOT = new NullTakesScreenshot();
    public static final WebDriver.TargetLocator NULL_TARGET_LOCATOR = new NullTargetLocator();
    public static final SearchContext NULL_SEARCH_CONTEXT = new NullSearchContext();

    public static final Data<JavascriptExecutor> NULL_JAVASCRIPT_EXECUTOR_DATA = DataFactoryFunctions.getWithMessage(NULL_JAVASCRIPT_EXECUTOR, false, "Dependency for JavascriptExecutor" + Strings.WAS_NULL);
    public static final Data<TakesScreenshot> NULL_TAKES_SCREENSHOT_DATA = DataFactoryFunctions.getWithMessage(NULL_TAKES_SCREENSHOT, false, "Dependency for TakesScreenshot" + Strings.WAS_NULL);
    public static final Data<WebDriver.TargetLocator> NULL_TARGET_LOCATOR_DATA = DataFactoryFunctions.getWithMessage(NULL_TARGET_LOCATOR, false, "Dependency for TargetLocator" + Strings.WAS_NULL);
    public static final Data<SearchContext> NULL_SEARCH_CONTEXT_DATA = DataFactoryFunctions.getWithMessage(NULL_SEARCH_CONTEXT, false, "Dependency for SearchContext" + Strings.WAS_NULL);
}
