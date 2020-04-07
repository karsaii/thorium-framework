package core.enums;

import selenium.enums.TypeKey;

import java.util.Objects;

public class TypedEnumKeyData<T> {
    public final TypeKey key;
    public final Class<T> clazz;

    public TypedEnumKeyData(TypeKey key, Class<T> clazz) {
        this.key = key;
        this.clazz = clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (TypedEnumKeyData<?>) o;
        return key == that.key && Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, clazz);
    }
}
