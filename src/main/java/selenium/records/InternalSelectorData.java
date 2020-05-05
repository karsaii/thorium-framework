package selenium.records;

import core.constants.CommandRangeDataConstants;
import core.records.command.CommandRangeData;
import core.constants.ExecutorConstants;

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
