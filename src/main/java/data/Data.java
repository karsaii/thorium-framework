package data;

import data.constants.DataDefaults;
import data.constants.Strings;
import data.tuples.MethodMessageData;

import java.util.Objects;

/**
 * The {@code Data} class is a classic data class; the heart of the command or pipe pattern associated.
 * Used for holding multiple return values from a function, as well, meaning essentially a named tuple.
 *
 * The class can hold an exception, and its message as optional parameters.
 *
 * @param <T>
 */
public class Data<T> {
    public final T object;
    public final boolean status;
    public final MethodMessageData message;
    public final Exception exception;
    public final String exceptionMessage;

    /**
     * Allocates a new {@code Data} instance, so that it represents a:
     * - T type Object,
     * - its status during any kind of execution,
     * - a {@code MethodMessageData} instance describing status,
     * - a potential exception that might've occurred or needs to be passed,
     * - a potential exception message, that is either the message of the passed exception,
     * or an overridden one for it.
     *
     * Overloaded constructors exist to simulate default parameters, and some convenience ones
     * for shorter code and less repetition.
     *
     * @param object
     *        A {@code T} type.
     *        Default is null.
     * @param status
     *        A {@code boolean}.
     *        Default is false.
     * @param message
     *        A {@code MethodMessageData}.
     *        Default is empty string.
     * @param exception
     *        A {@code Exception} instance, if any exception occurred, or needs to be passed along the object.
     *        Default is {@link DataDefaults#NULL_EXCEPTION}.
     * @param exceptionMessage
     *        A {@code String} of the exception, or an overriding message for the exception.
     *        Default is {@link Strings#NULL_EXCEPTION_MESSAGE}.
     */
    public Data(T object, boolean status, MethodMessageData message, Exception exception, String exceptionMessage) {
        this.object = object;
        this.status = status;
        this.message = message;
        this.exception = exception;
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * Works just like {@link #Data(Object, boolean, MethodMessageData, Exception, String)}.
     * With no parameters.
     *
     * @see Data(Object, boolean, MethodMessageData, Exception, String)
     */
    public Data() {
        this(null, false, new MethodMessageData(Strings.EMPTY), DataDefaults.NULL_EXCEPTION, Strings.NULL_EXCEPTION_MESSAGE);
    }

    /**
     * Works just like {@link #Data(Object, boolean, MethodMessageData, Exception, String)}.
     * The exception message is the message of the passed Exception.
     *
     * @see Data(Object, boolean, MethodMessageData, Exception, String)
     */
    public Data(T object, boolean status, MethodMessageData message, Exception exception) {
        this(object, status, message.nameof, (Objects.isNull(exception) ? "Exception " + Strings.WAS_NULL : "") + message.message, exception, (Objects.isNull(exception) ? "Exception " + Strings.WAS_NULL : exception.getMessage()));
    }

    /**
     * Works just like {@link #Data(Object, boolean, MethodMessageData, Exception, String)}.
     * The exception, and exceptionMessage parameters are defaults instead.
     *
     * @see Data(Object, boolean, MethodMessageData, Exception, String)
     */
    public Data(T object, boolean status, MethodMessageData message) {
        this(object, status, message, DataDefaults.NULL_EXCEPTION, Strings.NULL_EXCEPTION_MESSAGE);
    }

    /**
     * Works just like {@link #Data(Object, boolean, MethodMessageData, Exception, String)}.
     * The message parameter is an override of the Data instance.
     *
     * @see Data(Object, boolean, MethodMessageData, Exception, String)
     */
    public Data(Data<T> data, MethodMessageData message) {
        this(data.object, data.status, message, data.exception, data.exceptionMessage);
    }

    /**
     * Works just like {@link #Data(Object, boolean, MethodMessageData, Exception, String)}.
     * The message parameter is of the passed Data instance instead.
     *
     * @see Data(Object, boolean, MethodMessageData, Exception, String)
     */
    public Data(Data<T> data) {
        this(data.object, data.status, data.message, data.exception, data.exceptionMessage);
    }

    public Data(T object, boolean status, String message, Exception exception, String exceptionMessage) {
        this(object, status, new MethodMessageData(message), exception, exceptionMessage);
    }

    public Data(T object, boolean status, String nameof, String message, Exception exception, String exceptionMessage) {
        this(object, status, new MethodMessageData(nameof, message), exception, exceptionMessage);
    }

    public Data(T object, boolean status, String nameof, MethodMessageData messageData, Exception exception, String exceptionMessage) {
        this(object, status, new MethodMessageData(nameof, messageData.message), exception, exceptionMessage);
    }

    public Data(T object, boolean status, String message, Exception exception) {
        this(object, status, new MethodMessageData(message), exception);
    }

    public Data(T object, boolean status, String nameof, String message, Exception exception) {
        this(object, status, new MethodMessageData(nameof, message), exception);
    }

    public Data(T object, boolean status, String message) {
        this(object, status, new MethodMessageData(message), DataDefaults.NULL_EXCEPTION, Strings.NULL_EXCEPTION_MESSAGE);
    }

    public Data(T object, boolean status, String nameof, String message) {
        this(object, status, new MethodMessageData(nameof, message), DataDefaults.NULL_EXCEPTION, Strings.NULL_EXCEPTION_MESSAGE);
    }

    public Data(T object, boolean status, String nameof, MethodMessageData messageData) {
        this(object, status, new MethodMessageData(nameof, messageData.message), DataDefaults.NULL_EXCEPTION, Strings.NULL_EXCEPTION_MESSAGE);
    }

    public Data(Data<T> data, String message) {
        this(data.object, data.status, new MethodMessageData(message), data.exception, data.exceptionMessage);
    }

    public Data(Data<T> data, String nameof, String message) {
        this(data.object, data.status, new MethodMessageData(nameof, message), data.exception, data.exceptionMessage);
    }

    public Data(Data<T> data, String nameof, MethodMessageData messageData) {
        this(data.object, data.status, new MethodMessageData(nameof, messageData.message), data.exception, data.exceptionMessage);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (this == o) return true;
        final var data = (Data<?>) o;
        return status == data.status &&
            Objects.equals(object, data.object) &&
            Objects.equals(message, data.message) &&
            Objects.equals(exception, data.exception) &&
            Objects.equals(exceptionMessage, data.exceptionMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, status, message, exception, exceptionMessage);
    }
}
