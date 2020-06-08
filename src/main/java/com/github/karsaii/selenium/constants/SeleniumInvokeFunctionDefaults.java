package com.github.karsaii.selenium.constants;

import com.github.karsaii.core.constants.CastDataConstants;
import com.github.karsaii.core.extensions.namespaces.AmountPredicatesFunctions;
import com.github.karsaii.core.extensions.namespaces.NullableFunctions;
import com.github.karsaii.core.namespaces.InvokeFunctions;
import com.github.karsaii.core.records.reflection.InvokeParametersFieldDefaultsData;
import com.github.karsaii.core.reflection.InvokerParameterizedData;
import com.github.karsaii.core.reflection.InvokerRegularData;
import com.github.karsaii.core.records.reflection.ParameterizedInvokerDefaultsData;
import com.github.karsaii.core.records.reflection.RegularInvokerDefaultsData;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import com.github.karsaii.selenium.namespaces.SeleniumExceptionHandlers;
import com.github.karsaii.selenium.namespaces.validators.ScriptExecutions;

public abstract class SeleniumInvokeFunctionDefaults {
    public static final InvokeParametersFieldDefaultsData<WebElement> SINGLE_PARAMETER = new InvokeParametersFieldDefaultsData<>(AmountPredicatesFunctions::isSingle, InvokeFunctions::invokeWithParameters);
    public static final InvokeParametersFieldDefaultsData<SearchContext> SEARCH_CONTEXT_SINGLE_PARAMETER = new InvokeParametersFieldDefaultsData<>(AmountPredicatesFunctions::isSingle, InvokeFunctions::invokeWithParameters);
    public static final InvokeParametersFieldDefaultsData<WebElement> PARAMETERS = new InvokeParametersFieldDefaultsData<>(AmountPredicatesFunctions::isNonZero, InvokeFunctions::invokeWithParameters);

    public static final ParameterizedInvokerDefaultsData<WebElement, Object> OBJECT_PARAMETERS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isValidInvokerParameterizedData,
        CastDataConstants.OBJECT,
        SeleniumExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<WebElement, WebElement> WEB_ELEMENT_PARAMETERS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isValidInvokerParameterizedData,
        SeleniumCastDataConstants.WEB_ELEMENT,
        SeleniumExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<SearchContext, WebElement> SEARCH_CONTEXT_PARAMETERS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isValidInvokerParameterizedData,
        SeleniumCastDataConstants.WEB_ELEMENT,
        SeleniumExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<WebElement, String> STRING_PARAMETERS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isValidInvokerParameterizedData,
        CastDataConstants.STRING,
        SeleniumExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<WebElement, Boolean> BOOLEAN_PARAMETERS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isValidInvokerParameterizedData,
        CastDataConstants.BOOLEAN,
        SeleniumExceptionHandlers::invokeHandler
    );
    public static final ParameterizedInvokerDefaultsData<WebElement, Void> VOID_PARAMETERS = new ParameterizedInvokerDefaultsData<>(
        InvokerParameterizedData::new,
        ScriptExecutions::isValidInvokerParameterizedData,
        CastDataConstants.VOID,
        SeleniumExceptionHandlers::invokeHandler
    );
    public static final RegularInvokerDefaultsData<WebElement, Object> OBJECT_REGULAR = new RegularInvokerDefaultsData<>(
        InvokerRegularData::new,
        NullableFunctions::isNotNull,
        CastDataConstants.OBJECT,
        SeleniumExceptionHandlers::invokeHandler
    );
    public static final RegularInvokerDefaultsData<WebElement, String> STRING_REGULAR = new RegularInvokerDefaultsData<>(
        InvokerRegularData::new,
        NullableFunctions::isNotNull,
        CastDataConstants.STRING,
        SeleniumExceptionHandlers::invokeHandler
    );
    public static final RegularInvokerDefaultsData<WebElement, Boolean> BOOLEAN_REGULAR = new RegularInvokerDefaultsData<>(
        InvokerRegularData::new,
        NullableFunctions::isNotNull,
        CastDataConstants.BOOLEAN,
        SeleniumExceptionHandlers::invokeHandler
    );
    public static final RegularInvokerDefaultsData<WebElement, Void> VOID_REGULAR = new RegularInvokerDefaultsData<>(
        InvokerRegularData::new,
        NullableFunctions::isNotNull,
        CastDataConstants.VOID,
        SeleniumExceptionHandlers::invokeHandler
    );
}
