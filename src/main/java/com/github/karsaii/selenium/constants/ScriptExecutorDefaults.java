package com.github.karsaii.selenium.constants;

import com.github.karsaii.core.constants.CastDataConstants;
import com.github.karsaii.selenium.namespaces.validators.SeleniumFormatter;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.core.extensions.namespaces.AmountPredicatesFunctions;
import com.github.karsaii.core.namespaces.ExceptionHandlers;
import com.github.karsaii.core.records.HandleResultData;
import org.openqa.selenium.JavascriptExecutor;
import com.github.karsaii.selenium.namespaces.Driver;
import com.github.karsaii.selenium.namespaces.ScriptExecuteFunctions;
import com.github.karsaii.selenium.records.scripter.ExecuteParameterizedData;
import com.github.karsaii.selenium.records.scripter.ExecuteRegularData;
import com.github.karsaii.selenium.records.scripter.ParametersFieldDefaultsData;
import com.github.karsaii.selenium.records.scripter.ExecutorWrappedResultFunctionsData;
import com.github.karsaii.selenium.records.scripter.ParameterizedExecutorData;
import com.github.karsaii.selenium.records.scripter.RegularExecutorData;
import com.github.karsaii.selenium.namespaces.validators.ScriptExecutions;

public abstract class ScriptExecutorDefaults {
    public static final ParametersFieldDefaultsData SINGLE_PARAMETER_DEFAULTS = new ParametersFieldDefaultsData(AmountPredicatesFunctions::isSingle, ScriptExecuteFunctions.executeScriptWithParameters());
    public static final ParametersFieldDefaultsData SINGLE_PARAMETER_ASYNC_DEFAULTS = new ParametersFieldDefaultsData(AmountPredicatesFunctions::isSingle, ScriptExecuteFunctions.executeAsyncScriptWithParameters());
    public static final ParametersFieldDefaultsData PARAMETERS_DEFAULTS = new ParametersFieldDefaultsData(AmountPredicatesFunctions::isNonZero, ScriptExecuteFunctions.executeScriptWithParameters());
    public static final ParametersFieldDefaultsData PARAMETERS_ASYNC_DEFAULTS = new ParametersFieldDefaultsData(AmountPredicatesFunctions::isNonZero, ScriptExecuteFunctions.executeAsyncScriptWithParameters());
    public static final ExecutorWrappedResultFunctionsData<HandleResultData<String, Object>, Boolean, Object> OBJECT_RESULT_HANDLER = new ExecutorWrappedResultFunctionsData<>(SeleniumFormatter::getScriptExecutionMessage, ExceptionHandlers::classCastHandler);
    public static final ExecutorWrappedResultFunctionsData<HandleResultData<String, String>, Boolean, String> STRING_RESULT_HANDLER = new ExecutorWrappedResultFunctionsData<>(SeleniumFormatter::getScriptExecutionMessage, ExceptionHandlers::classCastHandler);
    public static final DriverFunction<JavascriptExecutor> JAVASCRIPT_EXECUTOR_GETTER = Driver.getExecutorData();
    public static final RegularExecutorData<Object> OBJECT_REGULAR_DEFAULTS = new RegularExecutorData<>(
            JAVASCRIPT_EXECUTOR_GETTER,
            ExecuteRegularData::new,
            ScriptExecutions::isValidExecutorRegularData,
            CastDataConstants.WRAPPED_OBJECT,
            OBJECT_RESULT_HANDLER
    );
    public static final RegularExecutorData<String> STRING_REGULAR_DEFAULTS = new RegularExecutorData<>(
            JAVASCRIPT_EXECUTOR_GETTER,
            ExecuteRegularData::new,
            ScriptExecutions::isValidExecutorRegularData,
            CastDataConstants.WRAPPED_STRING,
            STRING_RESULT_HANDLER
    );
    public static final ParameterizedExecutorData<Object> OBJECT_PARAMETERS_DEFAULTS = new ParameterizedExecutorData<>(
            JAVASCRIPT_EXECUTOR_GETTER,
            ExecuteParameterizedData::new,
            ScriptExecutions::isValidExecutorParametersData,
            CastDataConstants.WRAPPED_OBJECT,
            OBJECT_RESULT_HANDLER
    );
    public static final ParameterizedExecutorData<String> STRING_PARAMETERS_DEFAULTS = new ParameterizedExecutorData<>(
            JAVASCRIPT_EXECUTOR_GETTER,
            ExecuteParameterizedData::new,
            ScriptExecutions::isValidExecutorParametersData,
            CastDataConstants.WRAPPED_STRING,
            STRING_RESULT_HANDLER
    );
}
