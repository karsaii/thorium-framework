package data;

import data.tuples.MethodMessageData;
import org.openqa.selenium.WebDriver;

import java.util.function.Function;

public class DriverFunction<T> implements Function<WebDriver, Data<T>>, IContainedData<WebDriver, T> {
    private final Function<WebDriver, Data<T>> function;

    public DriverFunction(T value, boolean status, MethodMessageData message) {
        this.function = driver -> new Data<>(value, status, message);
    }

    public DriverFunction(T value, boolean status, String message) {
        this.function = driver -> new Data<>(value, status, message);
    }

    public DriverFunction(T value, boolean status, String nameof, String message, Exception ex, String exceptionMessage) {
        this.function = driver -> new Data<>(value, status, nameof, message, ex, exceptionMessage);
    }

    public DriverFunction(T value, boolean status, String nameof, String message, Exception ex) {
        this.function = driver -> new Data<>(value, status, nameof, message, ex, ex.getMessage());
    }

    public DriverFunction(T value, boolean status, String message, Exception ex, String exceptionMessage) {
        this.function = driver -> new Data<>(value, status, message, ex, exceptionMessage);
    }

    public DriverFunction(T value, boolean status, MethodMessageData message, Exception ex) {
        this.function = driver -> new Data<>(value, status, message, ex, ex.getMessage());
    }

    public DriverFunction(T value, String message, Exception ex, String exceptionMessage) {
        this(value, (Boolean)value, message, ex, exceptionMessage);
    }

    public DriverFunction(T value, String message) {
        this(value, (Boolean)value, message);
    }

    public DriverFunction(Data<T> data) {
        this(data.object, data.status, data.message.toString(), data.exception, data.exceptionMessage);
    }

    public DriverFunction(Function<WebDriver, Data<T>> function) {
        this.function = function;
    }

    @Override
    public Data<T> apply(WebDriver webDriver) {
        return function.apply(webDriver);
    }

    @Override
    public <V> Function<V, Data<T>> compose(Function<? super V, ? extends WebDriver> before) {
        return function.compose(before);
    }

    @Override
    public <V> Function<WebDriver, V> andThen(Function<? super Data<T>, ? extends V> after) {
        return function.andThen(after);
    }

    @Override
    public Function<WebDriver, Data<T>> get() {
        return function;
    }

}
