package selenium.constants;

import selenium.namespaces.extensions.boilers.ScriptHandlerFunction;
import selenium.records.ExecuteCoreData;
import selenium.records.scripter.ExecutorParametersFieldData;

public abstract class ExecuteCoreDataConstants {
    public static final ExecuteCoreData<ScriptHandlerFunction, Object> EXECUTE_RETURN_OBJECT = new ExecuteCoreData<>(ScriptExecutorDefaults.OBJECT_REGULAR_DEFAULTS, DriverFunctionConstants.FUNCTION_MAP, DriverFunctionConstants.OBJECT_FUNCTION_KEY);
    public static final ExecuteCoreData<ScriptHandlerFunction, String> EXECUTE_RETURN_STRING = new ExecuteCoreData<>(ScriptExecutorDefaults.STRING_REGULAR_DEFAULTS, DriverFunctionConstants.FUNCTION_MAP, DriverFunctionConstants.STRING_FUNCTION_KEY);
    public static final ExecuteCoreData<ExecutorParametersFieldData, Object> EXECUTE_PARAMETERS_RETURN_OBJECT = new ExecuteCoreData<>(ScriptExecutorDefaults.OBJECT_PARAMETERS_DEFAULTS, DriverFunctionConstants.FUNCTION_MAP, DriverFunctionConstants.OBJECT_FUNCTION_KEY);
    public static final ExecuteCoreData<ExecutorParametersFieldData, String> EXECUTE_PARAMETERS_RETURN_STRING = new ExecuteCoreData<>(ScriptExecutorDefaults.STRING_PARAMETERS_DEFAULTS, DriverFunctionConstants.FUNCTION_MAP, DriverFunctionConstants.STRING_FUNCTION_KEY);
}
