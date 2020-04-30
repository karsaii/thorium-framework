package core.records.executor;

import core.extensions.interfaces.functional.TriFunction;
import core.records.command.CommandRangeData;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ExecuteParametersData {
    public final CommandRangeData range;
    public final Predicate<Boolean> endStatus;
    public final BiFunction<String, String, String> messageHandler;
    public final TriFunction<Integer, Integer, String, String> endMessageHandler;

    public ExecuteParametersData(
        CommandRangeData range,
        Predicate<Boolean> endStatus,
        BiFunction<String, String, String> messageHandler,
        TriFunction<Integer, Integer, String, String> endMessageHandler
    ) {
        this.range = range;
        this.endStatus = endStatus;
        this.messageHandler = messageHandler;
        this.endMessageHandler = endMessageHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ExecuteParametersData) o;
        return (
            Objects.equals(range, that.range) &&
            Objects.equals(endStatus, that.endStatus) &&
            Objects.equals(messageHandler, that.messageHandler) &&
            Objects.equals(endMessageHandler, that.endMessageHandler)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(range, endStatus, messageHandler);
    }
}
