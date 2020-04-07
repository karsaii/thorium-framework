package core.namespaces;

import core.extensions.namespaces.CoreUtilities;
import core.extensions.namespaces.NullableFunctions;
import core.records.Data;
import core.records.MethodMessageData;
import org.apache.commons.lang3.ArrayUtils;
import selenium.enums.CoreConstants;

import static core.extensions.namespaces.CoreUtilities.Uncontains;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DataFunctions {
    static boolean isValid(MethodMessageData data) {
        return NullableFunctions.isNotNull(data) && isNotBlank(data.message) && isNotBlank(data.nameof);
    }

    static boolean isValid(Data<?> data) {
        return (NullableFunctions.isNotNull(data) && isValid(data.message));
    }

    static <T> boolean isValidNonFalse(Data<T> data) {
        return isValid(data) && data.status;
    }

    static boolean isInvalidOrFalse(Data<?> data) {
        return !isValidNonFalse(data);
    }

    static String getNameIfAbsent(Data<?> data, String nameof) {
        var name = "";
        final var nameNotBlank = isNotBlank(nameof);
        if (NullableFunctions.isNotNull(data)) {
            name = data.message.nameof;
            if (nameNotBlank && Uncontains(data.message.nameof, nameof)) {
                name = nameof + ": " + name;
            }
        } else {
            name = nameNotBlank ? nameof : "getNameIfAbsent";
        }

        return name;
    }

    static boolean isFalse(Data<?> data, int index, int length) {
        return isFalse(data) && (index < length);
    }

    static <T> boolean isFalse(Data<T> data) {
        return isValid(data) && CoreUtilities.isFalse(data.object);
    }

    static boolean isExecuting(Data<?> data, int index, int length) {
        return isValidNonFalse(data) && (index < length);
    }

    static Object[] unwrapToArray(Data<?> data) {
        return isValidNonFalse(data) ? ArrayUtils.toArray(data.object) : CoreConstants.EMPTY_OBJECT_ARRAY;
    }

    static <T> String getMessageFromData(T object) {
        return (object instanceof Data) ? ((Data<?>) object).message.getMessage() : String.valueOf(object);
    }

    static void throwIfException(Data<?> data) throws Exception {
        if (NullableFunctions.isNull(data)) {
            return;
        }

        final var exception = data.exception;
        if (CoreUtilities.isException(exception)) {
            throw exception;
        }
    }

    static <T> Data<T> replaceName(Data<T> data, String nameof) {
        return DataFactoryFunctions.getWithMessage(data.object, data.status, data.message.getMessage(nameof), data.exception, data.exceptionMessage);
    }

    static <T> Data<T> prependMessage(Data<T> data, String message) {
        return DataFactoryFunctions.getWithMessage(data.object, data.status, message + data.message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> prependMessage(Data<T> data, String nameof, String message) {
        return new Data<>(data.object, data.status, new MethodMessageData(nameof, message + data.message.message), data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceMessage(Data<T> data, String message) {
        return DataFactoryFunctions.getWithMessage(data.object, data.status, message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceMessage(Data<T> data, String nameof, String message) {
        return new Data<>(data.object, data.status, new MethodMessageData(nameof, message), data.exception, data.exceptionMessage);
    }

    static <T> Data<T> appendMessage(Data<T> data, String message) {
        return DataFactoryFunctions.getWithMessage(data.object, data.status, data.message + message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> appendMessage(Data<T> data, String nameof, String message) {
        return new Data<>(data.object, data.status, new MethodMessageData(nameof, data.message.message + message), data.exception, data.exceptionMessage);
    }
}
