package selenium.namespaces.validators;

import core.extensions.namespaces.EmptiableFunctions;
import data.constants.Strings;
import selenium.namespaces.extensions.boilers.LazyLocatorList;
import selenium.records.ElementFilterParameters;

import static data.namespaces.Formatter.getManyGetterErrorMessage;

import static data.namespaces.Formatter.isNullMessage;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ElementFilterParametersValidators {
    static String isNullLazyLocatorListMessage(LazyLocatorList locators) {
        var message = isNullMessage(locators, "Locators");
        if (isBlank(message) && EmptiableFunctions.isEmpty(locators)) {
            message += "List was empty" + Strings.END_LINE;
        }

        return isNotBlank(message) ? "isNullLazyLocatorListMessage: " + Strings.PARAMETER_ISSUES_LINE + message : Strings.EMPTY;
    }

    private static <T> String isInvalidElementFilterParametersMessageCore(ElementFilterParameters data) {
        return isNullLazyLocatorListMessage(data.locators) + getManyGetterErrorMessage(data.getterMap, data.getter);
    }

    static <T> String isInvalidElementFilterParametersMessage(ElementFilterParameters data) {
        var message = isNullMessage(data, "Element Filter Parameters data");
        if (isBlank(message)) {
            message += isInvalidElementFilterParametersMessageCore(data);
        }

        return isNotBlank(message) ? "isInvalidElementIndexFilterParametersMessage: " + message : Strings.EMPTY;
    }

}
