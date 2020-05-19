package data.namespaces;

import core.constants.CommandRangeDataConstants;
import core.constants.CoreDataConstants;
import core.extensions.DecoratedList;
import core.extensions.interfaces.IEmptiable;
import core.extensions.namespaces.BasicPredicateFunctions;
import core.extensions.namespaces.EmptiableFunctions;
import core.namespaces.validators.DataValidators;
import core.records.executor.ExecutionResultData;
import core.records.executor.ExecutionStateData;
import selenium.constants.SeleniumDataConstants;
import selenium.namespaces.extensions.boilers.DriverFunction;
import core.extensions.namespaces.CoreUtilities;
import core.extensions.namespaces.NullableFunctions;
import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import core.records.command.CommandRangeData;
import core.records.reflection.message.InvokeCommonMessageParametersData;
import core.records.reflection.message.InvokeParameterizedMessageData;
import data.constants.FormatterStrings;
import data.constants.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selectorSpecificity.tuples.SelectorSpecificsData;
import selenium.abstracts.AbstractLazyElement;
import selenium.constants.SeleniumCoreConstants;
import selenium.enums.ManyGetter;
import selenium.enums.SingleGetter;
import selenium.records.element.is.ElementConditionParameters;
import selenium.records.element.is.ElementFormatData;
import selenium.abstracts.ElementValueParameters;
import selenium.records.element.is.ElementParameterizedValueParameters;
import selenium.records.ExternalSelectorData;
import selenium.records.InternalSelectorData;
import selenium.records.lazy.LazyElement;
import selenium.namespaces.extensions.boilers.LazyLocatorList;
import selenium.records.ProbabilityData;
import selenium.records.SwitchResultMessageData;
import selenium.records.lazy.LazyElementWithOptionsData;
import selenium.records.lazy.LazyLocator;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static core.extensions.namespaces.CoreUtilities.areAnyBlank;
import static core.extensions.namespaces.CoreUtilities.areAnyNull;
import static core.extensions.namespaces.CoreUtilities.areNotBlank;
import static core.extensions.namespaces.CoreUtilities.areNotNull;
import static core.namespaces.DataFunctions.isTrue;
import static core.namespaces.DataFunctions.isFalse;
import static core.namespaces.validators.DataValidators.isInvalidOrFalse;
import static core.namespaces.validators.DataValidators.isValidNonFalse;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static selenium.namespaces.utilities.SeleniumUtilities.getLocator;

public interface Formatter {
    static String getNamedErrorMessageOrEmptyNoIssues(String name, String message) {
        final var nameof = isNotBlank(name) ? name : "getNamedErrorMessageOrEmpty: (Name was empty.) ";
        return isNotBlank(message) ? nameof + message : Strings.EMPTY;
    }

    static String getNamedErrorMessageOrEmpty(String name, String message) {
        final var nameof = isNotBlank(name) ? name : "getNamedErrorMessageOrEmpty: (Name was empty.) ";
        return isNotBlank(message) ? nameof + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }

    static String getOptionMessage(boolean status) {
        return status ? Strings.OPTION_EMPTY : Strings.OPTION_NOT;
    }

    static String isParameterMessage(boolean condition, String parameterName, String descriptor) {
        return condition ? parameterName + " parameter was " + descriptor + Strings.END_LINE : Strings.EMPTY;
    }

    static <T> String isNullMessageWithName(T object, String parameterName) {
        return isParameterMessage(NullableFunctions.isNull(object), parameterName, "null");
    }

    static <T> String isNullMessage(T object) {
        return isNullMessageWithName(object, "Object");
    }

    static <T> String isEmptyMessage(T[] object) {
        var message = isNullMessageWithName(object, "Objeect");
        if (isBlank(message)) {
            message += object.length < 1 ? "Object is empty" : Strings.EMPTY;
        }

        return getNamedErrorMessageOrEmpty("isEmptyMessage: ", message);
    }

    static String isInvalidOrFalseMessageWithName(Data data, String parameterName) {
        var message = isParameterMessage(isInvalidOrFalse(data), parameterName, "false data");
        if (isNotBlank(message)) {
            message += data.message;
        }

        return getNamedErrorMessageOrEmpty("isInvalidOrFalseMessage: ", message);
    }

    static String isInvalidOrFalseMessage(Data data) {
        return isInvalidOrFalseMessageWithName(data, "data");
    }


    static String isInvalidOrFalseMessageE(ExecutionResultData data) {
        return isNullMessageWithName(data.result, "Result Object");
    }

    static String isFalseMessageWithName(Data data, String parameterName) {
        var message = isParameterMessage(isFalse(data), parameterName, "false data");
        if (isNotBlank(message)) {
            message += data.message;
        }

        return getNamedErrorMessageOrEmpty("isFalseMessageWithName: ", message);
    }

    static String isFalseMessage(Data data) {
        return isFalseMessageWithName(data, "data");
    }

