package core.extensions.namespaces;

import java.util.Objects;
import java.util.function.Supplier;

public interface SizableFunctions {
    static boolean isSizeEqualTo(Supplier<Integer> sizeFunction, int size) {
        return (
            NullableFunctions.isNotNull(sizeFunction) &&
            BasicPredicateFunctions.isNonNegative(size) &&
            Objects.equals(sizeFunction.get(), size)
        );
    }

    static int size(Supplier<Integer> sizeFunction) {
        return NullableFunctions.isNotNull(sizeFunction) ? sizeFunction.get() : 0;
    }

    static int size(Object[] object) {
        return NullableFunctions.isNotNull(object) ? object.length : 0;
    }
}
