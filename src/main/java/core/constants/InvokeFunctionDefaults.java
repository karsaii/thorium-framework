package core.constants;

import core.namespaces.ExceptionHandlers;
import core.namespaces.InvokeFunctions;
import core.reflection.InvokeParametersFieldDefaultsData;
import core.reflection.InvokerParameterizedData;
import core.reflection.InvokerRegularData;
import core.reflection.ParameterizedInvokerDefaultsData;
import core.reflection.RegularInvokerDefaultsData;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import validators.ScriptExecutions;

public abstract class InvokeFunctionDefaults {
    public static final InvokeParametersFieldDefaultsData<WebElement> SINGLE_PARAMETER_DEFAULTS = new InvokeParametersFieldDefaultsData<>(ScriptExecutions::isSingleParameter, InvokeFunctions::invokeWithParameters);
    public static final InvokeParametersFieldDefaultsData<SearchContext> SEARCH_CONTEXT_SINGLE_PARAMETER_DEFAULTS = new InvokeParametersFieldDefaultsData<>(ScriptExecutions::isSingleParameter, InvokeFunctions::invokeWithParameters);
    public static final InvokeParametersFieldDefaultsData<WebElement> PARAMETERS_DEFAULTS = new InvokeParametersFieldDefaultsData<>(ScriptExecutions::isNonZeroAmountOfParameters, InvokeFunctions::invokeWithParameters);

    public static final ParameterizedInvokerDefaultsData<WebElement, Object> OBJECT_PARAMETERS_DEFAULTS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isInvalidInvokerParameterizedData,
        CastDataConstants.OBJECT,
        ExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<WebElement, WebElement> WEB_ELEMENT_PARAMETERS_DEFAULTS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isInvalidInvokerParameterizedData,
        CastDataConstants.WEB_ELEMENT,
        ExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<SearchContext, WebElement> SEARCH_CONTEXT_PARAMETERS_DEFAULTS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isInvalidInvokerParameterizedData,
        CastDataConstants.WEB_ELEMENT,
        ExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<WebElement, String> STRING_PARAMETERS_DEFAULTS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isInvalidInvokerParameterizedData,
        CastDataConstants.STRING,
        ExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<WebElement, Boolean> BOOLEAN_PARAMETERS_DEFAULTS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isInvalidInvokerParameterizedData,
        CastDataConstants.BOOLEAN,
        ExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<WebElement, Void> VOID_PARAMETERS_DEFAULTS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isInvalidInvokerParameterizedData,
        CastDataConstants.VOID,
        ExceptionHandlers::invokeHandler
    );
    public static final RegularInvokerDefaultsData<WebElement, Object> OBJECT_REGULAR_DEFAULTS = new RegularInvokerDefaultsData<>(
        InvokerRegularData::new,
        ScriptExecutions::isInvalidInvokerRegularData,
        CastDataConstants.OBJECT,
        ExceptionHandlers::invokeHandler
    );
    public static final RegularInvokerDefaultsData<WebElement, String> STRING_REGULAR_DEFAULTS = new RegularInvokerDefaultsData<>(
        InvokerRegularData::new,
        ScriptExecutions::isInvalidInvokerRegularData,
        CastDataConstants.STRING,
        ExceptionHandlers::invokeHandler
    );
    public static final RegularInvokerDefaultsData<WebElement, Boolean> BOOLEAN_REGULAR_DEFAULTS = new RegularInvokerDefaultsData<>(
        InvokerRegularData::new,
        ScriptExecutions::isInvalidInvokerRegularData,
        CastDataConstants.BOOLEAN,
        ExceptionHandlers::invokeHandler
    );
    public static final RegularInvokerDefaultsData<WebElement, Void> VOID_REGULAR_DEFAULTS = new RegularInvokerDefaultsData<>(
        InvokerRegularData::new,
        ScriptExecutions::isInvalidInvokerRegularData,
        CastDataConstants.VOID,
        ExceptionHandlers::invokeHandler
    );
}
