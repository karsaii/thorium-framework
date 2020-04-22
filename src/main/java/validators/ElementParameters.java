package validators;

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
import selenium.records.LazyElement;
import selenium.records.lazy.LazyElementParameters;
import selenium.records.lazy.LazyIndexedElementParameters;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;


import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static selenium.namespaces.SeleniumUtilities.areNullLazyData;

public interface ElementParameters {
    static String validateWaitTimeData(WaitTimeData timeData) {
        var message = Formatter.isNullMessage(timeData, "TimeData");
        if (isBlank(message)) {
            message += (
                Formatter.isNullMessage(timeData.clock, "TimeData clock") +
                Formatter.isNullMessage(timeData.interval, "TimeData interval") +
                Formatter.isNullMessage(timeData.duration, "TimeData duration")
            );
        }

        return isBlank(message) ? message : (Strings.PARAMETER_ISSUES + message);
    }
    static <T, V> String validateUntilParameters(Function<T, V> condition, Predicate<V> continueCondition, WaitTimeData timeData) {
        final var message = (
            Formatter.isNullMessage(condition, "Condition") +
            Formatter.isNullMessage(continueCondition, "ContinueCondition") +
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

    static boolean isValidLazyIndexedElement(LazyIndexedElementParameters data) {
        return !(isInvalidLazyElemenet(data) || (Objects.isNull(data.filterData)) || (Objects.isNull(data.filterData.filterParameter)));
    }

    static boolean isValidLazyTextFilteredElement(LazyIndexedElementParameters data) {
        return !(isInvalidLazyElemenet(data) || (Objects.isNull(data.filterData)) || (Objects.isNull(data.filterData.filterParameter)));
    }

    static <T> String validateCommonElementMethodParamaters(WrappedCastData<T> castData, BiPredicate<Method, String> condition, String methodName) {
        return (
            Formatter.isNullMessage(castData, "Generic cast type instance") +
            Formatter.isNullMessage(condition, "Condition method") +
            Formatter.isBlankMessage(methodName, "Method name")
        );
    }

    static <T, U> String validateElementMethodParameters(DriverFunction<WebElement> element, WrappedCastData<T> castData, BiPredicate<Method, String> condition, String methodName) {
        return (
            Formatter.isNullMessage(element, "selenium/element") +
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
        var message = Formatter.isNullMessage(data, "Method Get Parameters data");
        if (isBlank(message)) {
            message +=  (
                Formatter.isNullMessage(data.methodMap, "Method map") +
                Formatter.isNullMessage(data.list, "Method List") +
                Formatter.isFalseMessage(data.defaultValue, "Default Value")
            );
        }
        return isNotBlank(message) ? "validateMethodGetCommonParametersData: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }

    static String validateMethodParametersData(MethodParametersData parameterData) {
        var message = Formatter.isNullMessage(parameterData, "Method parameters data");
        if (isBlank(message)) {
            message +=  (
                Formatter.isNullMessage(parameterData.validator, "Condition method") +
                Formatter.isBlankMessage(parameterData.methodName, "Method name")
            );
        }

        return isNotBlank(message) ? "validateMethodParametersData: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }


    static String validateGetMethodFromList(MethodSourceData data, MethodParametersData parameterData) {
        return validateMethodSourceData(data) + validateMethodParametersData(parameterData);
    }
}
