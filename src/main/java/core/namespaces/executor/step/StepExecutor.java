package core.namespaces.executor.step;

import core.constants.CoreConstants;
import core.constants.ExecutorConstants;
import core.extensions.interfaces.functional.QuadFunction;
import core.extensions.interfaces.functional.TriPredicate;
import core.extensions.interfaces.functional.boilers.DataSupplier;
import core.extensions.interfaces.functional.boilers.IGetMessage;
import core.namespaces.DataFactoryFunctions;
import core.namespaces.DataSupplierFactory;
import core.namespaces.executor.ExecutionDataFactory;
import core.namespaces.executor.ExecutionParametersDataFactory;
import core.namespaces.executor.ExecutionResultDataFactory;
import core.namespaces.executor.ExecutionStateDataFactory;
import core.namespaces.executor.ExecutionStepsDataFactory;
import core.namespaces.executor.Executor;
import core.records.Data;
import core.records.SimpleMessageData;
import core.records.executor.ExecutionParametersData;
import core.records.executor.ExecutionResultData;
import core.records.executor.ExecutionStateData;
import core.records.executor.ExecutionStepsData;
import data.constants.Strings;
import data.namespaces.Formatter;

import java.util.Arrays;
import java.util.function.Function;

import static core.namespaces.DependencyExecutionFunctions.ifDependency;

public interface StepExecutor {
    private static <ReturnType, ParameterReturnType> DataSupplier<ReturnType> executeGuardCore(
        ExecutionParametersData<Function<Void, Data<?>>, DataSupplier<ParameterReturnType>> execution,
        DataSupplier<ReturnType> executionChain,
        Data<ReturnType> negative,
        int stepLength
    ) {
        return ifDependency("executeGuardCore", Formatter.getCommandAmountRangeErrorMessage(stepLength, execution.range), executionChain, negative);
    }

    @SafeVarargs
    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> execute(
        ExecutionParametersData<Function<Void, Data<?>>, DataSupplier<ExecutionResultData<ReturnType>>> execution,
        ExecutionStateData stateData,
        Function<Void, Data<?>>... steps
    ) {
        final var negative = DataFactoryFunctions.getWithMessage(ExecutionResultDataFactory.getWithDefaultState((ReturnType) CoreConstants.STOCK_OBJECT), false, Strings.EMPTY);
        return executeGuardCore(execution, DataSupplierFactory.get(execution.executor.apply(execution.functionData, stateData, steps)), negative, steps.length);
    }

    private static <ReturnType> Data<ReturnType> executeData(
        ExecutionStepsData<Void> stepsData,
        ExecutionParametersData<Function<Void, Data<?>>, DataSupplier<ExecutionResultData<ReturnType>>> execution
    ) {
        final var result = execute(execution, ExecutionStateDataFactory.getWithDefaults(), stepsData.steps).apply(stepsData.dependency);
        return DataFactoryFunctions.replaceObject(result, result.object.result);
    }

    private static <ReturnType> DataSupplier<ReturnType> executeData(
        ExecutionParametersData<Function<Void, Data<?>>, DataSupplier<ExecutionResultData<ReturnType>>> execution,
        DataSupplier<?>... steps
    ) {
        return DataSupplierFactory.get(dependency -> executeData(ExecutionStepsDataFactory.getWithStepsAndDependency(steps, dependency), execution));
    }

    static <ReturnType> DataSupplier<ReturnType> execute(ExecutionParametersData<Function<Void, Data<?>>, DataSupplier<ExecutionResultData<ReturnType>>> execution, DataSupplier<?>... steps) {
        final var negative = DataFactoryFunctions.getWithMessage((ReturnType) CoreConstants.STOCK_OBJECT, false, Strings.EMPTY);
        return executeGuardCore(execution, executeData(execution, steps), negative, steps.length);
    }

    static <ReturnType> DataSupplier<ReturnType> execute(IGetMessage stepMessage, DataSupplier<?>... steps) {
        return DataSupplierFactory.get(Executor.execute(
                ExecutionParametersDataFactory.getWithDefaultRangeDataSupplier(
                        ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(stepMessage, ExecutorConstants.DEFAULT_EXECUTION_DATA),
                        Executor::execute
                ),
                steps
        ));
    }

    static <ReturnType> DataSupplier<ReturnType> execute(String message, DataSupplier<?>... steps) {
        return DataSupplierFactory.get(Executor.execute(
                ExecutionParametersDataFactory.getWithDefaultRangeDataSupplier(
                        ExecutionDataFactory.getWithSpecificMessage(message),
                        Executor::execute
                ),
                steps
        ));
    }

