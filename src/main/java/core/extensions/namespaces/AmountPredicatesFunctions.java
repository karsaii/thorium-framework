package core.extensions.namespaces;

import java.util.function.Supplier;

import static core.extensions.namespaces.BasicPredicateFunctions.isBiggerThan;
import static core.extensions.namespaces.BasicPredicateFunctions.isNonNegative;
import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static core.extensions.namespaces.SizableFunctions.isSizeEqualTo;

public interface AmountPredicatesFunctions {
    static boolean isSingle(Supplier<Integer> sizeFunction) {
        return isSizeEqualTo(sizeFunction, 1);
    }

    static boolean isDouble(Supplier<Integer> sizeFunction) {
        return isSizeEqualTo(sizeFunction, 2);
    }

    static boolean hasMoreThan(Supplier<Integer> sizeFunction, int amount) {
        return (
            isNonNegative(amount) &&
            isBiggerThan(SizableFunctions.size(sizeFunction), amount)
        );
    }

    static boolean isAtleastDouble(Supplier<Integer> sizeFunction) {
        return hasMoreThan(sizeFunction, 2);
    }

    static boolean isMany(Supplier<Integer> sizeFunction) {
        return hasMoreThan(sizeFunction, 1);
    }

    static boolean hasAtleast(Supplier<Integer> sizeFunction, int amount) {
        return hasMoreThan(sizeFunction, amount - 1);
    }

    static boolean hasIndex(Supplier<Integer> sizeFunction, int index) {
        return hasMoreThan(sizeFunction, index);
    }

    static boolean isSingle(Object[] object) {
        return isNotNull(object) && isSizeEqualTo(object.length, 1);
    }

    static boolean isNonZero(Object[] object) {
        return isNotNull(object) && isBiggerThan(object.length, 0);
    }
}
