package core.namespaces;

import core.constants.CoreDataConstants;
import core.extensions.namespaces.CoreUtilities;
import core.extensions.namespaces.NullableFunctions;
import core.records.Data;
import core.records.executor.ExecuteParametersData;
import core.records.executor.ExecutionData;
import core.records.executor.ExecutionParametersData;
import data.constants.Strings;
import data.namespaces.Formatter;
import selenium.constants.ExecutorConstants;
import core.constants.CoreConstants;

import java.util.function.Function;

import static core.namespaces.validators.DataValidators.isValidNonFalse;
import static core.namespaces.DependencyExecutionFunctions.ifDependency;

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

        return DataFactoryFunctions.getWithMessage((ReturnType)data.object, status, index < length ? "Execution ended early:\n" + "\t" + data.message.toString().replaceAll("\n", "\n\t") : "");
    }

    @SafeVarargs
    static <DependencyType, Any> Function<DependencyType, Data<Any>> executeCoreStepMessages(ExecutionData executionData, Function<DependencyType, Data<?>>... steps) {
        return driver -> executeCoreStepMessagesCore(driver, executionData, steps);
    }

    @SafeVarargs
    static <DependencyType, Any> Function<DependencyType, Data<Any>> executeCoreNoMessages(ExecutionData executionData, Function<DependencyType, Data<?>>... steps) {
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
}