    static String isTrueMessageWithName(Data data, String parameterName) {
        var message = isParameterMessage(isTrue(data), parameterName, "true data");
        if (isNotBlank(message)) {
            message += data.message;
        }

        return getNamedErrorMessageOrEmpty("isTrueMessageWithName: ", message);
    }

    static String isTrueMessage(Data data) {
        return isTrueMessageWithName(data, "data");
    }

    static String isBlankMessageWithName(String value, String parameterName) {
        return isParameterMessage(isBlank(value), parameterName, "blank, empty or null");
    }

    static String isBlankMessage(String value) {
        return isBlankMessageWithName(value, "String value");
    }

    static String isFalseMessageWithName(boolean condition, String parameterName) {
        return isParameterMessage(CoreUtilities.isFalse(condition), parameterName, "false");
    }

    static String isInvalidMessage(boolean condition, String parameterName) {
        return isParameterMessage(CoreUtilities.isFalse(condition), parameterName, "invalid");
    }

    static Data<String> getIsValuesMessage(Map<String, String> map, Data<String> object, String expected, Boolean keyCondition, String descriptor, String conditionDescriptor) {
        final var valuesMessage = "Values ";
        final var defaultMessage = "getIsValuesMessage: ";
        final var errorMessage = (
            isBlankMessageWithName(conditionDescriptor, valuesMessage + "conditionDescriptor") +
            isNullMessageWithName(descriptor, valuesMessage + "descriptor") +
            isNullMessageWithName(keyCondition, valuesMessage + "boolean") +
            isNullMessageWithName(map, valuesMessage + " map") +
            isNullMessageWithName(expected, valuesMessage + "expected value") +
            isInvalidOrFalseMessageWithName(object, valuesMessage + "object")
        );

        if (isNotBlank(errorMessage)) {
            return DataFactoryFunctions.getWithMessage(Strings.EMPTY, false, defaultMessage + " " + conditionDescriptor + Strings.PARAMETER_ISSUES + Strings.DEFAULT_ERROR_MESSAGE_STRING + errorMessage);
        }

        final var key = String.valueOf(keyCondition);
        final var localObject = map.getOrDefault(key, Strings.EMPTY);
        final var status = isNotBlank(localObject);
        final var message = defaultMessage + (descriptor + "(" + object + ") " + localObject + conditionDescriptor + " expected (\"" + expected + "\")" + Strings.END_LINE);
        return DataFactoryFunctions.getWithMessage(message, status, message);
    }

    static String isValidElementFormatData(ElementFormatData<?> parameters) {
        final var baseName = "Element Function Parameters";
        var message = isNullMessageWithName(parameters, baseName);
        if (isBlank(message)) {
            message += (
                isNullMessageWithName(parameters.formatter, baseName + " Formatter") +
                isBlankMessageWithName(parameters.conditionName, baseName + " Name") +
                isBlankMessageWithName(parameters.descriptor, baseName + " Descriptor")
            );
        }

        return getNamedErrorMessageOrEmpty("isValidElementFormatData: ", message);
    }

    static String isValidElementValueParametersMessage(ElementValueParameters<?, ?> parameters) {
        final var baseName = "Element Value Parameters";
        var message = isNullMessageWithName(parameters, baseName);
        if (isBlank(message)) {
            message += (
                isNullMessageWithName(parameters.function, baseName + " Function") +
                isNullMessageWithName(parameters.handler, baseName + " Handler") +
                isValidElementFormatData(parameters.formatData)
            );
        }

        return getNamedErrorMessageOrEmpty("isValidElementValueParametersMessage: ", message);
    }

    static String isValidElementParameterizedValueParametersMessage(ElementParameterizedValueParameters<?> parameters) {
        final var baseName = "Element Parameterized Value Parameters";
        var message = isNullMessageWithName(parameters, baseName);
        if (isBlank(message)) {
            message += (
                isNullMessageWithName(parameters.function, baseName + " Function") +
                isNullMessageWithName(parameters.handler, baseName + " Handler") +
                isValidElementFormatData(parameters.formatData)
            );
        }

        return getNamedErrorMessageOrEmpty("isValidElementParameterizedValueParametersMessage: ", message);
    }

    static String isValidElementConditionParametersMessage(ElementConditionParameters<?> parameters) {
        final var baseName = "Element Regular Condition Function Parameters";
        var message = isValidElementValueParametersMessage(parameters);
        if (isBlank(message)) {
            message += isNullMessageWithName(parameters.inverter, baseName + " Inverter");
        }

        return getNamedErrorMessageOrEmpty("isValidElementConditionParametersMessage: ", message);
    }


    static String getConditionStatusMessage(boolean key) {
        return key ? "is" : "isn't";
    }

    static String getConditionMessage(String elementName, String descriptor, boolean option) {
        final var name = "getConditionMessage: ";
        final var errorMessage = (
            isBlankMessageWithName(elementName, "Element name") +
            isBlankMessageWithName(descriptor, "Descriptor")
        );
        return name + (
            isNotBlank(errorMessage) ? (
                Strings.PARAMETER_ISSUES_LINE + errorMessage
            ) : (FormatterStrings.ELEMENT + getConditionStatusMessage(option) + " " + descriptor + Strings.END_LINE)
        );
    }

