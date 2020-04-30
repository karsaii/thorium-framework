package core.extensions.interfaces.functional;

import core.records.Data;

@FunctionalInterface
public interface IStepHandler {
    <Any> Data<Any> apply(int index);
}
