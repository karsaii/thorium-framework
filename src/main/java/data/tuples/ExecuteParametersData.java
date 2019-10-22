package data.tuples;

import data.constants.Defaults;
import data.functions.Executor;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ExecuteParametersData {
    public final CommandRangeData range;
    public final Predicate<Boolean> exitCondition;
    public final BiFunction<String, String, String> messageHandler;

    public ExecuteParametersData(CommandRangeData range, Predicate<Boolean> exitCondition, BiFunction<String, String, String> messageHandler) {
        this.range = range;
        this.exitCondition = exitCondition;
        this.messageHandler = messageHandler;
    }

    public ExecuteParametersData() {
        this(Defaults.DEFAULT_RANGE, Executor::returnStatus, Executor::reduceMessage);
    }
}
