package selenium.namespaces;

import core.constants.CoreConstants;
import core.extensions.interfaces.functional.TriPredicate;
import core.extensions.interfaces.functional.boilers.IGetMessage;
import core.namespaces.DataFactoryFunctions;
import core.namespaces.ExecutionDataFactory;
import core.namespaces.ExecutionParametersDataFactory;
import core.namespaces.Executor;
import core.records.Data;
import core.records.executor.ExecutionData;
import core.records.executor.ExecutionParametersData;
import data.constants.Strings;
import data.namespaces.Formatter;
import org.openqa.selenium.WebDriver;
import core.constants.ExecutorConstants;
import core.records.SimpleMessageData;
import selenium.namespaces.extensions.boilers.DriverFunction;

import java.util.function.BiFunction;
import java.util.function.Function;

import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.ExecutionCore.validChain;

public interface SeleniumExecutor {
    private static <Any> Data<Any> executeCore(Data<Any> data, ExecutionData executionData) {
        final var status = data.status;
        return DataFactoryFunctions.getWithNameAndMessage(data.object, status, "executeCore", executionData.messageData.get().apply(status) + data.message);
    }

    private static <Any> Function<Data<Any>, Data<Any>> executeCore(ExecutionData executionData) {
        return data -> executeCore(data, executionData);
    }

    static <Any> DriverFunction<Any> execute(ExecutionParametersData<WebDriver, Any> execution, DriverFunction<?>... steps) {
        final var data = execution.data;
        final var negative = DataFactoryFunctions.getWithMessage((Any) CoreConstants.STOCK_OBJECT, false, Strings.EMPTY);
        return ifDriver(
            "execute",
            Formatter.getCommandAmountRangeErrorMessage(steps.length, execution.range),
            validChain(DriverFunctionFactory.getFunction(execution.executor.apply(data, steps)), executeCore(data), negative),
            negative
        );
    }


    static <Any> DriverFunction<Any> execute(IGetMessage stepMessage, DriverFunction<?>... steps) {
        return execute(
            ExecutionParametersDataFactory.getWithDefaultRangeAndMessagesExecutor(
                ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(stepMessage, ExecutorConstants.DEFAULT_EXECUTION_DATA)
            ),
            steps
        );
    }

    static <Any> DriverFunction<Any> execute(String message, DriverFunction<?>... steps) {
        return execute(
            ExecutionParametersDataFactory.getWithDefaultRangeAndMessagesExecutor(
                ExecutionDataFactory.getWithSpecificMessageData(new SimpleMessageData(message))
            ),
            steps
        );
    }

    static <Any> DriverFunction<Any> execute(BiFunction<String, String, String> messageHandler, DriverFunction<?>... steps) {
        return execute(
            ExecutionParametersDataFactory.getWithDefaultRangeAndMessagesExecutor(
                ExecutionDataFactory.getWithSpecificMessageHandler(messageHandler)
            ),
            steps
        );
    }

    static <Any> DriverFunction<Any> execute(DriverFunction<?>... steps) {
        return execute(ExecutionParametersDataFactory.getWithDefaultDataRangeAndMessagesExecutor(), steps);
    }


    static <T, U, Any> DriverFunction<Any> conditionalSequence(TriPredicate<Data<?>, Integer, Integer> guard, DriverFunction<T> before, DriverFunction<U> after) {
        return execute(
            new ExecutionParametersData<>(
                ExecutionDataFactory.getWithDefaultExecuteParametersData(new SimpleMessageData(Strings.EXECUTION_ENDED), guard),
                Executor::executeCoreStepMessages,
                ExecutorConstants.TWO_COMMANDS_RANGE
            ),
            before,
            after
        );
    }

    static <T, U, Any> DriverFunction<Any> conditionalSequence(DriverFunction<T> before, DriverFunction<U> after, Class<Any> clazz) {
        return execute(
            ExecutionParametersDataFactory.getWithMessagesAndSpecificRange(ExecutorConstants.TWO_COMMANDS_RANGE),
            before,
            after
        );
    }
}
