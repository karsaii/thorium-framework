package core.extensions.interfaces.functional.boilers;

import core.records.Data;

import java.util.function.Supplier;

@FunctionalInterface
public interface DataSupplier<T> extends Supplier<Data<T>> {}