    static String getElementValueMessage(String elementName, String descriptor, String value) {
        final var name = "getValueMessage: ";
        final var errorMessage = (
                isBlankMessageWithName(elementName, "Element name") +
                isBlankMessageWithName(descriptor, "Descriptor") +
                isNullMessageWithName(value, "Value")
        );
        return name + (
            isNotBlank(errorMessage) ? (
                Strings.PARAMETER_ISSUES_LINE + errorMessage
            ) : (FormatterStrings.ELEMENT + " " + elementName + " " + descriptor + " was (\"" + value +"\")"  + Strings.END_LINE)
        );
    }

    static Data<String> getValueMessage(Data<String> data, String descriptor) {
        final var defaultMessage = "getValueMessageString: Returning default empty string" + Strings.END_LINE;
        var message = isInvalidOrFalseMessage(data);
        var object = "";
        if (isNotBlank(message)) {
            object = data.object;
            message += isNullMessageWithName(data.object, "Value");
        }

        message += isBlankMessageWithName(descriptor, "Value descriptor");
        final var status = isBlank(message);
        return DataFactoryFunctions.getWithMessage(
            object,
            status,
            (status ? FormatterStrings.ELEMENT + descriptor + " value found, Is: \"" + object + "\""  : defaultMessage + Strings.PARAMETER_ISSUES + message) + Strings.END_LINE
        );
    }

    static Function<Data<String>, Data<String>> getValueMessage(String descriptor) {
        return data -> getValueMessage(data, descriptor);
    }

    static String getWaitErrorMessage(String message, long timeout, long interval) {
        return FormatterStrings.WAITING_FAILED + message + "Tried for " + timeout + " second(s) with " + interval + " milliseconds interval" + Strings.END_LINE;
    }

    static String getWaitInterruptMessage(String message) {
        return FormatterStrings.WAITING_FAILED + "Thread interruption occurred, exception message" + Strings.COLON_NEWLINE + message;
    }

    static String getExecutionStepMessage(int index, String message) {
        return (index + 1) + ". " + message + Strings.END_LINE;
    }

    static String getExecutionEndMessage(int index, int length, String message) {
        return "Execution End: " + (index == length ? ("All(" + length) : ("Some(" + index)) + ") steps were executed" + Strings.COLON_NEWLINE + "\t" + message.replaceAll("\n", "\n\t");
    }

    static String getExecutionStatusInvalidMessage(ExecutionStateData data) {
        var message = isNullMessageWithName(data, "Execution State Data");
        if (isBlank(message)) {
            message += (
                isNullMessageWithName(data.executionMap, "Execution Map") +
                isNullMessageWithName(data.indices, "Indices")
            );
        }

        return getNamedErrorMessageOrEmpty("getExecutionStatusInvalidMessage: ", message);
    }

    static String getExecutionEndParametersInvalidMessage(ExecutionStateData state, String key, int index, int length) {
        return getNamedErrorMessageOrEmpty(
            "getExecutionEndParametersInvalidMessage: ",
            (
                getExecutionStatusInvalidMessage(state) +
                isBlankMessageWithName(key, "Last key from execution") +
                isNegativeMessageWithName(index, "Index") +
                isNegativeMessageWithName(length, "Length")
            )
        );
    }

    static String getExecutionEndMessage(ExecutionStateData state, String key, int index, int length) {
        final var errorMessage = getExecutionEndParametersInvalidMessage(state, key, index, length);
        if (isNotBlank(errorMessage)) {
            return errorMessage;
        }

        final var map = state.executionMap;
        final var valueSet = map.values();
        final var passedValueAmount = valueSet.stream().filter(DataValidators::isValidNonFalse).count();
        final var valuesLength = valueSet.size();
        final var failedValueAmount = valuesLength - passedValueAmount;
        final var builder = new StringBuilder();
        final var values = valueSet.toArray(new Data<?>[0]);
        Data<?> step;
        if (BasicPredicateFunctions.isPositiveNonZero((int)failedValueAmount)) {
            for (var stepIndex = 0; stepIndex < valuesLength; ++stepIndex) {
                step = values[stepIndex];
                builder.append(getExecutionStepMessage(stepIndex, (isValidNonFalse(step) ? "Passed" : "Failed") + Strings.COLON_SPACE + step.message.toString()));
            }
        } else {
            step = map.get(key);
            builder.append(getExecutionStepMessage(valuesLength - 1, (isValidNonFalse(step) ? "Passed" : "Failed") + Strings.COLON_SPACE + step.message.toString()));
        }

        final var message = (
            ((index == length) ? "All" : "Some") + " steps were executed" + Strings.COLON_SPACE +
            (BasicPredicateFunctions.isPositiveNonZero((int)failedValueAmount) ? (
                passedValueAmount + " passed, " + failedValueAmount + " failed"
            ) : ("All(" + passedValueAmount + ") steps passed")) + Strings.END_LINE +
            "    " + builder.toString().replaceAll("\n", "\n    ")
        );

        return getNamedErrorMessageOrEmptyNoIssues("Execution end: ", message);
    }

