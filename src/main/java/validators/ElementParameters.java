package validators;

import core.extensions.interfaces.DriverFunction;
import core.records.MethodGetCommonParametersData;
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

    static boolean isInvalidLazyIndexedElement(LazyIndexedElementParameters data) {
        return isInvalidLazyElemenet(data) || (Objects.isNull(data.indexData)) || (data.indexData.index < 0);
    }

    static boolean isValidLazyElement(LazyElementParameters data) {
        return !isInvalidLazyElemenet(data);
    }

    static boolean isValidLazyIndexedElement(LazyIndexedElementParameters data) {
        return !isInvalidLazyIndexedElement(data);
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

    static String validateMethodGetCommonParametersData(MethodGetCommonParametersData data) {
        var message = Formatter.isNullMessage(data, "Method Get Parameters data");
        return isNotBlank(message) ? message : (
            Formatter.isNullMessage(data.methodMap, "Method map") +
            Formatter.isNullMessage(data.list, "Method List") +
            Formatter.isNullOrFalseDataMessage(data.defaultValue, "Default Value")
        );
    }

    static String validateMethodParametersData(MethodParametersData parameterData) {
        var message = Formatter.isNullMessage(parameterData, "Method parameters data");
        return isNotBlank(message) ? message : (
            Formatter.isNullMessage(parameterData.validator, "Condition method") +
            Formatter.isBlankMessage(parameterData.methodName, "Method name")
        );
    }


    static String validateGetMethodFromList(MethodGetCommonParametersData data, MethodParametersData parameterData) {
        return validateMethodGetCommonParametersData(data) + validateMethodParametersData(parameterData);
    }
}
