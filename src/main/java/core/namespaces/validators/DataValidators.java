package core.namespaces.validators;

import core.extensions.namespaces.CoreUtilities;
import core.records.Data;

import static core.extensions.namespaces.NullableFunctions.isNotNull;

public interface DataValidators {
    static boolean isValid(Data<?> data) {
        return (isNotNull(data) && MethodMessageDataValidators.isValid(data.message));
    }

    static boolean isInvalid(Data<?> data) {
        return !isValid(data);
    }

    static boolean isValidNonFalse(Data<?> data) {
        return isValid(data) && data.status;
    }

    static boolean isInvalidOrFalse(Data<?> data) {
        return !isValidNonFalse(data);
    }

    static boolean isFalse(Data<?> data) {
        return isNotNull(data) && CoreUtilities.isFalse(data.status);
    }

    static boolean isTrue(Data<?> data) {
        return isNotNull(data) && CoreUtilities.isTrue(data.status);
    }

    static boolean isValidAndFalse(Data<?> data) {
        return isValid(data) && CoreUtilities.isFalse(data.status);
    }
}
