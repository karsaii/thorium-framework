package com.github.karsaii.selenium.constants;

import com.github.karsaii.selenium.namespaces.element.Element;
import com.github.karsaii.selenium.namespaces.element.ElementAlternatives;
import com.github.karsaii.selenium.records.element.ClearData;
import com.github.karsaii.selenium.records.element.ClickData;
import com.github.karsaii.selenium.records.element.ElementFunctionsData;
import com.github.karsaii.selenium.records.element.SendKeysData;
import com.github.karsaii.selenium.records.expectedcondition.ElementAlternative;

public abstract class Alternatives {
    public static final ElementAlternative regular = new ElementAlternative();
    public static final ElementAlternative keyboardClear = new ElementAlternative(
        new ElementFunctionsData(
            new ClickData(Element::click, Element::click, Element::click),
            new ClearData(ElementAlternatives::clearWithSelectAll, ElementAlternatives::clearWithSelectAll, ElementAlternatives::clearWithSelectAll),
            new SendKeysData(Element::sendKeys, Element::sendKeys, Element::sendKeys)
        )
    );

    public static final ElementAlternative clickWithEventDispatcher = new ElementAlternative(
        new ElementFunctionsData(
            new ClickData(ElementAlternatives::clickWithEventDispatcher, ElementAlternatives::clickWithEventDispatcher, ElementAlternatives::clickWithEventDispatcher),
            new ClearData(Element::clear, Element::clear, Element::clear),
            new SendKeysData(Element::sendKeys, Element::sendKeys, Element::sendKeys)
        )
    );
}
