package waits;

import core.exceptions.WaitTimeoutException;
import core.namespaces.DataFactoryFunctions;
import core.namespaces.executor.step.StepExecutor;
import core.namespaces.executor.step.StepFactory;
import core.namespaces.validators.DataValidators;
import core.namespaces.wait.Wait;
import core.namespaces.wait.WaitTimeDataFactory;
import core.records.WaitData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

public class WaitTests {
    private static int count = 0;
    private static int increaseAndGetCount() {
        return ++count;
    }

    @DisplayName("Wait Repeat - one always fails second")
    @Test
    void oneFailsSecond() {
        final var countStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getBoolean(increaseAndGetCount() == 3, "test1", "Step was okay"), null);
        final var trueStringStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getWithNameAndMessage("Applesauce", false, "test2", "StringStep was oookay"), null);
        final var steps = StepExecutor.executeState("waitRepeat Test result message", countStep, trueStringStep);
        final var waitData = new WaitData<>(steps, DataValidators::isExecutionValidNonFalse, "Steps passed", WaitTimeDataFactory.getWithDefaultClock(100, 1000));

        Assertions.assertThrows(WaitTimeoutException.class, () -> Wait.repeatWithDefaultState(waitData));
    }

    @DisplayName("Wait Repeat - one always fails first")
    @Test
    void oneFailsFirst() {
        final var countStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getBoolean(increaseAndGetCount() == 3, "test1", "Step was okay"), null);
        final var trueStringStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getWithNameAndMessage("Applesauce", false, "test2", "StringStep was oookay"), null);
        final var steps = StepExecutor.executeState("waitRepeat Test result message", trueStringStep, countStep);
        final var waitData = new WaitData<>(steps, DataValidators::isExecutionValidNonFalse, "Steps passed", WaitTimeDataFactory.getWithDefaultClock(100, 1000));

        Assertions.assertThrows(WaitTimeoutException.class, () -> Wait.repeatWithDefaultState(waitData));
    }

    @DisplayName("Wait Repeat - none fails over time")
    @Test
    @Tags(@Tag("slow"))
    void noneFails() {
        count = 0;
        final var countStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getBoolean(increaseAndGetCount() == 3, "test1", "Step was okay"), null);
        final var trueStringStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getWithNameAndMessage("Applesauce", true, "test2", "StringStep was oookay"), null);
        final var steps = StepExecutor.executeState("waitRepeat Test result message", countStep, trueStringStep);
        final var waitData = new WaitData<>(steps, DataValidators::isExecutionValidNonFalse, "Steps passed", WaitTimeDataFactory.getWithDefaultClock(100, 10000));
        final var result = Wait.repeatWithDefaultState(waitData);

        Assertions.assertTrue(result.status, result.message.toString());
    }

    @DisplayName("Wait Repeat - both always fail")
    @Test
    void waitRepeatTest() {
        final var countStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getBoolean(increaseAndGetCount() < -1, "test1", "Step was okay"), null);
        final var trueStringStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getWithNameAndMessage("Applesauce", false, "test2", "StringStep was oookay"), null);
        final var steps = StepExecutor.executeState("waitRepeat Test result message", countStep, trueStringStep);
        final var waitData = new WaitData<>(steps, DataValidators::isExecutionValidNonFalse, "Steps passed", WaitTimeDataFactory.getWithDefaultClock(100, 1000));

        Assertions.assertThrows(WaitTimeoutException.class, () -> Wait.repeatWithDefaultState(waitData));
    }

}
