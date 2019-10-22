package validators;

import data.AbstractLazyElement;
import data.DriverFunction;
import data.LazyElement;
import data.lazy.tuples.LazyElementParameters;
import data.lazy.tuples.LazyIndexedElementParameters;
import utilities.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import formatter.Formatter;
import org.openqa.selenium.WebElement;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface ElementParameters {
    static <T, V> String validateUntilParameters(Function<T, V> condition, Predicate<V> continueCondition) {
        final var message = (
            Formatter.isNullMessage(condition, "Condition") +
            Formatter.isNullMessage(continueCondition, "ContinueCondition")
        );

        return isBlank(message) ? message : ("Wait.until: " + message);
    }

    static boolean isInvalidLazyElemenet(LazyElementParameters data) {
        return Objects.isNull(data) || StringUtils.isBlank(data.getter) || utils.areNullLazyData(data.lazyLocators);
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

    static <T> String validateCommonElementMethodParamaters(Class<T> clazz, BiPredicate<Method, String> condition, String methodName) {
        return (
            Formatter.isNullMessage(clazz, "Generic cast type instance") +
            Formatter.isNullMessage(condition, "Condition method") +
            Formatter.isBlankMessage(methodName, "Method name")
        );
    }

    static <T, U> String validateElementMethodParameters(DriverFunction<WebElement> element, Class<U> clazz, BiPredicate<Method, String> condition, String methodName) {
        return (
            Formatter.isNullMessage(element, "element") +
            validateCommonElementMethodParamaters(clazz, condition, methodName)
        );
    }

    static <T, U> String validateElementMethodParameters(LazyElement element, Class<U> clazz, BiPredicate<Method, String> condition, String methodName) {
        return validateElementMethodParameters(element.get(), clazz, condition, methodName);
    }

    static <T, U> String validateElementMethodParameters(AbstractLazyElement<T> element, Class<U> clazz, BiPredicate<Method, String> condition, String methodName) {
        return (
            Formatter.isNullLazyElementMessage(element) +
            validateCommonElementMethodParamaters(clazz, condition, methodName)
        );
    }

    static String validateGetMethodFromList(BiPredicate<Method, String> condition, String methodName) {
        return (
            Formatter.isNullMessage(condition, "Condition method") +
            Formatter.isBlankMessage(methodName, "Method name")
        );
    }
}
