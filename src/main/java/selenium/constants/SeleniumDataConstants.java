package selenium.constants;

import core.constants.CoreDataConstants;
import selenium.namespaces.extensions.boilers.WebElementList;
import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import data.constants.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.records.ExternalElementData;
import selenium.records.lazy.LazyElement;
import selenium.records.lazy.CachedLazyElementData;

import java.util.function.Function;

public abstract class SeleniumDataConstants {
    public static final Data<By> NULL_BY = DataFactoryFunctions.getWithMessage(By.cssSelector(Strings.EMPTY), false, "Null By Data.");
    public static final Data<WebElement> NULL_ELEMENT = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, "defaultNullWebElementData", "Internal null element" + Strings.END_LINE);
    public static final Data<WebElement> NULL_ELEMENT_NULL_DRIVER = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, Strings.DRIVER_WAS_NULL);
    public static final Data<WebElementList> NULL_LIST = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.NULL_ELEMENT_LIST, false, "nullList data" + Strings.END_LINE);
    public static final Data<WebElementList> DRIVER_WAS_NULL_LIST = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.NULL_ELEMENT_LIST, false, Strings.DRIVER_WAS_NULL);
    public static final Data<WebElementList> LOCATOR_WAS_NULL_LIST = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.NULL_ELEMENT_LIST, false, Strings.LOCATOR_WAS_NULL);
    public static final Data<LazyElement> NULL_LAZY_ELEMENT = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.NULL_LAZY_ELEMENT, false, "");
    public static final Data<CachedLazyElementData> NULL_CACHED_LAZY_ELEMENT = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.NULL_CACHED_LAZY_ELEMENT_DATA, false, "");
    public static final Data<CachedLazyElementData> ELEMENT_WAS_NOT_CACHED = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.NULL_CACHED_LAZY_ELEMENT_DATA, false, "getIfContains", "Element wasn't cached" + Strings.END_LINE);
    public static final Data<ExternalElementData> NULL_EXTERNAL_ELEMENT = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.NULL_EXTERNAL_ELEMENT_DATA, false, "");
    public static final Data<Boolean> LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL = DataFactoryFunctions.getBoolean(false, Strings.LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL);
    public static final Data<Boolean> LAZY_ELEMENT_WAS_NULL = DataFactoryFunctions.getBoolean(false, Strings.LAZY_ELEMENT_WAS_NULL);

    public static final Data<WebDriver> NULL_DRIVER = DataFactoryFunctions.getWithMessage(null, false, "No Drivers.Driver instance found" + Strings.END_LINE);

    public static final Function<WebDriver.TargetLocator, Data<Boolean>> SWITCH_TO_NEGATIVE = targetLocator -> CoreDataConstants.NULL_BOOLEAN;
}
