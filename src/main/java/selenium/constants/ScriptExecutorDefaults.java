package selenium.constants;

import core.constants.CastDataConstants;
import selenium.namespaces.extensions.boilers.DriverFunction;
import core.extensions.namespaces.AmountPredicatesFunctions;
import core.namespaces.ExceptionHandlers;
import core.records.HandleResultData;
import data.namespaces.Formatter;
import org.openqa.selenium.JavascriptExecutor;
import selenium.namespaces.Driver;
import selenium.namespaces.ScriptExecuteFunctions;
import selenium.records.scripter.ExecuteParameterizedData;
import selenium.records.scripter.ExecuteRegularData;
import selenium.records.scripter.ParametersFieldDefaultsData;
import selenium.records.scripter.ExecutorWrappedResultFunctionsData;
import selenium.records.scripter.ParameterizedExecutorData;
import selenium.records.scripter.RegularExecutorData;
import validators.ScriptExecutions;

public abstract class ScriptExecutorDefaults {
    public static final ParametersFieldDefaultsData SINGLE_PARAMETER_DEFAULTS = new ParametersFieldDefaultsData(AmountPredicatesFunctions::isSingle, ScriptExecuteFunctions.executeScriptWithParameters());
    public static final ParametersFieldDefaultsData SINGLE_PARAMETER_ASYNC_DEFAULTS = new ParametersFieldDefaultsData(AmountPredicatesFunctions::isSingle, ScriptExecuteFunctions.executeAsyncScriptWithParameters());
    public static final ParametersFieldDefaultsData PARAMETERS_DEFAULTS = new ParametersFieldDefaultsData(AmountPredicatesFunctions::isNonZero, ScriptExecuteFunctions.executeScriptWithParameters());
    public static final ParametersFieldDefaultsData PARAMETERS_ASYNC_DEFAULTS = new ParametersFieldDefaultsData(AmountPredicatesFunctions::isNonZero, ScriptExecuteFunctions.executeAsyncScriptWithParameters());
    public static final ExecutorWrappedResultFunctionsData<HandleResultData<String, Object>, Boolean, Object> OBJECT_RESULT_HANDLER = new ExecutorWrappedResultFunctionsData<>(Formatter::getStringExecutionMessage, ExceptionHandlers::classCastHandler);
    public static final ExecutorWrappedResultFunctionsData<HandleResultData<String, String>, Boolean, String> STRING_RESULT_HANDLER = new ExecutorWrappedResultFunctionsData<>(Formatter::getStringExecutionMessage, ExceptionHandlers::classCastHandler);
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
