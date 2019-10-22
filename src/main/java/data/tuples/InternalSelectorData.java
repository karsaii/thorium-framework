package data.tuples;

import data.constants.Defaults;

public class InternalSelectorData {
    public final CommandRangeData range;
    public final int limit;

    public InternalSelectorData(CommandRangeData range, int limit) {
        this.range = range;
        this.limit = limit;
    }

    public InternalSelectorData(int limit) {
        this(Defaults.DEFAULT_RANGE, limit);
    }

    public InternalSelectorData() {
        this(1);
    }
}
