package core.reflection.message;

import core.reflection.abstracts.InvokeBaseMessageData;

import java.util.Objects;

public class InvokeCommonMessageParametersData extends InvokeBaseMessageData {
    public InvokeCommonMessageParametersData(String message, String returnType, String parameterTypes) {
        super(message, returnType, parameterTypes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (InvokeCommonMessageParametersData) o;
        return Objects.equals(message, that.message) && Objects.equals(returnType, that.returnType) && Objects.equals(parameterTypes, that.parameterTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, returnType, parameterTypes);
    }
}
