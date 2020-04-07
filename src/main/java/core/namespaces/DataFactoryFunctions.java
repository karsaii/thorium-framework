package core.namespaces;

import core.records.Data;
import core.records.MethodMessageData;
import data.constants.Strings;
import selenium.enums.CoreConstants;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DataFactoryFunctions {
    static Data<Void> getVoid() {
        return new Data<>(null, false, new MethodMessageData(Strings.EMPTY), CoreConstants.NULL_EXCEPTION, Strings.NULL_EXCEPTION_MESSAGE);
    }

    static <T> Data<T> getWithMethodMessage(T object, boolean status, MethodMessageData messageData, Exception exception) {
        final var isExceptionNull = Objects.isNull(exception);
        final var message = (isExceptionNull ? Strings.EXCEPTION_WAS_NULL : "") + messageData.message;
        final var exceptionMessage = (isExceptionNull ? Strings.EXCEPTION_WAS_NULL : "") + exception.getMessage();
        return getWithNameAndMessage(object, status, messageData.nameof, message, exception, exceptionMessage);
    }

    static <T> Data<T> getWithMethodMessage(T object, boolean status, MethodMessageData message) {
        return getWithMethodMessage(object, status, message, CoreConstants.NULL_EXCEPTION);
    }

    static <T> Data<T> getWithMethodMessage(T object, boolean status, String nameof, MethodMessageData messageData) {
        return new Data<>(object, status, new MethodMessageData(nameof, messageData.message), CoreConstants.NULL_EXCEPTION, Strings.NULL_EXCEPTION_MESSAGE);
    }

    static <T> Data<T> getWithNameAndMessage(T object, boolean status, String nameof, String message, Exception exception, String exceptionMessage) {
        return new Data<>(object, status, new MethodMessageData(nameof, message), exception, exceptionMessage);
    }

    static <T> Data<T> getWithNameAndMessage(T object, boolean status, String nameof, MethodMessageData messageData, Exception exception, String exceptionMessage) {
        return new Data<>(object, status, new MethodMessageData(nameof, messageData.message), exception, exceptionMessage);
    }

    static <T> Data<T> getWithNameAndMessage(T object, boolean status, String nameof, String message, Exception exception) {
        return new Data<>(object, status, new MethodMessageData(nameof, message), exception, exception.getMessage());
    }

    static <T> Data<T> getWithNameAndMessage(T object, boolean status, String nameof, String message) {
        return new Data<>(object, status, new MethodMessageData(nameof, message), CoreConstants.NULL_EXCEPTION, Strings.NULL_EXCEPTION_MESSAGE);
    }

    static <T> Data<T> getWithMessage(T object, boolean status, String nameof, String message, Exception exception, String exceptionMessage) {
        return new Data<>(object, status, new MethodMessageData(nameof, message), exception, exceptionMessage);
    }

    static <T> Data<T> getWithMessage(T object, boolean status, String message, Exception exception, String exceptionMessage) {
        return new Data<>(object, status, new MethodMessageData(message), exception, exceptionMessage);
    }

    static <T> Data<T> getWithMessage(T object, boolean status, String message, Exception exception) {
        return new Data<>(object, status, new MethodMessageData(message), exception, exception.getMessage());
    }

    static <T> Data<T> getWithMessage(T object, boolean status, String message) {
        return new Data<>(object, status, new MethodMessageData(message), CoreConstants.NULL_EXCEPTION, Strings.NULL_EXCEPTION_MESSAGE);
    }

    static <T> Data<T> replaceMethodMessageAndName(Data<T> data, String nameof, MethodMessageData messageData) {
        return new Data<>(data.object, data.status, new MethodMessageData(nameof, messageData.message), data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceMethodMessageData(Data<T> data, MethodMessageData message) {
        return new Data<>(data.object, data.status, message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceNameAndMessage(Data<T> data, String nameof, String message) {
        return new Data<>(data.object, data.status, new MethodMessageData(nameof, message), data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceMessage(Data<T> data, String message) {
        return new Data<>(data.object, data.status, new MethodMessageData(message), data.exception, data.exceptionMessage);
    }

    static <T> Data<T> getErrorFunction(T value, String message) {
        return getWithMethodMessage(value, false, new MethodMessageData(message));
    }

    static <T> Data<T> getErrorFunction(T value, String nameof, String message) {
        return getWithNameAndMessage(value, false, nameof, message);
    }

    static Data<String> getString(String object, String message) {
        return getWithMessage(object, isNotBlank(object), message);
    }

    static Data<String> getString(String object, String nameof, String message) {
        return getWithMethodMessage(object, isNotBlank(object), new MethodMessageData(nameof, message));
    }

    static Data<Boolean> getBoolean(boolean status, String message) {
        return getWithMethodMessage(status, status, new MethodMessageData(message));
    }

    static Data<Boolean> getBoolean(boolean status, String message, Exception exception) {
        return getWithMethodMessage(status, status, new MethodMessageData(message), exception);
    }

    static Data<Boolean> getBoolean(boolean status, String nameof, String message) {
        return getWithMethodMessage(status, status, new MethodMessageData(nameof, message));
    }

    static Data<Boolean> getBoolean(boolean status, String nameof, String message, Exception exception) {
        return getWithMethodMessage(status, status, new MethodMessageData(nameof, message), exception);
    }

    static Data<String> getString(String object, String message, Exception ex, String exceptionMessage) {
        return getWithMessage(object, isNotBlank(object), message, ex, exceptionMessage);
    }

    static Data<Boolean> getBoolean(boolean status, String message, Exception ex, String exceptionMessage) {
        return getWithMessage(status, status, message, ex, exceptionMessage);
    }

    static Data<Boolean> getBoolean(boolean status, String nameof, String message, Exception ex, String exceptionMessage) {
        return getWithMessage(status, status, nameof, message, ex, exceptionMessage);
    }
}
