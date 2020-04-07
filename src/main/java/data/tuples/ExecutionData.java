package data.tuples;

import data.IGetMessage;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ExecutionData {
    public final IGetMessage messageData;
    public final CommandRangeData range;
    public final Predicate<Boolean> exitCondition;
    public final BiFunction<String, String, String> messageHandler;

    public ExecutionData(CommandRangeData range, IGetMessage messageData, Predicate<Boolean> exitCondition, BiFunction<String, String, String> messageHandler) {
        this.range = range;
        this.messageData = messageData;
        this.exitCondition = exitCondition;
        this.messageHandler = messageHandler;
    }

    public ExecutionData(IGetMessage messageData, ExecuteParametersData data) {
        this(data.range, messageData, data.exitCondition, data.messageHandler);
    }

    public ExecutionData() {
        this(null, null, null, null);
    }
}
