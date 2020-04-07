package selenium.namespaces;

import core.extensions.interfaces.functional.boilers.ScriptFunction;
import core.extensions.interfaces.functional.boilers.ScriptHandlerFunction;
import core.namespaces.DataFunctions;
import core.records.Data;
import selenium.enums.CoreConstants;
import selenium.records.scripter.ScriptParametersData;
import validators.ScriptExecutions;

import java.util.function.BiFunction;

public interface ScriptExecuteFunctions {
    static <T extends Data> Object[] handleDataParameter(ScriptParametersData<T> data) {
        if (ScriptExecutions.isInvalidScriptParametersData(data)) {
            return CoreConstants.EMPTY_OBJECT_ARRAY;
        }

        final var parameters = data.parameters;
        return data.validator.test(parameters) ? data.converter.apply(parameters) : CoreConstants.EMPTY_OBJECT_ARRAY;
    }

    static ScriptHandlerFunction executeScript() {
        return executor -> executor::executeScript;
    }

    static ScriptHandlerFunction executeAsyncScript() {
        return executor -> executor::executeAsyncScript;
    }

    static ScriptFunction<BiFunction<String, Object[], Object>> executeScriptWithParameters() {
        return executor -> executor::executeScript;
    }

    static ScriptFunction<BiFunction<String, Object[], Object>> executeAsyncScriptWithParameters() {
        return executor -> executor::executeAsyncScript;
    }

    static <T> ScriptParametersData<Data<T>> getScriptParametersDataWithDefaults(Data<T> data) {
        return new ScriptParametersData<>(data, DataFunctions::isValidNonFalse, DataFunctions::unwrapToArray);
    }

    static <T, V extends Data<T>> Object[] handleDataParameterWithDefaults(V data) {
        return handleDataParameter(getScriptParametersDataWithDefaults(data));
    }
}