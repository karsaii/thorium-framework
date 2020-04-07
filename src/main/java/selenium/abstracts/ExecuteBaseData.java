package selenium.abstracts;

import core.extensions.interfaces.functional.boilers.ScriptFunction;
import core.records.ExecuteCommonData;

public abstract class ExecuteBaseData<ParameterType, ReturnType> implements ScriptFunction<ReturnType> {
    public final ExecuteCommonData<ParameterType> common;

    public ExecuteBaseData(ExecuteCommonData<ParameterType> common) {
        this.common = common;
    }
}
