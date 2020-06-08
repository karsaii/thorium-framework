package com.github.karsaii.selenium.records.scripter;

import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import com.github.karsaii.selenium.namespaces.extensions.boilers.ScriptFunction;
import com.github.karsaii.core.extensions.namespaces.NullableFunctions;
import com.github.karsaii.core.records.ExecuteCommonData;
import org.openqa.selenium.JavascriptExecutor;
import com.github.karsaii.selenium.abstracts.ExecuteBaseData;

import java.security.InvalidParameterException;
import java.util.function.Function;

public class ExecuteRegularData<T> extends ExecuteBaseData<T, Function<String, Object>> implements ScriptFunction<Function<String, Object>> {
    public final ScriptFunction<Function<String, Object>> handler;

    public ExecuteRegularData(
        ExecuteCommonData<T> commonData,
        ScriptFunction<Function<String, Object>> handler
    ) {
        super(commonData);
        this.handler = handler;
    }

    @Override
    public Function<String, Object> apply(JavascriptExecutor executor) {
        if (NullableFunctions.isNull(executor)) {
            throw new InvalidParameterException("Executor parameter" + CoreFormatterConstants.WAS_NULL);
        }

        return handler.apply(executor);
    }
}
