package data;

import java.util.function.Function;

@FunctionalInterface
public interface IContainedData<T, U> extends IContained<T, Data<U>> {
    Function<T, Data<U>> get();
}
