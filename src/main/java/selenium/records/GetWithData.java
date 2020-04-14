package selenium.records;

import selenium.namespaces.extensions.boilers.DriverFunction;
import core.records.Data;

import java.util.function.Function;

public class GetWithData<T, U, V, W> {
    public final T locators;
    public final Function<T, U> locatorGetter;
    public final Function<V, DriverFunction<W>> getter;
    public final Data<W> guardData;

    public GetWithData(T locators, Function<T, U> locatorGetter, Function<V, DriverFunction<W>> getter, Data<W> guardData) {
        this.locators = locators;
        this.locatorGetter = locatorGetter;
        this.getter = getter;
        this.guardData = guardData;
    }
}
