package data;

import java.util.function.Function;

@FunctionalInterface
public interface IContained<U, T> {
    Function<U, T> get();
}
