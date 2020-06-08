package com.github.karsaii.selenium.namespaces.validators;

import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.core.namespaces.validators.CoreFormatter;
import com.github.karsaii.selenium.records.scripter.ExecuteCoreFunctionData;
import com.github.karsaii.selenium.records.scripter.ParametersFieldDefaultsData;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ExecuteCoreValidators {
    static String isInvalidParametersFieldDefaultsData(ParametersFieldDefaultsData parameterData) {
        final var baseName = "Parameter data";
        var message = CoreFormatter.isNullMessageWithName(parameterData, baseName);
        if (isBlank(message)) {
            message += (
                CoreFormatter.isNullMessageWithName(parameterData.handler, baseName + " Handler") +
                CoreFormatter.isNullMessageWithName(parameterData.validator, baseName + " Validator")
            );
        }

        return isNotBlank(message) ? "isInvalidParametersFieldDefaultsData: " + CoreFormatterConstants.PARAMETER_ISSUES_LINE + message : CoreFormatterConstants.EMPTY;
    }

    static String isInvalidExecuteCoreFunctionData(ExecuteCoreFunctionData<ParametersFieldDefaultsData> executionData) {
        var message = CoreFormatter.isNullMessageWithName(executionData, "Execute Core function data");
        if (isBlank(message)) {
            message += (
                CoreFormatter.isBlankMessageWithName(executionData.nameof, "Name of Execution Data") +
                isInvalidParametersFieldDefaultsData(executionData.handler)
            );
        }

        return isNotBlank(message) ? "isInvalidExecuteCoreFunctionData: " + CoreFormatterConstants.PARAMETER_ISSUES_LINE + message : CoreFormatterConstants.EMPTY;
    }
}
