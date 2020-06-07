package com.github.karsaii.selenium.namespaces.scripter;

import com.github.karsaii.core.constants.CoreDataConstants;
import com.github.karsaii.core.namespaces.validators.DataValidators;
import com.github.karsaii.core.namespaces.validators.CoreFormatter;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import com.github.karsaii.selenium.namespaces.validators.SeleniumFormatter;
import org.apache.commons.lang3.ArrayUtils;
import com.github.karsaii.selenium.constants.scripts.ClickFunctions;
import com.github.karsaii.selenium.namespaces.SeleniumExecutor;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.core.extensions.namespaces.CoreUtilities;
import com.github.karsaii.core.extensions.namespaces.NullableFunctions;
import com.github.karsaii.core.namespaces.DataFactoryFunctions;
import com.github.karsaii.core.namespaces.DataFunctions;
import com.github.karsaii.core.namespaces.executor.Executor;
import com.github.karsaii.core.records.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.github.karsaii.selenium.constants.SeleniumDataConstants;
import com.github.karsaii.selenium.constants.DriverFunctionConstants;
import com.github.karsaii.selenium.enums.SingleGetter;
import com.github.karsaii.selenium.constants.scripts.Attribute;
import com.github.karsaii.selenium.constants.scripts.GetStyle;
import com.github.karsaii.selenium.constants.scripts.ReadyState;
import com.github.karsaii.selenium.constants.scripts.ScrollIntoView;
import com.github.karsaii.selenium.constants.scripts.ShadowRoot;
import com.github.karsaii.selenium.namespaces.Driver;
import com.github.karsaii.selenium.namespaces.DriverFunctionFactory;
import com.github.karsaii.selenium.namespaces.ScriptExecuteFunctions;
import com.github.karsaii.selenium.namespaces.repositories.LocatorRepository;
import com.github.karsaii.selenium.records.lazy.LazyElement;
import com.github.karsaii.selenium.records.scripter.ScriptParametersData;

import java.util.Objects;

import static com.github.karsaii.core.extensions.namespaces.CoreUtilities.areNotBlank;
import static com.github.karsaii.core.extensions.namespaces.CoreUtilities.areNotNull;
import static com.github.karsaii.core.extensions.namespaces.NullableFunctions.isNotNull;
import static com.github.karsaii.core.extensions.namespaces.NullableFunctions.isNull;
import static com.github.karsaii.core.namespaces.DataFactoryFunctions.getWithDefaultExceptionData;
import static com.github.karsaii.core.namespaces.validators.DataValidators.isInvalidOrFalse;
import static com.github.karsaii.core.namespaces.validators.DataValidators.isValidNonFalse;
import static com.github.karsaii.core.namespaces.DataFactoryFunctions.getArrayWithName;
import static com.github.karsaii.core.namespaces.DataFactoryFunctions.replaceMessage;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static com.github.karsaii.selenium.namespaces.ExecutionCore.ifDriver;
import static com.github.karsaii.selenium.namespaces.ExecutionCore.ifDriverFunction;
import static com.github.karsaii.selenium.namespaces.ExecutionCore.validChain;
import static com.github.karsaii.selenium.namespaces.utilities.SeleniumUtilities.isNotNullLazyElement;
import static com.github.karsaii.selenium.namespaces.utilities.SeleniumUtilities.isNotNullWebElement;

public interface Execute {
    static <T> DriverFunction<T> isCommonExists(String nameof, String isExists, Data<T> defaultValue) {
        return ifDriver(
            nameof,
            isNotBlank(isExists) && isNotNull(defaultValue),
            driver -> {
                final var result = Driver.execute(isExists).apply(driver);
                return getWithDefaultExceptionData((T)result.object, result.status, result.message);
            },
            defaultValue
        );
    }

    static DriverFunction<Boolean> setCommon(String nameof, DriverFunction<Boolean> precondition, String function, Data<Boolean> defaultValue) {
        return ifDriver(
            nameof,
            areNotNull(precondition, defaultValue) && isNotBlank(function),
            driver -> {
                Data<?> result = precondition.apply(driver);
                if (!result.status) {
                    return defaultValue;
                }

                if (CoreUtilities.isFalse(result.object)) {
                    result = Driver.execute(function).apply(driver);
                }

                final var status = isValidNonFalse(result);
                return DataFactoryFunctions.getBoolean(status, CoreFormatter.getExecuteFragment(status) + " Scroll into view: " + result.message.toString() + CoreFormatterConstants.END_LINE);
            },
            defaultValue
        );
    }


    static <T> DriverFunction<T> commonExecutor(String nameof, DriverFunction<WebElement> getter, String function, Data<T> defaultValue) {
        return ifDriver(
            nameof,
            isNotNull(getter),
            driver -> {
                final var result = Driver.executeSingleParameter(function, ScriptExecuteFunctions.handleDataParameterWithDefaults(getter.apply(driver))).apply(driver);
                return getWithDefaultExceptionData((T)result.object, result.status, result.message);
            },
            defaultValue
        );
    }

