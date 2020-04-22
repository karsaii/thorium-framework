package selenium.namespaces.validators;

import core.namespaces.validators.DataValidators;
import core.records.Data;
import data.constants.Strings;
import selenium.namespaces.extensions.boilers.WebElementList;

import static data.namespaces.Formatter.isBlankMessage;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ElementGetterValidators {
    static String isInvalidElementByTextParameters(Data<WebElementList> data, String text) {
        var message = DataValidators.isInvalidOrFalseMessage(data) + isBlankMessage(text, "Text");
        return isNotBlank(message) ? "getElementByTextParametersInvalidMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }
}
