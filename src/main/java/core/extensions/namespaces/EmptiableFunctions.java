package core.extensions.namespaces;

import java.util.Collection;
import java.util.Map;

public interface EmptiableFunctions {
    static <T> boolean isEmpty(Collection<T> collection) {
        return NullableFunctions.isNotNull(collection) && collection.isEmpty();
    }

    static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return NullableFunctions.isNull(collection) || collection.isEmpty();
    }

    static <T> boolean isNotNullAndNonEmpty(Collection<T> collection) {
        return !(isNullOrEmpty(collection));
    }

    static <T, U> boolean isEmpty(Map<T, U> map) {
        return NullableFunctions.isNotNull(map) && map.isEmpty();
    }

    static <T, U> boolean isNullOrEmpty(Map<T, U> map) {
        return NullableFunctions.isNull(map) || map.isEmpty();
    }

    static <T, U> boolean isNotNullAndNonEmpty(Map<T, U> map) {
        return !(isNullOrEmpty(map));
    }
}