    static DriverFunction<Boolean> isScrollIntoViewExistsData() {
        return ifDriver(
            "isScrollIntoViewExistsData",
            driver -> {
                final var result = Driver.execute(ScrollIntoView.IS_EXISTS).apply(driver);
                return getWithDefaultExceptionData(Boolean.valueOf(result.object.toString()), result.status, result.message);
            },
            CoreDataConstants.NULL_BOOLEAN
        );
    }

    static DriverFunction<Boolean> scrollIntoViewExecutor(LazyElement data) {
        return isNotNullLazyElement(data) ? scrollIntoViewExecutor(data.get()) : DriverFunctionConstants.NULL_BOOLEAN;
    }

    static DriverFunction<Boolean> scrollIntoViewExecutor(DriverFunction<WebElement> getter) {
        return ifDriver(
            "scrollIntoViewExecutor",
            isNotNull(getter),
            driver -> {
                final var parameters = new ScriptParametersData<>(getter.apply(driver), DataValidators::isValidNonFalse, DataFunctions::unwrapToArray);
                final var result = Driver.executeSingleParameter(ScrollIntoView.EXECUTE, ScriptExecuteFunctions.handleDataParameter(parameters)).apply(driver);
                return getWithDefaultExceptionData(isNotNull(result.object), result.status, result.message);
            },
            CoreDataConstants.NULL_BOOLEAN
        );
    }

    static DriverFunction<Boolean> setScrollIntoView() {
        return ifDriver(
            "setScrollIntoView",
            driver -> {
                final var result = SeleniumExecutor.conditionalSequence(Executor::isFalse, isScrollIntoViewExistsData(), Driver.execute(ScrollIntoView.SET_FUNCTIONS)).apply(driver);
                final var status = isValidNonFalse(result);
                return DataFactoryFunctions.getBoolean(status, SeleniumFormatter.getScrollIntoViewMessage(result.message.getMessage(), status));
            },
            CoreDataConstants.NULL_BOOLEAN
        );
    }

    static DriverFunction<Boolean> scrollIntoView(LazyElement data) {
        return SeleniumExecutor.execute(Driver.isElementHidden(data), setScrollIntoView(), scrollIntoViewExecutor(data));
    }

    static DriverFunction<Boolean> scrollIntoView(Data<LazyElement> data) {
        return ifDriver("scrollIntoView", isValidNonFalse(data), scrollIntoView(data.object), CoreDataConstants.NULL_BOOLEAN);
    }

    static DriverFunction<Boolean> scrollIntoView(By locator, SingleGetter getter) {
        return scrollIntoView(LocatorRepository.getIfContains(locator, getter));
    }

    static DriverFunction<Boolean> scrollIntoView(By locator) {
        return scrollIntoView(locator, SingleGetter.DEFAULT);
    }

    static <T> Data<Object[]> handleDataParameterDefault(Data<T> parameter) {
        return DataFactoryFunctions.getWithMessage(
            ScriptExecuteFunctions.handleDataParameter(new ScriptParametersData<>(parameter, DataValidators::isValidNonFalse, DataFunctions::unwrapToArray)),
            false,
            ""
        );
    }

    static DriverFunction<Object> getStyle(LazyElement data) {
        return ifDriver(
            "getStyle",
            isNotNull(data),
            driver -> {
                final var steps = validChain(data.get(), Execute::handleDataParameterDefault, CoreDataConstants.NULL_PARAMETER_ARRAY);
                final var parameter = SeleniumExecutor.conditionalSequence(Driver.isElementPresent(data), steps, Object[].class).apply(driver);

                return isValidNonFalse(parameter) ? Driver.executeSingleParameter(GetStyle.GET_STYLES_IN_JSON, parameter.object).apply(driver) : CoreDataConstants.NULL_OBJECT;
            },
            CoreDataConstants.NULL_OBJECT
        );
    }

    private static DriverFunction<WebElement> getShadowRootCore(Data<WebElement> data) {
        return ifDriver(
            "getShadowRootCore",
            isNotNullWebElement(data),
            driver -> {
                final var parameter = handleDataParameterDefault(data);
                if(isInvalidOrFalse(parameter)) {
                    return replaceMessage(SeleniumDataConstants.NULL_ELEMENT, parameter.message.toString());
                }

                final var result = Driver.executeSingleParameter(ShadowRoot.GET_SHADOW_ROOT, parameter.object).apply(driver);
                return isValidNonFalse(result) ? (
                    getWithDefaultExceptionData((WebElement)result.object, result.status, result.message)
                ) : replaceMessage(SeleniumDataConstants.NULL_ELEMENT, result.message.toString());
            },
            SeleniumDataConstants.NULL_ELEMENT
        );
    }

    static DriverFunction<WebElement> getShadowRootElement(Data<WebElement> data) {
        return getShadowRootCore(data);
    }

    static DriverFunction<WebElement> getShadowRoot(DriverFunction<WebElement> getter) {
        return ifDriverFunction("getShadowRoot", NullableFunctions::isNotNull, getter, Execute::getShadowRootCore, SeleniumDataConstants.NULL_ELEMENT);
    }

