package com.github.karsaii.selenium.namespaces.utilities;

import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import org.openqa.selenium.remote.RemoteWebElement;

public interface BasicTypeUtilities {
    static RemoteWebElement getStockElement() {
        final var element = new RemoteWebElement();
        element.setId(SeleniumFormatterConstants.NULL_ELEMENT_ID);
        return element;
    }
}
