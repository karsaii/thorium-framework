package data.constants;

import data.tuples.Cardinality;

public class CardinalityDefaults {
    public static Cardinality any = new Cardinality(false, true, false),
        all = new Cardinality(true, false, true),
        none = new Cardinality(true, false, false);
}
