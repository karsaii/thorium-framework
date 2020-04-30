package selenium.namespaces.validators;

import core.records.HandleResultData;
import data.constants.Strings;

import static data.namespaces.Formatter.isNullMessageWithName;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface HandlerResultDataValidator {
    static <T, U> String isInvalidHandlerResultDataMessage(HandleResultData<T, U> data) {
        final var baseName = "Handle Result Data";
        var message = isNullMessageWithName(data, baseName);
        if (isBlank(message)) {
            message += (
                isNullMessageWithName(data.caster, baseName + " Caster") +
                isNullMessageWithName(data.parameter, baseName + " Parameter")
            );
        }

        return isNotBlank(message) ? "isInvalidHandlerResultDataMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }
}