    static String getExecutionEndMessageAggregate(ExecutionStateData state, String key, int index, int length) {
        final var errorMessage = getExecutionEndParametersInvalidMessage(state, key, index, length);
        if (isNotBlank(errorMessage)) {
            return errorMessage;
        }

        final var valueSet = state.executionMap.values();
        final var passedValueAmount = valueSet.stream().filter(DataValidators::isValidNonFalse).count();
        final var failedValueAmount = length - passedValueAmount;
        final var builder = new StringBuilder();
        final var values = valueSet.toArray(new Data<?>[0]);
        var stepIndex = 0;
        Data<?> step;
        for(; stepIndex < length; ++stepIndex) {
            step = values[stepIndex];
            builder.append(getExecutionStepMessage(stepIndex, (isValidNonFalse(step) ? "Passed" : "Failed") + Strings.COLON_SPACE + step.message.toString()));
        }

        final var message = (
            ((index == length) ? "All" : "Some") + " steps were executed" + Strings.COLON_SPACE +
            (BasicPredicateFunctions.isPositiveNonZero((int)failedValueAmount) ? (
                passedValueAmount + " passed, " + failedValueAmount + " failed"
            ) : ("All(" + passedValueAmount + ") steps passed")) + Strings.END_LINE +
            "    " + builder.toString().replaceAll("\n", "\n    ")
        );

        return getNamedErrorMessageOrEmpty("getExecutionEndMessage", message);
    }

    static String getMethodFromMapMessage(String methodName, boolean status) {
        return "Method(" + methodName + ") " + getOptionMessage(status) + " found in map" + Strings.END_LINE;
    }

    static String getExecuteFragment(boolean status) {
        return status ? Strings.SUCCESSFULLY_EXECUTE : Strings.COULDNT_EXECUTE;
    }

    static String getScrollIntoViewMessage(String message, boolean status) {
        return getExecuteFragment(status) + " Scroll into view: " + message + Strings.END_LINE;
    }

    static String getStringExecutionMessage(boolean status) {
        return getExecuteFragment(status) + " script" + Strings.END_LINE;
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
        return isNullMessageWithName(locator, "By locator");
    }

    static String getNestedElementsErrorMessage(By locator, Data<SearchContext> context) {
        return getLocatorErrorMessage(locator) + isInvalidOrFalseMessageWithName(context, "Search Context");
    }

    static String getElementClickableMessage(boolean status, String message) {
        return status ? Strings.ELEMENT_CLICKABLE : FormatterStrings.ELEMENT + "isn't clickable, because: " + message + Strings.END_LINE;
    }

    static String getActionMessage(String message, String dataMessage, boolean status) {
        return Strings.ELEMENT + getOptionMessage(status) + " " + message + Strings.END_LINE + dataMessage + Strings.END_LINE;
    }

    static String getActionMessage(String message, String dataMessage, String input, boolean status) {
        return getActionMessage(message, dataMessage, status) + "Input was: \"" + input + "\"" + Strings.END_LINE;
    }

    static String getInputErrorMessage(String input) {
        return isBlankMessageWithName(input, "Input");
    }

    static String getGetterErrorMessage(Function getter) {
        return isNullMessageWithName(getter, "Getter");
    }

    static String getLocatorAndGetterErrorMessage(By locator, SingleGetter getter) {
        return getLocatorErrorMessage(locator) + isNullMessageWithName(getter, "Getter");
    }

    static String getSendKeysErrorMessage(String message) {
        return isNotBlank(message) ? "getSendKeysErrorMessage: " + Strings.PARAMETER_ISSUES_LINE + message + "Not sending" + Strings.END_LINE : Strings.EMPTY;
    }

    static String getSendKeysNotSendingMessage(Function getter, String input) {
        return getSendKeysErrorMessage(getGetterErrorMessage(getter) + getInputErrorMessage(input));
    }

    static String getSendKeysNotSendingMessage(By locator, String input, SingleGetter getter) {
        return getSendKeysErrorMessage(getLocatorAndGetterErrorMessage(locator, getter) + getInputErrorMessage(input));
    }

    static String getSendKeysNotSendingMessage(By locator, String input) {
        return getSendKeysErrorMessage(getLocatorErrorMessage(locator) + getInputErrorMessage(input));
    }

    static String getSendKeysNotSendingMessage(LazyElement data, String input) {
        return getSendKeysErrorMessage(isNullLazyElementMessage(data) + getInputErrorMessage(input));
    }

    static <T> String isEmptyMessage(T data, String parameterName) {
        var message = isNullMessageWithName(data, parameterName);
        if (isBlank(message)) {
            //TODO Java13-14 instanceof + switch expression.
            var type = "";
            if (data instanceof List && EmptiableFunctions.isEmpty((List)data)) {
                type = "(List)";
            }

            if (data instanceof Map && EmptiableFunctions.isEmpty((Map)data)) {
                type = "(Map)";
            }

            if (isNotBlank(type)) {
                message += parameterName + type + " was empty" + Strings.END_LINE;
            }
        }

        return getNamedErrorMessageOrEmpty("isEmptyMessage: ", message);
    }