    static <ReturnType> DataSupplier<ReturnType> execute(QuadFunction<ExecutionStateData, Data<?>, Integer, Integer, String> messageHandler, DataSupplier<?>... steps) {
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithTwoCommandsRangeDataSupplier(
                ExecutionDataFactory.getWithDefaultExitConditionAndMessageData(messageHandler),
                Executor::execute
            ),
            steps
        ));
    }

    static <ReturnType> DataSupplier<ReturnType> execute(DataSupplier<?>... steps) {
        return DataSupplierFactory.get(Executor.execute(ExecutionParametersDataFactory.getWithMessagesAndDefaultRangeDataSupplier(Executor::execute), steps));
    }


    static <ReturnType> DataSupplier<ReturnType> conditionalSequence(TriPredicate<Data<?>, Integer, Integer> guard, DataSupplier<?> before, DataSupplier<?> after) {
        final DataSupplier<?>[] steps = Arrays.asList(before, after).toArray(new DataSupplier<?>[0]);
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithTwoCommandsRangeDataSupplier(
                    ExecutionDataFactory.getWithSpecificMessageDataAndBreakCondition(new SimpleMessageData(Strings.EXECUTION_ENDED), guard),
                    Executor::execute
            ),
            steps
        ));
    }

    static <T, U, ReturnType> DataSupplier<ExecutionResultData<ReturnType>> conditionalSequence(DataSupplier<T> before, DataSupplier<U> after, Class<ReturnType> clazz) {
        final DataSupplier<?>[] steps = Arrays.asList(before, after).toArray(new DataSupplier<?>[0]);
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultFunctionDataAndTwoCommandRangeDataSupplier(Executor::execute),
            steps
        ));
    }

    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> execute(IGetMessage stepMessage, ExecutionStateData stateData, DataSupplier<?>... steps) {
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultRangeDataSupplier(
                ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(stepMessage, ExecutorConstants.DEFAULT_EXECUTION_DATA),
                Executor::execute
            ),
            stateData,
            steps
        ));
    }

    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> execute(String message, ExecutionStateData stateData, DataSupplier<?>... steps) {
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultRangeDataSupplier(
                ExecutionDataFactory.getWithSpecificMessage(message),
                Executor::execute
            ),
            stateData,
            steps
        ));
    }

    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> execute(QuadFunction<ExecutionStateData, Data<?>, Integer, Integer, String> messageHandler, ExecutionStateData stateData, DataSupplier<?>... steps) {
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithTwoCommandsRangeDataSupplier(
                ExecutionDataFactory.getWithDefaultExitConditionAndMessageData(messageHandler),
                Executor::execute
            ),
            stateData,
            steps
        ));
    }

    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> execute(ExecutionStateData stateData, DataSupplier<?>... steps) {
        return DataSupplierFactory.get(Executor.execute(ExecutionParametersDataFactory.getWithMessagesAndDefaultRangeDataSupplier(Executor::execute), stateData, steps));
    }


    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> conditionalSequence(TriPredicate<Data<?>, Integer, Integer> guard, ExecutionStateData stateData, DataSupplier<?> before, DataSupplier<?> after) {
        final DataSupplier<?>[] steps = Arrays.asList(before, after).toArray(new DataSupplier<?>[0]);
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithTwoCommandsRangeDataSupplier(
                ExecutionDataFactory.getWithSpecificMessageDataAndBreakCondition(new SimpleMessageData(Strings.EXECUTION_ENDED), guard),
                Executor::execute
            ),
            stateData,
            steps
        ));
    }

    static <T, U, ReturnType> DataSupplier<ExecutionResultData<ReturnType>> conditionalSequence(ExecutionStateData stateData, DataSupplier<T> before, DataSupplier<U> after, Class<ReturnType> clazz) {
        final DataSupplier<?>[] steps = Arrays.asList(before, after).toArray(new DataSupplier<?>[0]);
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultFunctionDataAndTwoCommandRangeDataSupplier(Executor::execute),
            stateData,
            steps
        ));
    }

    static <ReturnType> Function<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>> executeState(IGetMessage stepMessage, DataSupplier<?>... steps) {
        return stateData -> DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultRangeDataSupplier(
                ExecutionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(stepMessage, ExecutorConstants.DEFAULT_EXECUTION_DATA),
                Executor::execute
            ),
            stateData,
            steps
        ));
    }

    static <ReturnType> Function<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>> executeState(String message, DataSupplier<?>... steps) {
        return stateData -> DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultRangeDataSupplier(
                ExecutionDataFactory.getWithSpecificMessage(message),
                Executor::execute
            ),
            stateData,
            steps
        ));
    }

    static <ReturnType> Function<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>> executeState(QuadFunction<ExecutionStateData, Data<?>, Integer, Integer, String> messageHandler, DataSupplier<?>... steps) {
        return stateData -> DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithTwoCommandsRangeDataSupplier(
                ExecutionDataFactory.getWithDefaultExitConditionAndMessageData(messageHandler),
                Executor::execute
            ),
            stateData,
            steps
        ));
    }

    static <ReturnType> Function<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>> executeState(DataSupplier<?>... steps) {
        return stateData -> DataSupplierFactory.get(Executor.execute(ExecutionParametersDataFactory.getWithMessagesAndDefaultRangeDataSupplier(Executor::execute), stateData, steps));
    }


    static <ReturnType> Function<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>> conditionalSequenceState(TriPredicate<Data<?>, Integer, Integer> guard, DataSupplier<?> before, DataSupplier<?> after) {
        final DataSupplier<?>[] steps = Arrays.asList(before, after).toArray(new DataSupplier<?>[0]);
        return stateData -> DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithTwoCommandsRangeDataSupplier(
                ExecutionDataFactory.getWithSpecificMessageDataAndBreakCondition(new SimpleMessageData(Strings.EXECUTION_ENDED), guard),
                Executor::execute
            ),
            stateData,
            steps
        ));
    }

    static <T, U, ReturnType> Function<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>> conditionalSequenceState(DataSupplier<T> before, DataSupplier<U> after, Class<ReturnType> clazz) {
        final DataSupplier<?>[] steps = Arrays.asList(before, after).toArray(new DataSupplier<?>[0]);
        return stateData -> DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultFunctionDataAndTwoCommandRangeDataSupplier(Executor::execute),
            stateData,
            steps
        ));
    }

}
