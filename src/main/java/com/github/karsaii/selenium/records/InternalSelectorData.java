package com.github.karsaii.selenium.records;

import com.github.karsaii.core.constants.CommandRangeDataConstants;
import com.github.karsaii.core.records.command.CommandRangeData;

public class InternalSelectorData {
    public final CommandRangeData range;
    public final int limit;

    public InternalSelectorData(CommandRangeData range, int limit) {
        this.range = range;
        this.limit = limit;
    }

    public InternalSelectorData(int limit) {
        this(CommandRangeDataConstants.DEFAULT_RANGE, limit);
    }

    public InternalSelectorData() {
        this(1);
    }
}
