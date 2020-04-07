package core.extensions.namespaces;

import core.extensions.DecoratedList;
import selenium.constants.DataConstants;

import java.util.Objects;

public interface DecoratedListFunctions {
    static <T, U> boolean isOfTypeNonEmpty(DecoratedList<T> list, Class<U> clazz) {
        return (
            CoreUtilities.areAny(NullableFunctions::isNotNull, list, clazz) &&
            list.isNotNullAndNonEmpty() &&
            Objects.equals(clazz.getTypeName(), list.getType()) &&
            !Objects.equals(DataConstants.NULL_ELEMENT, list.first())
        );
    }
}
