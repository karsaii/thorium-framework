package core.namespaces.command;

import core.constants.CommandRangeDataConstants;
import core.extensions.interfaces.functional.TriFunction;
import core.namespaces.validators.Range;
import core.records.command.CommandRangeData;

public interface CommandRangeDataFactory {
    static CommandRangeData getWithDefaultRangeValues(TriFunction<Integer, Integer, Integer, Boolean> invalidator) {
        return new CommandRangeData(invalidator, CommandRangeDataConstants.MINIMUM_COMMAND_LIMIT, CommandRangeDataConstants.MAXIMUM_COMMAND_LIMIT);
    }

    static CommandRangeData getWithDefaultRangeInvalidator(int min, int max) {
        return new CommandRangeData(Range::isOutOfRange, min, max);
    }

    static CommandRangeData getWithDefaults() {
        return getWithDefaultRangeInvalidator(CommandRangeDataConstants.MINIMUM_COMMAND_LIMIT, CommandRangeDataConstants.MAXIMUM_COMMAND_LIMIT);
    }
}
