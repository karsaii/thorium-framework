package selenium.records.scripter;

import core.records.TypedEnumKeyData;
import selenium.namespaces.extensions.boilers.DriverFunction;
import core.enums.TypeKey;

import java.util.Map;
import java.util.Objects;

public class ExecuteCoreData<HandlerType, ReturnType> {
    public final ExecutorData<HandlerType, String, Boolean, ReturnType> data;
    public final Map<TypeKey, DriverFunction<?>> functionMap;
    public final TypedEnumKeyData<ReturnType> negativeKeyData;

    public ExecuteCoreData(ExecutorData<HandlerType, String, Boolean, ReturnType> data, Map<TypeKey, DriverFunction<?>> functionMap, TypedEnumKeyData<ReturnType> negativeKeyData) {
        this.data = data;
        this.functionMap = functionMap;
        this.negativeKeyData = negativeKeyData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ExecuteCoreData<?, ?>) o;
        return Objects.equals(data, that.data) && Objects.equals(functionMap, that.functionMap) && Objects.equals(negativeKeyData, that.negativeKeyData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, functionMap, negativeKeyData);
    }
}
