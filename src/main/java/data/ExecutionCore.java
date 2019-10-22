package data;

import data.constants.DataDefaults;
import data.constants.Defaults;
import data.constants.Strings;
import data.functions.Executor;
import utilities.utils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.function.Function;
import java.util.function.Predicate;

import static utilities.utils.*;
import static utilities.utils.areNotNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public interface ExecutionCore {
    static <T, Any> DriverFunction<Any> ifDriver(String nameof, boolean status, DriverFunction<T> positive, Data<T> negative) {
        final var lNameof = isBlank(nameof) ? "ifDriver" : nameof;
        return status && areNotNull(positive, negative) ? new DriverFunction<Any>(driver -> {
            var exception = negative.exception;
            var returnException = isNull(exception) ? Defaults.EXCEPTION : exception;
            if (isNull(driver)) {
                return new Data<Any>(
                    (Any)negative.object,
                    negative.status,
                    lNameof + "\n" + negative.message.nameof,
                    negative.message.message + Strings.DRIVER_WAS_NULL,
                    returnException
                );
            }

            final var result = positive.apply(driver);
            exception = result.exception;
            returnException = isNull(exception) ? Defaults.EXCEPTION : exception;
            final var lStatus = isNotNullOrFalseDataOrDataObject(result);
            final var name = (Uncontains(result.message.nameof, lNameof) ? lNameof + "\n" : "") + result.message.nameof;
            return isNonException(exception) ? (
                new Data<Any>((Any)result.object, lStatus, name, result.message.message)
            ) : new Data<Any>((Any)negative.object, lStatus, name, result.message.message, returnException);
        }) : new DriverFunction<Any>((Any)negative.object, negative.status, (Uncontains(negative.message.nameof, lNameof) ? lNameof : "") + negative.message.nameof, negative.message.message, negative.exception);
    }

    static <T, Any> DriverFunction<Any> ifDriver(String nameof, String errorMessage, DriverFunction<T> positive, Data<T> negative) {
        return ifDriver(nameof, isBlank(errorMessage), positive, replaceMessage(negative, nameof, errorMessage));
    }

    static <T, U> DriverFunction<T> ifDriverGuardObject(String nameof, Function<WebDriver, U> dependency, Function<U, Data<T>> positive, T defaultValue) {
        final var negative = new Data<T>(defaultValue, false, "Negative");
        return ifDriver(
            nameof,
            areNotNull(dependency, positive, defaultValue, negative),
            new DriverFunction<T>(driver -> isNotNull(driver) ? positive.apply(dependency.apply(driver)) : negative),
            negative
        );
    }

    static <T, U> DriverFunction<T> ifDriverGuardData(String nameof, Predicate<U> guard, Function<WebDriver, U> dependency, Function<U, Data<T>> positive, Data<T> negative) {
        return ifDriver(
            nameof,
            areNotNull(guard, dependency, positive, negative),
            new DriverFunction<T>(driver -> {
                final var dep = dependency.apply(driver);
                return guard.test(dep) ? positive.apply(dep) : negative;
            }),
            negative
        );
    }

    static <T, U> DriverFunction<T> ifDriverGuardData(String nameof, Function<WebDriver, U> dependency, Function<U, Data<T>> positive, Data<T> negative) {
        return areNotNull(dependency, positive, negative) ? ifDriverGuardData(nameof, utils::isNotNull, dependency, positive, negative) : new DriverFunction<T>(negative);
    }

    static <T, U> DriverFunction<T> ifDriverGuardFunction(String nameof, Function<WebDriver, U> dependency, Function<U, Data<T>> positive, DriverFunction<T> negative) {
        return ifDriver(
            nameof,
            areNotNull(dependency, positive, negative),
            new DriverFunction<T>(driver -> {
                final var dep = dependency.apply(driver);
                return isNotNull(dep) ? positive.apply(dep) : negative.apply(driver);
            }),
            new Data<T>((T) DataDefaults.STOCK_OBJECT, false, "Parameters " + Strings.WAS_NULL)
        );
    }

    static <T, U> DriverFunction<T> ifDriver(String nameof, boolean status, Function<WebDriver, U> dependency, Function<U, Data<T>> positive, Data<T> negative) {
        return ifDriver(
            nameof,
            status && areNotNull(dependency, positive, negative),
            ifDriverGuardData(nameof, dependency, positive, negative),
            negative
        );
    }

    static <T, U> DriverFunction<T> ifDriver(String nameof, DriverFunction<T> positive, Data<T> negative) {
        return ifDriver(nameof, areNotNull(positive, negative), positive, prependMessage(negative, negative.message.nameof, Strings.DRIVER_WAS_NULL + negative.message.message));
    }

    static <T, U> DriverFunction<U> ifDriver(String nameof, Predicate<Data<T>> guard, DriverFunction<T> dependency, Function<Data<T>, Data<U>> positive, Data<U> negative) {
        return ifDriver(nameof, areNotNull(guard, dependency, positive, negative), ifDriverGuardData(nameof, guard, dependency.get(), positive, negative), negative);
    }

    static <T, U> DriverFunction<U> ifDriverFunction(String nameof, Predicate<Data<?>> guard, DriverFunction<T> dependency, Function<Data<T>, DriverFunction<U>> positive, Data<U> negative) {
        return ifDriver(
            nameof,
            areNotNull(guard, dependency, positive, negative),
            new DriverFunction<U>(driver -> {
                final var dep = dependency.apply(driver);
                return guard.test(dep) ? positive.apply(dep).apply(driver) : negative;
            }),
            negative
        );
    }

    static <T, U> Data<U> ifData(String nameof, Predicate<T> guard, T dependency, Function<T, Data<U>> positive, Data<U> negative) {
        return prependMessage((isNotNull(guard) && guard.test(dependency) ? positive.apply(dependency) : negative), nameof, "");
    }

    static <T, U> DriverFunction<U> ifDriver(String nameof, boolean status, Predicate<Data<?>> guard, DriverFunction<T> dependency, Function<Data<T>, Data<U>> positive, Data<U> negative) {
        return ifDriver(
            nameof,
            status && areNotNull(guard, dependency, positive),
            new DriverFunction<U>(driver -> {
                final Data<T> dep = dependency.apply(driver);
                return guard.test(dep) ? positive.apply(dep) : negative;
            }),
            negative
        );
    }

    static <T, U> DriverFunction<U> ifDriver(String nameof, String errorMessage, DriverFunction<T> dependency, Function<Data<T>, Data<U>> positive, Data<U> negative) {
        return ifDriver(nameof, isBlank(errorMessage) && isNotNull(dependency), new DriverFunction<U>(driver -> positive.apply(dependency.apply(driver))), negative);
    }

    static <T, U> DriverFunction<U> ifDriver(String nameof, String errorMessage, Predicate<Data<?>> guard, DriverFunction<T> dependency, Function<Data<T>, Data<U>> positive, Data<U> negative) {
        return ifDriver(
            nameof,
            isBlank(errorMessage) && areNotNull(guard, dependency, positive),
            new DriverFunction<U>(driver -> {
                final Data<T> dep = dependency.apply(driver);
                return guard.test(dep) ? positive.apply(dep) : negative;
            }),
            new Data<U>(replaceMessage(negative, errorMessage))
        );
    }

    static <T, U> DriverFunction<U> ifDriver(String nameof, boolean status, DriverFunction<WebElement> dependency, Function<Data<SearchContext>, DriverFunction<U>> positive, Data<U> negative) {
        return ifDriver(
            nameof,
            status && isNotNull(dependency),
            new DriverFunction<U>(driver -> {
                final var dep = dependency.apply(driver);
                return isNotNullOrFalseDataOrDataObject(dep) ? positive.apply(getSearchContext(dep.object)).apply(driver) : negative;
            }),
            negative
        );
    }

    static <T> DriverFunction<T> ifDriver(String nameof, String errorMessage, DriverFunction<Boolean> dependency, DriverFunction<T> positive, Data<T> negative) {
        return ifDriver(
            nameof,
            isBlank(errorMessage) && areNotNull(dependency, positive) && isNotNullOrFalseDataOrDataObject(negative),
            Executor.execute(dependency, positive),
            negative
        );
    }

    static <T, U> DriverFunction<U> ifDriverWebElement(String nameof, boolean status, DriverFunction<WebElement> dependency, Function<Data<WebElement>, Data<U>> positive, Data<U> negative) {
        return ifDriver(
                nameof,
                status && isNotNull(dependency),
                new DriverFunction<U>(driver -> {
                    final var dep = dependency.apply(driver);
                    return isNotNullOrFalseDataOrDataObject(dep) ? positive.apply(dep) : negative;
                }),
                negative
        );
    }

    static <T, U> DriverFunction<U> ifDriverList(String nameof, boolean status, Predicate<Data<WebElementList>> guard, DriverFunction<WebElementList> dependency, Function<Data<WebElementList>, Data<U>> positive, Data<U> negative) {
        return ifDriver(
            nameof,
            status && areNotNull(guard, dependency),
            new DriverFunction<U>(driver -> {
                final var dep = dependency.apply(driver);
                return guard.test(dep) ? positive.apply(dep) : negative;
            }),
            negative
        );
    }

    static <T, U> DriverFunction<T> ifDriverExecute(String nameof, boolean status, DriverFunction<T> positive) {
        return ifDriver(nameof, status && isNotNull(positive), positive, new Data<T>((T)DataDefaults.STOCK_OBJECT, false, "Execution status was false" + Strings.END_LINE));
    }

    static <T, U> DriverFunction<T> ifDriverExecute(String nameof, boolean status, Function<WebDriver, U> dependency, Function<U, Data<T>> positive) {
        final var negative = new Data<T>((T)DataDefaults.STOCK_OBJECT, false, "Execution status was false" + Strings.END_LINE);
        return ifDriver(
            nameof,
            status && areNotNull(dependency, positive),
            ifDriverGuardData(nameof, dependency, positive, negative),
            negative
        );
    }
}
