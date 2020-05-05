package core.namespaces;

import core.constants.CommandRangeDataConstants;
import core.constants.CoreConstants;
import core.constants.CoreDataConstants;
import core.constants.ExecutorConstants;
import core.extensions.interfaces.functional.TriPredicate;
import core.extensions.interfaces.functional.boilers.DataSupplier;
import core.extensions.interfaces.functional.boilers.IGetMessage;
import core.records.Data;
import core.records.SimpleMessageData;
import core.records.executor.ExecutionData;
import core.records.executor.StepExecutionParametersData;
import data.constants.Strings;
import data.namespaces.Formatter;

import java.util.function.BiFunction;
import java.util.function.Function;

import static core.namespaces.DependencyExecutionFunctions.ifDependency;

public interface StepExecutor {
    private static <ReturnType> Data<ReturnType> executeCoreStepMessagesCore(ExecutionData executionData, DataSupplier<?>... steps) {
        Data<?> data = CoreDataConstants.NO_STEPS;
        var message = "";
        var index = 0;
        var status = true;
        final var length = steps.length;
        final var exitCondition = executionData.exitCondition;
        for(; exitCondition.test(data, index, length); ++index) {
            data = steps[index].apply();
            message = executionData.messageHandler.apply(message, Formatter.getExecutionStepMessage(index, data.message.toString()));
            status = executionData.endStatus.test(data.status);
        }

        return DataFactoryFunctions.getWithNameAndMessage((ReturnType)data.object, status, "executeCoreStepMessagesCore", Formatter.getExecutionEndMessage(index, length, message));
    }

    private static <ReturnType> Data<ReturnType> executeCoreNoMessagesCore(ExecutionData executionData, DataSupplier<?>... steps) {
        Data<?> data = CoreDataConstants.NO_STEPS;
        var index = 0;
        var status = true;
        final var length = steps.length;
        final var exitCondition = executionData.exitCondition;
        for(; exitCondition.test(data, index, length); ++index) {
            data = steps[index].apply();
            status = executionData.endStatus.test(data.status);
        }

        return DataFactoryFunctions.getWithNameAndMessage((ReturnType)data.object, status, "executeCoreNoMessagesCore", index < length ? "Execution ended early:\n" + "\t" + data.message.toString().replaceAll("\n", "\n\t") : "");
    }

    static <Any> DataSupplier<Any> executeCoreStepMessages(ExecutionData executionData, DataSupplier<?>... steps) {
        return (nothing) -> executeCoreStepMessagesCore(executionData, steps);
    }

    static <Any> DataSupplier<Any> executeCoreNoMessages(ExecutionData executionData, DataSupplier<?>... steps) {
        return (nothing) -> executeCoreNoMessagesCore(executionData, steps);
    }

    private static <Any> Data<Any> executeCore(Data<Any> data, ExecutionData executionData) {
        final var status = data.status;
        return DataFactoryFunctions.getWithNameAndMessage(data.object, status, "executeCore", executionData.messageData.get().apply(status) + data.message);
    }

    private static <Any> Function<Data<Any>, Data<Any>> executeCore(ExecutionData executionData) {
        return data -> executeCore(data, executionData);
    }

    static <ReturnType> DataSupplier<ReturnType> execute(StepExecutionParametersData<ReturnType> execution, DataSupplier<?>... steps) {
        final var data = execution.data;
        final var negative = DataFactoryFunctions.getWithMessage((ReturnType) CoreConstants.STOCK_OBJECT, false, Strings.EMPTY);
        return ifDependency(
            "execute",
            Formatter.getCommandAmountRangeErrorMessage(steps.length, execution.range),
            DependencyExecutionFunctions.validChain(execution.executor.apply(data, steps), executeCore(data), negative),
            negative
        );
    }

    static <ReturnType> DataSupplier<ReturnType> execute(IGetMessage stepMessage, DataSupplier<?>... steps) {
        return execute(
            new StepExecutionParametersData<>(
                ExecutionDataFactory.getWithSpecificMessageData(stepMessage),
                StepExecutor::executeCoreStepMessages,
                CommandRangeDataConstants.DEFAULT_RANGE
            ),
            steps
        );
    }

    static <ReturnType> DataSupplier<ReturnType> execute(String message, DataSupplier<?>... steps) {
        return execute(
            StepExecutionParametersDataFactory.getWithDefaultRange(
                ExecutionDataFactory.getWithSpecificMessageData(new SimpleMessageData(message)),
                StepExecutor::executeCoreStepMessages
            ),
            steps
        );
    }

    static <ReturnType> DataSupplier<ReturnType> execute(BiFunction<String, String, String> messageHandler, DataSupplier<?>... steps) {
        return execute(
            StepExecutionParametersDataFactory.getWithDefaultRange(
                ExecutionDataFactory.getWithSpecificMessageHandler(messageHandler),
                StepExecutor::executeCoreStepMessages
            ),
            steps
        );
    }

    static <ReturnType> DataSupplier<ReturnType> execute(DataSupplier<?>... steps) {
        return execute(StepExecutionParametersDataFactory.getWithDefaultRange(ExecutorConstants.DEFAULT_EXECUTION_ENDED, StepExecutor::executeCoreStepMessages), steps);
    }

    static <T, U, ReturnType> DataSupplier<ReturnType> conditionalSequence(TriPredicate<Data<?>, Integer, Integer> guard, DataSupplier<T> before, DataSupplier<U> after) {
        return execute(
            new StepExecutionParametersData<>(
                ExecutionDataFactory.getWithDefaultExecuteParametersData(new SimpleMessageData(Strings.EXECUTION_ENDED), guard),
                StepExecutor::executeCoreStepMessages,
                CommandRangeDataConstants.TWO_COMMANDS_RANGE
            ),
            before,
            after
        );
    }

    static <T, U, ReturnType> DataSupplier<ReturnType> conditionalSequence(DataSupplier<T> before, DataSupplier<U> after, Class<ReturnType> clazz) {
        return execute(
            new StepExecutionParametersData<>(ExecutorConstants.DEFAULT_EXECUTION_ENDED, StepExecutor::executeCoreStepMessages, CommandRangeDataConstants.TWO_COMMANDS_RANGE),
            before,
            after
        );
    }


}
