package core.records.executor;

import core.extensions.interfaces.functional.TriPredicate;
import core.extensions.interfaces.functional.boilers.IGetMessage;
import core.records.Data;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ExecutionData {
    public final IGetMessage messageData;
    public final TriPredicate<Data<?>, Integer, Integer> exitCondition;
    public final Predicate<Boolean> endStatus;
    public final BiFunction<String, String, String> messageHandler;

    public ExecutionData(IGetMessage messageData, TriPredicate<Data<?>, Integer, Integer> exitCondition, Predicate<Boolean> endStatus, BiFunction<String, String, String> messageHandler) {
        this.messageData = messageData;
        this.exitCondition = exitCondition;
        this.endStatus = endStatus;
        this.messageHandler = messageHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ExecutionData) o;
        return (
            Objects.equals(messageData, that.messageData) &&
            Objects.equals(exitCondition, that.exitCondition) &&
            Objects.equals(endStatus, that.endStatus) &&
            Objects.equals(messageHandler, that.messageHandler)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageData, exitCondition, endStatus, messageHandler);
    }
}
