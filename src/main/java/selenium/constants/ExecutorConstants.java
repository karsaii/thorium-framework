package selenium.constants;

import core.namespaces.ExecutionDataFactory;
import core.namespaces.Executor;
import core.records.command.CommandRangeData;
import core.records.executor.ExecuteParametersData;
import core.records.executor.ExecutionData;
import data.constants.Strings;
import selenium.element.functions.SimpleMessageData;

public abstract class ExecutorConstants {
    public static final int MAXIMUM_COMMAND_LIMIT = 20;
    public static final int TWO_COMMANDS_VALUE = 2;
    public static final int MINIMUM_COMMAND_LIMIT = 1;

    public static final CommandRangeData DEFAULT_RANGE = new CommandRangeData(MINIMUM_COMMAND_LIMIT, MAXIMUM_COMMAND_LIMIT);
    public static final CommandRangeData TWO_COMMANDS_RANGE = new CommandRangeData(TWO_COMMANDS_VALUE, TWO_COMMANDS_VALUE);

    public static final ExecuteParametersData DEFAULT_EXECUTION_DATA = Executor.getDefaultExecuteParametersData();
    public static final ExecutionData DEFAULT_EXECUTION_ENDED = ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(new SimpleMessageData(Strings.EXECUTION_ENDED), DEFAULT_EXECUTION_DATA);
    //public static final ExecuteParametersData<> TWO_COMMANDS_STEP_EXECUTION = new ExecutionParametersData<>(ExecutorConstants.DEFAULT_EXECUTION_ENDED, Executor::executeCoreStepMessages, ExecutorConstants.TWO_COMMANDS_RANGE)
}