    static <T> String isEmptyMessage(T data) {
        return isEmptyMessage(data, "Emptiable data");
    }

    static <T> String areNullLazyElementParametersMessage(Collection<T> data, Predicate<T> validator) {
        var message = isEmptyMessage(data) + isNullMessageWithName(validator, "Validator");
        var sb = new StringBuilder();
        if (isBlank(message)) {
            var index = 0;
            for(T parameters : data) {
                sb.append(isInvalidMessage(validator.test(parameters), index + ". element data"));
            }
        }

        message += sb.toString();
        return getNamedErrorMessageOrEmpty("areNullLazyElementParametersMessage: ", message);
    }

    static <T> String isNullLazyElementMessage(AbstractLazyElement<T> object) {
        final var baseMessage = "isNullLazyElementMessage: " + Strings.PARAMETER_ISSUES_LINE;
        var message = isNullMessageWithName(object, "Lazy Element");
        if (isNotBlank(message)) {
            return baseMessage + message;
        }
        final var parameters = object.parameters;
        if (isBlank(message)) {
            message += (
                isBlankMessageWithName(object.name, Strings.ELEMENT + " name") +
                isNullMessageWithName(parameters, Strings.ELEMENT_PARAMETERS)
            );
        }
        if (isBlank(message)) {
            message += areNullLazyElementParametersMessage(parameters.values(), object.validator);
        }

        return isNotBlank(message) ? baseMessage + message : Strings.EMPTY;
    }

    static String getShadowRootElementMessage(String message, boolean status) {
        return message + " Root selenium.element " + Formatter.getOptionMessage(status) + "found" + Strings.END_LINE;
    }

    static String getElementsAmountMessage(By locator, boolean status, int expectedSize, int size) {
        return (status ? expectedSize : (size > 0 ? "Wrong(" + expectedSize + ") amount of" : "No")) + " elements found by: " + locator.toString() + Strings.END_LINE;
    }
    static String getElementAmountErrorMessage(String message, int size) {
        var lMessage = "Wrong amount(" + size + ") of elements found" + Strings.END_LINE;
        if (isNotBlank(message)) {
            lMessage += message + Strings.END_LINE;
        }

        return lMessage;
    }

    static String getFindElementsMessage(String locator, int size) {
        return (size > 0 ? size + " amount of" : "No") + " elements found by: " + locator + Strings.END_LINE;
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
            isNullMessageWithName(nameof, "Caller function's name") +
            isBlankMessageWithName(parameterName, "Name of the parameter") +
            isBlankMessageWithName(conditionDescriptor, "Condition Descriptor")
        );

        final var lNameof = isBlank(nameof) ? "isNumberConditionCore" : nameof;
        if (isNotBlank(message)) {
            return DataFactoryFunctions.getWithNameAndMessage(Strings.EMPTY, false, lNameof, Strings.PARAMETER_ISSUES + message);
        }

        final var lParameterName = isBlank(parameterName) ? "Number" : parameterName;
        final var option = getOptionMessage(status);
        final var object = lParameterName + "(\"" + number + "\") was" + option + " " + conditionDescriptor + " expected(\"" + expected +"\")" + Strings.END_LINE;
        final var returnMessage = "Parameters were" + option + " okay." ;
        return status ? (
            DataFactoryFunctions.getWithNameAndMessage(object, status, lNameof, returnMessage + "\n")
        ) : DataFactoryFunctions.getWithNameAndMessage(Strings.EMPTY, status, lNameof, returnMessage + " " + object);
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
        var message = isNullMessageWithName(range, "Range object");
        if (isBlank(message)) {
            final var minData = isMoreThanExpected(range.min, 0, "Range minimum");
            final var maxData = isLessThanExpected(range.max, 1000, "Range maximum");
            message += (
                (isFalse(minData) ? minData.message.toString() : Strings.EMPTY) +
                (isFalse(maxData) ? maxData.message.toString() : Strings.EMPTY) +
                isNullMessageWithName(range.rangeInvalidator, "Command Range validator function")
            );
        }

        return getNamedErrorMessageOrEmpty("getCommandRangeParameterMessage: ", message);
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
        return getCommandAmountRangeErrorMessage(length, CommandRangeDataConstants.MINIMUM_COMMAND_LIMIT, CommandRangeDataConstants.MAXIMUM_COMMAND_LIMIT);
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

    private static <T> String getInvokeMethodCoreMessage(Exception exception, String message, String returnType, String parameterTypes) {
        final var endLine = Strings.END_LINE;
        return CoreUtilities.isException(exception) ? (
            String.join(
                endLine,
                message,
                "An Exception(" + exception.getClass() + ") has occurred",
                "Exception Message:\n" + exception.getMessage(),
                "Cause: " + exception.getCause(),
                "Method parameter types: " + parameterTypes,
                "Result is of type " + returnType
            ) + endLine
        ) : Strings.EMPTY;
    }

