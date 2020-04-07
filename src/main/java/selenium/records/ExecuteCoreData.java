package selenium.records;

import core.enums.TypedEnumKeyData;
import core.extensions.interfaces.DriverFunction;
import selenium.enums.TypeKey;
import selenium.records.scripter.ExecutorData;

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
