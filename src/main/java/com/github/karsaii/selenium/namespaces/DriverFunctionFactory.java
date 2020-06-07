package com.github.karsaii.selenium.namespaces;

import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.core.namespaces.DataFactoryFunctions;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.records.MethodMessageData;
import org.openqa.selenium.WebDriver;

import java.util.function.Function;

public interface DriverFunctionFactory {
    static <T> DriverFunction<T> getErrorFunction(String message, T value) {
        return driver -> DataFactoryFunctions.getErrorFunction(value, SeleniumFormatterConstants.DRIVER_WAS_NULL + message);
    }

    static <T> DriverFunction<T> getWithMethodMessage(T object, boolean status, MethodMessageData message) {
        return driver -> DataFactoryFunctions.getWithMethodMessage(object, status, message);
    }

    static <T> DriverFunction<T> getWithMessage(T object, boolean status, String message) {
        return driver -> DataFactoryFunctions.getWithMessage(object, status, message);
    }

    static <T> DriverFunction<T> getWithNameAndMessage(T object, boolean status, String nameof, String message, Exception ex, String exceptionMessage) {
        return driver -> DataFactoryFunctions.getWithNameAndMessage(object, status, nameof, message, ex, exceptionMessage);
    }

    static <T> DriverFunction<T> getWithNameAndMessage(T object, boolean status, String nameof, String message, Exception ex) {
        return driver -> DataFactoryFunctions.getWithNameAndMessage(object, status, nameof, message, ex, ex.getMessage());
    }

    static <T> DriverFunction<T> getWithMessage(T object, boolean status, String message, Exception ex, String exceptionMessage) {
        return driver -> DataFactoryFunctions.getWithMessage(object, status, message, ex, exceptionMessage);
    }

    static <T> DriverFunction<T> getWithMethodMessage(T object, boolean status, MethodMessageData message, Exception ex) {
        return driver -> DataFactoryFunctions.getWithMethodMessage(object, status, message, ex);
    }

    static <T> DriverFunction<T> getWithMessage(T object, String message, Exception ex, String exceptionMessage) {
        return driver -> DataFactoryFunctions.getWithMessage(object, (Boolean)object, message, ex, exceptionMessage);
    }

    static <T> DriverFunction<T> getWithMessage(T object, String message) {
        return driver -> DataFactoryFunctions.getWithMessage(object, (Boolean)object, message);
    }

    static <T> DriverFunction<T> get(Data<T> data) {
        return driver -> DataFactoryFunctions.getWithMessage(data.object, data.status, data.message.toString(), data.exception, data.exceptionMessage);
    }

    static <T> DriverFunction<T> getFunction(Function<WebDriver, Data<T>> function) {
        return function::apply;
    }

    static <T> DriverFunction<T> replaceName(DriverFunction<T> function, String nameof) {
        return driver -> DataFactoryFunctions.replaceName(function.apply(driver), nameof);
    }

    static <T> DriverFunction<T> prependMessage(DriverFunction<T> function, String message) {
        return driver -> DataFactoryFunctions.prependMessage(function.apply(driver), message);
    }

    static <T> DriverFunction<T> replaceMessage(DriverFunction<T> function, String message) {
        return driver -> DataFactoryFunctions.replaceMessage(function.apply(driver), message);
    }

    static <T> DriverFunction<T> appendMessage(DriverFunction<T> function, String message) {
        return driver -> DataFactoryFunctions.appendMessage(function.apply(driver), message);
    }
}
