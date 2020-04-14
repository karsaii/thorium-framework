package core.namespaces.validators;

import core.records.MethodMessageData;

import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface MethodMessageDataValidators {
    static boolean isValid(MethodMessageData data) {
        return isNotNull(data) && isNotBlank(data.message) && isNotNull(data.nameof);
    }
}
