package selenium.namespaces.validators;

import core.records.Data;
import data.constants.Strings;
import selenium.records.lazy.CachedLazyElementData;

import java.util.Map;

import static data.namespaces.Formatter.isBlankMessage;
import static data.namespaces.Formatter.isNullMessage;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ElementRepositoryValidators {
    static <T> String isInvalidContainsElementMessage(Map<String, CachedLazyElementData> elementRepository, String name, Data<?> defaultValue) {
        var message = (
            isNullMessage(elementRepository, "Element Repository") +
            isBlankMessage(name, "Name") +
            isNullMessage(defaultValue, "Default Data Value")
        );
        return isNotBlank(message) ? "isInvalidContainsElementMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }
}
