package core.extensions.namespaces;

import java.util.function.Supplier;

public interface AmountPredicatesFunctions {
    static boolean isSingle(Supplier<Integer> sizeFunction) {
        return SizableFunctions.isSizeEqualTo(sizeFunction, 1);
    }

    static boolean isDouble(Supplier<Integer> sizeFunction) {
        return SizableFunctions.isSizeEqualTo(sizeFunction, 2);
    }

    static boolean hasMoreThan(Supplier<Integer> sizeFunction, int amount) {
        return BasicPredicateFunctions.isNonNegative(amount) && (SizableFunctions.size(sizeFunction) > amount);
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
}
