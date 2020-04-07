package selenium.namespaces;

import core.extensions.namespaces.CoreUtilities;
import core.records.Data;
import selenium.records.LazyElement;

import static core.namespaces.DataFunctions.isInvalidOrFalse;
import static core.namespaces.DataFunctions.isValidNonFalse;

public interface WaitPredicateFunctions {
    static <T> boolean isFalsyData(T object) {
        return (
            ((object instanceof Data) && (isInvalidOrFalse((Data<?>) object))) ||
            ((object instanceof LazyElement) && (SeleniumUtilities.isNullLazyElement((LazyElement)object)))
        ) || CoreUtilities.isFalse(object);
    }

    static <T> boolean isTruthyData(T object) {
        return (
            ((object instanceof Data) && (isValidNonFalse((Data<?>) object))) ||
            ((object instanceof LazyElement) && (SeleniumUtilities.isNotNullLazyElement((LazyElement)object)))
        ) || CoreUtilities.isTrue(object);
    }
}
