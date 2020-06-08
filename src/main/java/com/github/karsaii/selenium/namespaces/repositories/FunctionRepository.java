package com.github.karsaii.selenium.namespaces.repositories;

import com.github.karsaii.core.records.TypedEnumKeyData;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.core.enums.TypeKey;

import java.util.Map;

public interface FunctionRepository {
    static <T> DriverFunction<T> get(Map<TypeKey, DriverFunction<?>> functionMap, TypedEnumKeyData<T> keyData) {
        return (DriverFunction<T>) functionMap.get(keyData.key);
    }
}