    static String getInvokeMethodCommonMessage(InvokeCommonMessageParametersData data, Exception exception) {
        return (areNotNull(data, exception) && areNotBlank(data.message, data.parameterTypes, data.returnType)) ? (
            getInvokeMethodCoreMessage(exception, data.message, data.parameterTypes, data.returnType)
        ) : "Data parameter" + Strings.WAS_NULL;
    }

    static String getInvokeMethodParameterizedMessage(InvokeParameterizedMessageData data, Exception exception) {
        if (areAnyNull(data, exception) || areAnyBlank(data.message, data.parameterTypes, data.returnType)) {
            return "Data parameter" + Strings.WAS_NULL;
        }

        final var parameter = data.parameter;
        final var parameterMessage = (isNotBlank(parameter) ? "Parameter was specified: " + parameter : "Parameter wasn't specified") + Strings.END_LINE;
        final var invokeMessage = getInvokeMethodCoreMessage(exception, data.message, data.parameterTypes, data.returnType);
        return isNotBlank(invokeMessage) ? invokeMessage + parameterMessage : Strings.EMPTY;
    }

    static Function<Exception, String> getInvokeMethodCommonMessageFunction(InvokeCommonMessageParametersData data) {
        return exception -> areAnyNull(data, exception) ? getInvokeMethodCommonMessage(data, exception) : Strings.PARAMETER_ISSUES;
    }

    static Function<Exception, String> getInvokeMethodParameterizedMessageFunction(InvokeParameterizedMessageData data) {
        return exception -> areAnyNull(data, exception) ? getInvokeMethodParameterizedMessage(data, exception) : "Data or exception" + Strings.WAS_NULL;
    }

    static String getNullDriverMessage(WebDriver driver) {
        return NullableFunctions.isNull(driver) ? Strings.DRIVER_WAS_NULL : "Driver wasn't null" + Strings.END_LINE;
    }

    static String getNestedElementErrorMessage(LazyLocatorList locators) {
        var message = "";
        if (locators.isNullOrEmpty()) {
            message += "Locators list was null or empty" + Strings.END_LINE;
        }

        message += isNullMessageWithName(locators.first(), "First locator");
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

        message += isNullMessageWithName(locators.first(), "First locator");
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
        var message = isNullMessageWithName(list, parameterName);
        final var name = (isBlank(parameterName) ? "List" : parameterName);
        if (isBlank(message)) {
            message += list.isEmpty() ? name + " was empty" + Strings.END_LINE : Strings.EMPTY;
        }

        return getNamedErrorMessageOrEmpty("isNullOrEmptyListMessage: ", message);
    }

    static <T> String getLazyParameterErrorMessage(LazyElement element, List<Class<?>> classes, Class<T> clazz, String nameof, Data<T> defaultValue) {
        var message = (
            isNullLazyElementMessage(element) +
            isBlankMessageWithName(nameof, "Name of the function") +
            isNullOrEmptyListMessage(classes, "Allowed classes list") +
            isNullMessageWithName(defaultValue, "T type defaultValue") +
            isNullMessageWithName(clazz, "Class reference parameter")
        );
        if (!classes.contains(clazz)) {
            message += (
                "Lazy selenium.element passed class: " +
                clazz.getName() +
                " is not in the allowed list of " +
                SeleniumCoreConstants.CLASSES_OF_GET_MECHANISMS.toString() +
                Strings.END_LINE
            );
        }

        return getNamedErrorMessageOrEmpty("getLazyParameterErrorMessage: ", message);
    }

    static <T> String getLazyParameterErrorMessage(LazyElement element, String nameof) {
        var message = isNullLazyElementMessage(element) + isBlankMessageWithName(nameof, "Name of the function");
        return getNamedErrorMessageOrEmpty("getLazyParameterErrorMessage: ", message);
    }

    static String getElementConditionMessage(DriverFunction<WebElement> getter, UnaryOperator<Boolean> inverter) {
        var message = isNullMessageWithName(getter, "Getter") + isNullMessageWithName(inverter, "Boolean inverter");
        return getNamedErrorMessageOrEmpty("getElementConditionMessage: ", message);
    }

    static String isElementFunctionMessage(LazyElement element, ElementValueParameters<?, ?> parameters) {
        final var message = isNullLazyElementMessage(element) + isValidElementValueParametersMessage(parameters);
        return getNamedErrorMessageOrEmpty("isElementFunctionMessage: ", message);
    }

    static String isElementFunctionMessage(LazyElement element, ElementParameterizedValueParameters<?> parameters) {
        final var message = isNullLazyElementMessage(element) + isValidElementParameterizedValueParametersMessage(parameters);
        return getNamedErrorMessageOrEmpty("isElementFunctionMessage: ", message);
    }

