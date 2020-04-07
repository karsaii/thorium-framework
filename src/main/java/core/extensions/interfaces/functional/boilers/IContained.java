package core.extensions.interfaces.functional.boilers;

import java.util.function.Function;

@FunctionalInterface
public interface IContained<U, T> {
    Function<U, T> get();
}
