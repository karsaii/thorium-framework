package selenium.constants;

import core.constants.CastDataConstants;
import core.extensions.namespaces.AmountPredicatesFunctions;
import core.extensions.namespaces.NullableFunctions;
import core.namespaces.ExceptionHandlers;
import core.namespaces.InvokeFunctions;
import core.records.reflection.InvokeParametersFieldDefaultsData;
import core.reflection.InvokerParameterizedData;
import core.reflection.InvokerRegularData;
import core.records.reflection.ParameterizedInvokerDefaultsData;
import core.records.reflection.RegularInvokerDefaultsData;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import validators.ScriptExecutions;

public abstract class SeleniumInvokeFunctionDefaults {
    public static final InvokeParametersFieldDefaultsData<WebElement> SINGLE_PARAMETER = new InvokeParametersFieldDefaultsData<>(AmountPredicatesFunctions::isSingle, InvokeFunctions::invokeWithParameters);
    public static final InvokeParametersFieldDefaultsData<SearchContext> SEARCH_CONTEXT_SINGLE_PARAMETER = new InvokeParametersFieldDefaultsData<>(AmountPredicatesFunctions::isSingle, InvokeFunctions::invokeWithParameters);
    public static final InvokeParametersFieldDefaultsData<WebElement> PARAMETERS = new InvokeParametersFieldDefaultsData<>(AmountPredicatesFunctions::isNonZero, InvokeFunctions::invokeWithParameters);

    public static final ParameterizedInvokerDefaultsData<WebElement, Object> OBJECT_PARAMETERS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isValidInvokerParameterizedData,
        CastDataConstants.OBJECT,
        ExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<WebElement, WebElement> WEB_ELEMENT_PARAMETERS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isValidInvokerParameterizedData,
        SeleniumCastDataConstants.WEB_ELEMENT,
        ExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<SearchContext, WebElement> SEARCH_CONTEXT_PARAMETERS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isValidInvokerParameterizedData,
        SeleniumCastDataConstants.WEB_ELEMENT,
        ExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<WebElement, String> STRING_PARAMETERS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isValidInvokerParameterizedData,
        CastDataConstants.STRING,
        ExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<WebElement, Boolean> BOOLEAN_PARAMETERS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isValidInvokerParameterizedData,
        CastDataConstants.BOOLEAN,
        ExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<WebElement, Void> VOID_PARAMETERS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isValidInvokerParameterizedData,
        CastDataConstants.VOID,
        ExceptionHandlers::invokeHandler
    );
    public static final RegularInvokerDefaultsData<WebElement, Object> OBJECT_REGULAR = new RegularInvokerDefaultsData<>(
        InvokerRegularData::new,
        NullableFunctions::isNotNull,
        CastDataConstants.OBJECT,
        ExceptionHandlers::invokeHandler
    );
    public static final RegularInvokerDefaultsData<WebElement, String> STRING_REGULAR = new RegularInvokerDefaultsData<>(
        InvokerRegularData::new,
        NullableFunctions::isNotNull,
        CastDataConstants.STRING,
        ExceptionHandlers::invokeHandler
    );
    public static final RegularInvokerDefaultsData<WebElement, Boolean> BOOLEAN_REGULAR = new RegularInvokerDefaultsData<>(
        InvokerRegularData::new,
        NullableFunctions::isNotNull,
        CastDataConstants.BOOLEAN,
        ExceptionHandlers::invokeHandler
    );
    public static final RegularInvokerDefaultsData<WebElement, Void> VOID_REGULAR = new RegularInvokerDefaultsData<>(
        InvokerRegularData::new,
        NullableFunctions::isNotNull,
        CastDataConstants.VOID,
        ExceptionHandlers::invokeHandler
    );
}