    static <T> String getManyGetterErrorMessage(Map<ManyGetter, Function<LazyLocatorList, DriverFunction<T>>> getterMap, ManyGetter key) {
        final var nameof = "getManyGetterErrorMessage";
        final var parameterName = "Getter map";
        var message = isNullMessageWithName(getterMap, parameterName);
        if (isNotBlank(message)) {
            return nameof + message;
        }

        if (getterMap.isEmpty()) {
            message += parameterName + " was empty" + Strings.END_LINE;
        } else {
            if (!getterMap.containsKey(key)) {
                message += "Getter was not found in map with " + key.getName();
            }
        }

        return isNotBlank(message) ? nameof + message : Strings.EMPTY;
    }

    static <T> String getSingleGetterErrorMessage(Map<SingleGetter, Function<LazyLocatorList, DriverFunction<T>>> getterMap, SingleGetter key) {
        final var nameof = "getSingleGetterErrorMessage";
        final var parameterName = "Getter map";
        var message = isNullMessageWithName(getterMap, parameterName);
        if (isNotBlank(message)) {
            return nameof + message;
        }

        if (getterMap.isEmpty()) {
            message += parameterName + " was empty" + Strings.END_LINE;
        } else {
            if (!getterMap.containsKey(key)) {
                message += "Getter was not found in map with " + key.getName();
            }
        }

        return isNotBlank(message) ? nameof + message : Strings.EMPTY;
    }

    static String isNullLazyDataMessage(LazyLocator locator) {
        final var parameterName = "Lazy locator";
        var message = isNullMessageWithName(locator, parameterName);
        if (isBlank(message)) {
            message += (
                isNullMessageWithName(locator.locator, parameterName + " value") +
                isNullMessageWithName(locator.strategy, parameterName + " strategy")
            );
        }

        if (isBlank(message)) {
            message += isNullMessageWithName(getLocator(locator), "Actual locator from locator");
        }

        return isNotBlank(message) ? "isNotNullLazyDataMessage: " + message : Strings.EMPTY;
    }

    static String isNullExternalSelectorData(ExternalSelectorData object) {
        final var nameof = "isNullExternalSelectorData";
        var message = isNullMessageWithName(object, "External Selector Data");
        if (isNotBlank(message)) {
            return message;
        }

        final var range = object.range;
        message += getCommandRangeParameterMessage(range);
        if (isBlank(message)) {
            message += getCommandAmountRangeErrorMessage(object.limit, range);
        }
        message += (
            isNullMessageWithName(object.getSelector, "Selector getter function") +
            isNullMessageWithName(object.preferredProperties, "Preferred properties ") +
            isNullMessageWithName(object.selectorType, "Selector type") +
            isNullMessageWithName(object.defaultSelector, "Default Selector value")
        );
        return isNotBlank(message) ? nameof + message : Strings.EMPTY;
    }

    static String getExternalSelectorDataErrorMessage(LazyElement element, ExternalSelectorData externalData, String nameof) {
        var message = isBlankMessageWithName(nameof, "Name of the function") + getLazyParameterErrorMessage(element, nameof) + isNullExternalSelectorData(externalData);
        return isNotBlank(message) ? "getExternalSelectorDataErrorMessage: " + message : Strings.EMPTY;
    }

    static String getSpecificityString(SelectorSpecificsData data) {
        if (NullableFunctions.isNull(data) || NullableFunctions.isNull(data.specifics) || isBlank(data.selector)) {
            return "0-0-0-0";
        }

        final var specifics = data.specifics;
        final var separator = "-";
        return specifics.idsCount + separator + specifics.classesCount + separator + specifics.elementsCount + separator + specifics.validRestCount;
    }

    static String getUniqueGeneratedName(String selectorType) {
        return selectorType + "-" + CoreUtilities.getIncrementalUUID(SeleniumCoreConstants.ATOMIC_COUNT) + "-generated";
    }

    static String getProbabilityAdjustmentMessage(String key, double original, double adjusted, boolean increase, boolean generated, boolean belowThreshold) {
        var message = (increase ? "Increased" : "Reduced") + " probability of selector(\"" + original + "\") to \"" + adjusted + "\"" + Strings.END_LINE;
        if (belowThreshold) {
            message += (generated ? "External" : "Regular") + "selector by key(\"" + key + "\") is below threshold(\"" + adjusted + "\"), set to \"0.0\"" + Strings.END_LINE;
        }

        return message;
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
        var message = isNullMessageWithName(data, "Data");
        if (isBlank(message)) {
            message += (
                isNullMessageWithName(data.getSelector, "Selector Driver provider function") +
                isBlankMessageWithName(data.preferredProperties, "Preferred Properties") +
                isBlankMessageWithName(data.selectorType, "Selector type") +
                getCommandAmountRangeErrorMessage(data.limit, data.range) +
                isNullMessageWithName(data.defaultSelector, "Default Selector Data")
            );
        }

        return isNotBlank(message) ? "getExternalSelectorDataMessage: " + message : Strings.EMPTY;
    }

    static String getInternalSelectorDataMessage(InternalSelectorData internalData) {
        var message = Formatter.isNullMessageWithName(internalData, "Internal Data");
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
            isNullMessageWithName(data, "Probability data")
        );

