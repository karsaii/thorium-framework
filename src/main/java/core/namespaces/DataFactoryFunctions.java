package core.namespaces;

import core.constants.CoreDataConstants;
import core.records.Data;
import core.records.MethodMessageData;
import data.constants.Strings;
import core.constants.CoreConstants;

import java.util.Objects;

import static core.extensions.namespaces.CoreUtilities.areNotNull;
import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DataFactoryFunctions {
    static Data<Void> getVoid() {
        return new Data<>(null, false, new MethodMessageData(Strings.EMPTY), CoreConstants.EXCEPTION, Strings.NON_EXCEPTION_MESSAGE);
    }

    static <T> Data<T> getWithMethodMessage(T object, boolean status, MethodMessageData messageData, Exception exception, String exceptionMessage) {
        final var isExceptionNull = Objects.isNull(exception);
        final var message = (isExceptionNull ? Strings.EXCEPTION_WAS_NULL : Strings.EMPTY) + messageData.message;
        final var exMessage = isNotBlank(exceptionMessage) ? exceptionMessage : ((isExceptionNull ? Strings.EXCEPTION_WAS_NULL : exception.getMessage()));
        return getWithNameAndMessage(object, status, messageData.nameof, message, exception, exMessage);
    }

    static <T> Data<T> getWithMethodMessage(T object, boolean status, MethodMessageData messageData, Exception exception) {
        final var exceptionMessage = (Objects.isNull(exception) ? Strings.EXCEPTION_WAS_NULL : exception.getMessage());
        return getWithMethodMessage(object, status, messageData, exception, exceptionMessage);
    }

    static <T> Data<T> getWithMethodMessage(T object, boolean status, MethodMessageData message) {
        return getWithMethodMessage(object, status, message, CoreConstants.EXCEPTION);
    }

    static <T> Data<T> getWithMethodMessage(T object, boolean status, String nameof, MethodMessageData messageData) {
        return new Data<>(object, status, new MethodMessageData(nameof, messageData.message), CoreConstants.EXCEPTION, Strings.NON_EXCEPTION_MESSAGE);
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
        return new Data<>(object, status, new MethodMessageData(nameof, message), CoreConstants.EXCEPTION, Strings.NON_EXCEPTION_MESSAGE);
    }

    static <T> Data<T> getInvalidWithNameAndMessage(T object, String nameof, String message) {
        return getWithNameAndMessage(object, false, nameof, message);
    }

    static Data<Boolean> getInvalidBooleanWithNameAndMessage(String nameof, String message) {
        return getWithNameAndMessage(false, false, nameof, message);
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
        return new Data<>(object, status, new MethodMessageData(message), CoreConstants.EXCEPTION, Strings.NON_EXCEPTION_MESSAGE);
    }

    static <T> Data<T> getErrorFunction(T value, String message) {
        return getWithMethodMessage(value, false, new MethodMessageData(message));
    }

    static <T> Data<T> getErrorFunction(T value, String nameof, String message) {
        return getWithNameAndMessage(value, false, nameof, message);
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

    static <T> Data<T> replaceName(Data<T> data, String nameof) {
        return DataFactoryFunctions.getWithMessage(data.object, data.status, data.message.getMessage(nameof), data.exception, data.exceptionMessage);
    }

    static <T> Data<T> prependMessage(Data<T> data, String message) {
        return DataFactoryFunctions.getWithMessage(data.object, data.status, message + data.message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> prependMessage(Data<T> data, String nameof, String message) {
        return DataFactoryFunctions.getWithMethodMessage(data.object, data.status, new MethodMessageData(nameof, message + data.message.message), data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceMessage(Data<T> data, String nameof, String message) {
        return DataFactoryFunctions.getWithMethodMessage(data.object, data.status, new MethodMessageData(nameof, message), data.exception, data.exceptionMessage);
    }

    static <T> Data<T> appendMessage(Data<T> data, String message) {
        return DataFactoryFunctions.getWithMessage(data.object, data.status, data.message + message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> appendMessage(Data<T> data, String nameof, String message) {
        return DataFactoryFunctions.getWithMethodMessage(data.object, data.status, new MethodMessageData(nameof, data.message.message + message), data.exception, data.exceptionMessage);
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

    static Data<String> getString(String object, String message, Exception exception, String exceptionMessage) {
        return getWithMessage(object, isNotBlank(object), message, exception, exceptionMessage);
    }

    static Data<Boolean> getBoolean(boolean status, String message, Exception exception, String exceptionMessage) {
        return getWithMessage(status, status, message, exception, exceptionMessage);
    }

    static Data<Boolean> getBoolean(boolean status, String nameof, String message, Exception exception, String exceptionMessage) {
        return areNotNull(message, nameof, exception, exceptionMessage) && status ? (
            getWithMessage(status, status, nameof, message, exception, exceptionMessage)
        ) : CoreDataConstants.NULL_BOOLEAN;
    }

    static Data<Object[]> getArrayWithName(Object[] object, boolean status, String nameof) {
        return areNotNull(object, nameof) ? (
            getWithMethodMessage(object, status, new MethodMessageData(nameof, "Element was found. Array of length " + object.length + " was constructed" + Strings.END_LINE))
        ) : CoreDataConstants.NULL_PARAMETER_ARRAY;
    }

    static Data<Object[]> getArrayWithName(Object[] object) {
        return isNotNull(object) ? (
            getWithMethodMessage(object, true, new MethodMessageData("getArrayWithName", "Element was found. Array of length " + object.length + " was constructed" + Strings.END_LINE))
        ) : CoreDataConstants.NULL_PARAMETER_ARRAY;
    }
}
