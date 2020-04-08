package selenium.javascriptCommands;

import core.constants.CoreDataConstants;
import core.extensions.interfaces.DriverFunction;
import core.extensions.namespaces.CoreUtilities;
import core.extensions.namespaces.NullableFunctions;
import core.namespaces.DataFactoryFunctions;
import core.namespaces.DataFunctions;
import core.namespaces.Executor;
import core.records.Data;
import data.constants.Strings;
import data.namespaces.Formatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.constants.SeleniumDataConstants;
import selenium.constants.DriverFunctionConstants;
import selenium.enums.SingleGetter;
import selenium.javascriptCommands.scripts.Attribute;
import selenium.javascriptCommands.scripts.GetStyle;
import selenium.javascriptCommands.scripts.ReadyState;
import selenium.javascriptCommands.scripts.ScrollIntoView;
import selenium.javascriptCommands.scripts.ShadowRoot;
import selenium.namespaces.Driver;
import selenium.namespaces.DriverFunctionFactoryFunctions;
import selenium.namespaces.ScriptExecuteFunctions;
import selenium.namespaces.repositories.LocatorRepository;
import selenium.records.LazyElement;
import selenium.records.scripter.ScriptParametersData;

import java.util.Objects;

import static core.extensions.namespaces.CoreUtilities.areNotBlank;
import static core.extensions.namespaces.CoreUtilities.areNotNull;
import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static core.extensions.namespaces.NullableFunctions.isNull;
import static core.namespaces.DataFunctions.isInvalidOrFalse;
import static core.namespaces.DataFunctions.isValidNonFalse;
import static core.namespaces.DataFunctions.replaceMessage;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.ExecutionCore.ifDriverFunction;
import static selenium.namespaces.ExecutionCore.validChain;
import static selenium.namespaces.SeleniumUtilities.isNotNullLazyElement;
import static selenium.namespaces.SeleniumUtilities.isNotNullWebElement;
import static selenium.namespaces.SeleniumUtilities.isNullWebElement;

public interface Execute {
    static <T> DriverFunction<T> isCommonExists(String nameof, String isExists, Data<T> defaultValue) {
        return ifDriver(
            nameof,
            isNotBlank(isExists) && isNotNull(defaultValue),
            driver -> {
                final var result = Driver.execute(isExists).apply(driver);
                return DataFactoryFunctions.getWithMethodMessage((T)result.object, result.status, result.message);
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
                return DataFactoryFunctions.getBoolean(status, Formatter.getExecuteFragment(status) + " Scroll into view: " + result.message.toString() + Strings.END_LINE);
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
                return DataFactoryFunctions.getWithMethodMessage((T)result.object, result.status, result.message);
            },
            defaultValue
        );
    }

    static DriverFunction<Object[]> getParameterArray(String item) {
        final var nameof = "getParameterArray";
        return ifDriver(
            nameof,
            isNotNull(item),
            driver -> {
                final var length = 1;
                final var parameters = new Object[1];
                parameters[0] = item;

                return DataFactoryFunctions.getWithNameAndMessage(parameters, true, nameof, "Array of length " + length + " was constructed" + Strings.END_LINE);
            },
            CoreDataConstants.NULL_PARAMETER_ARRAY
        );
    }

    static DriverFunction<Object[]> getParameterArray(String first, String second) {
        final var nameof = "getParameterArray";
        return ifDriver(
            nameof,
            areNotNull(first, second),
            driver -> {
                final var length = 2;
                final var parameters = new Object[length];
                parameters[0] = first;
                parameters[1] = second;

                return DataFactoryFunctions.getWithNameAndMessage(parameters, true, nameof, "Array of length " + length + " was constructed" + Strings.END_LINE);
            },
            CoreDataConstants.NULL_PARAMETER_ARRAY
        );
    }


    static Data<Object[]> getParameterArray(Data<WebElement> element, String attribute, String value) {
        final var nameof = "getParameterArray";
        if (isNullWebElement(element) || isBlank(attribute) || isNull(value)) {
            return CoreDataConstants.NULL_PARAMETER_ARRAY;
        }

        final var length = 3;
        final var parameters = new Object[length];
        parameters[0] = element.object;
        parameters[1] = attribute;
        parameters[2] = value;

        return DataFactoryFunctions.getWithNameAndMessage(parameters, true, nameof, "Element was found. Array of length " + length + " was constructed" + Strings.END_LINE);
    }

