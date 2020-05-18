package core.namespaces;

import core.extensions.interfaces.functional.boilers.DataSupplier;
import core.records.Data;

import java.util.function.Function;

public interface DataSupplierFactory {
    static <T> DataSupplier<T> get(Function<Void, Data<T>> function) {
        return function::apply;
    }
}
