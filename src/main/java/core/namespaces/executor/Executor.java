package core.namespaces.executor;

import core.constants.CoreDataConstants;
import core.constants.CoreConstants;
import core.extensions.namespaces.BasicPredicateFunctions;
import core.extensions.namespaces.CoreUtilities;
import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import core.records.executor.ExecutionResultData;
import core.records.executor.ExecutionStateData;
import core.records.executor.ExecutionStepsData;
import core.records.executor.ExecutorFunctionData;
import core.records.executor.ExecutionParametersData;
import data.constants.Strings;
import data.namespaces.Formatter;

import java.util.function.Function;

import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static core.namespaces.validators.DataValidators.isInvalidOrFalse;
import static core.namespaces.validators.DataValidators.isValidNonFalse;
import static core.namespaces.DependencyExecutionFunctions.ifDependency;

public interface Executor {
    static boolean isFalse(Data<?> data, int index, int length) {
        return (
            isNotNull(data) &&
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

    private static <DependencyType, ReturnType> Data<ExecutionResultData<ReturnType>> executeCore(
        ExecutionStepsData<DependencyType> stepsData,
        ExecutorFunctionData functionData,
        ExecutionStateData stateData
    ) {
        Data<?> data = CoreDataConstants.NO_STEPS;
        final var exitCondition = functionData.breakCondition;
        final var filter = functionData.filterCondition;
        final var indices = stateData.indices;
        final var map = stateData.executionMap;
        final var length = indices.size();
        final var steps = stepsData.steps;
        final var dependency = stepsData.dependency;
        var intervalIndex = 0;
        var index = 0;
        for(; exitCondition.test(data, index, length); ++index) {
            data = steps[indices.get(intervalIndex)].apply(dependency);
            map.put(Formatter.getExecutionResultKey(data.message.nameof, index), data);
            if (filter.test(data)) {
                indices.remove(intervalIndex);
            } else {
                ++intervalIndex;
            }
        }

        final var executionStatus = ExecutionStateDataFactory.getWith(map, indices);
        final var status = functionData.endCondition.test(executionStatus, steps.length, index, indices.size());
        final var message = functionData.messageData.get().apply(status) + functionData.endMessageHandler.apply(executionStatus, data, index, length);
        return DataFactoryFunctions.getWithNameAndMessage(ExecutionResultDataFactory.getWith(executionStatus, (ReturnType)data.object), status, "executeCore", message);
    }

    private static <DependencyType, ArrayType, ReturnType, ParameterReturnType> Function<DependencyType, Data<ReturnType>> executeGuardCore(
        ExecutionParametersData<ArrayType, Function<DependencyType, Data<ParameterReturnType>>> execution,
        Function<DependencyType, Data<ReturnType>> executionChain,
        Data<ReturnType> negative,
        int stepLength
    ) {
        return ifDependency("executeGuardCore", Formatter.getCommandAmountRangeErrorMessage(stepLength, execution.range), executionChain, negative);
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, Data<ExecutionResultData<ReturnType>>> execute(
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution,
        ExecutionStateData stateData,
        Function<DependencyType, Data<?>>... steps
    ) {
        final var negative = DataFactoryFunctions.getWithMessage(ExecutionResultDataFactory.getWithDefaultState((ReturnType) CoreConstants.STOCK_OBJECT), false, Strings.EMPTY);
        return executeGuardCore(execution, execution.executor.apply(execution.functionData, stateData, steps), negative, steps.length);
    }

    private static <DependencyType, ReturnType> Data<ReturnType> executeData(
        ExecutionStepsData<DependencyType> stepsData,
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution
    ) {
        final var result = execute(execution, ExecutionStateDataFactory.getWithDefaultMapAndSpecificLength(stepsData.length), stepsData.steps).apply(stepsData.dependency);
        return DataFactoryFunctions.replaceObject(result, result.object.result);
    }

    @SafeVarargs
    private static <DependencyType, ReturnType> Function<DependencyType, Data<ReturnType>> executeData(
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution,
        Function<DependencyType, Data<?>>... steps
    ) {
        return dependency -> executeData(ExecutionStepsDataFactory.getWithStepsAndDependency(steps, dependency), execution);
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, Data<ExecutionResultData<ReturnType>>> execute(ExecutorFunctionData functionData, ExecutionStateData stateData, Function<DependencyType, Data<?>>... steps) {
        return dependency -> executeCore(ExecutionStepsDataFactory.getWithStepsAndDependency(steps, dependency), functionData, stateData);
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, Data<ExecutionResultData<ReturnType>>> execute(ExecutorFunctionData functionData, Function<DependencyType, Data<?>>... steps) {
        return dependency -> executeCore(ExecutionStepsDataFactory.getWithStepsAndDependency(steps, dependency), functionData, ExecutionStateDataFactory.getWithDefaultMapAndSpecificLength(steps.length));
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, Data<ReturnType>> execute(ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution, Function<DependencyType, Data<?>>... steps) {
        final var negative = DataFactoryFunctions.getWithMessage((ReturnType) CoreConstants.STOCK_OBJECT, false, Strings.EMPTY);
        return executeGuardCore(execution, executeData(execution, steps), negative, steps.length);
    }
}
