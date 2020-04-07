package selenium.namespaces.repositories;

import core.enums.TypedEnumKeyData;
import core.extensions.interfaces.DriverFunction;
import selenium.enums.TypeKey;

import java.util.Map;

public interface FunctionRepository {
    static <T> DriverFunction<T> get(Map<TypeKey, DriverFunction<?>> functionMap, TypedEnumKeyData<T> keyData) {
        return (DriverFunction<T>) functionMap.get(keyData.key);
    }
}
