package selenium.records.element.is;

import core.extensions.interfaces.functional.TriFunction;

import java.util.Objects;

public class ElementFormatData<T> {
    public final TriFunction<String, String, T, String> formatter;
    public final String conditionName;
    public final String descriptor;

    public ElementFormatData(TriFunction<String, String, T, String> formatter, String conditionName, String descriptor) {
        this.formatter = formatter;
        this.conditionName = conditionName;
        this.descriptor = descriptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ElementFormatData<?>) o;
        return Objects.equals(formatter, that.formatter) && Objects.equals(conditionName, that.conditionName) && Objects.equals(descriptor, that.descriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formatter, conditionName, descriptor);
    }
}
