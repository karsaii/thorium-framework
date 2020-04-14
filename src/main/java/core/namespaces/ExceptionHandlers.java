package core.namespaces;

import core.exceptions.MethodInvokeException;
import core.records.Data;
import core.records.HandleResultData;
import data.constants.Strings;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import core.constants.CoreConstants;
import validators.ScriptExecutions;

import static core.extensions.namespaces.CoreUtilities.isNonException;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ExceptionHandlers {
    static <CastParameterType, ReturnType> Data<ReturnType> classCastHandler(HandleResultData<CastParameterType, ReturnType> data) {
        final var defaultValue = data.defaultValue;
        final var errorMessage = ScriptExecutions.isInvalidHandlerResultDataMessage(data);
        if (isNotBlank(errorMessage)) {
            return DataFactoryFunctions.getInvalidWithNameAndMessage(defaultValue, "classCastHandler", errorMessage);
        }

        var exception = CoreConstants.EXCEPTION;
        var result = defaultValue;
        try {
            result = data.caster.apply(data.parameter);
        } catch (ClassCastException ex) {
            exception = ex;
        }

        final var status = isNonException(exception);
        return DataFactoryFunctions.getWithMessage(result, status, status ? Strings.INVOCATION_SUCCESSFUL : Strings.INVOCATION_EXCEPTION, exception);
    }

    static <CastParameterType, ReturnType> Data<ReturnType> invokeHandler(HandleResultData<CastParameterType, ReturnType> data) {
        final var defaultValue = data.defaultValue;
        final var errorMessage = ScriptExecutions.isInvalidHandlerResultDataMessage(data);
        if (isNotBlank(errorMessage)) {
            return DataFactoryFunctions.getInvalidWithNameAndMessage(defaultValue, "invokeHandler", errorMessage);
        }

        var exception = CoreConstants.EXCEPTION;
        var result = defaultValue;
        try {
            result = data.caster.apply(data.parameter);
        } catch (
            IllegalArgumentException |
            NoSuchElementException |
            StaleElementReferenceException |
            MethodInvokeException ex
        ) {
            exception = ex;
        }

        final var status = isNonException(exception);
        return DataFactoryFunctions.getWithMessage(result, status, status ? Strings.INVOCATION_SUCCESSFUL : Strings.INVOCATION_EXCEPTION, exception);
    }

    static <CastParameterType, ReturnType> Data<ReturnType> findElementsHandler(HandleResultData<CastParameterType, ReturnType> data) {
        final var defaultValue = data.defaultValue;
        final var errorMessage = ScriptExecutions.isInvalidHandlerResultDataMessage(data);
        if (isNotBlank(errorMessage)) {
            return DataFactoryFunctions.getInvalidWithNameAndMessage(defaultValue, "findElementsHandler", errorMessage);
        }

        var exception = CoreConstants.EXCEPTION;
        var result = defaultValue;
        try {
            result = data.caster.apply(data.parameter);
        } catch (NoSuchElementException | StaleElementReferenceException ex) {
            exception = ex;
        }

        final var status = isNonException(exception);
        return DataFactoryFunctions.getWithMessage(result, status, status ? Strings.FIND_ELEMENTS_SUCCESSFUL : Strings.FIND_ELEMENTS_EXCEPTION, exception);
    }
}
