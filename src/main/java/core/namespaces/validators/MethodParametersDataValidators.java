package core.namespaces.validators;

import core.records.MethodParametersData;
import data.constants.Strings;
import data.namespaces.Formatter;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface MethodParametersDataValidators {
    static String isValid(MethodParametersData data) {
        var message = Formatter.isNullMessageWithName(data, "Method Parameter data");
        if (isBlank(message)) {
            message += (
                Formatter.isNullMessageWithName(data.methodName, "Method name") +
                Formatter.isNullMessageWithName(data.validator, "Validator")
            );
        }

        return isNotBlank(message) ? Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }
}
