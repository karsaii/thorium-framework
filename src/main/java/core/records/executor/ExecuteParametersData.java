package core.records.executor;

import core.records.command.CommandRangeData;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ExecuteParametersData {
    public final CommandRangeData range;
    public final Predicate<Boolean> endStatus;
    public final BiFunction<String, String, String> messageHandler;

    public ExecuteParametersData(CommandRangeData range, Predicate<Boolean> endStatus, BiFunction<String, String, String> messageHandler) {
        this.range = range;
        this.endStatus = endStatus;
        this.messageHandler = messageHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ExecuteParametersData) o;
        return Objects.equals(range, that.range) && Objects.equals(endStatus, that.endStatus) && Objects.equals(messageHandler, that.messageHandler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(range, endStatus, messageHandler);
    }
}
