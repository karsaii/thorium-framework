package core.namespaces.validators;

import core.records.MethodParametersData;
import data.constants.Strings;

import static data.namespaces.Formatter.isNullMessage;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface MethodParametersDataValidators {
    static String isValid(MethodParametersData data) {
        var message = isNullMessage(data, "Method Parameter data");
        if (isBlank(message)) {
            message += (
                isNullMessage(data.methodName, "Method name") +
                isNullMessage(data.validator, "Validator")
            );
        }

        return isNotBlank(message) ? Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }
}
