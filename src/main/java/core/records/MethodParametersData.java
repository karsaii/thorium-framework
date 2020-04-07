package core.records;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.BiPredicate;

public class MethodParametersData {
    public final String methodName;
    public final BiPredicate<Method, String> validator;

    public MethodParametersData(String methodName, BiPredicate<Method, String> validator) {
        this.methodName = methodName;
        this.validator = validator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (MethodParametersData) o;
        return Objects.equals(methodName, that.methodName) && Objects.equals(validator, that.validator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodName, validator);
    }
}
