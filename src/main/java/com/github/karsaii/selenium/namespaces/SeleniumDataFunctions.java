package com.github.karsaii.selenium.namespaces;

import com.github.karsaii.selenium.namespaces.extensions.boilers.WebElementList;
import com.github.karsaii.core.extensions.namespaces.DecoratedListFunctions;
import com.github.karsaii.core.extensions.namespaces.NullableFunctions;
import com.github.karsaii.core.records.Data;

import java.util.function.Predicate;

import static com.github.karsaii.core.namespaces.validators.DataValidators.isValidNonFalse;

public interface SeleniumDataFunctions {
    private static <T> boolean isOfTypeNonEmpty(Data<WebElementList> listData, Class<T> clazz) {
        return (
            isValidNonFalse(listData) &&
            NullableFunctions.isNotNull(clazz) &&
            DecoratedListFunctions.isOfTypeNonEmpty(listData.object, clazz)
        );
    }

    static <T> Predicate<Data<WebElementList>> isOfTypeNonEmpty(Class<T> clazz) {
        return list -> isOfTypeNonEmpty(list, clazz);
    }
}
