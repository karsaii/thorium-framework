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
import selenium.constants.ExecutorConstants;
import selenium.element.functions.SimpleMessageData;
import selenium.namespaces.extensions.boilers.DriverFunction;

import java.util.function.BiFunction;
import java.util.function.Function;

import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.ExecutionCore.validChain;

public interface SeleniumExecutor {
    private static <Any> Data<Any> executeDriverCore(Data<Any> data, ExecutionData executionData) {
        final var status = data.status;
        return DataFactoryFunctions.getWithMessage(data.object, status, executionData.messageData.get().apply(status) + data.message);
    }

    private static <Any> Function<Data<Any>, Data<Any>> executeDriverCore(ExecutionData executionData) {
        return data -> executeDriverCore(data, executionData);
    }

    static <Any> DriverFunction<Any> executeDriver(ExecutionParametersData<WebDriver, Any> execution, DriverFunction<?>... steps) {
        final var data = execution.data;
        final var negative = DataFactoryFunctions.getWithMessage((Any) CoreConstants.STOCK_OBJECT, false, Strings.EMPTY);
        return ifDriver(
            "executeDriver",
            Formatter.getCommandAmountRangeErrorMessage(steps.length, execution.range),
            validChain(DriverFunctionFactory.getFunction(execution.executor.apply(data, steps)), executeDriverCore(data), negative),
            negative
        );
    }


    static <Any> DriverFunction<Any> execute(IGetMessage stepMessage, DriverFunction<?>... steps) {
        return DriverFunctionFactory.getFunction(Executor.execute(
            new ExecutionParametersData<>(
                ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(stepMessage, ExecutorConstants.DEFAULT_EXECUTION_DATA),
                Executor::executeCoreStepMessages,
                ExecutorConstants.DEFAULT_RANGE
            ),
            steps
        ));
    }

    static <Any> DriverFunction<Any> execute(String message, DriverFunction<?>... steps) {
        return DriverFunctionFactory.getFunction(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultRange(
                ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(new SimpleMessageData(message), ExecutorConstants.DEFAULT_EXECUTION_DATA),
                Executor::executeCoreStepMessages
            ),
            steps
        ));
    }

    static <Any> DriverFunction<Any> execute(BiFunction<String, String, String> messageHandler, DriverFunction<?>... steps) {
        return DriverFunctionFactory.getFunction(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultRange(
                ExecutionDataFactory.getWithDefaultExitCondition(new SimpleMessageData(), Executor::returnStatus, messageHandler),
                Executor::executeCoreStepMessages
            ),
            steps
        ));
    }

    static <Any> DriverFunction<Any> execute(DriverFunction<?>... steps) {
        return DriverFunctionFactory.getFunction(Executor.execute(ExecutionParametersDataFactory.getWithDefaultRange(ExecutorConstants.DEFAULT_EXECUTION_ENDED, Executor::executeCoreStepMessages), steps));
    }


    static <T, U, Any> DriverFunction<Any> conditionalSequence(TriPredicate<Data<?>, Integer, Integer> guard, DriverFunction<T> before, DriverFunction<U> after) {
        return executeDriver(
            new ExecutionParametersData<>(
                ExecutionDataFactory.getWithExecuteParametersData(new SimpleMessageData(Strings.EXECUTION_ENDED), guard, ExecutorConstants.DEFAULT_EXECUTION_DATA),
                Executor::executeCoreStepMessages,
                ExecutorConstants.TWO_COMMANDS_RANGE
            ),
            before,
            after
        );
    }

    static <T, U, Any> DriverFunction<Any> conditionalSequence(DriverFunction<T> before, DriverFunction<U> after, Class<Any> clazz) {
        return executeDriver(
            new ExecutionParametersData<>(ExecutorConstants.DEFAULT_EXECUTION_ENDED, Executor::executeCoreStepMessages, ExecutorConstants.TWO_COMMANDS_RANGE),
            before,
            after
        );
    }
}
