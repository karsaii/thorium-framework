package selenium.records;

import selenium.namespaces.extensions.boilers.ScriptFunction;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ParametersFieldDefaultsData {
    public final Predicate<Object[]> validator;
    public final ScriptFunction<BiFunction<String, Object[], Object>> handler;

    public ParametersFieldDefaultsData(Predicate<Object[]> validator, ScriptFunction<BiFunction<String, Object[], Object>> handler) {
        this.validator = validator;
        this.handler = handler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ParametersFieldDefaultsData) o;
        return Objects.equals(validator, that.validator) && Objects.equals(handler, that.handler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validator, handler);
    }
}
