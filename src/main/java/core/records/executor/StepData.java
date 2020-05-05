package core.records.executor;

import core.records.Data;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class StepData<DependencyType, ReturnType> {
    public final Function<DependencyType, Data<?>>[] functions;
    public final DependencyType dependency;
    public final Function<Object, ReturnType> caster;

    public StepData(Function<DependencyType, Data<?>>[] functions, DependencyType dependency, Function<Object, ReturnType> caster) {
        this.functions = functions;
        this.dependency = dependency;
        this.caster = caster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var stepData = (StepData<?, ?>) o;
        return Arrays.equals(functions, stepData.functions) && Objects.equals(dependency, stepData.dependency) && Objects.equals(caster, stepData.caster);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(dependency, caster) + Arrays.hashCode(functions);
    }
}
