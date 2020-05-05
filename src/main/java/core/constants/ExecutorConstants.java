package core.constants;

import core.namespaces.ExecutionDataFactory;
import core.namespaces.Executor;
import core.records.Data;
import core.records.command.CommandRangeData;
import core.records.executor.ExecuteParametersData;
import core.records.executor.ExecutionData;
import data.constants.Strings;
import core.records.SimpleMessageData;
import data.namespaces.Formatter;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class ExecutorConstants {
    public static final ExecuteParametersData DEFAULT_EXECUTION_DATA = new ExecuteParametersData(CommandRangeDataConstants.DEFAULT_RANGE, Executor::returnStatus, Executor::reduceMessage, Formatter::getExecutionEndMessage);
    public static final ExecutionData DEFAULT_EXECUTION_ENDED = ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(new SimpleMessageData(Strings.EXECUTION_ENDED), DEFAULT_EXECUTION_DATA);
    //public static final ExecuteParametersData<> TWO_COMMANDS_STEP_EXECUTION = new ExecutionParametersData<>(ExecutorConstants.DEFAULT_EXECUTION_ENDED, Executor::executeCoreStepMessages, ExecutorConstants.TWO_COMMANDS_RANGE)
}
