package formatter;

import data.constants.Defaults;
import data.constants.FormatterStrings;
import data.constants.Strings;
import data.AbstractLazyElement;
import data.enums.SingleGetter;
import data.lazy.tuples.LazyElementWithOptionsData;
import data.tuples.*;
import data.Data;
import data.DriverFunction;
import data.extensions.DecoratedList;
import data.LazyLocatorList;
import data.LazyElement;
import data.LazyLocator;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selectorSpecificity.tuples.SelectorSpecificsData;
import utilities.utils;

import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static utilities.utils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface Formatter {
    static String getOptionMessage(boolean status) {
        return status ? Strings.OPTION_EMPTY : Strings.OPTION_NOT;
    }
    static String isParameterMessage(boolean condition, String parameterName, String descriptor) {
        return condition ? parameterName + " parameter was " + descriptor + Strings.END_LINE : Strings.EMPTY;
    }

    static <T> String isNullMessage(T object, String parameterName) {
        return isParameterMessage(isNull(object), parameterName, "null");
    }

    static String isNullOrFalseDataMessage(Data object, String parameterName) {
        final var result = isParameterMessage(isNullOrFalseDataOrDataObject(object), parameterName, "false data");
        return StringUtils.isBlank(result) ? result : result + object.message;
    }

    static String isNullOrFalseDataMessage(Data object) {
        return isNullOrFalseDataMessage(object, "data");
    }

    static String isNullOrFalseDataWebElementMessage(Data object) {
        return isNullOrFalseDataMessage(object, "data");
    }

    static String isBlankMessage(String object, String parameterName) {
        return isParameterMessage(isBlank(object), parameterName, "blank, empty or null");
    }

    static String isFalseMessage(boolean condition, String parameterName) {
        return isParameterMessage(condition, parameterName, "false");
    }

    static Data<String> getIsValuesMessage(Map<String, String> map, Data<String> object, String expected, Boolean keyCondition, String descriptor, String conditionDescriptor) {
        final var valuesMessage = "Values ";
        final var defaultMessage = "getIsValuesMessage: ";
        final var errorMessage = (
            isBlankMessage(conditionDescriptor, valuesMessage + "conditionDescriptor") +
            isNullMessage(descriptor, valuesMessage + "descriptor") +
            isNullMessage(keyCondition, valuesMessage + "boolean") +
            isNullMessage(map, valuesMessage + " map") +
            isNullMessage(expected, valuesMessage + "expected value") +
            isNullOrFalseDataMessage(object, valuesMessage + "object")
        );

        if (isNotBlank(errorMessage)) {
            return new Data<>(Strings.EMPTY, false, defaultMessage + " " + conditionDescriptor + Strings.PARAMETER_ISSUES + Strings.DEFAULT_ERROR_MESSAGE_STRING + errorMessage);
        }

        final var key = String.valueOf(keyCondition);
        final var localObject = map.getOrDefault(key, Strings.EMPTY);
        final var status = isNotBlank(localObject);
        final var message = defaultMessage + (descriptor + "(" + object + ") " + localObject + conditionDescriptor + " expected (\"" + expected + "\")" + Strings.END_LINE);
        return new Data<>(message, status, message);
    }

    static Data<String> getConditionMessage(String name, Map<String, String> map, boolean keyCondition, String descriptor, String negator) {
        final var nameof = "getConditionMessage: ";
        final var conditionMessage = "Condition descriptor";
        final var errorMessage = (
            isBlankMessage(descriptor, conditionMessage) +
            isNullMessage(negator, conditionMessage + " negator") +
            isNullMessage(map, conditionMessage + " map")
        );

        if (isNotBlank(errorMessage)) {
            return new Data<>(Strings.EMPTY, false, nameof, Strings.PARAMETER_ISSUES + Strings.DEFAULT_ERROR_MESSAGE_STRING + errorMessage);
        }

        final var key = negator + keyCondition;
        final var object = map.getOrDefault(key, Strings.EMPTY);
        final var status = isNotBlank(object);
        final var message = (status ? FormatterStrings.ELEMENT + object + " " + descriptor : ("Value \"" + object + "\" wasn't found by key \"" + key + "\"")) + Strings.END_LINE;
        return new Data<>(message, status, nameof, message);
    }

    static Data<String> getValueMessage(Data<String> data, String descriptor) {
        final var defaultMessage = "getValueMessageString: Returning default empty string" + Strings.END_LINE;
        var message = isNullOrFalseDataMessage(data);
        var object = "";
        if (isNotBlank(message)) {
            object = data.object;
            message += isNullMessage(data.object, "Value");
        }

        message += isBlankMessage(descriptor, "Value descriptor");
        final var status = isBlank(message);
        return new Data<>(
            object,
            status,
            (status ? FormatterStrings.ELEMENT + descriptor + " value found, Is: \"" + object + "\""  : defaultMessage + Strings.PARAMETER_ISSUES + message) + Strings.END_LINE
        );
    }

    static Function<Data<String>, Data<String>> getValueMessage(String descriptor) {
        return data -> getValueMessage(data, descriptor);
    }

    static String getWaitErrorMessage(String message, long timeout, long interval) {
        return FormatterStrings.WAITING_FAILED + message + " tried for " + timeout + " second(s) with " + interval + " milliseconds INTERVAL" + Strings.END_LINE;
    }

    static String getWaitInterruptMessage(String message) {
        return FormatterStrings.WAITING_FAILED + "Thread interruption occurred, exception message:\n" + message;
    }

    static String getExecutionStepMessage(int index, String message) {
        return (index + 1) + ". " + message + Strings.END_LINE;
    }

    static String getExecutionEndMessage(int index, int length, String message) {
        return "Execution End: " + (index == length ? ("All(" + length) : ("Some(" + (index + 1))) + ") steps were executed" + Strings.END_LINE + message;
    }

    static String getMethodFromMapMessage(String methodName, boolean status) {
        return "getMethodFromMap: Method(" + methodName + ") " + getOptionMessage(status) + " found in map.";
    }

    static String getExecuteFragment(boolean status) {
        return status ? Strings.SUCCESSFULLY_EXECUTE : Strings.COULDNT_EXECUTE;
    }

    static String getScrollIntoViewMessage(String message, boolean status) {
        return Formatter.getExecuteFragment(status) + " Scroll into view: " + message + Strings.END_LINE;
    }

    static String getStringExecutionMessage(boolean status) {
        return Formatter.getExecuteFragment(status) + " script" + Strings.END_LINE;
    }

    static String getScreenshotFileName(String path) {
        return (
            path +
            String.join(FormatterStrings.SS_NAME_SEPARATOR, FormatterStrings.NAME_START, LocalTime.now().toString(), UUID.randomUUID().toString()) +
            FormatterStrings.EXTENSION
        );
    }

    static String getMethodFromListMessage(String methodName, boolean status) {
        return "Method(" + methodName + ") " + getOptionMessage(status) + " found" + Strings.END_LINE;
    }

    static String getLocatorErrorMessage(By locator) {
        return isNullMessage(locator, "By locator");
    }

    static String getNestedElementsErrorMessage(By locator, Data<SearchContext> context) {
        return getLocatorErrorMessage(locator) + isNullOrFalseDataMessage(context, "Search Context");
    }

    static String getElementClickableMessage(boolean status, String message) {
        return status ? Strings.ELEMENT_CLICKABLE : FormatterStrings.ELEMENT + "isn't clickable, because: " + message + Strings.END_LINE;
    }

    static String getActionMessage(String message, String dataMessage, boolean status) {
        return Strings.ELEMENT + getOptionMessage(status) + " " + message + Strings.END_LINE + dataMessage + Strings.END_LINE;
    }

    static String getActionWithInputMessage(String message, String dataMessage, String input, boolean status) {
        return Formatter.getActionMessage(message, dataMessage, status) + "Input was: \"" + input + "\"" + Strings.END_LINE;
    }

    static String getInputErrorMessage(String input) {
        return isBlankMessage(input, "Input");
    }

    static String getGetterErrorMessage(Function getter) {
        return isNullMessage(getter, "Getter");
    }

    static String getLocatorAndGetterErrorMessage(By locator, SingleGetter getter) {
        return getLocatorErrorMessage(locator) + isNullMessage(getter, "Getter");
    }

    static String getSendKeysErrorMessage(String errorMessage) {
        return "sendKeys: " + errorMessage + "Not sending" + Strings.END_LINE;
    }

    static String getSendKeysNotSendingMessage(Function getter, String input) {
        final var errorMessage = getGetterErrorMessage(getter) + getInputErrorMessage(input);
        return isBlank(errorMessage) ? Strings.EMPTY : getSendKeysErrorMessage(errorMessage);
    }

    static String getSendKeysNotSendingMessage(By locator, String input, SingleGetter getter) {
        final var errorMessage = getLocatorAndGetterErrorMessage(locator, getter) + getInputErrorMessage(input);
        return isBlank(errorMessage) ? Strings.EMPTY : getSendKeysErrorMessage(errorMessage);
    }

    static String getSendKeysNotSendingMessage(By locator, String input) {
        final var errorMessage = getLocatorErrorMessage(locator) + getInputErrorMessage(input);
        return isBlank(errorMessage) ? Strings.EMPTY : getSendKeysErrorMessage(errorMessage);
    }

    static <T> String getEmptyMessage(T object, String parameterName) {
        var message = "";
        final var isList = (object instanceof List);
        if (isList && ((List)object).isEmpty()) {
            message += parameterName + " List was empty.";
        }

        final var isMap = (object instanceof Map);
        if (isMap && ((Map)object).isEmpty()) {
            message += parameterName + " Map was empty.";
        }

        return message;
    }

    static <T> String isNullLazyElementMessage(AbstractLazyElement<T> object) {
        var errorMessage = isNullMessage(object, "Lazy Element");
        if (isNotBlank(errorMessage)) {
            return errorMessage;
        }

        final var elementParameters = "Element parameters";
        errorMessage += (
            isBlankMessage(object.name, "Element name") +
            isNullMessage(object.parameters, elementParameters) +
            isNullMessage(object.validator, "Element parameter validator")
        );
        if (isNotBlank(errorMessage)) {
            return errorMessage;
        }

        errorMessage += (
            getEmptyMessage(object.parameters, elementParameters)
        );

        return isNotBlank(errorMessage) ? (
            errorMessage
        ) : (isNullAbstractLazyElementParametersList(object.parameters.values(), object.validator) ? elementParameters + "' values were invalid." : Strings.EMPTY);
    }

    static String getSendKeysNotSendingMessage(LazyElement data, String input) {
        var errorMessage = isNullLazyElementMessage(data);
        if (isBlank(errorMessage)) {
            errorMessage += Formatter.getInputErrorMessage(input);
        }
        return isBlank(errorMessage) ? Strings.EMPTY : getSendKeysErrorMessage(errorMessage);
    }

    static String getNullOrFalseActionDataMessage(ActionData data) {
        return isNullOrFalseActionData(data) ? "ActionData" + Strings.WAS_NULL : Strings.EMPTY;
    }

    static String getWhenCoreMessage(Data<Boolean> data) {
        var message = isNullOrFalseDataMessage(data);
        if (isNotBlank(message)) {
            message += Strings.END_LINE + "Wait result was null or false" + Strings.END_LINE;
        }

        return message;
    }

    static String getNotNullOrFalseDataLambdaMessage(boolean status) {
        return "isNotNullOrFalseDataLambda: Data is " + (status ? "okay." : "null or false data.");
    }

    static <T> String getDataNullnessStatusMessage(T object) {
        String errorMessage = "";
        if (object instanceof Data) {
            errorMessage = isNullOrFalseDataMessage((Data)object);
        }
        if (object instanceof LazyElement) {
            errorMessage = isNullLazyElementMessage((LazyElement)object);
        }
        if (isFalse(object)) {
            errorMessage = "Object was false" + Strings.END_LINE;
        }

        return errorMessage;
    }

    static String getShadowRootElementMessage(String message, boolean status) {
        return message + " Root element " + Formatter.getOptionMessage(status) + "found" + Strings.END_LINE;
    }

    static String getElementsAmountMessage(LazyLocator locator, boolean status, int expectedSize, int size) {
        return (status ? expectedSize : (size > 0 ? "Wrong(" + expectedSize + ") amount of" : "No")) + " elements found by: " + locator.toString() + Strings.END_LINE;
    }
    static String getElementAmountErrorMessage(String message, int size) {
        var lMessage = "Wrong amount(" + size + ") of elements found" + Strings.END_LINE;
        if (isNotBlank(message)) {
            lMessage += message + Strings.END_LINE;
        }

        return lMessage;
    }

    static String getFindElementsMessage(LazyLocator locator, int size) {
        return (size > 0 ? size + " amount of" : "No") + " elements found by: " + locator.toString() + Strings.END_LINE;
    }

    static String getInvokeMethodMessage(String message) {
        return isBlank(message) ? Strings.EMPTY : "invokeMethod: " + message;
    }

    static String getMessageIfErrorMessage(String nameof, String message) {
        return isBlank(message) ? Strings.EMPTY : (nameof + ": " + message);
    }

    static String getNumberOfWindowsEqualToMessage(boolean status, int expected, int count) {
        return "Number of browser windows are " + Formatter.getOptionMessage(status)+ " equal. Actual: " + count + ", Expected: " + expected + Strings.END_LINE;
    }

    static String getCommandAmountRangeErrorMessage(int length, int min, int max) {
        var message = "";
        if (length < min) {
            message += "The commands' amount was below the minimum(" + min + ") limit";
        }
        if (length > max) {
            message += "The commands' amount was above the maximum(" + max +") limit";
        }

        return message;
    }

    static Data<String> isNumberConditionCore(boolean status, int number, int expected, String parameterName, String conditionDescriptor, String nameof) {
        var message = (
            isNullMessage(nameof, "Caller function's name") +
            isNullMessage(parameterName, "Name of the parameter") +
            isBlankMessage(conditionDescriptor, "Condition Descriptor")
        );

        final var lNameof = isBlank(nameof) ? "isNumberConditionCore: " : nameof;
        if (isNotBlank(message)) {
            return new Data<>(Strings.EMPTY, false, lNameof, Strings.PARAMETER_ISSUES + message);
        }

        final var lParameterName = isBlank(parameterName) ? "Number" : parameterName;
        final var option = getOptionMessage(status);
        final var object = lParameterName + "(\"" + number + "\") was" + option + " " + conditionDescriptor + " expected(\"" + expected +"\")" + Strings.END_LINE;
        final var returnMessage = "Parameters were" + option + " okay" + Strings.END_LINE;
        return status ? (
            new Data<String>(object, status, lNameof, returnMessage)
        ) : new Data<String>(Strings.EMPTY, status, lNameof, returnMessage + object);
    }

    static Data<String> isEqualToExpected(int number, int expected, String parameterName) {
        return isNumberConditionCore(number == expected, number, expected, parameterName, "equal to", "isEqualToExpected");
    }

    static Data<String> isLessThanExpected(int number, int expected, String parameterName) {
        return isNumberConditionCore(number < expected, number, expected, parameterName, "less than", "isLessThanExpected");
    }

    static Data<String> isMoreThanExpected(int number, int expected, String parameterName) {
        return isNumberConditionCore(number > expected, number, expected, parameterName, "more than", "isMoreThanExpected");
    }

    static String getCommandRangeParameterMessage(CommandRangeData range) {
        var message = isNullMessage(range, "Range object");
        if (isBlank(message)) {
            final var minData = isMoreThanExpected(range.min, 0, "Range minimum");
            final var maxData = isLessThanExpected(range.max, 1000, "Range maximum");
            message += (
                (isNullOrFalseDataOrDataObject(minData) ? minData.message.toString() : Strings.EMPTY) +
                (isNullOrFalseDataOrDataObject(maxData) ? maxData.message.toString() : Strings.EMPTY) +
                isNullMessage(range.rangeInvalidator, "Command Range validator function")
            );
        }

        return isNotBlank(message) ? "getCommandRangeParameterMessage: " + message : Strings.EMPTY;
    }

    static String getCommandAmountRangeErrorMessage(int length, CommandRangeData range) {
        var message = getCommandRangeParameterMessage(range);
        if (isNotBlank(message)) {
            return message + "Cannot invalidate length(\"" + length + "\") on an invalid range" + Strings.END_LINE;
        }

        final var status = range.rangeInvalidator.apply(range.min, length, range.max);
        if (!status) {
            return Strings.EMPTY;
        }

        final var parameterName = "Actual Command amount";
        final var minData = isLessThanExpected(length, range.min, parameterName);
        if (!minData.status) {
            message += minData.object;
        }

        final var maxData = isMoreThanExpected(length, range.max, parameterName);
        if (!maxData.status) {
            message += maxData.object;
        }

        return message;
    }

    static String getCommandAmountRangeErrorMessage(int length) {
        return getCommandAmountRangeErrorMessage(length, Defaults.MINIMUM_COMMAND_LIMIT, Defaults.MAXIMUM_COMMAND_LIMIT);
    }

    static String formatPrefixSuffixMessage(String prefix, Boolean status, String suffix) {
        return prefix + status + suffix;
    }

    static <T> String getSwitchToMessage(boolean status, SwitchResultMessageData<T> data) {
        final var target = data.target;
        var message = data.nameof + (status ? Strings.SUCCESSFULLY_SWITCHED_TO : Strings.COULDNT_SWITCH_TO) + data.type;

        if (!Objects.isNull(target)) {
            message += "(\"" + target + "\")";
        }

        return message + Strings.END_LINE;
    }

    static <T> String getInvokeMethodCommonMessage(Exception exception, String message, String returnType, String parameterTypes) {
        return (
            isException(exception) ? (
                message + Strings.END_LINE +
                "An Exception(" + exception.getClass() + ") has occurred" + Strings.END_LINE +
                "Exception Message:\n" + exception.getMessage() + Strings.END_LINE +
                "Cause: " + exception.getCause() + Strings.END_LINE +
                "Method parameter types: " + parameterTypes + Strings.END_LINE +
                "Result is of type " + returnType + Strings.END_LINE
            ) : Strings.EMPTY
        );
    }

    static <T> String getInvokeMethodCommonMessage(Exception exception, String returnType, String parameterTypes) {
        return isException(exception) ? (
            "An Exception(" + exception.getClass() + ") has occurred" + Strings.END_LINE +
            "Exception Message:\n" + exception.getMessage() + Strings.END_LINE +
            "Cause: " + exception.getCause() + Strings.END_LINE +
            "Method parameter types: " + parameterTypes + Strings.END_LINE +
            "Result is of type " + returnType + Strings.END_LINE
        ) : Strings.EMPTY;
    }

    static String getNullDriverMessage(WebDriver driver) {
        return isNull(driver) ? Strings.DRIVER_WAS_NULL : "Driver wasn't null" + Strings.END_LINE;
    }

    static String getNestedElementErrorMessage(LazyLocatorList locators) {
        var message = "";
        if (locators.isNullOrEmpty()) {
            message += "Locators list was null or empty" + Strings.END_LINE;
        }

        message += isNullMessage(locators.first(), "First locator");
        if (isNotBlank(message)) {
            message = Strings.PARAMETER_ISSUES + message;
        }

        return message;
    }

    static String getNestedElementErrorMessage(WebDriver driver, DecoratedList<By> locators) {
        var message = getNullDriverMessage(driver);
        if (locators.isNullOrEmpty()) {
            message += "Locators list was null or empty" + Strings.END_LINE;
        }

        message += isNullMessage(locators.first(), "First locator");
        if (isNotBlank(message)) {
            message = Strings.PARAMETER_ISSUES + message;
        }

        return message;
    }

    static String getWindowHandlesMessage(boolean status, int size) {
        return "Window handles were" + (!status ? "n't" : " (\"" + size + "\")") + "found" + Strings.END_LINE;
    }

    static String getWindowHandleMessage(boolean status, String handle) {
        return (status ? "Empty window handle name. Cannot use" : "Specific Window handle (\"" + handle + "\") was found") + Strings.END_LINE;
    }

    static String isNullOrEmptyListMessage(List<?> list, String parameterName) {
        var message = isNullMessage(list, parameterName);
        final var name = (isBlank(parameterName) ? "List" : parameterName);
        if (isBlank(message)) {
            message += list.isEmpty() ? name + " was empty" + Strings.END_LINE : Strings.EMPTY;
        }

        return isNotBlank(message) ? message : Strings.EMPTY;
    }

    static <T> String getLazyParameterErrorMessage(LazyElement element, List<Class<?>> classes, Class<T> clazz, String nameof, Data<T> defaultValue) {
        var message = (
            isNullLazyElementMessage(element) +
            isBlankMessage(nameof, "Name of the function") +
            isNullOrEmptyListMessage(classes, "Allowed classes list") +
            isNullMessage(defaultValue, "T type defaultValue") +
            isNullMessage(clazz, "Class reference parameter")
        );
        if (!classes.contains(clazz)) {
            message += (
                "Lazy element passed class: " +
                clazz.getName() +
                " is not in the allowed list of " +
                Defaults.CLASSES_OF_GET_MECHANISMS.toString() +
                Strings.END_LINE
            );
        }

        return isNotBlank(message) ? "getLazyParameterErrorMessage: " + message : Strings.EMPTY;
    }

    static <T> String getLazyParameterErrorMessage(LazyElement element, String nameof) {
        var message = isNullLazyElementMessage(element) + isBlankMessage(nameof, "Name of the function");
        return isNotBlank(message) ? "getLazyParameterErrorMessage: " + message : Strings.EMPTY;
    }

    static String getIsElementConditionMessage(DriverFunction<WebElement> getter, UnaryOperator<Boolean> inverter) {
        return isNullMessage(getter, "Getter") + isNullMessage(inverter, "Boolean inverter");
    }

    static <T> String getGetterErrorMessage(Map<String, Function<LazyLocatorList, DriverFunction<T>>> getterMap, String functionGetter) {
        var message = isNullMessage(getterMap, "Getter map");
        if (isBlank(message)) {
            if (getterMap.isEmpty()) {
                message += "Getter map was empty" + Strings.END_LINE;
            } else {
                if (!getterMap.containsKey(functionGetter)) {
                    message += "Getter was not found in map with " + functionGetter;
                }
            }
        }

        return isNotBlank(message) ? "getGetterErrorMessage: " + message : Strings.EMPTY;
    }

    static String isNotNullLazyDataMessage(LazyLocator locator) {
        var message = isNullMessage(locator, "Lazy Locator");
        if (isBlank(message)) {
            message += isNullMessage(locator.locator, "Lazy locator's value") + isNullMessage(locator.strategy, "Lazy locator's strategy");
        }

        if (isBlank(message)) {
            message += isNullMessage(getLocator(locator), "Actual locator from locator");
        }

        return isNotBlank(message) ? "isNotNullLazyDataMessage: " + message : Strings.EMPTY;
    }

    static String getStringListAsString(List<String> items) {
        return "['" + String.join("', '", items) + "']";
    }

    static String isNullExternalSelectorData(ExternalSelectorData object) {
        final var nameof = "isNullExternalSelectorData";
        var message = isNullMessage(object, "External Selector Data");
        if (isNotBlank(message)) {
            return message;
        }

        final var range = object.range;
        message += getCommandRangeParameterMessage(range);
        if (isBlank(message)) {
            message += getCommandAmountRangeErrorMessage(object.limit, range);
        }
        message += (
                isNullMessage(object.getSelector, "Selector getter function") +
                isNullMessage(object.preferredProperties, "Preferred properties ") +
                isNullMessage(object.selectorType, "Selector type") +
                isNullMessage(object.defaultSelector, "Default Selector value")
        );
        return isNotBlank(message) ? nameof + message : Strings.EMPTY;
    }

    static String getExternalSelectorDataErrorMessage(LazyElement element, ExternalSelectorData externalData, String nameof) {
        var message = isBlankMessage(nameof, "Name of the function") + getLazyParameterErrorMessage(element, nameof) + isNullExternalSelectorData(externalData);
        return isNotBlank(message) ? "getExternalSelectorDataErrorMessage: " + message : Strings.EMPTY;
    }

    static String getSpecificityString(SelectorSpecificsData data) {
        if (isNull(data) || isNull(data.specifics) || isBlank(data.selector)) {
            return "0-0-0-0";
        }

        final var specifics = data.specifics;
        final var separator = "-";
        return specifics.idsCount + separator + specifics.classesCount + separator + specifics.elementsCount + separator + specifics.validRestCount;
    }

    static String getUniqueGeneratedName(String selectorType) {
        return selectorType + "-" + utils.getIncrementalUUID(Defaults.atomicCount) + "-generated";
    }

    static String getProbabilityAdjustmentMessage(String key, double original, double adjusted, boolean increase, boolean generated, boolean belowThreshold) {
        var message = (increase ? "Increased" : "Reduced") + " probability of selector(\"" + original + "\") to \"" + adjusted + "\"" + Strings.END_LINE;
        if (belowThreshold) {
            message += (generated ? "External" : "Regular") + "selector by key(\"" + key + "\") is below threshold(\"" + adjusted + "\"), set to \"0.0\"" + Strings.END_LINE;
        }

        return message;
    }

    static String getNotNullOrFalseDataOrDataObjectMessage(Data<?> data) {
        var message = isNullMessage(data, "Data");
        if (isBlank(message)) {
            message += (
                isNullMessage(data.object, "Data object") +
                isBlankMessage(data.message.toString(), "Data message string")
            );
        }

        return isNotBlank(message) ? "getNotNullOrFalseDataOrDataObjectMessage: " + message + ("Status was " + data.status) : Strings.EMPTY;
    }

    static <T> String getListEmptyMessage(DecoratedList<T> list, String parameterName) {
        final var name = isBlank(parameterName) ? "List" : parameterName;
        var message = "";
        if (list.isNull()) {
            message += name + Strings.WAS_NULL;
        }

        if (isBlank(message) && list.isEmpty()) {
            message += name + "was empty" + Strings.END_LINE;
        }

        return isNotBlank(message) ? "getListEmptyMessage: " + message : Strings.EMPTY;
    }

    static String getExternalSelectorDataMessage(ExternalSelectorData data) {
        var message = isNullMessage(data, "Data");
        if (isBlank(message)) {
            message += (
                isNullMessage(data.getSelector, "Selector Driver provider function") +
                isBlankMessage(data.preferredProperties, "Preferred Properties") +
                isBlankMessage(data.selectorType, "Selector type") +
                getCommandAmountRangeErrorMessage(data.limit, data.range) +
                isNullMessage(data.defaultSelector, "Default Selector Data")
            );
        }

        return isNotBlank(message) ? "getExternalSelectorDataMessage: " + message : Strings.EMPTY;
    }

    static String getInternalSelectorDataMessage(InternalSelectorData internalData) {
        var message = Formatter.isNullMessage(internalData, "Internal Data");
        if (isBlank(message)) {
            message += Formatter.getCommandAmountRangeErrorMessage(internalData.limit, internalData.range);
        }

        return isNotBlank(message) ? "getInternalSelectorDataMessage: " + message : Strings.EMPTY;
    }

    static <T> String getListNotEnoughMessage(DecoratedList<T> list, String parameterName, int expected) {
        final var name = isBlank(parameterName) ? "List" : parameterName;
        var message = "";
        if (list.isNull()) {
            message += name + Strings.WAS_NULL;
        }

        if (isBlank(message) && !list.hasAtleast(expected)) {
            message += name + "length was less than " + expected + Strings.END_LINE;
        }

        return isNotBlank(message) ? "getListEmptyMessage: " + message : Strings.EMPTY;
    }

    static String getLazyElementWithExternalMessage(String nameof, LazyElement element, InternalSelectorData internalData, DecoratedList<String> getOrder, ProbabilityData data) {
        var message = (
            getLazyParameterErrorMessage(element, nameof) +
            getInternalSelectorDataMessage(internalData) +
            getListNotEnoughMessage(getOrder, "GetOrder list", 1) +
            isNullMessage(data, "Probability data")
        );

        return isNotBlank(message) ? "getLazyElementWithExternalMessage: " + message : Strings.EMPTY;
    }

    static String getLazyElementWithOptionsMessage(LazyElementWithOptionsData data, String nameof) {
        var message = isNullMessage(data, "Data");
        if (isBlank(message)) {
            message += getLazyElementWithExternalMessage(nameof, data.element, data.internalData, data.getOrder, data.probabilityData);
        }

        return isNotBlank(message) ? "getLazyElementWithOptionsMessage: " + message : Strings.EMPTY;
    }


}
