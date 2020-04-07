package selenium.namespaces;

import core.extensions.boilers.WebElementList;
import core.extensions.namespaces.DecoratedListFunctions;
import core.extensions.namespaces.NullableFunctions;
import core.namespaces.DataFunctions;
import core.records.Data;

import java.util.function.Predicate;

public interface SeleniumDataFunctions {
    private static <T> boolean isOfTypeNonEmpty(Data<WebElementList> listData, Class<T> clazz) {
        return (
            DataFunctions.isValidNonFalse(listData) &&
            NullableFunctions.isNotNull(clazz) &&
            DecoratedListFunctions.isOfTypeNonEmpty(listData.object, clazz)
        );
    }

    static <T> Predicate<Data<WebElementList>> isOfTypeNonEmpty(Class<T> clazz) {
        return list -> isOfTypeNonEmpty(list, clazz);
    }
}
