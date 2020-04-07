package selenium.records;

import core.extensions.interfaces.DriverFunction;

import java.util.Objects;

public class ActionWhenData<T, U> {
    public final DriverFunction<T> condition;
    public final DriverFunction<U> action;

    public ActionWhenData(DriverFunction<T> condition, DriverFunction<U> action) {
        this.condition = condition;
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ActionWhenData<?, ?>) o;
        return Objects.equals(condition, that.condition) && Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, action);
    }
}
