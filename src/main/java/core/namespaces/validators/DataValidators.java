package core.namespaces.validators;

import core.records.Data;

import static core.extensions.namespaces.NullableFunctions.isNotNull;

public interface DataValidators {
    static boolean isValid(Data<?> data) {
        return (isNotNull(data) && MethodMessageDataValidators.isValid(data.message));
    }

    static boolean isValidNonFalse(Data<?> data) {
        return isValid(data) && data.status;
    }

    static boolean isInvalidOrFalse(Data<?> data) {
        return !isValidNonFalse(data);
    }
}
