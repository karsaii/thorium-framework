package selenium.constants;

import core.records.caster.BasicCastData;
import org.openqa.selenium.WebElement;
import core.constants.CoreConstants;

public abstract class SeleniumCastDataConstants {
    public static final BasicCastData<WebElement> WEB_ELEMENT = new BasicCastData<>(SeleniumCoreConstants.STOCK_ELEMENT, SeleniumCoreConstants.WEB_ELEMENT_CASTER_FUNCTION);
}
