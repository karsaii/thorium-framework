package core.namespaces;

import core.constants.CoreDataConstants;
import core.constants.CoreConstants;
import core.extensions.namespaces.BasicPredicateFunctions;
import core.extensions.namespaces.CoreUtilities;
import core.extensions.namespaces.NullableFunctions;
import core.records.Data;
import core.records.executor.ExecutionData;
import core.records.executor.ExecutionParametersData;
import data.constants.Strings;
import data.namespaces.Formatter;
import org.openqa.selenium.WebDriver;

import java.util.function.Function;

import static core.namespaces.validators.DataValidators.isInvalidOrFalse;
import static core.namespaces.validators.DataValidators.isValidNonFalse;
import static core.namespaces.DependencyExecutionFunctions.ifDependency;
import static selenium.namespaces.ExecutionCore.validChain;

public interface Executor {
    static boolean isFalse(Data<?> data, int index, int length) {
        return (
            NullableFunctions.isNotNull(data) &&
            CoreUtilities.isFalse(data.status) &&
            BasicPredicateFunctions.isSmallerThan(index, length)
        );
    }

    static boolean isExecuting(Data<?> data, int index, int length) {
        return isValidNonFalse(data) && BasicPredicateFunctions.isSmallerThan(index, length);
    }

    static boolean isExecutingUntilAny(Data<?> data, int index, int length) {
        return isInvalidOrFalse(data) && BasicPredicateFunctions.isSmallerThan(index, length);
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

    private static Data<?> coreWithMessages(ExecutionData executionData, Data<?> stepResult, int index, String message) {
        return DataFactoryFunctions.replaceStatusAndMessage(
            stepResult,
            executionData.endStatus.test(stepResult.status),
            executionData.messageHandler.apply(message, Formatter.getExecutionStepMessage(index, stepResult.message.toString()))
        );
    }

    private static Data<?> core(ExecutionData executionData, Data<?> stepResult) {
        return DataFactoryFunctions.replaceStatus(stepResult, executionData.endStatus.test(stepResult.status));
    }

    private static <DependencyType, ReturnType> Data<ReturnType> executeCoreStepMessagesCore(DependencyType dependency, ExecutionData executionData, Function<DependencyType, Data<?>>... steps) {
        Data<?> data = CoreDataConstants.NO_STEPS;
        var index = 0;
        var previousMessage = "";
        final var length = steps.length;
        final var exitCondition = executionData.exitCondition;
        for(; exitCondition.test(data, index, length); ++index) {
            data = coreWithMessages(executionData, steps[index].apply(dependency), index, previousMessage);
            previousMessage = data.message.toString();
        }

        return DataFactoryFunctions.getWithNameAndMessage((ReturnType)data.object, data.status, "executeCoreStepMessagesCore", Formatter.getExecutionEndMessage(index, length, data.message.toString()));
    }

    @SafeVarargs
    private static <DependencyType, ReturnType> Data<ReturnType> executeCoreNoMessagesCore(DependencyType dependency, ExecutionData executionData, Function<DependencyType, Data<?>>... steps) {
        Data<?> data = CoreDataConstants.NO_STEPS;
        var index = 0;
        final var length = steps.length;
        final var exitCondition = executionData.exitCondition;
        for(; exitCondition.test(data, index, length); ++index) {
            data = core(executionData, steps[index].apply(dependency));
        }

        return DataFactoryFunctions.getWithNameAndMessage((ReturnType)data.object, data.status, "executeCoreNoMessagesCore", Formatter.getExecutionEndNoMessagesMessage(index, length, data.message.toString()));
    }

    @SafeVarargs
    static <DependencyType, Any> Function<DependencyType, Data<Any>> executeCoreStepMessages(ExecutionData executionData, Function<DependencyType, Data<?>>... steps) {
        return driver -> executeCoreStepMessagesCore(driver, executionData, steps);
    }

    @SafeVarargs
    static <DependencyType, Any> Function<DependencyType, Data<Any>> executeCoreNoMessages(ExecutionData executionData, Function<DependencyType, Data<?>>... steps) {
        return driver -> executeCoreNoMessagesCore(driver, executionData, steps);
    }

    static <Any> Data<Any> executeCore(Data<Any> data, ExecutionData executionData) {
        final var status = data.status;
        return DataFactoryFunctions.getWithNameAndMessage(data.object, status, "executeCore", executionData.messageData.get().apply(status) + data.message);
    }

    static <Any> Function<Data<Any>, Data<Any>> executeCore(ExecutionData executionData) {
        return data -> executeCore(data, executionData);
    }

    @SafeVarargs
    static <DependencyType, Any> Function<DependencyType, Data<Any>> execute(ExecutionParametersData<DependencyType, Any> execution, Function<DependencyType, Data<?>>... steps) {
        final var data = execution.data;
        final var negative = DataFactoryFunctions.getWithMessage((Any) CoreConstants.STOCK_OBJECT, false, Strings.EMPTY);
        return ifDependency(
            "execute",
            Formatter.getCommandAmountRangeErrorMessage(steps.length, execution.range),
            validChain(execution.executor.apply(data, steps), executeCore(data), negative),
            DataFactoryFunctions.getWithNameAndMessage((Any)CoreConstants.STOCK_OBJECT, false, "execute", Strings.EMPTY)
        );
    }
}
