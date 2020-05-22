package selenium.namespaces.validators;

import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import data.constants.Strings;
import org.openqa.selenium.WebElement;
import selenium.constants.SeleniumCoreConstants;
import selenium.namespaces.extensions.boilers.WebElementList;

import java.util.function.Function;

public interface MessageFormatter {
    static Data<WebElement> getInvalidIndexMessageFunction(int index) {
        return DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, "Index(\"" + index +"\") was negative" + Strings.END_LINE);
    }

    static Function<Data<WebElementList>, Data<WebElement>> getElementIndexWasNegative(int index) {
        return listData -> MessageFormatter.getInvalidIndexMessageFunction(index);
    }

    static Data<WebElement> getInvalidTextMessageFunction(String message) {
        return DataFactoryFunctions.getWithMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, "Text(\"" + message + "\") was blank" + Strings.END_LINE);
    }
}
