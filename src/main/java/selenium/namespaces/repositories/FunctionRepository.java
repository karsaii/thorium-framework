package selenium.namespaces.repositories;

import core.records.TypedEnumKeyData;
import selenium.namespaces.extensions.boilers.DriverFunction;
import core.enums.TypeKey;

import java.util.Map;

public interface FunctionRepository {
    static <T> DriverFunction<T> get(Map<TypeKey, DriverFunction<?>> functionMap, TypedEnumKeyData<T> keyData) {
        return (DriverFunction<T>) functionMap.get(keyData.key);
    }
}