    static DriverFunction<Object[]> getParameterArray(Data<WebElement> element) {
        final var nameof = "getParameterArray";
        return ifDriver(
            nameof,
            isNotNullWebElement(element),
            driver -> {
                final var length = 1;
                final var parameters = new Object[length];
                parameters[0] = element.object;
                return DataFactoryFunctions.getWithNameAndMessage(parameters, true, nameof, "Element was found. Array of length " + length + " was constructed" + Strings.END_LINE);
            },
            CoreDataConstants.NULL_PARAMETER_ARRAY
        );
    }

    static DriverFunction<Object[]> getParameterArray(LazyElement element, String attribute, String value) {
        final var nameof = "getParameterArray";
        return ifDriver(
            nameof,
            areNotNull(attribute, value) && isNotNullLazyElement(element),
            driver -> getParameterArray(element.get().apply(driver), attribute, value),
            CoreDataConstants.NULL_PARAMETER_ARRAY
        );
    }

    static DriverFunction<Boolean> isScrollIntoViewExistsData() {
        return ifDriver(
            "isScrollIntoViewExistsData",
            driver -> {
                final var result = Driver.execute(ScrollIntoView.IS_EXISTS).apply(driver);
                return DataFactoryFunctions.getWithMethodMessage(Boolean.valueOf(result.object.toString()), result.status, result.message);
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
                final var parameters = new ScriptParametersData<>(getter.apply(driver), DataFunctions::isValidNonFalse, DataFunctions::unwrapToArray);
                final var result = Driver.executeSingleParameter(ScrollIntoView.EXECUTE, ScriptExecuteFunctions.handleDataParameter(parameters)).apply(driver);
                return DataFactoryFunctions.getWithMethodMessage(isNotNull(result.object), result.status, result.message);
            },
            CoreDataConstants.NULL_BOOLEAN
        );
    }

    static DriverFunction<Boolean> setScrollIntoView() {
        return ifDriver(
            "setScrollIntoView",
            driver -> {
                final var result = Executor.conditionalSequence(Executor::isFalse, isScrollIntoViewExistsData(), Driver.execute(ScrollIntoView.SET_FUNCTIONS)).apply(driver);
                final var status = isValidNonFalse(result);
                return DataFactoryFunctions.getBoolean(status, Formatter.getScrollIntoViewMessage(result.message.getMessage(), status));
            },
            CoreDataConstants.NULL_BOOLEAN
        );
    }

    static DriverFunction<Boolean> scrollIntoView(LazyElement data) {
        return Executor.execute(Driver.isElementHidden(data), setScrollIntoView(), scrollIntoViewExecutor(data));
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
            ScriptExecuteFunctions.handleDataParameter(new ScriptParametersData<>(parameter, DataFunctions::isValidNonFalse, DataFunctions::unwrapToArray)),
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
                final var parameter = Executor.conditionalSequence(Driver.isElementPresent(data), steps, Object[].class).apply(driver);

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
                    DataFactoryFunctions.getWithMethodMessage((WebElement)result.object, result.status, result.message)
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
        ) : DriverFunctionFactoryFunctions.get(replaceMessage(SeleniumDataConstants.NULL_ELEMENT, "getShadowRoot", "LazyElement element" + Strings.WAS_NULL));
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
        return ifDriver(
            "setAttribute",
            areNotBlank(attribute, value) && isNotNullWebElement(element),
            driver -> {
                final var parametersData = getParameterArray(element, attribute, value);
                if (isInvalidOrFalse(parametersData)) {
                    return CoreDataConstants.NULL_STRING;
                }

                final var result = Driver.executeParameters(Attribute.SET_ATTRIBUTE, parametersData.object).apply(driver);
                final var returnedValue = String.valueOf(result.object);
                final var status = isValidNonFalse(result) && Objects.equals(value, returnedValue);
                return DataFactoryFunctions.getWithMessage(returnedValue, status, "Value \"" + value + "\" was " + Formatter.getOptionMessage(status) + "set" + Strings.END_LINE);
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
        return setAttribute(element, Strings.PRIMARY_STRATEGY, value);
    }

    static DriverFunction<String> setId(LazyElement element, String value) {
        return setAttribute(element, Strings.PRIMARY_STRATEGY, value);
    }
}
