package selenium.constants;

import selenium.namespaces.extensions.boilers.ScriptHandlerFunction;
import selenium.namespaces.ScriptExecuteFunctions;
import selenium.records.ExecuteCoreFunctionData;
import selenium.records.ParametersFieldDefaultsData;

public abstract class ExecuteCoreFunctionDataConstants {
    public static final ExecuteCoreFunctionData<ScriptHandlerFunction> EXECUTE = new ExecuteCoreFunctionData<>(ExecuteFunctionNameConstants.EXECUTE, ScriptExecuteFunctions.executeScript()),
        EXECUTE_ASYNC = new ExecuteCoreFunctionData<>(ExecuteFunctionNameConstants.EXECUTE_ASYNC, ScriptExecuteFunctions.executeAsyncScript());

    public static final ExecuteCoreFunctionData<ParametersFieldDefaultsData> EXECUTE_PARAMETERS = new ExecuteCoreFunctionData<>(ExecuteFunctionNameConstants.EXECUTE_PARAMATERS, ScriptExecutorDefaults.PARAMETERS_DEFAULTS),
        EXECUTE_ASYNC_PARAMETERS = new ExecuteCoreFunctionData<>(ExecuteFunctionNameConstants.EXECUTE_ASYNC_PARAMETERS, ScriptExecutorDefaults.PARAMETERS_ASYNC_DEFAULTS),
        EXECUTE_SINGLE_PARAMETER = new ExecuteCoreFunctionData<>(ExecuteFunctionNameConstants.EXECUTE_SINGLE_PARAMETER, ScriptExecutorDefaults.SINGLE_PARAMETER_DEFAULTS),
        EXECUTE_ASYNC_SINGLE_PARAMETER = new ExecuteCoreFunctionData<>(ExecuteFunctionNameConstants.EXECUTE_ASYNC_SINGLE_PARAMETER, ScriptExecutorDefaults.SINGLE_PARAMETER_ASYNC_DEFAULTS);
}
