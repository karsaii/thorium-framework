package core.namespaces;

import core.extensions.namespaces.CoreUtilities;
import core.records.Data;
import org.apache.commons.lang3.ArrayUtils;
import core.constants.CoreConstants;

import static core.extensions.namespaces.CoreUtilities.Uncontains;
import static core.extensions.namespaces.CoreUtilities.isException;
import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static core.extensions.namespaces.NullableFunctions.isNull;
import static core.namespaces.validators.DataValidators.isValid;
import static core.namespaces.validators.DataValidators.isValidNonFalse;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DataFunctions {
    static String getNameIfAbsent(Data<?> data, String nameof) {
        var name = "";
        final var nameNotBlank = isNotBlank(nameof);
        if (isNotNull(data)) {
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

    static boolean isFalse(Data<?> data) {
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
        if (isNull(data)) {
            return;
        }

        final var exception = data.exception;
        if (isException(exception)) {
            throw exception;
        }
    }
}
