package com.github.karsaii.selenium.namespaces.validators;

import com.github.karsaii.core.extensions.DecoratedList;
import com.github.karsaii.core.extensions.namespaces.CoreUtilities;
import com.github.karsaii.core.extensions.namespaces.NullableFunctions;
import com.github.karsaii.core.namespaces.DataFactoryFunctions;
import com.github.karsaii.core.namespaces.validators.CoreFormatter;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selectorSpecificity.tuples.SelectorSpecificsData;
import com.github.karsaii.selenium.abstracts.AbstractLazyElement;
import com.github.karsaii.selenium.abstracts.ElementValueParameters;
import com.github.karsaii.selenium.constants.SeleniumCoreConstants;
import com.github.karsaii.selenium.constants.SeleniumDataConstants;
import com.github.karsaii.selenium.enums.ManyGetter;
import com.github.karsaii.selenium.enums.SingleGetter;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.selenium.namespaces.extensions.boilers.LazyLocatorList;
import com.github.karsaii.selenium.namespaces.extensions.boilers.WebElementList;
import com.github.karsaii.selenium.records.ExternalSelectorData;
import com.github.karsaii.selenium.records.InternalSelectorData;
import com.github.karsaii.selenium.records.ProbabilityData;
import com.github.karsaii.selenium.records.SwitchResultMessageData;
import com.github.karsaii.selenium.records.element.is.ElementConditionParameters;
import com.github.karsaii.selenium.records.element.is.ElementFormatData;
import com.github.karsaii.selenium.records.element.is.ElementParameterizedValueParameters;
import com.github.karsaii.selenium.records.lazy.LazyElement;
import com.github.karsaii.selenium.records.lazy.LazyElementWithOptionsData;
import com.github.karsaii.selenium.records.lazy.LazyLocator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static com.github.karsaii.selenium.namespaces.utilities.SeleniumUtilities.getLocator;

public interface SeleniumFormatter {
    static Data<String> getIsValuesMessage(Map<String, String> map, Data<String> object, String expected, Boolean keyCondition, String descriptor, String conditionDescriptor) {
        final var valuesMessage = "Values ";
        final var defaultMessage = "getIsValuesMessage: ";
        final var errorMessage = (
            CoreFormatter.isBlankMessageWithName(conditionDescriptor, valuesMessage + "conditionDescriptor") +
            CoreFormatter.isNullMessageWithName(descriptor, valuesMessage + "descriptor") +
            CoreFormatter.isNullMessageWithName(keyCondition, valuesMessage + "boolean") +
            CoreFormatter.isNullMessageWithName(map, valuesMessage + " map") +
            CoreFormatter.isNullMessageWithName(expected, valuesMessage + "expected value") +
            CoreFormatter.isInvalidOrFalseMessageWithName(object, valuesMessage + "object")
        );

        if (isNotBlank(errorMessage)) {
            return DataFactoryFunctions.getWithMessage(CoreFormatterConstants.EMPTY, false, defaultMessage + " " + conditionDescriptor + CoreFormatterConstants.PARAMETER_ISSUES + CoreFormatterConstants.DEFAULT_ERROR_MESSAGE_STRING + errorMessage);
        }

        final var key = String.valueOf(keyCondition);
        final var localObject = map.getOrDefault(key, CoreFormatterConstants.EMPTY);
        final var status = isNotBlank(localObject);
        final var message = defaultMessage + (descriptor + "(" + object + ") " + localObject + conditionDescriptor + " expected (\"" + expected + "\")" + CoreFormatterConstants.END_LINE);
        return DataFactoryFunctions.getWithMessage(message, status, message);
    }

