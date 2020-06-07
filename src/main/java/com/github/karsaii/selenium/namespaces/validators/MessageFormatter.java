package com.github.karsaii.selenium.namespaces.validators;

import com.github.karsaii.core.namespaces.DataFactoryFunctions;
import com.github.karsaii.core.records.Data;
import com.github.karsaii.core.constants.validators.CoreFormatterConstants;
import org.openqa.selenium.WebElement;
import com.github.karsaii.selenium.constants.SeleniumCoreConstants;
import com.github.karsaii.selenium.namespaces.extensions.boilers.WebElementList;

import java.util.function.Function;

public interface MessageFormatter {
    static Data<WebElement> getInvalidIndexMessageFunction(int index) {
        return DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, "Index(\"" + index +"\") was negative" + CoreFormatterConstants.END_LINE);
    }

    static Function<Data<WebElementList>, Data<WebElement>> getElementIndexWasNegative(int index) {
        return listData -> MessageFormatter.getInvalidIndexMessageFunction(index);
    }

    static Data<WebElement> getInvalidTextMessageFunction(String message) {
        return DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, "Text(\"" + message + "\") was blank" + CoreFormatterConstants.END_LINE);
    }
}
