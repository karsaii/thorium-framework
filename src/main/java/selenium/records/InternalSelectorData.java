package selenium.records;

import core.records.command.CommandRangeData;
import selenium.constants.ExecutorConstants;

public class InternalSelectorData {
    public final CommandRangeData range;
    public final int limit;

    public InternalSelectorData(CommandRangeData range, int limit) {
        this.range = range;
        this.limit = limit;
    }

    public InternalSelectorData(int limit) {
        this(ExecutorConstants.DEFAULT_RANGE, limit);
    }

    public InternalSelectorData() {
        this(1);
    }
}
