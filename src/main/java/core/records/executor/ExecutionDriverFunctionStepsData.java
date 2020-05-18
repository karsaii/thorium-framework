package core.records.executor;

import core.records.Data;
import org.openqa.selenium.WebDriver;
import selenium.namespaces.extensions.boilers.DriverFunction;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class ExecutionDriverFunctionStepsData {
    public final DriverFunction<?>[] steps;
    public final WebDriver dependency;
    public final int length;

    public ExecutionDriverFunctionStepsData(DriverFunction<?>[] steps, WebDriver dependency, int length) {
        this.steps = steps;
        this.dependency = dependency;
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ExecutionStepsData<?>) o;
        return length == that.length && Arrays.equals(steps, that.steps) && Objects.equals(dependency, that.dependency);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(dependency, length) + Arrays.hashCode(steps);
    }
}
