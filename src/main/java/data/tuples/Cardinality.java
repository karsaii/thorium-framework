package data.tuples;

public class Cardinality {
    public final boolean finalValue,
        guardValue,
        invert;

    public Cardinality(boolean finalValue, boolean guardValue, boolean invert) {
        this.finalValue = finalValue;
        this.guardValue = guardValue;
        this.invert = invert;
    }
}
