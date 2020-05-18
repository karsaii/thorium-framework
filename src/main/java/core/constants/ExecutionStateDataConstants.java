package core.constants;

import core.extensions.namespaces.NullableFunctions;
import core.records.Data;

import java.util.function.Predicate;

public abstract class ExecutionStateDataConstants {
    public static final Predicate<Data<?>> DEFAULT_FILTER = NullableFunctions::isNotNull;
}
