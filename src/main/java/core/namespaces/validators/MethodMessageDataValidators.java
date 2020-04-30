package core.namespaces.validators;

import core.records.MethodMessageData;
import data.constants.Strings;
import data.namespaces.Formatter;

import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface MethodMessageDataValidators {
    static boolean isValid(MethodMessageData data) {
        return isNotNull(data) && isNotBlank(data.message) && isNotNull(data.nameof);
    }

    static String isInvalidMessage(MethodMessageData data) {
        final var baseName = "Data";
        var message = Formatter.isNullMessageWithName(data, baseName);
        if (isBlank(message)) {
            message += Formatter.isBlankMessageWithName(data.message, baseName + " Message");
        }

        final var nameParameterDescriptor = baseName + " Name of source";
        message += isNotBlank(message) ? Formatter.isBlankMessageWithName(data.nameof, nameParameterDescriptor) : Formatter.isNullMessageWithName(data.nameof, nameParameterDescriptor);

        return isNotBlank(message) ? "isInvalidMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }
}
