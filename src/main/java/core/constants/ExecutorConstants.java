package core.constants;

import core.extensions.namespaces.CoreUtilities;
import core.namespaces.executor.ExecutionDataFactory;
import core.namespaces.executor.Executor;
import core.records.executor.ExecuteParametersData;
import core.records.executor.ExecutorFunctionData;
import data.constants.Strings;
import core.records.SimpleMessageData;
import data.namespaces.Formatter;

public abstract class ExecutorConstants {
    public static final ExecuteParametersData DEFAULT_EXECUTION_DATA = new ExecuteParametersData(CommandRangeDataConstants.DEFAULT_RANGE, CoreUtilities::isAllDone, Formatter::getExecutionEndMessage);
    public static final ExecutorFunctionData DEFAULT_EXECUTION_ENDED = ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(new SimpleMessageData(Strings.EXECUTION_ENDED), DEFAULT_EXECUTION_DATA);
    //public static final ExecuteParametersData<> TWO_COMMANDS_STEP_EXECUTION = new ExecutionParametersData<>(ExecutorConstants.DEFAULT_EXECUTION_ENDED, Executor::executeCoreStepMessages, ExecutorConstants.TWO_COMMANDS_RANGE)
}
