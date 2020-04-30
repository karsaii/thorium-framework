package selenium.records.scripter;

import selenium.namespaces.extensions.boilers.ScriptFunction;
import core.extensions.namespaces.NullableFunctions;
import core.records.ExecuteCommonData;
import data.constants.Strings;
import org.openqa.selenium.JavascriptExecutor;
import selenium.abstracts.ExecuteBaseData;

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
            throw new InvalidParameterException("Executor parameter" + Strings.WAS_NULL);
        }

        return handler.apply(executor);
    }
}