    static DriverFunction<WebElement> getShadowRoot(LazyElement element) {
        return isNotNullLazyElement(element) ? (
            getShadowRoot(element.get())
        ) : DriverFunctionFactory.get(replaceMessage(SeleniumDataConstants.NULL_ELEMENT, "getShadowRoot", "LazyElement element" + CoreFormatterConstants.WAS_NULL));
    }

    static DriverFunction<WebElement> getShadowRoot(Data<LazyElement> data) {
        return ifDriver("getShadowRoot", isValidNonFalse(data), getShadowRoot(data.object), SeleniumDataConstants.NULL_ELEMENT);
    }

    static DriverFunction<WebElement> getShadowRoot(By locator, SingleGetter getter) {
        return getShadowRoot(LocatorRepository.getIfContains(locator, getter));
    }

    static DriverFunction<WebElement> getShadowRoot(By locator) {
        return getShadowRoot(locator, SingleGetter.DEFAULT);
    }

    static DriverFunction<Boolean> readyState() {
        final var negative = CoreDataConstants.NULL_BOOLEAN;
        return ifDriver(
            "readyState",
            driver -> {
                final var result = Driver.execute(ReadyState.script).apply(driver);
                return isValidNonFalse(result) ? new Data<>(Boolean.valueOf(result.object.toString()), result.status, result.message, result.exception, result.exceptionMessage) : negative;
            },
            negative
        );
    }

    static DriverFunction<String> setAttribute(Data<WebElement> element, String attribute, String value) {
        final var nameof = "setAttribute";
        return ifDriver(
            nameof,
            areNotBlank(attribute, value) && isNotNullWebElement(element),
            driver -> {
                final var parametersData = getArrayWithName(ArrayUtils.toArray(element, attribute, value));
                if (isInvalidOrFalse(parametersData)) {
                    return CoreDataConstants.NULL_STRING;
                }

                final var result = Driver.executeParameters(Attribute.SET_ATTRIBUTE, parametersData.object).apply(driver);
                final var returnedValue = String.valueOf(result.object);
                final var status = isValidNonFalse(result) && Objects.equals(value, returnedValue);
                return DataFactoryFunctions.getWithMessage(returnedValue, status, "Value \"" + value + "\" was " + CoreFormatter.getOptionMessage(status) + "set" + CoreFormatterConstants.END_LINE);
            },
            CoreDataConstants.NULL_STRING
        );
    }

    static DriverFunction<String> setAttribute(DriverFunction<WebElement> element, String attribute, String value) {
        return ifDriver(
            "setAttribute",
            isNotNull(element) && areNotBlank(attribute, value),
            driver -> setAttribute(element.apply(driver), attribute, value).apply(driver),
            CoreDataConstants.NULL_STRING
        );
    }

    static DriverFunction<String> setAttribute(LazyElement element, String attribute, String value) {
        return ifDriver(
            "setAttribute",
            isNotNullLazyElement(element),
            setAttribute(element.get(), attribute, value),
            CoreDataConstants.NULL_STRING
        );
    }

    static DriverFunction<String> setId(Data<WebElement> element, String value) {
        return setAttribute(element, SeleniumFormatterConstants.PRIMARY_STRATEGY, value);
    }

    static DriverFunction<String> setId(LazyElement element, String value) {
        return setAttribute(element, SeleniumFormatterConstants.PRIMARY_STRATEGY, value);
    }

    static DriverFunction<Boolean> clickEventDispatcher(Data<WebElement> element) {
        final var nameof = "clickEventDispatcher";
        return ifDriver(
            nameof,
            isNotNullWebElement(element),
            driver -> {
                if (isInvalidOrFalse(element)) {
                    return CoreDataConstants.NULL_BOOLEAN;
                }

                final var parametersData = DataFunctions.unwrapToArray(element);
                if (isNull(parametersData)) {
                    return CoreDataConstants.NULL_BOOLEAN;
                }

                final var result = Driver.executeParameters(ClickFunctions.CLICK_DISPATCHER, parametersData).apply(driver);
                final var returnedValue = String.valueOf(result.object);
                final var status = isValidNonFalse(result);
                return DataFactoryFunctions.getWithMessage(CoreUtilities.castToBoolean(returnedValue), status, "Element was " + CoreFormatter.getOptionMessage(status) + "clicked" + CoreFormatterConstants.END_LINE);
            },
            CoreDataConstants.NULL_BOOLEAN
        );
    }

    static DriverFunction<Boolean> clickEventDispatcher(DriverFunction<WebElement> getter) {
        return ifDriver("clickEventDispatcher", isNotNull(getter), driver -> clickEventDispatcher(getter.apply(driver)).apply(driver), CoreDataConstants.NULL_BOOLEAN);
    }

    static DriverFunction<Boolean> clickEventDispatcher(LazyElement element) {
        return ifDriver("clickEventDispatcher", isNotNullLazyElement(element), clickEventDispatcher(element.get()), CoreDataConstants.NULL_BOOLEAN);
    }
}
