package core.extensions.namespaces;

import java.util.Objects;

import static core.extensions.namespaces.NullableFunctions.isNull;

public interface ArrayFunctions {
    static <T> boolean isNullOrEmptyArray(T[] array) {
        if (isNull(array)) {
            return true;
        }

        final var length = array.length;
        final var expectedSize = 1;
        return (
            BasicPredicateFunctions.isBiggerThan(expectedSize, length) ||
            (Objects.equals(length, expectedSize) && isNull(array[0]))
        );
    }
}
