package data.constants;

import data.Data;
import data.LazyElement;
import data.DriverFunction;
import data.WebElementList;
import data.StringSet;
import data.lazy.tuples.CachedLazyElementData;
import data.tuples.ExternalElementData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.lang.reflect.Method;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DataDefaults {
    private static RemoteWebElement getStockElement() {
        final var element = new RemoteWebElement();
        element.setId(Strings.NULL_ELEMENT_ID);
        return element;
    }

    public static <T> DriverFunction<T> getErrorFunction(String message, T value) {
        return new DriverFunction<T>(driver -> new Data<T>(value, false, Strings.DRIVER_WAS_NULL + message));
    }

    public static <T> Data<T> getErrorFunctionData(String message, T value) {
        return new Data<>(value, false, message);
    }

    public static <T> Data<T> getErrorFunctionData(String message, T value, String nameof) {
        return new Data<>(value, false, nameof, message);
    }

    public static Data<String> getSimpleStringData(String object, String message) {
        return new Data<>(object, isNotBlank(object), message);
    }

    public static Data<String> getSimpleStringData(String object, String nameof, String message) {
        return new Data<>(object, isNotBlank(object), nameof, message);
    }

    public static Data<Boolean> getSimpleBooleanData(boolean status, String message) {
        return new Data<>(status, status, message);
    }

    public static Data<Boolean> getSimpleBooleanData(boolean status, String nameof, String message) {
        return new Data<>(status, status, nameof, message);
    }

    public static Data<String> getStringData(String object, String message, Exception ex, String exceptionMessage) {
        return new Data<>(object, isNotBlank(object), message, ex, exceptionMessage);
    }

    public static Data<Boolean> getBooleanData(boolean status, String message, Exception ex, String exceptionMessage) {
        return new Data<>(status, status, message, ex, exceptionMessage);
    }

    public static final Object STOCK_OBJECT = new Object();
    public static final Method STOCK_METHOD = WebElement.class.getDeclaredMethods()[0];

    public static final WebElement STOCK_ELEMENT = getStockElement();
    public static final Data<Object[]> NULL_PARAMETER_ARRAY_DATA = new Data<>(new Object[0], false, "Null Parameter Array.");
    public static final Data<WebElement> NULL_ELEMENT_DATA = new Data<>(STOCK_ELEMENT, false, "nullElement data.");
    public static final Data<WebElement> NULL_ELEMENT_NULL_DRIVER_DATA = new Data<>(STOCK_ELEMENT, false, Strings.DRIVER_WAS_NULL);
    public static final WebElementList NULL_ELEMENT_LIST = new WebElementList(new ArrayList<>());
    public static final Data<Integer> NULL_INTEGER_DATA = new Data<>(0, false, "nullInteger data.");
    public static final Data<Integer> NULL_INTEGER_NULL_DRIVER_DATA = new Data<>(0, false, Strings.DRIVER_WAS_NULL);
    public static final Data<Integer> NO_ELEMENTS_FOUND_DATA_FALSE_OR_NULL = new Data<>(0, false, "No elements were found, data was false or null" + Strings.END_LINE);
    public static final Data<Integer> NO_ELEMENTS_FOUND_DATA = new Data<>(0, false, Strings.ELEMENT_LIST_EMPTY_OR_NULL);
    public static final Data<By> NULL_BY_DATA = new Data<>(By.cssSelector(Strings.EMPTY), false, "Null By Data.");
    public static final Data<String> NULL_STRING_DATA = new Data<>(Strings.EMPTY, false, "nullString data.");
    public static final Data<Boolean> DATA_WAS_NULL_OR_FALSE_BOOLEAN_DATA = getSimpleBooleanData(false, Strings.DATA_NULL_OR_FALSE);
    public static final Data<String> DATA_WAS_NULL_OR_FALSE_STRING_DATA = getSimpleStringData(Strings.EMPTY, Strings.DATA_NULL_OR_FALSE);

    public static final Data<Boolean> DRIVER_WAS_NULL_DATA = getSimpleBooleanData(false, Strings.DRIVER_WAS_NULL);
    public static final Data<Object> NULL_OBJECT_DATA = new Data<>(STOCK_OBJECT, false, "null object data.");
    public static final Data<String> GET_FORMATTED_ELEMENT_VALUE_ERROR_DATA = new Data<>("", false, Strings.GET_FORMATTED_ELEMENT_VALUE_ERROR);
    public static final Data<Object> WONT_EXECUTE_DATA = new Data<>(STOCK_OBJECT, false, "Won't execute blank, empty or null script" + Strings.END_LINE);
    public static final Data<Method> NULL_METHOD_DATA = new Data<>(STOCK_METHOD, false, "null method data.");
    public static final Data<Object> NULL_EXECUTE_OBJECT_DATA = new Data<>(STOCK_OBJECT, false, "List was empty, or null" + Strings.END_LINE);
    public static final Data<Boolean> NULL_BOOLEAN_DATA = getSimpleBooleanData(false, "nullBoolean data" + Strings.END_LINE);
    public static final Data<Boolean> NO_STEPS_DATA = getSimpleBooleanData(true, "No Steps were provided" + Strings.END_LINE);
    public static final Data<Boolean> LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL_DATA = getSimpleBooleanData(false, Strings.LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL);
    public static final Data<Boolean> LAZY_ELEMENT_WAS_NULL_DATA = getSimpleBooleanData(false, Strings.LAZY_ELEMENT_WAS_NULL);
    public static final Data<WebElementList> NULL_LIST_DATA = new Data<>(NULL_ELEMENT_LIST, false, "nullList data" + Strings.END_LINE);
    public static final Data<WebElementList> DRIVER_WAS_NULL_LIST_DATA = new Data<>(NULL_ELEMENT_LIST, false, Strings.DRIVER_WAS_NULL);
    public static final StringSet NULL_STRING_SET = new StringSet(new HashSet<>());
    public static final Data<StringSet> NULL_STRING_SET_DATA = new Data<>(NULL_STRING_SET, false, "Null String Set data" + Strings.END_LINE);

    public static final List<Method> WEB_ELEMENT_METHOD_LIST = Arrays.asList(WebElement.class.getMethods());
    public static final Exception NULL_EXCEPTION = new Exception(Strings.NULL_EXCEPTION_MESSAGE);

    public static final DriverFunction<Boolean> NULL_BOOLEAN_DF = new DriverFunction<Boolean>(driver -> DataDefaults.NULL_BOOLEAN_DATA);
    public static final DriverFunction<Integer> NULL_INTEGER_DF = new DriverFunction<Integer>(driver -> DataDefaults.NULL_INTEGER_DATA);
    public static final DriverFunction<StringSet> NULL_STRINGSET_DF = new DriverFunction<StringSet>(driver -> DataDefaults.NULL_STRING_SET_DATA);
    public static final DriverFunction<Boolean> LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL_DF = new DriverFunction<Boolean>(LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL_DATA);
    public static final DriverFunction<Boolean> LAZY_ELEMENT_WAS_NULL_DF = new DriverFunction<Boolean>(LAZY_ELEMENT_WAS_NULL_DATA);
    public static final DriverFunction<WebElementList> NULL_LIST_DF = new DriverFunction<WebElementList>(driver -> DataDefaults.NULL_LIST_DATA);

    public static final Data<LazyElement> NULL_LAZY_ELEMENT_DATA = new Data<LazyElement>(Defaults.NULL_LAZY_ELEMENT, false, "");
    public static final Data<CachedLazyElementData> NULL_CACHED_LAZY_ELEMENT_DATA = new Data<>(Defaults.NULL_CACHED_LAZY_ELEMENT_DATA, false, "");
    public static final Data<ExternalElementData> NULL_EXTERNAL_ELEMENT_DATA = new Data<>(Defaults.NULL_EXTERNAL_ELEMENT_DATA, false, "");
}
