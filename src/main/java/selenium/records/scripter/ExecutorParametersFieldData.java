package selenium.records.scripter;

import selenium.namespaces.extensions.boilers.ScriptFunction;
import selenium.records.ParametersFieldDefaultsData;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ExecutorParametersFieldData {
    public final Object[] parameters;
    public final Predicate<Object[]> validator;
    public final ScriptFunction<BiFunction<String, Object[], Object>> handler;

    public ExecutorParametersFieldData(Object[] parameters, Predicate<Object[]> validator, ScriptFunction<BiFunction<String, Object[], Object>> handler) {
        this.parameters = parameters;
        this.validator = validator;
        this.handler = handler;
    }

    public ExecutorParametersFieldData(Object[] parameters, ParametersFieldDefaultsData data) {
        this(parameters, data.validator, data.handler);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ExecutorParametersFieldData) o;
        return Arrays.equals(parameters, that.parameters) && Objects.equals(validator, that.validator) && Objects.equals(handler, that.handler);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(validator, handler) + Arrays.hashCode(parameters);
    }
}
