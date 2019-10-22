package javascriptCommands;

import data.constants.Strings;
import data.Data;
import data.enums.SingleGetter;
import data.LazyElement;
import data.functions.Executor;
import data.constants.DataDefaults;

import data.DriverFunction;
import drivers.Driver;
import formatter.Formatter;
import javascriptCommands.scripts.*;
import utilities.utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Objects;

import static data.ExecutionCore.ifDriver;
import static data.ExecutionCore.ifDriverFunction;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static utilities.utils.*;

public interface Execute {
    static <T> DriverFunction<T> isCommonExists(String nameof, String isExists, Data<T> defaultValue) {
        return ifDriver(
            nameof,
            isNotBlank(isExists) && isNotNull(defaultValue),
            new DriverFunction<T> (driver -> {
                final var result = Driver.executeData(isExists).apply(Driver.getExecutor(driver));
                return new Data<T>((T)result.object, result.status, result.message);
            }),
            defaultValue
        );
    }

    static DriverFunction<Boolean> setCommon(String nameof, DriverFunction<Boolean> precondition, String function, Data<Boolean> defaultValue) {
        return ifDriver(
            nameof,
            areNotNull(precondition, defaultValue) && isNotBlank(function),
            new DriverFunction<Boolean> (driver -> {
                Data<?> result = precondition.apply(driver);
                if (!result.status) {
                    return defaultValue;
                }

                if (isFalse(result.object)) {
                    result = Driver.executeData(function).apply(Driver.getExecutor(driver));
                }

                final var status = isNotNullOrFalseDataOrDataObject(result);
                return DataDefaults.getSimpleBooleanData(status, Formatter.getExecuteFragment(status) + " Scroll into view: " + result.message.toString() + Strings.END_LINE);
            }),
            defaultValue
        );
    }

    static <T> DriverFunction<T> commonExecutor(String nameof, LazyElement data, String function, Data<T> defaultValue) {
        return ifDriver(nameof, isNotNull(data), commonExecutor(nameof, data.get(), function, defaultValue), defaultValue);
    }

    static <T> DriverFunction<T> commonExecutor(String nameof, DriverFunction<WebElement> getter, String function, Data<T> defaultValue) {
        return ifDriver(
            nameof,
            isNotNull(getter),
            new DriverFunction<T> (driver -> {
                final var result = Driver.executeSingleParameter(function, getter.apply(driver)).apply(driver);
                return new Data<T>((T)result.object, result.status, result.message);
            }),
            defaultValue
        );
    }

    static DriverFunction<Object[]> getParameterArray(String item) {
        final var nameof = "getParameterArray";
        return ifDriver(
            nameof,
            isNotNull(item),
            new DriverFunction<Object[]> (driver -> {
                final var length = 1;
                final var parameters = new Object[1];
                parameters[0] = item;

                return new Data<Object[]>(parameters, true, nameof, "Array of length " + length + " was constructed" + Strings.END_LINE);
            }),
            DataDefaults.NULL_PARAMETER_ARRAY_DATA
        );
    }

    static DriverFunction<Object[]> getParameterArray(String first, String second) {
        final var nameof = "getParameterArray";
        return ifDriver(
            nameof,
            areNotNull(first, second),
            new DriverFunction<Object[]> (driver -> {
                final var length = 2;
                final var parameters = new Object[length];
                parameters[0] = first;
                parameters[1] = second;

                return new Data<Object[]>(parameters, true, nameof, "Array of length " + length + " was constructed" + Strings.END_LINE);
            }),
            DataDefaults.NULL_PARAMETER_ARRAY_DATA
        );
    }


    static DriverFunction<Object[]> getParameterArray(Data<WebElement> element, String attribute, String value) {
        final var nameof = "getParameterArray";
        return ifDriver(
            nameof,
            isNotNullOrFalseDataOrDataObject(element) && areNotNull(attribute, value),
            new DriverFunction<Object[]> (driver -> {
                final var length = 3;
                final var parameters = new Object[length];
                parameters[0] = element.object;
                parameters[1] = attribute;
                parameters[2] = value;

                return new Data<Object[]>(parameters, true, nameof, "Element was found. Array of length " + length + " was constructed" + Strings.END_LINE);
            }),
            DataDefaults.NULL_PARAMETER_ARRAY_DATA
        );
    }

