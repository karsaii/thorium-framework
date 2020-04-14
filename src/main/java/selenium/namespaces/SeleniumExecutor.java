package selenium.namespaces;

import core.constants.CoreConstants;
import core.extensions.interfaces.functional.TriPredicate;
import core.extensions.interfaces.functional.boilers.IGetMessage;
import core.namespaces.DataFactoryFunctions;
import core.namespaces.ExecutionDataFactory;
import core.namespaces.ExecutionParametersDataFactory;
import core.namespaces.Executor;
import core.records.Data;
import core.records.executor.ExecutionParametersData;
import data.constants.Strings;
import data.namespaces.Formatter;
import org.openqa.selenium.WebDriver;
import selenium.constants.ExecutorConstants;
import selenium.element.functions.SimpleMessageData;
import selenium.namespaces.extensions.boilers.DriverFunction;

import java.util.function.BiFunction;

import static selenium.namespaces.ExecutionCore.ifDriver;

public interface SeleniumExecutor {
    static <Any> DriverFunction<Any> executeDriver(ExecutionParametersData<WebDriver, Any> execution, DriverFunction<?>... steps) {
        final var data = execution.data;
        return ifDriver(
            "executeDriver",
            Formatter.getCommandAmountRangeErrorMessage(steps.length, execution.range),
            driver -> {
                final var result = execution.executor.apply(data, steps).apply(driver);
                final var status = result.status;
                return DataFactoryFunctions.getWithMessage(result.object, status, data.messageData.get().apply(status) + result.message);
            },
            DataFactoryFunctions.getWithMessage((Any) CoreConstants.STOCK_OBJECT, false, Strings.EMPTY)
        );
    }


    static <Any> DriverFunction<Any> execute(IGetMessage stepMessage, DriverFunction<?>... steps) {
        return DriverFunctionFactoryFunctions.getFunction(Executor.execute(
            new ExecutionParametersData<>(
                ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(stepMessage, ExecutorConstants.DEFAULT_EXECUTION_DATA),
                Executor::executeCoreStepMessages,
                ExecutorConstants.DEFAULT_RANGE
            ),
            steps
        ));
    }

    static <Any> DriverFunction<Any> execute(String message, DriverFunction<?>... steps) {
        return DriverFunctionFactoryFunctions.getFunction(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultRange(
                ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(new SimpleMessageData(message), ExecutorConstants.DEFAULT_EXECUTION_DATA),
                Executor::executeCoreStepMessages
            ),
            steps
        ));
    }

    static <Any> DriverFunction<Any> execute(BiFunction<String, String, String> messageHandler, DriverFunction<?>... steps) {
        return DriverFunctionFactoryFunctions.getFunction(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultRange(
                ExecutionDataFactory.getWithDefaultExitCondition(new SimpleMessageData(), Executor::returnStatus, messageHandler),
                Executor::executeCoreStepMessages
            ),
            steps
        ));
    }

    static <Any> DriverFunction<Any> execute(DriverFunction<?>... steps) {
        return DriverFunctionFactoryFunctions.getFunction(Executor.execute(ExecutionParametersDataFactory.getWithDefaultRange(ExecutorConstants.DEFAULT_EXECUTION_ENDED, Executor::executeCoreStepMessages), steps));
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
