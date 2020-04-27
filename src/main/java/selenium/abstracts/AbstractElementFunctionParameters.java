package selenium.abstracts;

import core.extensions.interfaces.functional.TriFunction;
import selenium.records.ElementValueParameters;

import java.util.Objects;

public class AbstractElementFunctionParameters<T> {
    public final TriFunction<String, String, T, String> formatter;
    public final String conditionName;
    public final String descriptor;

    public AbstractElementFunctionParameters(TriFunction<String, String, T, String> formatter, String conditionName, String descriptor) {
        this.formatter = formatter;
        this.conditionName = conditionName;
        this.descriptor = descriptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ElementValueParameters<?>) o;
        return (
            Objects.equals(conditionName, that.conditionName) &&
            Objects.equals(descriptor, that.descriptor) &&
            Objects.equals(formatter, that.formatter)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(formatter, conditionName, descriptor);
    }
}