    static DriverFunction<Object[]> getParameterArray(Data<WebElement> element) {
        final var nameof = "getParameterArray";
        return ifDriver(
            nameof,
            isNotNullOrFalseDataOrDataObject(element),
            new DriverFunction<Object[]> (driver -> {
                final var length = 1;
                final var parameters = new Object[length];
                parameters[0] = element.object;
                return new Data<Object[]>(parameters, true, nameof, "Element was found. Array of length " + length + " was constructed" + Strings.END_LINE);
            }),
            DataDefaults.NULL_PARAMETER_ARRAY_DATA
        );
    }

    static DriverFunction<Object[]> getParameterArray(LazyElement element, String attribute, String value) {
        final var nameof = "getParameterArray: ";
        return ifDriver(
                nameof,
                areNotNull(attribute, value) && isNotNullLazyElement(element),
                new DriverFunction<Object[]>(driver -> getParameterArray(element.get().apply(driver), attribute, value).apply(driver)),
                DataDefaults.NULL_PARAMETER_ARRAY_DATA
        );
    }

    static DriverFunction<Boolean> isScrollIntoViewExistsData() {
        return ifDriver(
            "isScrollIntoViewExistsData",
            new DriverFunction<Boolean> (driver -> {
                final var result = Driver.executeData(ScrollIntoView.IS_EXISTS).apply(Driver.getExecutor(driver));
                return new Data<Boolean>((Boolean)result.object, result.status, result.message);
            }),
            DataDefaults.NULL_BOOLEAN_DATA
        );
    }

    static DriverFunction<Boolean> scrollIntoViewExecutor(LazyElement data) {
        return isNotNull(data) ? scrollIntoViewExecutor(data.get()) : new DriverFunction<Boolean>(DataDefaults.NULL_BOOLEAN_DATA);
    }

    static DriverFunction<Boolean> scrollIntoViewExecutor(DriverFunction<WebElement> getter) {
        return ifDriver(
            "scrollIntoViewExecutor",
            isNotNull(getter),
            new DriverFunction<Boolean> (driver -> {
                final var result = Driver.executeSingleParameter(ScrollIntoView.EXECUTE, getter.apply(driver)).apply(driver);
                return new Data<Boolean>(isNotNull(result.object), result.status, result.message);
            }),
            DataDefaults.NULL_BOOLEAN_DATA
        );
    }

    static DriverFunction<Boolean> setScrollIntoView() {
        return new DriverFunction<Boolean> (driver -> {
            if (isNull(driver)) {
                return DataDefaults.NULL_BOOLEAN_DATA;
            }
            Data<?> result = isScrollIntoViewExistsData().apply(driver);
            if (!result.status) {
                return DataDefaults.NULL_BOOLEAN_DATA;
            }

            if (isFalse(result.object)) {
                result = Driver.executeData(ScrollIntoView.SET_FUNCTIONS).apply(Driver.getExecutor(driver));
            }

            final var status = isNotNullOrFalseDataOrDataObject(result);
            return DataDefaults.getSimpleBooleanData(status, Formatter.getScrollIntoViewMessage(result.message.getMessage(), status));
        });
    }

    static DriverFunction<Boolean> scrollIntoView(LazyElement data) {
        return Executor.execute(
            Driver.isElementHidden(data),
            setScrollIntoView(),
            scrollIntoViewExecutor(data)
        );
    }

    static DriverFunction<Boolean> scrollIntoView(By locator, SingleGetter getter) {
        return scrollIntoView(new LazyElement(locator, getter));
    }

    static DriverFunction<Boolean> scrollIntoView(By locator) {
        return scrollIntoView(locator, SingleGetter.DEFAULT);
    }

    static DriverFunction<Object> getStyle(LazyElement data) {
        final var nameof = "getStyle: ";
        return ifDriver(
                nameof,
                isNotNull(data),
                new DriverFunction<Object>(driver -> {
                    if (isNullOrFalseDataOrDataObject(Driver.isElementAbsent(data).apply(driver))) {
                        return new Data<Object>(new Object(), false, nameof + "Element absent, no properties can be fetched.");
                    }

                    final var result = Driver.executeSingleParameter(GetStyle.GET_STYLES_IN_JSON, data.get().apply(driver)).apply(driver);
                    return utils.isNotNullOrFalseDataOrDataObject(result) ? new Data<Object>(result.object, result.status, result.message) : DataDefaults.NULL_OBJECT_DATA;
                }),
                DataDefaults.NULL_OBJECT_DATA
        );
    }

