package core.extensions.namespaces;

import core.extensions.DecoratedList;
import selenium.constants.SeleniumDataConstants;

import java.util.Objects;

public interface DecoratedListFunctions {
    static <T, U> boolean isOfTypeNonEmpty(DecoratedList<T> list, Class<U> clazz) {
        return (
            CoreUtilities.areAny(NullableFunctions::isNotNull, list, clazz) &&
            list.isNotNullAndNonEmpty() &&
            Objects.equals(clazz.getTypeName(), list.getType()) &&
            !Objects.equals(SeleniumDataConstants.NULL_ELEMENT, list.first())
        );
    }
}
