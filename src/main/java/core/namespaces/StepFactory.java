package core.namespaces;

import core.extensions.interfaces.functional.boilers.DataSupplier;
import core.records.Data;
import core.records.executor.ExecutionStepData;

import java.util.function.Function;

public interface StepFactory {
    static <DependencyType, ReturnType> DataSupplier<ReturnType> step(Function<DependencyType, Data<ReturnType>> function, DependencyType dependency) {
        return new ExecutionStepData<>(function, dependency);
    }
}
