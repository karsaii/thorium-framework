package data.tuples;

import data.DriverFunction;

public class ActionWhenData<T, U> {
    public final DriverFunction<T> condition;
    public final DriverFunction<U> action;

    public ActionWhenData(DriverFunction<T> condition, DriverFunction<U> action) {
        this.condition = condition;
        this.action = action;
    }
}
