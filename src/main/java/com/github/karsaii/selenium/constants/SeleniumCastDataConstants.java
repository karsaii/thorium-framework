package com.github.karsaii.selenium.constants;

import com.github.karsaii.core.records.caster.BasicCastData;
import org.openqa.selenium.WebElement;

public abstract class SeleniumCastDataConstants {
    public static final BasicCastData<WebElement> WEB_ELEMENT = new BasicCastData<>(SeleniumCoreConstants.STOCK_ELEMENT, SeleniumCoreConstants.WEB_ELEMENT_CASTER_FUNCTION);
}
