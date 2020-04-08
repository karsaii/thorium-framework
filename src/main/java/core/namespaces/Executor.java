package core.namespaces;

import core.constants.CoreDataConstants;
import core.extensions.interfaces.DriverFunction;
import core.extensions.interfaces.functional.TriPredicate;
import core.extensions.interfaces.functional.boilers.IGetMessage;
import core.extensions.namespaces.CoreUtilities;
import core.extensions.namespaces.NullableFunctions;
import core.records.Data;
import core.records.executor.ExecuteParametersData;
import core.records.executor.ExecutionData;
import core.records.executor.ExecutionParametersData;
import data.constants.Strings;
import data.namespaces.Formatter;
import org.openqa.selenium.WebDriver;
import selenium.constants.ExecutorConstants;
import selenium.element.functions.SimpleMessageData;
import selenium.enums.CoreConstants;
import selenium.namespaces.DriverFunctionFactoryFunctions;

import java.util.function.BiFunction;
import java.util.function.Function;

import static core.namespaces.DataFunctions.isValidNonFalse;
import static core.namespaces.DependencyExecutionFunctions.ifDependency;
import static selenium.namespaces.ExecutionCore.ifDriver;

public interface Executor {
    static boolean isFalse(Data<?> data, int index, int length) {
        return NullableFunctions.isNotNull(data) && CoreUtilities.isFalse(data.status) && (index < length);
    }

    static boolean isExecuting(Data<?> data, int index, int length) {
        return isValidNonFalse(data) && (index < length);
    }

    static String aggregateMessage(String message, String newMessage) {
        return message + newMessage;
    }

    static String reduceMessage(String message, String newMessage) {
        return newMessage;
    }

    static Boolean returnStatus(Boolean status) {
        return status;
    }

    static Boolean returnTrue(Boolean status) {
        return true;
    }

    @SafeVarargs
    private static <DependencyType, ReturnType> Data<ReturnType> executeCoreStepMessagesCore(DependencyType dependency, ExecutionData executionData, Function<DependencyType, Data<?>>... steps) {
        Data<?> data = CoreDataConstants.NO_STEPS;
        var message = "";
        var index = 0;
        var status = true;
        final var length = steps.length;
        final var exitCondition = executionData.exitCondition;
        for(; exitCondition.test(data, index, length); ++index) {
            data = steps[index].apply(dependency);
            message = executionData.messageHandler.apply(message, Formatter.getExecutionStepMessage(index, data.message.toString()));
            status = executionData.endStatus.test(data.status);
        }

        return DataFactoryFunctions.getWithMessage((ReturnType)data.object, status, Formatter.getExecutionEndMessage(index, length, message));
    }

    @SafeVarargs
    private static <DependencyType, ReturnType> Data<ReturnType> executeCoreNoMessagesCore(DependencyType dependency, ExecutionData executionData, Function<DependencyType, Data<?>>... steps) {
        Data<?> data = CoreDataConstants.NO_STEPS;
        var index = 0;
        var status = true;
        final var length = steps.length;
        final var exitCondition = executionData.exitCondition;
        for(; exitCondition.test(data, index, length); ++index) {
            data = steps[index].apply(dependency);
            status = executionData.endStatus.test(data.status);
        }

        return DataFactoryFunctions.getWithMessage((ReturnType)data.object, status, index < length ? "Execution ended early:\n" + data.message : "");
    }

    @SafeVarargs
    private static <DependencyType, Any> Function<DependencyType, Data<Any>> executeCoreStepMessages(ExecutionData executionData, Function<DependencyType, Data<?>>... steps) {
        return driver -> executeCoreStepMessagesCore(driver, executionData, steps);
    }

    @SafeVarargs
    private static <DependencyType, Any> Function<DependencyType, Data<Any>> executeCoreNoMessages(ExecutionData executionData, Function<DependencyType, Data<?>>... steps) {
        return driver -> executeCoreNoMessagesCore(driver, executionData, steps);
    }

    static ExecuteParametersData getDefaultExecuteParametersData() {
        return new ExecuteParametersData(ExecutorConstants.DEFAULT_RANGE, Executor::returnStatus, Executor::reduceMessage);
    }

    @SafeVarargs
    static <DependencyType, Any> Function<DependencyType, Data<Any>> execute(ExecutionParametersData<DependencyType, Any> execution, Function<DependencyType, Data<?>>... steps) {
        final var data = execution.data;
        return ifDependency(
            "executeCoreCommon",
            Formatter.getCommandAmountRangeErrorMessage(steps.length, execution.range),
            driver -> {
                final var result = execution.executor.apply(data, steps).apply(driver);
                final var status = result.status;
                return DataFactoryFunctions.getWithMessage(result.object, status, data.messageData.get().apply(status) + result.message);
            },
            DataFactoryFunctions.getWithMessage((Any)CoreConstants.STOCK_OBJECT, false, Strings.EMPTY)
        );
    }



    static <Any> DriverFunction<Any> execute(IGetMessage stepMessage, DriverFunction<?>... steps) {
        return DriverFunctionFactoryFunctions.getFunction(execute(
            new ExecutionParametersData<>(
                ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(stepMessage, ExecutorConstants.DEFAULT_EXECUTION_DATA),
                Executor::executeCoreStepMessages,
                ExecutorConstants.DEFAULT_RANGE
            ),
            steps
        ));
    }

    static <Any> DriverFunction<Any> execute(String message, DriverFunction<?>... steps) {
        return DriverFunctionFactoryFunctions.getFunction(execute(
            ExecutionParametersDataFactory.getWithDefaultRange(
                ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(new SimpleMessageData(message), ExecutorConstants.DEFAULT_EXECUTION_DATA),
                Executor::executeCoreStepMessages
            ),
            steps
        ));
    }

    static <Any> DriverFunction<Any> execute(BiFunction<String, String, String> messageHandler, DriverFunction<?>... steps) {
        return DriverFunctionFactoryFunctions.getFunction(execute(
                ExecutionParametersDataFactory.getWithDefaultRange(
                ExecutionDataFactory.getWithDefaultExitCondition(new SimpleMessageData(), Executor::returnStatus, messageHandler),
                Executor::executeCoreStepMessages
            ),
            steps
        ));
    }

    static <Any> DriverFunction<Any> execute(DriverFunction<?>... steps) {
        return DriverFunctionFactoryFunctions.getFunction(execute(ExecutionParametersDataFactory.getWithDefaultRange(ExecutorConstants.DEFAULT_EXECUTION_ENDED, Executor::executeCoreStepMessages), steps));
    }


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
                DataFactoryFunctions.getWithMessage((Any)CoreConstants.STOCK_OBJECT, false, Strings.EMPTY)
        );
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