    static String isValidElementFormatData(ElementFormatData<?> parameters) {
        final var baseName = "Element Function Parameters";
        var message = CoreFormatter.isNullMessageWithName(parameters, baseName);
        if (isBlank(message)) {
            message += (
                CoreFormatter.isNullMessageWithName(parameters.formatter, baseName + " Formatter") +
                CoreFormatter.isBlankMessageWithName(parameters.conditionName, baseName + " Name") +
                CoreFormatter.isBlankMessageWithName(parameters.descriptor, baseName + " Descriptor")
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("isValidElementFormatData: ", message);
    }

    static String isValidElementValueParametersMessage(ElementValueParameters<?, ?> parameters) {
        final var baseName = "Element Value Parameters";
        var message = CoreFormatter.isNullMessageWithName(parameters, baseName);
        if (isBlank(message)) {
            message += (
                CoreFormatter.isNullMessageWithName(parameters.function, baseName + " Function") +
                CoreFormatter.isNullMessageWithName(parameters.handler, baseName + " Handler") +
                isValidElementFormatData(parameters.formatData)
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("isValidElementValueParametersMessage: ", message);
    }

    static String isValidElementParameterizedValueParametersMessage(ElementParameterizedValueParameters<?> parameters) {
        final var baseName = "Element Parameterized Value Parameters";
        var message = CoreFormatter.isNullMessageWithName(parameters, baseName);
        if (isBlank(message)) {
            message += (
                CoreFormatter.isNullMessageWithName(parameters.function, baseName + " Function") +
                CoreFormatter.isNullMessageWithName(parameters.handler, baseName + " Handler") +
                isValidElementFormatData(parameters.formatData)
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("isValidElementParameterizedValueParametersMessage: ", message);
    }

    static String isValidElementConditionParametersMessage(ElementConditionParameters<?> parameters) {
        final var baseName = "Element Regular Condition Function Parameters";
        var message = isValidElementValueParametersMessage(parameters);
        if (isBlank(message)) {
            message += CoreFormatter.isNullMessageWithName(parameters.inverter, baseName + " Inverter");
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("isValidElementConditionParametersMessage: ", message);
    }

    static String getScrollIntoViewMessage(String message, boolean status) {
        return CoreFormatter.getExecuteFragment(status) + " Scroll into view: " + message + CoreFormatterConstants.END_LINE;
    }

    static String getScriptExecutionMessage(boolean status) {
        return CoreFormatter.getExecuteFragment(status) + " script" + CoreFormatterConstants.END_LINE;
    }

    static String getLocatorErrorMessage(By locator) {
        return CoreFormatter.isNullMessageWithName(locator, "By locator");
    }

    static String getNestedElementsErrorMessage(By locator, Data<SearchContext> context) {
        return getLocatorErrorMessage(locator) + CoreFormatter.isInvalidOrFalseMessageWithName(context, "Search Context");
    }

    static String getActionMessage(String message, String dataMessage, boolean status) {
        return CoreFormatterConstants.ELEMENT + CoreFormatter.getOptionMessage(status) + " " + message + CoreFormatterConstants.END_LINE + dataMessage + CoreFormatterConstants.END_LINE;
    }

    static String getActionMessage(String message, String dataMessage, String input, boolean status) {
        return getActionMessage(message, dataMessage, status) + "Input was: \"" + input + "\"" + CoreFormatterConstants.END_LINE;
    }

    static String getGetterErrorMessage(Function getter) {
        return CoreFormatter.isNullMessageWithName(getter, "Getter");
    }

    static String getLocatorAndGetterErrorMessage(By locator, SingleGetter getter) {
        return getLocatorErrorMessage(locator) + CoreFormatter.isNullMessageWithName(getter, "Getter");
    }

    static String getSendKeysErrorMessage(String message) {
        return isNotBlank(message) ? "getSendKeysErrorMessage: " + CoreFormatterConstants.PARAMETER_ISSUES_LINE + message + "Not sending" + CoreFormatterConstants.END_LINE : CoreFormatterConstants.EMPTY;
    }

    static String getSendKeysNotSendingMessage(By locator, String input, SingleGetter getter) {
        return getSendKeysErrorMessage(getLocatorAndGetterErrorMessage(locator, getter) + CoreFormatter.getInputErrorMessage(input));
    }

    static String getSendKeysNotSendingMessage(By locator, String input) {
        return getSendKeysErrorMessage(getLocatorErrorMessage(locator) + CoreFormatter.getInputErrorMessage(input));
    }

    static String getSendKeysNotSendingMessage(LazyElement data, String input) {
        return getSendKeysErrorMessage(isNullLazyElementMessage(data) + CoreFormatter.getInputErrorMessage(input));
    }

    static <T> String areNullLazyElementParametersMessage(Collection<T> data, Predicate<T> validator) {
        var message = CoreFormatter.isEmptyMessage(data) + CoreFormatter.isNullMessageWithName(validator, "Validator");
        var sb = new StringBuilder();
        if (isBlank(message)) {
            var index = 0;
            for(T parameters : data) {
                sb.append(CoreFormatter.isInvalidMessage(validator.test(parameters), index + ". element data"));
            }
        }

        message += sb.toString();
        return CoreFormatter.getNamedErrorMessageOrEmpty("areNullLazyElementParametersMessage: ", message);
    }

    static <T> String isNullLazyElementMessage(AbstractLazyElement<T> object) {
        final var baseMessage = "isNullLazyElementMessage: " + CoreFormatterConstants.PARAMETER_ISSUES_LINE;
        var message = CoreFormatter.isNullMessageWithName(object, "Lazy Element");
        if (isNotBlank(message)) {
            return baseMessage + message;
        }
        final var parameters = object.parameters;
        if (isBlank(message)) {
            message += (
                CoreFormatter.isBlankMessageWithName(object.name, CoreFormatterConstants.ELEMENT + " name") +
                CoreFormatter.isNullMessageWithName(parameters, SeleniumFormatterConstants.ELEMENT_PARAMETERS)
            );
        }
        if (isBlank(message)) {
            message += areNullLazyElementParametersMessage(parameters.values(), object.validator);
        }

        return isNotBlank(message) ? baseMessage + message : CoreFormatterConstants.EMPTY;
    }

    static String getShadowRootElementMessage(String message, boolean status) {
        return message + " Root com.github.karsaii.selenium.element " + CoreFormatter.getOptionMessage(status) + "found" + CoreFormatterConstants.END_LINE;
    }

    static String getElementsAmountMessage(By locator, boolean status, int expectedSize, int size) {
        return (status ? expectedSize : (size > 0 ? "Wrong(" + expectedSize + ") amount of" : "No")) + " elements found by: " + locator.toString() + CoreFormatterConstants.END_LINE;
    }

    static String getElementAmountErrorMessage(String message, int size) {
        var lMessage = "Wrong amount(" + size + ") of elements found" + CoreFormatterConstants.END_LINE;
        if (isNotBlank(message)) {
            lMessage += message + CoreFormatterConstants.END_LINE;
        }

        return lMessage;
    }

    static String getFindElementsMessage(String locator, int size) {
        return (size > 0 ? size + " amount of" : "No") + " elements found by: " + locator + CoreFormatterConstants.END_LINE;
    }

    static String getNumberOfWindowsEqualToMessage(boolean status, int expected, int count) {
        return "Number of browser windows are " + CoreFormatter.getOptionMessage(status)+ " equal. Actual: " + count + ", Expected: " + expected + CoreFormatterConstants.END_LINE;
    }

    static String getUniqueGeneratedName(String selectorType) {
        return selectorType + "-" + CoreUtilities.getIncrementalUUID(SeleniumCoreConstants.ATOMIC_COUNT) + "-generated";
    }

    static <T> String getSwitchToMessage(boolean status, SwitchResultMessageData<T> data) {
        final var target = data.target;
        var message = data.nameof + (status ? SeleniumFormatterConstants.SUCCESSFULLY_SWITCHED_TO : SeleniumFormatterConstants.COULDNT_SWITCH_TO) + data.type;

        if (!Objects.isNull(target)) {
            message += "(\"" + target + "\")";
        }

        return message + CoreFormatterConstants.END_LINE;
    }

    static String getNullDriverMessage(WebDriver driver) {
        return NullableFunctions.isNull(driver) ? SeleniumFormatterConstants.DRIVER_WAS_NULL : "Driver wasn't null" + CoreFormatterConstants.END_LINE;
    }

    static String getNestedElementErrorMessage(LazyLocatorList locators) {
        var message = "";
        if (locators.isNullOrEmpty()) {
            message += "Locators list was null or empty" + CoreFormatterConstants.END_LINE;
        }

        message += CoreFormatter.isNullMessageWithName(locators.first(), "First locator");
        if (isNotBlank(message)) {
            message = CoreFormatterConstants.PARAMETER_ISSUES + message;
        }

        return message;
    }

    static String getNestedElementErrorMessage(WebDriver driver, DecoratedList<By> locators) {
        var message = getNullDriverMessage(driver);
        if (locators.isNullOrEmpty()) {
            message += "Locators list was null or empty" + CoreFormatterConstants.END_LINE;
        }

        message += CoreFormatter.isNullMessageWithName(locators.first(), "First locator");
        if (isNotBlank(message)) {
            message = CoreFormatterConstants.PARAMETER_ISSUES + message;
        }

        return message;
    }

    static String getWindowHandlesMessage(boolean status, int size) {
        return "Window handles were" + (!status ? "n't" : " (\"" + size + "\")") + "found" + CoreFormatterConstants.END_LINE;
    }

    static String getWindowHandleMessage(boolean status, String handle) {
        return (status ? "Empty window handle name. Cannot use" : "Specific Window handle (\"" + handle + "\") was found") + CoreFormatterConstants.END_LINE;
    }

    static <T> String getLazyParameterErrorMessage(LazyElement element, List<Class<?>> classes, Class<T> clazz, String nameof, Data<T> defaultValue) {
        var message = (
            isNullLazyElementMessage(element) +
            CoreFormatter.isBlankMessageWithName(nameof, "Name of the function") +
            CoreFormatter.isNullOrEmptyListMessage(classes, "Allowed classes list") +
            CoreFormatter.isNullMessageWithName(defaultValue, "T type defaultValue") +
            CoreFormatter.isNullMessageWithName(clazz, "Class reference parameter")
        );
        if (!classes.contains(clazz)) {
            message += (
                "Lazy com.github.karsaii.selenium.element passed class: " +
                clazz.getName() +
                " is not in the allowed list of " +
                SeleniumCoreConstants.CLASSES_OF_GET_MECHANISMS.toString() +
                CoreFormatterConstants.END_LINE
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("getLazyParameterErrorMessage: ", message);
    }

    static <T> String getLazyParameterErrorMessage(LazyElement element, String nameof) {
        var message = isNullLazyElementMessage(element) + CoreFormatter.isBlankMessageWithName(nameof, "Name of the function");
        return CoreFormatter.getNamedErrorMessageOrEmpty("getLazyParameterErrorMessage: ", message);
    }

    static String getElementConditionMessage(DriverFunction<WebElement> getter, UnaryOperator<Boolean> inverter) {
        var message = CoreFormatter.isNullMessageWithName(getter, "Getter") + CoreFormatter.isNullMessageWithName(inverter, "Boolean inverter");
        return CoreFormatter.getNamedErrorMessageOrEmpty("getElementConditionMessage: ", message);
    }

    static String isElementFunctionMessage(LazyElement element, ElementValueParameters<?, ?> parameters) {
        final var message = isNullLazyElementMessage(element) + isValidElementValueParametersMessage(parameters);
        return CoreFormatter.getNamedErrorMessageOrEmpty("isElementFunctionMessage: ", message);
    }

    static String isElementFunctionMessage(LazyElement element, ElementParameterizedValueParameters<?> parameters) {
        final var message = isNullLazyElementMessage(element) + isValidElementParameterizedValueParametersMessage(parameters);
        return CoreFormatter.getNamedErrorMessageOrEmpty("isElementFunctionMessage: ", message);
    }

    static <T> String getManyGetterErrorMessage(Map<ManyGetter, Function<LazyLocatorList, DriverFunction<T>>> getterMap, ManyGetter key) {
        final var nameof = "getManyGetterErrorMessage";
        final var parameterName = "Getter map";
        var message = CoreFormatter.isNullMessageWithName(getterMap, parameterName);
        if (isNotBlank(message)) {
            return nameof + message;
        }

        if (getterMap.isEmpty()) {
            message += parameterName + " was empty" + CoreFormatterConstants.END_LINE;
        } else {
            if (!getterMap.containsKey(key)) {
                message += "Getter was not found in map with " + key.getName();
            }
        }

        return isNotBlank(message) ? nameof + message : CoreFormatterConstants.EMPTY;
    }

    static <T> String getSingleGetterErrorMessage(Map<SingleGetter, Function<LazyLocatorList, DriverFunction<T>>> getterMap, SingleGetter key) {
        final var nameof = "getSingleGetterErrorMessage";
        final var parameterName = "Getter map";
        var message = CoreFormatter.isNullMessageWithName(getterMap, parameterName);
        if (isNotBlank(message)) {
            return nameof + message;
        }

        if (getterMap.isEmpty()) {
            message += parameterName + " was empty" + CoreFormatterConstants.END_LINE;
        } else {
            if (!getterMap.containsKey(key)) {
                message += "Getter was not found in map with " + key.getName();
            }
        }

        return isNotBlank(message) ? nameof + message : CoreFormatterConstants.EMPTY;
    }

    static String isNullLazyDataMessage(LazyLocator locator) {
        final var parameterName = "Lazy locator";
        var message = CoreFormatter.isNullMessageWithName(locator, parameterName);
        if (isBlank(message)) {
            message += (
                CoreFormatter.isNullMessageWithName(locator.locator, parameterName + " value") +
                CoreFormatter.isNullMessageWithName(locator.strategy, parameterName + " strategy")
            );
        }

        if (isBlank(message)) {
            message += CoreFormatter.isNullMessageWithName(getLocator(locator), "Actual locator from locator");
        }

        return isNotBlank(message) ? "isNotNullLazyDataMessage: " + message : CoreFormatterConstants.EMPTY;
    }

    static String isNullExternalSelectorData(ExternalSelectorData object) {
        final var nameof = "isNullExternalSelectorData";
        var message = CoreFormatter.isNullMessageWithName(object, "External Selector Data");
        if (isNotBlank(message)) {
            return message;
        }

        final var range = object.range;
        message += CoreFormatter.getCommandRangeParameterMessage(range);
        if (isBlank(message)) {
            message += CoreFormatter.getCommandAmountRangeErrorMessage(object.limit, range);
        }
        message += (
            CoreFormatter.isNullMessageWithName(object.getSelector, "Selector getter function") +
            CoreFormatter.isNullMessageWithName(object.preferredProperties, "Preferred properties ") +
            CoreFormatter.isNullMessageWithName(object.selectorType, "Selector type") +
            CoreFormatter.isNullMessageWithName(object.defaultSelector, "Default Selector value")
        );
        return isNotBlank(message) ? nameof + message : CoreFormatterConstants.EMPTY;
    }

    static String getExternalSelectorDataErrorMessage(LazyElement element, ExternalSelectorData externalData, String nameof) {
        var message = CoreFormatter.isBlankMessageWithName(nameof, "Name of the function") + getLazyParameterErrorMessage(element, nameof) + isNullExternalSelectorData(externalData);
        return isNotBlank(message) ? "getExternalSelectorDataErrorMessage: " + message : CoreFormatterConstants.EMPTY;
    }

    static String getProbabilityAdjustmentMessage(String key, double original, double adjusted, boolean increase, boolean generated, boolean belowThreshold) {
        var message = (increase ? "Increased" : "Reduced") + " probability of com.github.karsaii.selenium.selector(\"" + original + "\") to \"" + adjusted + "\"" + CoreFormatterConstants.END_LINE;
        if (belowThreshold) {
            message += (generated ? "External" : "Regular") + "com.github.karsaii.selenium.selector by key(\"" + key + "\") is below threshold(\"" + adjusted + "\"), set to \"0.0\"" + CoreFormatterConstants.END_LINE;
        }

        return message;
    }

    static String getExternalSelectorDataMessage(ExternalSelectorData data) {
        var message = CoreFormatter.isNullMessageWithName(data, "Data");
        if (isBlank(message)) {
            message += (
                CoreFormatter.isNullMessageWithName(data.getSelector, "Selector Driver provider function") +
                CoreFormatter.isBlankMessageWithName(data.preferredProperties, "Preferred Properties") +
                CoreFormatter.isBlankMessageWithName(data.selectorType, "Selector type") +
                CoreFormatter.getCommandAmountRangeErrorMessage(data.limit, data.range) +
                CoreFormatter.isNullMessageWithName(data.defaultSelector, "Default Selector Data")
            );
        }

        return isNotBlank(message) ? "getExternalSelectorDataMessage: " + message : CoreFormatterConstants.EMPTY;
    }

    static String getInternalSelectorDataMessage(InternalSelectorData internalData) {
        var message = CoreFormatter.isNullMessageWithName(internalData, "Internal Data");
        if (isBlank(message)) {
            message += CoreFormatter.getCommandAmountRangeErrorMessage(internalData.limit, internalData.range);
        }

        return isNotBlank(message) ? "getInternalSelectorDataMessage: " + message : CoreFormatterConstants.EMPTY;
    }

    static String getLazyElementWithExternalMessage(String nameof, LazyElement element, InternalSelectorData internalData, DecoratedList<String> getOrder, ProbabilityData data) {
        var message = (
            getLazyParameterErrorMessage(element, nameof) +
            getInternalSelectorDataMessage(internalData) +
            CoreFormatter.getListNotEnoughMessage(getOrder, "GetOrder list", 1) +
            CoreFormatter.isNullMessageWithName(data, "Probability data")
        );

        return isNotBlank(message) ? "getLazyElementWithExternalMessage: " + message : CoreFormatterConstants.EMPTY;
    }

    static String getLazyElementWithOptionsMessage(LazyElementWithOptionsData data, String nameof) {
        var message = CoreFormatter.isNullMessageWithName(data, "Data");
        if (isBlank(message)) {
            message += getLazyElementWithExternalMessage(nameof, data.element, data.internalData, data.getOrder, data.probabilityData);
        }

        return isNotBlank(message) ? "getLazyElementWithOptionsMessage: " + message : CoreFormatterConstants.EMPTY;
    }

    static String getElementAttributeMessage(Data<LazyElement> data, String value, String parameterName) {
        final var name = isBlank(parameterName) ? "Value" : parameterName;
        var message = CoreFormatter.isInvalidOrFalseMessageWithName(data, "Element data");
        if (isBlank(message)) {
            message += (
                isNullLazyElementMessage(data.object) +
                CoreFormatter.isBlankMessageWithName(value, name)
            );
        }

        return isNotBlank(message) ? "getElementAttributeMessage: " + CoreFormatterConstants.PARAMETER_ISSUES_LINE + message : CoreFormatterConstants.EMPTY;
    }

    static String getCountOfElementsMessage(boolean status, int value) {
        return (status ? value : "No") + " elements found" + CoreFormatterConstants.END_LINE;
    }

    static String isNullWebElementMessage(WebElement element) {
        var message = CoreFormatter.isNullMessageWithName(element, "Element");
        if (isBlank(message)) {
            message += (
                CoreFormatter.isEqualMessage(SeleniumCoreConstants.STOCK_ELEMENT, "Null Selenium Element", element, "Element Parameter") +
                CoreFormatter.isEqualMessage(element.getAttribute("id"), "Element ID", SeleniumFormatterConstants.NULL_ELEMENT_ID, "Null Element ID")
            );
        }

        return isNotBlank(message) ? "isNullWebElementMessage: " + CoreFormatterConstants.PARAMETER_ISSUES_LINE + message + CoreFormatterConstants.END_LINE : CoreFormatterConstants.EMPTY;
    }

    static String isNullWebElementDataMessage(Data<WebElement> element) {
        var message = CoreFormatter.isInvalidOrFalseMessage(element);
        if (isBlank(message)) {
            message += (
                CoreFormatter.isEqualMessage(SeleniumDataConstants.NULL_ELEMENT, "Null Selenium Element Data", element, "Element Data Parameter") +
                isNullWebElementMessage(element.object)
            );
        }

        return isNotBlank(message) ? "isNullWebElementMessage: " + CoreFormatterConstants.PARAMETER_ISSUES_LINE + message + CoreFormatterConstants.END_LINE : CoreFormatterConstants.EMPTY;
    }

    static String getElementsParametersMessage(LazyLocatorList locators, Function<LazyLocator, DriverFunction<WebElementList>> getter) {
        return CoreFormatter.getNamedErrorMessageOrEmpty("getElementsParametersMessage: ", CoreFormatter.isNullOrEmptyMessageWithName(locators, "Lazy Locators List") + CoreFormatter.isNullMessageWithName(getter, "Getter"));
    }

    static String getElementsParametersMessage(LazyLocatorList locators) {
        return CoreFormatter.getNamedErrorMessageOrEmpty("getElementsParametersMessage: ", CoreFormatter.isNullOrEmptyMessageWithName(locators, "Lazy Locators List"));

    }

    static String getSpecificityString(SelectorSpecificsData data) {
        if (NullableFunctions.isNull(data) || NullableFunctions.isNull(data.specifics) || isBlank(data.selector)) {
            return "0-0-0-0";
        }

        final var specifics = data.specifics;
        final var separator = "-";
        return specifics.idsCount + separator + specifics.classesCount + separator + specifics.elementsCount + separator + specifics.validRestCount;
    }
}
