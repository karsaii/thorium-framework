package selenium.constants;

import core.extensions.namespaces.CardinalitiesFunctions;
import data.constants.Strings;
import data.namespaces.Formatter;
import selenium.namespaces.Driver;
import selenium.namespaces.validators.SeleniumDataValidators;
import selenium.records.ElementParameterizedValueParameters;
import selenium.records.ElementConditionParameters;
import selenium.records.ElementValueParameters;

public abstract class ElementFunctionConstants {
    public static final ElementConditionParameters<Boolean> PRESENT = new ElementConditionParameters<>(SeleniumDataValidators::isValidLazyElement, Formatter::getConditionMessage, CardinalitiesFunctions::noopBoolean, "isElementPresent", Strings.PRESENT);
    public static final ElementConditionParameters<Boolean> DISPLAYED = new ElementConditionParameters<>(Driver::invokeElementDisplayed, Formatter::getConditionMessage, CardinalitiesFunctions::noopBoolean, "isElementDisplayed", Strings.DISPLAYED);
    public static final ElementConditionParameters<Boolean> ENABLED = new ElementConditionParameters<>(Driver::invokeElementEnabled, Formatter::getConditionMessage, CardinalitiesFunctions::noopBoolean, "isElementEnabled", Strings.ENABLED);
    public static final ElementConditionParameters<Boolean> CLICKABLE = new ElementConditionParameters<>(Driver::invokeElementClickable, Formatter::getConditionMessage, CardinalitiesFunctions::noopBoolean, "isElementClickable", Strings.CLICKABLE);
    public static final ElementConditionParameters<Boolean> SELECTED = new ElementConditionParameters<>(Driver::invokeElementSelected, Formatter::getConditionMessage, CardinalitiesFunctions::noopBoolean, "isElementSelected", Strings.SELECTED);

    public static final ElementConditionParameters<Boolean> ABSENT = new ElementConditionParameters<>(SeleniumDataValidators::isNotNull, Formatter::getConditionMessage, CardinalitiesFunctions::invertBoolean, "isElementAbsent", Strings.ABSENT);
    public static final ElementConditionParameters<Boolean> HIDDEN = new ElementConditionParameters<>(Driver::invokeElementDisplayed, Formatter::getConditionMessage, CardinalitiesFunctions::invertBoolean, "isElementHidden", Strings.HIDDEN);
    public static final ElementConditionParameters<Boolean> DISABLED = new ElementConditionParameters<>(Driver::invokeElementEnabled, Formatter::getConditionMessage, CardinalitiesFunctions::invertBoolean, "isElementDisabled", Strings.ENABLED);
    public static final ElementConditionParameters<Boolean> UNCLICKABLE = new ElementConditionParameters<>(Driver::invokeElementClickable, Formatter::getConditionMessage, CardinalitiesFunctions::invertBoolean, "isElementUnclickable", Strings.UNCLICKABLE);
    public static final ElementConditionParameters<Boolean> UNSELECTED = new ElementConditionParameters<>(Driver::invokeElementSelected, Formatter::getConditionMessage, CardinalitiesFunctions::invertBoolean, "isElementUnselected", Strings.SELECTED);

    public static final ElementValueParameters<String> TEXT = new ElementValueParameters<>(Driver::invokeGetElementText, Formatter::getElementValueMessage, "getElementText", Strings.TEXT);
    public static final ElementValueParameters<String> TAGNAME = new ElementValueParameters<>(Driver::invokeGetElementText, Formatter::getElementValueMessage, "getElementTagName", Strings.TAGNAME);
    public static final ElementParameterizedValueParameters<String> ATTRIBUTE = new ElementParameterizedValueParameters<>(Driver::invokeGetElementAttribute, Formatter::getElementValueMessage, "getElementAttribute", Strings.ATTRIBUTE);
    public static final ElementParameterizedValueParameters<String> VALUE_ATTRIBUTE = new ElementParameterizedValueParameters<>(Driver::invokeGetElementAttribute, Formatter::getElementValueMessage, "getElementValueAttribute", Strings.VALUE_ATTRIBUTE);
    public static final ElementParameterizedValueParameters<String> CSS_VALUE = new ElementParameterizedValueParameters<>(Driver::invokeGetElementCssValue, Formatter::getElementValueMessage, "getElementCssValue", Strings.CSS_VALUE);
}
