package core.extensions.namespaces;

public interface BasicPredicateFunctions {
    static boolean isNonNegative(int value) {
        return isBiggerThan(value, -1);
    }

    static boolean isNegative(int value) {
        return isBiggerThan(0, value);
    }

    static boolean isPositiveNonZero(int value) {
        return isBiggerThan(value, 0);
    }

    static boolean isBiggerThan(long value, long limit) {
        return value > limit;
    }
}
