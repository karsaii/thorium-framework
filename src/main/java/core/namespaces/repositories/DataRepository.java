package core.namespaces.repositories;

import core.enums.TypedEnumKeyData;
import core.records.Data;
import selenium.enums.TypeKey;

import java.util.Map;

public interface DataRepository {
    static <T> Data<T> get(Map<TypeKey, Data<?>> dataMap, TypedEnumKeyData<T> keyData) {
        return (Data<T>) dataMap.get(keyData.key);
    }
}
