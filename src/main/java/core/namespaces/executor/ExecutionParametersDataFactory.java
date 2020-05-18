package core.namespaces.executor;

import core.constants.CommandRangeDataConstants;
import core.extensions.interfaces.functional.TriFunction;
import core.records.Data;
import core.records.command.CommandRangeData;
import core.records.executor.ExecutionStateData;
import core.records.executor.ExecutorFunctionData;
import core.records.executor.ExecutionParametersData;
import core.constants.ExecutorConstants;
import org.openqa.selenium.WebDriver;

import java.util.function.Function;

public interface ExecutionParametersDataFactory {
    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWith(
            ExecutorFunctionData data,
            TriFunction<ExecutorFunctionData, ExecutionStateData, ArrayType[], ReturnType> executor,
            CommandRangeData range
    ) {
        return new ExecutionParametersData<>(data, executor, range);
    }

    static <ReturnType> ExecutionParametersData<Function<WebDriver, Data<?>>, Function<WebDriver, Data<ReturnType>>> getWithDriver(
            ExecutorFunctionData data,
            TriFunction<ExecutorFunctionData, ExecutionStateData, Function<WebDriver, Data<?>>[], Function<WebDriver, Data<ReturnType>>> executor,
            CommandRangeData range
    ) {
        return new ExecutionParametersData<>(data, executor, range);
    }

    static <ReturnType> ExecutionParametersData<Function<WebDriver, Data<?>>, Function<WebDriver, Data<ReturnType>>> getWithDefaultRangeDriver(
            ExecutorFunctionData data,
            TriFunction<ExecutorFunctionData, ExecutionStateData, Function<WebDriver, Data<?>>[], Function<WebDriver, Data<ReturnType>>> executor
    ) {
        return getWithDriver(data, executor, CommandRangeDataConstants.DEFAULT_RANGE);
    }

    static <ReturnType> ExecutionParametersData<Function<Void, Data<?>>, Function<Void, Data<ReturnType>>> getWithDataSupplier(
            ExecutorFunctionData data,
            TriFunction<ExecutorFunctionData, ExecutionStateData, Function<Void, Data<?>>[], Function<Void, Data<ReturnType>>> executor,
            CommandRangeData range
    ) {
        return new ExecutionParametersData<>(data, executor, range);
    }

    static <ReturnType> ExecutionParametersData<Function<Void, Data<?>>, Function<Void, Data<ReturnType>>> getWithDefaultRangeDataSupplier(
            ExecutorFunctionData data,
            TriFunction<ExecutorFunctionData, ExecutionStateData, Function<Void, Data<?>>[], Function<Void, Data<ReturnType>>> executor
    ) {
        return getWithDataSupplier(data, executor, CommandRangeDataConstants.DEFAULT_RANGE);
    }

    static <ReturnType> ExecutionParametersData<Function<Void, Data<?>>, Function<Void, Data<ReturnType>>> getWithTwoCommandsRangeDataSupplier(
            ExecutorFunctionData data,
            TriFunction<ExecutorFunctionData, ExecutionStateData, Function<Void, Data<?>>[], Function<Void, Data<ReturnType>>> executor
    ) {
        return getWithDataSupplier(data, executor, CommandRangeDataConstants.TWO_COMMANDS_RANGE);
    }

    static <ReturnType> ExecutionParametersData<Function<Void, Data<?>>, Function<Void, Data<ReturnType>>> getWithDefaultFunctionDataDataSupplier(
        TriFunction<ExecutorFunctionData, ExecutionStateData, Function<Void, Data<?>>[], Function<Void, Data<ReturnType>>> executor,
        CommandRangeData commandRange
    ) {
        return getWithDataSupplier(ExecutorConstants.DEFAULT_EXECUTION_ENDED, executor, commandRange);
    }

    static <ReturnType> ExecutionParametersData<Function<Void, Data<?>>, Function<Void, Data<ReturnType>>> getWithDefaultFunctionDataAndTwoCommandRangeDataSupplier(
        TriFunction<ExecutorFunctionData, ExecutionStateData, Function<Void, Data<?>>[], Function<Void, Data<ReturnType>>> executor
    ) {
        return getWithDataSupplier(ExecutorConstants.DEFAULT_EXECUTION_ENDED, executor, CommandRangeDataConstants.TWO_COMMANDS_RANGE);
    }

    static <ReturnType> ExecutionParametersData<Function<WebDriver, Data<?>>, Function<WebDriver, Data<ReturnType>>> getWithDefaultFunctionDataAndTwoCommandRangeDriver(
        TriFunction<ExecutorFunctionData, ExecutionStateData, Function<WebDriver, Data<?>>[], Function<WebDriver, Data<ReturnType>>> executor
    ) {
        return getWithDriver(ExecutorConstants.DEFAULT_EXECUTION_ENDED, executor, CommandRangeDataConstants.TWO_COMMANDS_RANGE);
    }

    static <ReturnType> ExecutionParametersData<Function<Void, Data<?>>, Function<Void, Data<ReturnType>>> getWithMessagesAndDefaultRangeDataSupplier(
            TriFunction<ExecutorFunctionData, ExecutionStateData, Function<Void, Data<?>>[], Function<Void, Data<ReturnType>>> executor
    ) {
        return getWith(ExecutorConstants.DEFAULT_EXECUTION_ENDED, executor, CommandRangeDataConstants.DEFAULT_RANGE);
    }

    static <ReturnType> ExecutionParametersData<Function<WebDriver, Data<?>>, Function<WebDriver, Data<ReturnType>>> getWithMessagesAndDefaultRangeDriver(
        TriFunction<ExecutorFunctionData, ExecutionStateData, Function<WebDriver, Data<?>>[], Function<WebDriver, Data<ReturnType>>> executor
    ) {
        return getWith(ExecutorConstants.DEFAULT_EXECUTION_ENDED, executor, CommandRangeDataConstants.DEFAULT_RANGE);
    }


    static <ReturnType> ExecutionParametersData<Function<WebDriver, Data<?>>, Function<WebDriver, Data<ReturnType>>> getWithTwoCommandsRangeDriver(
            ExecutorFunctionData data,
            TriFunction<ExecutorFunctionData, ExecutionStateData, Function<WebDriver, Data<?>>[], Function<WebDriver, Data<ReturnType>>> executor
    ) {
        return getWithDriver(data, executor, CommandRangeDataConstants.TWO_COMMANDS_RANGE);
    }

    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWithDefaultRange(
            ExecutorFunctionData data,
            TriFunction<ExecutorFunctionData, ExecutionStateData, ArrayType[], ReturnType> executor
    ) {
        return getWith(data, executor, CommandRangeDataConstants.DEFAULT_RANGE);
    }
}
