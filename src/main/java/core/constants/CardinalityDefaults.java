package core.constants;

import core.records.CardinalityData;

public abstract class CardinalityDefaults {
    public static final CardinalityData any = new CardinalityData(false, true, false),
        all = new CardinalityData(true, false, true),
        none = new CardinalityData(true, false, false);
}
