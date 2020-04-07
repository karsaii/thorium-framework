package core.extensions.namespaces;

import java.util.Collection;

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
}
