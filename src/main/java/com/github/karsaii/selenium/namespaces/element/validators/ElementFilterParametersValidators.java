package com.github.karsaii.selenium.namespaces.element.validators;

import com.github.karsaii.core.extensions.namespaces.EmptiableFunctions;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.core.namespaces.validators.CoreFormatter;
import com.github.karsaii.selenium.namespaces.extensions.boilers.LazyLocatorList;
import com.github.karsaii.selenium.records.element.finder.ElementFilterParameters;

import static com.github.karsaii.selenium.namespaces.validators.SeleniumFormatter.getManyGetterErrorMessage;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ElementFilterParametersValidators {
    static String isNullLazyLocatorListMessage(LazyLocatorList locators) {
        var message = CoreFormatter.isNullMessageWithName(locators, "Locators");
        if (isBlank(message) && EmptiableFunctions.isEmpty(locators)) {
            message += "List was empty" + CoreFormatterConstants.END_LINE;
        }

        return isNotBlank(message) ? "isNullLazyLocatorListMessage: " + CoreFormatterConstants.PARAMETER_ISSUES_LINE + message : CoreFormatterConstants.EMPTY;
    }

    private static <T> String isInvalidElementFilterParametersMessageCore(ElementFilterParameters data) {
        return isNullLazyLocatorListMessage(data.locators) + getManyGetterErrorMessage(data.getterMap, data.getter);
    }

    static <T> String isInvalidElementFilterParametersMessage(ElementFilterParameters data) {
        var message = CoreFormatter.isNullMessageWithName(data, "Element Filter Parameters data");
        if (isBlank(message)) {
            message += isInvalidElementFilterParametersMessageCore(data);
        }

        return isNotBlank(message) ? "isInvalidElementIndexFilterParametersMessage: " + message : CoreFormatterConstants.EMPTY;
    }

}
