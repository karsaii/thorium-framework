package selenium.namespaces.utilities;

import data.constants.Strings;
import org.openqa.selenium.remote.RemoteWebElement;

public interface BasicTypeUtilities {
    static RemoteWebElement getStockElement() {
        final var element = new RemoteWebElement();
        element.setId(Strings.NULL_ELEMENT_ID);
        return element;
    }
}
