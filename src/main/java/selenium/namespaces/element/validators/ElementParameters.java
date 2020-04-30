package selenium.namespaces.element.validators;

import selenium.namespaces.extensions.boilers.DriverFunction;
import core.records.MethodSourceData;
import core.records.MethodParametersData;
import core.records.WaitTimeData;
import core.records.caster.WrappedCastData;
import data.constants.Strings;
import data.namespaces.Formatter;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import selenium.abstracts.AbstractLazyElement;
import selenium.records.lazy.LazyElement;
import selenium.records.lazy.LazyElementParameters;
import selenium.records.lazy.filtered.LazyFilteredElementParameters;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;


import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static selenium.namespaces.utilities.SeleniumUtilities.areNullLazyData;

public interface ElementParameters {
    static String validateWaitTimeData(WaitTimeData timeData) {
        var message = Formatter.isNullMessageWithName(timeData, "TimeData");
        if (isBlank(message)) {
            message += (
                Formatter.isNullMessageWithName(timeData.clock, "TimeData clock") +
                Formatter.isNullMessageWithName(timeData.interval, "TimeData interval") +
                Formatter.isNullMessageWithName(timeData.duration, "TimeData duration")
            );
        }

        return isBlank(message) ? message : (Strings.PARAMETER_ISSUES + message);
    }
    static <T, V> String validateUntilParameters(Function<T, V> condition, Predicate<V> continueCondition, WaitTimeData timeData) {
        final var message = (
            Formatter.isNullMessageWithName(condition, "Condition") +
            Formatter.isNullMessageWithName(continueCondition, "ContinueCondition") +
            validateWaitTimeData(timeData)
        );

        return isBlank(message) ? message : ("Wait.until: " + message);
    }

    static boolean isInvalidLazyElemenet(LazyElementParameters data) {
        return Objects.isNull(data) || StringUtils.isBlank(data.getter) || areNullLazyData(data.lazyLocators);
    }

    static boolean isValidLazyElement(LazyElementParameters data) {
        return !isInvalidLazyElemenet(data);
    }

    static boolean isValidLazyIndexedElement(LazyFilteredElementParameters data) {
        return !(isInvalidLazyElemenet(data) || (Objects.isNull(data.filterData)) || (Objects.isNull(data.filterData.filterParameter)));
    }

    static boolean isValidLazyTextFilteredElement(LazyFilteredElementParameters data) {
        return !(isInvalidLazyElemenet(data) || (Objects.isNull(data.filterData)) || (Objects.isNull(data.filterData.filterParameter)));
    }

    static <T> String validateCommonElementMethodParamaters(WrappedCastData<T> castData, BiPredicate<Method, String> condition, String methodName) {
        return (
            Formatter.isNullMessageWithName(castData, "Generic cast type instance") +
            Formatter.isNullMessageWithName(condition, "Condition method") +
            Formatter.isBlankMessageWithName(methodName, "Method name")
        );
    }

    static <T, U> String validateElementMethodParameters(DriverFunction<WebElement> element, WrappedCastData<T> castData, BiPredicate<Method, String> condition, String methodName) {
        return (
            Formatter.isNullMessageWithName(element, "selenium/element") +
            validateCommonElementMethodParamaters(castData, condition, methodName)
        );
    }

    static <T, U> String validateElementMethodParameters(LazyElement element, WrappedCastData<T> castData, BiPredicate<Method, String> condition, String methodName) {
        return validateElementMethodParameters(element.get(), castData, condition, methodName);
    }

    static <T, U> String validateElementMethodParameters(AbstractLazyElement<T> element, WrappedCastData<T> castData, BiPredicate<Method, String> condition, String methodName) {
        return (
            Formatter.isNullLazyElementMessage(element) +
            validateCommonElementMethodParamaters(castData, condition, methodName)
        );
    }

    static String validateMethodSourceData(MethodSourceData data) {
        var message = Formatter.isNullMessageWithName(data, "Method Get Parameters data");
        if (isBlank(message)) {
            message +=  (
                Formatter.isNullMessageWithName(data.methodMap, "Method map") +
                Formatter.isNullMessageWithName(data.list, "Method List") +
                Formatter.isFalseMessageWithName(data.defaultValue, "Default Value")
            );
        }
        return isNotBlank(message) ? "validateMethodGetCommonParametersData: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }

    static String validateMethodParametersData(MethodParametersData parameterData) {
        var message = Formatter.isNullMessageWithName(parameterData, "Method parameters data");
        if (isBlank(message)) {
            message +=  (
                Formatter.isNullMessageWithName(parameterData.validator, "Condition method") +
                Formatter.isBlankMessageWithName(parameterData.methodName, "Method name")
            );
        }

        return isNotBlank(message) ? "validateMethodParametersData: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }


    static String validateGetMethodFromList(MethodSourceData data, MethodParametersData parameterData) {
        return validateMethodSourceData(data) + validateMethodParametersData(parameterData);
    }
}
