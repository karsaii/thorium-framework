package utilities;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Cardinalities<T> {
    static <T> Predicate<T> noopBoolean(Predicate<T> object) {
        return object;
    }

    static <T> Predicate<T> invertBoolean(Predicate<T> object) {
        return _object -> !object.test(_object);
    }

    static <T> Function<Predicate<T>, Predicate<T>> getPredicate(boolean invert) {
        return invert ? Cardinalities::invertBoolean : Cardinalities::noopBoolean;
    }

    static boolean invertBoolean(boolean status) {
        return !status;
    }

    static boolean noopBoolean(boolean status) {
        return status;
    }
}