        return isNotBlank(message) ? "getLazyElementWithExternalMessage: " + message : Strings.EMPTY;
    }

    static String getLazyElementWithOptionsMessage(LazyElementWithOptionsData data, String nameof) {
        var message = isNullMessageWithName(data, "Data");
        if (isBlank(message)) {
            message += getLazyElementWithExternalMessage(nameof, data.element, data.internalData, data.getOrder, data.probabilityData);
        }

        return isNotBlank(message) ? "getLazyElementWithOptionsMessage: " + message : Strings.EMPTY;
    }

    static String getElementAttributeMessage(Data<LazyElement> data, String value, String parameterName) {
        final var name = isBlank(parameterName) ? "Value" : parameterName;
        var message = isInvalidOrFalseMessageWithName(data, "Element data");
        if (isBlank(message)) {
            message += (
                isNullLazyElementMessage(data.object) +
                isBlankMessageWithName(value, name)
            );
        }

        return isNotBlank(message) ? "getElementAttributeMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }

    static String getCountOfElementsMessage(boolean status, int value) {
        return (status ? value : "No") + " elements found" + Strings.END_LINE;
    }

    static Function<Boolean, String> isFormatterNullAndMessageBlank() {
        return status -> Strings.EXECUTION_STATUS_COLON_SPACE + status + Strings.END_LINE;
    }

    static Function<Boolean, String> isFormatterNull(String message) {
        return status -> Strings.EXECUTION_STATUS_COLON_SPACE + status + Strings.END_LINE + "Message: " + message;
    }

    static Function<Boolean, String> isMessageBlank(BiFunction<String, Boolean, String> formatter) {
        return status -> Strings.EXECUTION_STATUS_COLON_SPACE + status + "Message was empty, please fix - result: " + formatter.apply(Strings.EMPTY, status) + Strings.END_LINE;
    }

    static Function<Boolean, String> isFormatterAndMessageValid(BiFunction<String, Boolean, String> formatter, String message) {
        return status -> formatter.apply(message, status);
    }

    static String isEqualMessage(Object left, String leftDescriptor, Object right, String rightDescriptor) {
        var message = isNullMessageWithName(left, "Left Object") + isNullMessageWithName(right, "Right Object");
        if (isBlank(message) && Objects.equals(left, right)) {
            message += (
                (
                    areAnyBlank(leftDescriptor, rightDescriptor) ? "The two objects" : (leftDescriptor + " and " + rightDescriptor)
                ) + " are equal" + Strings.END_LINE
            );
        }

        return isNotBlank(message) ? "isEqualMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }

    static String isNullWebElementMessage(WebElement element) {
        var message = isNullMessageWithName(element, "Element");
        if (isBlank(message)) {
            message += (
                isEqualMessage(SeleniumCoreConstants.STOCK_ELEMENT, "Null Selenium Element", element, "Element Parameter") +
                isEqualMessage(element.getAttribute("id"), "Element ID", Strings.NULL_ELEMENT_ID, "Null Element ID")
            );
        }

        return isNotBlank(message) ? "isNullWebElementMessage: " + Strings.PARAMETER_ISSUES_LINE + message + Strings.END_LINE : Strings.EMPTY;
    }

    static String isNullWebElementDataMessage(Data<WebElement> element) {
        var message = isInvalidOrFalseMessage(element);
        if (isBlank(message)) {
            message += (
                isEqualMessage(SeleniumDataConstants.NULL_ELEMENT, "Null Selenium Element Data", element, "Element Data Parameter") +
                isNullWebElementMessage(element.object)
            );
        }

        return isNotBlank(message) ? "isNullWebElementMessage: " + Strings.PARAMETER_ISSUES_LINE + message + Strings.END_LINE : Strings.EMPTY;
    }

    static String isNegativeMessageWithName(int value, String parameterName) {
        final var name = isNotBlank(parameterName) ? parameterName : "Value parameter";
        final var status = BasicPredicateFunctions.isNegative(value);
        var message = "";
        if (status) {
            message += name + "(\"" + value +"\") is negative" + Strings.END_LINE;
        }

        return isNotBlank(message) ? "isNegativeMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }

    static String isNegativeMessage(int value) {
        return isNegativeMessageWithName(value, "Value parameter");
    }

    static String isNullOrEmpty(IEmptiable emptiable, String parameterName) {
        final var baseName = isNotBlank(parameterName) ? parameterName : "Emptiable";
        var message = isNullMessageWithName(emptiable, baseName);
        if (isBlank(message)) {
            if (emptiable.isEmpty()) {
                message += baseName + " was null or empty" + Strings.END_LINE;
            }
        }

        return getNamedErrorMessageOrEmpty("isNullOrEmpty: ", message);
    }

    static String isNullOrEmpty(IEmptiable emptiable) {
        return isNullOrEmpty(emptiable, "Emptiable");
    }

    static String getExecutionResultKey(String name, int index) {
        return name + "-" + index;
    }
}
