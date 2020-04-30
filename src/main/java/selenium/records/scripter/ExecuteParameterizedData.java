package selenium.records.scripter;

import selenium.namespaces.extensions.boilers.ScriptFunction;
import core.extensions.namespaces.NullableFunctions;
import core.records.ExecuteCommonData;
import data.constants.Strings;
import org.openqa.selenium.JavascriptExecutor;
import selenium.abstracts.ExecuteBaseData;

import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.function.Function;

public class ExecuteParameterizedData<T> extends ExecuteBaseData<T, Function<String, Object>> implements ScriptFunction<Function<String, Object>> {
    public final ExecutorParametersFieldData parameterData;

    public ExecuteParameterizedData(ExecuteCommonData<T> commonData, ExecutorParametersFieldData parameterData) {
        super(commonData);
        this.parameterData = parameterData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ExecuteParameterizedData<?>) o;
        return Objects.equals(parameterData, that.parameterData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameterData);
    }

    @Override
    public Function<String, Object> apply(JavascriptExecutor executor) {
        if (NullableFunctions.isNull(executor)) {
            throw new InvalidParameterException("Executor parameter" + Strings.WAS_NULL);
        }

        final var parameters = parameterData.parameters;
        return script -> parameterData.handler.apply(executor).apply(script, parameters);
    }
}
