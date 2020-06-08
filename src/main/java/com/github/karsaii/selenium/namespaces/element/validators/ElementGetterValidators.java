package com.github.karsaii.selenium.namespaces.element.validators;

import com.github.karsaii.core.namespaces.validators.DataValidators;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.core.namespaces.validators.CoreFormatter;
import com.github.karsaii.selenium.namespaces.extensions.boilers.WebElementList;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ElementGetterValidators {
    static String isInvalidElementByTextParameters(Data<WebElementList> data, String text) {
        var message = DataValidators.isInvalidOrFalseMessage(data) + CoreFormatter.isBlankMessageWithName(text, "Text");
        return isNotBlank(message) ? "getElementByTextParametersInvalidMessage: " + CoreFormatterConstants.PARAMETER_ISSUES_LINE + message : CoreFormatterConstants.EMPTY;
    }
}
