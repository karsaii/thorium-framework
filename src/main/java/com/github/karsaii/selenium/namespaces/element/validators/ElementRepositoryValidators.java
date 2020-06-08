package com.github.karsaii.selenium.namespaces.element.validators;

import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.core.namespaces.validators.CoreFormatter;
import com.github.karsaii.selenium.records.lazy.CachedLazyElementData;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ElementRepositoryValidators {
    static <T> String isInvalidContainsElementMessage(Map<String, CachedLazyElementData> elementRepository, String name, Data<?> defaultValue) {
        var message = (
            CoreFormatter.isNullMessageWithName(elementRepository, "Element Repository") +
            CoreFormatter.isBlankMessageWithName(name, "Name") +
            CoreFormatter.isNullMessageWithName(defaultValue, "Default Data Value")
        );
        return isNotBlank(message) ? "isInvalidContainsElementMessage: " + CoreFormatterConstants.PARAMETER_ISSUES_LINE + message : CoreFormatterConstants.EMPTY;
    }
}
