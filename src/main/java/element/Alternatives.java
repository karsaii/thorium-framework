package element;

import data.ElementAlternative;
import data.functions.ClearData;
import data.functions.ClickData;
import data.functions.ElementFunctionsData;
import data.functions.SendKeysData;

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
