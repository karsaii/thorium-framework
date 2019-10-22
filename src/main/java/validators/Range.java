package validators;

public interface Range {
    static Boolean isOutOfRange(int min, int length, int max) {
        return (length < min) || (length > max);
    }
}
