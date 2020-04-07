package selenium.element;

import selenium.element.functions.ClearData;
import selenium.element.functions.ClickData;
import selenium.element.functions.ElementFunctionsData;
import selenium.element.functions.SendKeysData;
import selenium.records.ElementAlternative;

public class Alternatives {
    public static final ElementAlternative regular = new ElementAlternative();
    public static final ElementAlternative keyboardClear = new ElementAlternative(
        new ElementFunctionsData(
            new ClickData(Element::click, Element::click, Element::click),
            new ClearData(ElementAlternatives::clearWithSelectAll, ElementAlternatives::clearWithSelectAll, ElementAlternatives::clearWithSelectAll),
            new SendKeysData(Element::sendKeys, Element::sendKeys, Element::sendKeys)
        )
    );
}
