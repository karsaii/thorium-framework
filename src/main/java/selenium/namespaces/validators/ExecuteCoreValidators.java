package selenium.namespaces.validators;

import data.constants.Strings;
import data.namespaces.Formatter;
import selenium.records.ExecuteCoreFunctionData;
import selenium.records.ParametersFieldDefaultsData;

import static data.namespaces.Formatter.isNullMessage;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ExecuteCoreValidators {
    static String isInvalidParametersFieldDefaultsData(ParametersFieldDefaultsData parameterData) {
        final var baseName = "Parameter data";
        var message = isNullMessage(parameterData, baseName);
        if (isBlank(message)) {
            message += (
                isNullMessage(parameterData.handler, baseName + " Handler") +
                isNullMessage(parameterData.validator, baseName + " Validator")
            );
        }

        return isNotBlank(message) ? "isInvalidParametersFieldDefaultsData: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }

    static String isInvalidExecuteCoreFunctionData(ExecuteCoreFunctionData<ParametersFieldDefaultsData> executionData) {
        var message = isNullMessage(executionData, "Execute Core function data");
        if (isBlank(message)) {
            message += (
                Formatter.isBlankMessageWithName(executionData.nameof, "Name of Execution Data") +
                isInvalidParametersFieldDefaultsData(executionData.handler)
            );
        }

        return isNotBlank(message) ? "isInvalidExecuteCoreFunctionData: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }
}