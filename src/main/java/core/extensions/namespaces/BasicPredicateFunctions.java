package core.extensions.namespaces;

public interface BasicPredicateFunctions {
    static boolean isNonNegative(int value) {
        return value > -1;
    }

    static boolean isNegative(int value) {
        return value < 0;
    }

    static boolean isPositiveNonZero(int value) {
        return value > 0;
    }
}
