package selenium.constants;

import core.extensions.boilers.StringSet;
import core.extensions.boilers.WebElementList;
import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import core.records.MethodData;
import data.constants.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import selenium.enums.CoreConstants;
import selenium.records.ExternalElementData;
import selenium.records.LazyElement;
import selenium.records.lazy.CachedLazyElementData;

import java.lang.reflect.Method;

public abstract class DataConstants {
    public static final Data<By> NULL_BY = DataFactoryFunctions.getWithMessage(By.cssSelector(Strings.EMPTY), false, "Null By Data.");
    public static final Data<Boolean> NULL_BOOLEAN = DataFactoryFunctions.getBoolean(false, "nullBoolean data" + Strings.END_LINE);
    public static final Data<String> NULL_STRING = DataFactoryFunctions.getWithMessage(Strings.EMPTY, false, "nullString data.");
    public static final Data<Object> NULL_OBJECT = DataFactoryFunctions.getWithMessage(CoreConstants.STOCK_OBJECT, false, "null object data.");
    public static final Data<Method> NULL_METHOD = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.STOCK_WEBELEMENT_METHOD, false, "null method data.");
    public static final Data<MethodData> NULL_METHODDATA = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.STOCK_WEBELEMENT_METHOD_DATA, false, "Null methodData data.");
    public static final Data<Void> NULL_VOID = DataFactoryFunctions.getWithMessage(null, false, "Void data" + Strings.END_LINE);
    public static final Data<Object[]> NULL_PARAMETER_ARRAY = DataFactoryFunctions.getWithMessage(new Object[0], false, "Null Parameter Array.");
    public static final Data<WebElement> NULL_ELEMENT = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, "nullElement data.");
    public static final Data<WebElement> NULL_ELEMENT_NULL_DRIVER = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, Strings.DRIVER_WAS_NULL);
    public static final Data<Integer> NULL_INTEGER = DataFactoryFunctions.getWithMessage(0, false, "nullInteger data.");
    public static final Data<Integer> NULL_INTEGER_NULL_DRIVER = DataFactoryFunctions.getWithMessage(0, false, Strings.DRIVER_WAS_NULL);
    public static final Data<Integer> NO_ELEMENTS_FOUND_DATA_FALSE_OR_NULL = DataFactoryFunctions.getWithMessage(0, false, "No elements were found, data was false or null" + Strings.END_LINE);
    public static final Data<Integer> NO_ELEMENTS_FOUND = DataFactoryFunctions.getWithMessage(0, false, Strings.ELEMENT_LIST_EMPTY_OR_NULL);
    public static final Data<String> GET_FORMATTED_ELEMENT_VALUE_ERROR = DataFactoryFunctions.getWithMessage("", false, Strings.GET_FORMATTED_ELEMENT_VALUE_ERROR);
    public static final Data<Boolean> DATA_PARAMETER_WAS_NULL = DataFactoryFunctions.getWithMessage(false, false, "Data parameter " + Strings.WAS_NULL);
    public static final Data<Boolean> PARAMETERS_NULL_BOOLEAN = DataFactoryFunctions.getWithMessage(false, false, "Parameters " + Strings.WAS_NULL);
    public static final Data<String> PARAMETERS_NULL_STRING = DataFactoryFunctions.getWithMessage(Strings.EMPTY, false, "Parameters " + Strings.WAS_NULL);
    public static final Data<Object> WONT_EXECUTE = DataFactoryFunctions.getWithMessage(CoreConstants.STOCK_OBJECT, false, "Won't execute blank, empty or null script" + Strings.END_LINE);
    public static final Data<Object> NULL_EXECUTE_OBJECT = DataFactoryFunctions.getWithMessage(CoreConstants.STOCK_OBJECT, false, "List was empty, or null" + Strings.END_LINE);
    public static final Data<WebElementList> NULL_LIST = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.NULL_ELEMENT_LIST, false, "nullList data" + Strings.END_LINE);
    public static final Data<WebElementList> DRIVER_WAS_NULL_LIST = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.NULL_ELEMENT_LIST, false, Strings.DRIVER_WAS_NULL);
    public static final Data<WebElementList> LOCATOR_WAS_NULL_LIST = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.NULL_ELEMENT_LIST, false, Strings.LOCATOR_WAS_NULL);
    public static final Data<StringSet> NULL_STRING_SET = DataFactoryFunctions.getWithMessage(CoreConstants.NULL_STRING_SET, false, "Null String Set data" + Strings.END_LINE);
    public static final Data<LazyElement> NULL_LAZY_ELEMENT = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.NULL_LAZY_ELEMENT, false, "");
    public static final Data<CachedLazyElementData> NULL_CACHED_LAZY_ELEMENT = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.NULL_CACHED_LAZY_ELEMENT_DATA, false, "");
    public static final Data<CachedLazyElementData> ELEMENT_WAS_NOT_CACHED = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.NULL_CACHED_LAZY_ELEMENT_DATA, false, "getIfContains", "Element wasn't cached" + Strings.END_LINE);
    ;
    public static final Data<ExternalElementData> NULL_EXTERNAL_ELEMENT = DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.NULL_EXTERNAL_ELEMENT_DATA, false, "");
    public static final Data<Boolean> DATA_WAS_NULL_OR_FALSE_BOOLEAN = DataFactoryFunctions.getBoolean(false, Strings.DATA_NULL_OR_FALSE);
    public static final Data<String> DATA_WAS_NULL_OR_FALSE_STRING = DataFactoryFunctions.getString(Strings.EMPTY, Strings.DATA_NULL_OR_FALSE);
    public static final Data<Boolean> DRIVER_WAS_NULL = DataFactoryFunctions.getBoolean(false, Strings.DRIVER_WAS_NULL);
    public static final Data<Boolean> NO_STEPS = DataFactoryFunctions.getBoolean(true, "No Steps were provided" + Strings.END_LINE);
    public static final Data<Boolean> LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL = DataFactoryFunctions.getBoolean(false, Strings.LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL);
    public static final Data<Boolean> LAZY_ELEMENT_WAS_NULL = DataFactoryFunctions.getBoolean(false, Strings.LAZY_ELEMENT_WAS_NULL);

    public static final Data<WebDriver> NULL_DRIVER = DataFactoryFunctions.getWithMessage(null, false, "No Drivers.Driver instance found.");
}
