package data.tuples;

import data.Data;
import data.extensions.DecoratedList;
import data.lazy.SelectorKeySpecificityData;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class ExternalElementData {
    public final Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys;
    public final Data<WebElement> found;

    public ExternalElementData(Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, Data<WebElement> found) {
        this.typeKeys = typeKeys;
        this.found = found;
    }
}