    private static DriverFunction<WebElement> getShadowRootCore(Data<WebElement> data) {
        return ifDriver(
            "getShadowRootCore",
            isNotNullOrFalseDataOrDataObject(data),
            new DriverFunction<WebElement> (driver -> {
                final var result = Driver.executeSingleParameter(ShadowRoot.GET_SHADOW_ROOT, data).apply(driver);
                return utils.isNotNullOrFalseDataOrDataObject(result) ? new Data<WebElement>((WebElement)result.object, result.status, result.message) : DataDefaults.NULL_ELEMENT_DATA;
            }),
            DataDefaults.NULL_ELEMENT_DATA
        );
    }

    static DriverFunction<WebElement> getShadowRoot(Data<WebElement> data) {
        return getShadowRootCore(data);
    }

    static DriverFunction<WebElement> getShadowRoot(DriverFunction<WebElement> getter) {
        return ifDriverFunction("getShadowRoot", utils::isNull, getter, Execute::getShadowRootCore, DataDefaults.NULL_ELEMENT_DATA);
    }

    static DriverFunction<WebElement> getShadowRoot(LazyElement data) {
        return isNotNull(data) ? getShadowRoot(data.get()) : new DriverFunction<WebElement>(DataDefaults.NULL_ELEMENT_DATA);
    }

    static DriverFunction<WebElement> getShadowRoot(By locator, SingleGetter getter) {
        return getShadowRoot(new LazyElement(locator, getter));
    }

    static DriverFunction<WebElement> getShadowRoot(By locator) {
        return getShadowRoot(locator, SingleGetter.DEFAULT);
    }

    static DriverFunction<Boolean> readyState() {
        return new DriverFunction<Boolean> (driver -> {
            if (isNull(driver)) {
                return DataDefaults.NULL_BOOLEAN_DATA;
            }

            final var result = Driver.executeData(ReadyState.script).apply(Driver.getExecutor(driver));
            return utils.isNotNullOrFalseDataOrDataObject(result) ? new Data<Boolean>(Boolean.valueOf(result.object.toString()), result.status, result.message, result.exception, result.exceptionMessage) : DataDefaults.NULL_BOOLEAN_DATA;
        });
    }

    static DriverFunction<String> setAttribute(Data<WebElement> element, String attribute, String value) {
        return ifDriver(
                "setAttribute",
                areNotBlank(attribute, value) && isNotNullOrFalseDataOrDataObject(element),
                new DriverFunction<String>(driver -> {
                    final var parametersData = getParameterArray(element, attribute, value).apply(driver);
                    if (isNullOrFalseDataOrDataObject(parametersData)) {
                        return DataDefaults.NULL_STRING_DATA;
                    }

                    final var result = Driver.executeParametersCore(Driver.getExecutor(driver)::executeScript, Attribute.SET_ATTRIBUTE, parametersData.object);
                    final var returnedValue = String.valueOf(result.object);
                    final var status = utils.isNotNullOrFalseDataOrDataObject(result) && Objects.equals(value, returnedValue);
                    return new Data<String>(returnedValue, status, "Value \"" + value + "\" was " + Formatter.getOptionMessage(status) + "set" + Strings.END_LINE);
                }),
                DataDefaults.NULL_STRING_DATA
        );
    }

    static DriverFunction<String> setAttribute(DriverFunction<WebElement> element, String attribute, String value) {
        return ifDriver(
            "setAttribute",
            areNotBlank(attribute, value) && isNotNull(element),
            new DriverFunction<String>(driver -> setAttribute(element.apply(driver), attribute, value).apply(driver)),
            DataDefaults.NULL_STRING_DATA
        );
    }

    static DriverFunction<String> setAttribute(LazyElement element, String attribute, String value) {
        return ifDriver(
            "setAttribute",
            isNotNull(element),
            setAttribute(element.get(), attribute, value),
            DataDefaults.NULL_STRING_DATA
        );
    }

    static DriverFunction<String> setId(Data<WebElement> element, String value) {
        return setAttribute(element, Strings.PRIMARY_STRATEGY, value);
    }

    static DriverFunction<String> setId(LazyElement element, String value) {
        return setAttribute(element, Strings.PRIMARY_STRATEGY, value);
    }
}
