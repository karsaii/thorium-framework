package selenium.records;

import core.extensions.DecoratedList;
import core.records.Data;
import org.openqa.selenium.WebElement;
import selector.records.SelectorKeySpecificityData;

import java.util.Map;
import java.util.Objects;

public class ExternalElementData {
    public final Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys;
    public final Data<WebElement> found;

    public ExternalElementData(Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, Data<WebElement> found) {
        this.typeKeys = typeKeys;
        this.found = found;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ExternalElementData) o;
        return Objects.equals(typeKeys, that.typeKeys) && Objects.equals(found, that.found);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeKeys, found);
    }
}
