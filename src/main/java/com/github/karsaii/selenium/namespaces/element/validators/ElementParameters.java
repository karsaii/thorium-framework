package com.github.karsaii.selenium.namespaces.element.validators;

import com.github.karsaii.core.namespaces.validators.CoreFormatter;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.selenium.namespaces.validators.SeleniumFormatter;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.core.records.WaitTimeData;
import com.github.karsaii.core.records.caster.WrappedCastData;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import com.github.karsaii.selenium.abstracts.AbstractLazyElement;
import com.github.karsaii.selenium.records.lazy.LazyElement;
import com.github.karsaii.selenium.records.lazy.LazyElementParameters;
import com.github.karsaii.selenium.records.lazy.filtered.LazyFilteredElementParameters;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;


import static org.apache.commons.lang3.StringUtils.isBlank;
import static com.github.karsaii.selenium.namespaces.utilities.SeleniumUtilities.areNullLazyData;

public interface ElementParameters {
    static String validateWaitTimeData(WaitTimeData timeData) {
        var message = CoreFormatter.isNullMessageWithName(timeData, "TimeData");
        if (isBlank(message)) {
            message += (
                CoreFormatter.isNullMessageWithName(timeData.clock, "TimeData clock") +
                CoreFormatter.isNullMessageWithName(timeData.interval, "TimeData interval") +
                CoreFormatter.isNullMessageWithName(timeData.duration, "TimeData duration")
            );
        }

        return isBlank(message) ? message : (CoreFormatterConstants.PARAMETER_ISSUES + message);
    }
    static <T, V> String validateUntilParameters(Function<T, V> condition, Predicate<V> continueCondition, WaitTimeData timeData) {
        final var message = (
            CoreFormatter.isNullMessageWithName(condition, "Condition") +
            CoreFormatter.isNullMessageWithName(continueCondition, "ContinueCondition") +
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
            CoreFormatter.isNullMessageWithName(castData, "Generic cast type instance") +
            CoreFormatter.isNullMessageWithName(condition, "Condition method") +
            CoreFormatter.isBlankMessageWithName(methodName, "Method name")
        );
    }

    static <T, U> String validateElementMethodParameters(DriverFunction<WebElement> element, WrappedCastData<T> castData, BiPredicate<Method, String> condition, String methodName) {
        return (
            CoreFormatter.isNullMessageWithName(element, "com/github/karsaii/selenium/element") +
            validateCommonElementMethodParamaters(castData, condition, methodName)
        );
    }

    static <T, U> String validateElementMethodParameters(LazyElement element, WrappedCastData<T> castData, BiPredicate<Method, String> condition, String methodName) {
        return validateElementMethodParameters(element.get(), castData, condition, methodName);
    }

    static <T, U> String validateElementMethodParameters(AbstractLazyElement<T> element, WrappedCastData<T> castData, BiPredicate<Method, String> condition, String methodName) {
        return (
            SeleniumFormatter.isNullLazyElementMessage(element) +
            validateCommonElementMethodParamaters(castData, condition, methodName)
        );
    }


}
