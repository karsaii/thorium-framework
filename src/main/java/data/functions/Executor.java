package data.functions;

import data.constants.DataDefaults;
import data.constants.Defaults;
import data.constants.Strings;
import data.Data;
import data.DriverFunction;
import data.tuples.ExecutionParametersData;
import data.IGetMessage;
import data.tuples.ExecutionData;
import formatter.Formatter;

import java.util.function.BiFunction;

import static data.ExecutionCore.ifDriver;
import static utilities.utils.isNotNullOrFalseDataOrDataObject;

public interface Executor {
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

    private static <Any> DriverFunction<Any> executeCoreStepMessages(ExecutionData data, DriverFunction<?>... steps) {
        return new DriverFunction<Any>(driver -> {
            Data<?> step = DataDefaults.NO_STEPS_DATA;
            var message = "";
            var index = 0;
            var status = true;
            var length = steps.length;
            for(; isNotNullOrFalseDataOrDataObject(step) && (index < length); ++index) {
                step = steps[index].apply(driver);
                message = data.messageHandler.apply(message, Formatter.getExecutionStepMessage(index, step.message.toString()));
                status = data.exitCondition.test(step.status);
            }

            return new Data<Any>((Any)step.object, status, Formatter.getExecutionEndMessage(index, length, message));
        });
    }

    private static <Any> DriverFunction<Any> executeCoreNoMessages(ExecutionData data, DriverFunction<?>... steps) {
        return new DriverFunction<Any>(driver -> {
            Data<?> step = DataDefaults.NO_STEPS_DATA;
            var index = 0;
            var status = true;
            var length = steps.length;
            for(; isNotNullOrFalseDataOrDataObject(step) && (index < length); ++index) {
                step = steps[index].apply(driver);
                status = data.exitCondition.test(step.status);
            }

            return new Data<Any>((Any)step.object, status, index < length ? "Execution ended early:\n" + step.message : "");
        });
    }

    static <Any> DriverFunction<Any> executeCoreCommon(ExecutionParametersData<Any> execution, DriverFunction<?>... steps) {
        final var data = execution.data;
        return ifDriver(
            "executeCoreCommon",
            Formatter.getCommandAmountRangeErrorMessage(steps.length, data.range),
            new DriverFunction<Any>(driver -> {
                final var result = execution.executor.apply(data, steps).apply(driver);
                final var status = result.status;
                return new Data<Any>(result.object, status, data.messageData.get().apply(status) + result.message);
            }),
            new Data<Any>((Any)DataDefaults.STOCK_OBJECT, false, Strings.EMPTY)
        );
    }

    static <Any> DriverFunction<Any> execute(IGetMessage stepMessage, DriverFunction<?>... steps) {
        return executeCoreCommon(
            new ExecutionParametersData<Any>(new ExecutionData(stepMessage, Defaults.DEFAULT_EXECUTION_DATA), Executor::executeCoreStepMessages),
            steps
        );
    }

    static <Any> DriverFunction<Any> execute(String message, DriverFunction<?>... steps) {
        return executeCoreCommon(
            new ExecutionParametersData<Any>(
                new ExecutionData(new SimpleMessageData(message), Defaults.DEFAULT_EXECUTION_DATA),
                Executor::executeCoreStepMessages
            ),
            steps
        );
    }

    static <Any> DriverFunction<Any> execute(BiFunction<String, String, String> messageHandler, DriverFunction<?>... steps) {
        return executeCoreCommon(
            new ExecutionParametersData<Any>(
                new ExecutionData(
                    Defaults.DEFAULT_RANGE,
                    new SimpleMessageData(),
                    Executor::returnStatus,
                    messageHandler
                ),
                Executor::executeCoreStepMessages
            ),
            steps
        );
    }

    static <Any> DriverFunction<Any> execute(DriverFunction<?>... steps) {
        return executeCoreCommon(
            new ExecutionParametersData<Any>(
                new ExecutionData(new SimpleMessageData(Strings.EXECUTION_ENDED), Defaults.DEFAULT_EXECUTION_DATA),
                Executor::executeCoreStepMessages
            ),
            steps
        );
    }

}
