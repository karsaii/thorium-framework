package core.records.command;

import core.extensions.interfaces.functional.TriFunction;
import validators.Range;

public class CommandRangeData {
    public final TriFunction<Integer, Integer, Integer, Boolean> rangeInvalidator;
    public final int min;
    public final int max;

    public CommandRangeData(TriFunction<Integer, Integer, Integer, Boolean> rangeInvalidator, int min, int max) {
        this.rangeInvalidator = rangeInvalidator;
        this.min = min;
        this.max = max;
    }

    public CommandRangeData(int min, int max) {
        this.rangeInvalidator = Range::isOutOfRange;
        this.min = min;
        this.max = max;
    }
}
