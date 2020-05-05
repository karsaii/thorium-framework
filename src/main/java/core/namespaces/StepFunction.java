package core.namespaces;

import core.records.Data;

public interface StepFunction<T> {
    Data<T> apply();
}
