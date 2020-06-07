package com.github.karsaii.selenium.namespaces;

import com.github.karsaii.core.namespaces.validators.DataValidators;
import com.github.karsaii.selenium.namespaces.extensions.boilers.ScriptFunction;
import com.github.karsaii.selenium.namespaces.extensions.boilers.ScriptHandlerFunction;
import com.github.karsaii.core.namespaces.DataFunctions;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.constants.CoreConstants;
import com.github.karsaii.selenium.records.scripter.ScriptParametersData;
import com.github.karsaii.selenium.namespaces.validators.ScriptExecutions;

import java.util.function.BiFunction;

public interface ScriptExecuteFunctions {
    static <T extends Data> Object[] handleDataParameter(ScriptParametersData<T> data) {
        if (!ScriptExecutions.isValidScriptParametersData(data)) {
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
        return new ScriptParametersData<>(data, DataValidators::isValidNonFalse, DataFunctions::unwrapToArray);
    }

    static <T, V extends Data<T>> Object[] handleDataParameterWithDefaults(V data) {
        return handleDataParameter(getScriptParametersDataWithDefaults(data));
    }
}