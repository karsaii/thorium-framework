package selenium.constants;

import core.extensions.namespaces.CardinalitiesFunctions;
import data.constants.Strings;
import data.namespaces.Formatter;
import selenium.namespaces.Driver;
import selenium.namespaces.ExecutionCore;
import selenium.namespaces.validators.SeleniumDataValidators;
import selenium.records.element.is.ElementFormatData;
import selenium.records.element.is.ElementParameterizedValueParameters;
import selenium.records.element.is.ElementConditionParameters;
import selenium.records.element.is.ElementStringValueParameters;

public abstract class ElementFunctionConstants {
    public static final ElementFormatData<Boolean> PRESENT_DATA = new ElementFormatData<>(Formatter::getConditionMessage, "isElementPresent", Strings.PRESENT);
    public static final ElementFormatData<Boolean> DISPLAYED_DATA = new ElementFormatData<>(Formatter::getConditionMessage, "isElementDisplayed", Strings.DISPLAYED);
    public static final ElementFormatData<Boolean> ENABLED_DATA = new ElementFormatData<>(Formatter::getConditionMessage, "isElementEnabled", Strings.ENABLED);
    public static final ElementFormatData<Boolean> CLICKABLE_DATA = new ElementFormatData<>(Formatter::getConditionMessage, "isElementClickable", Strings.CLICKABLE);
    public static final ElementFormatData<Boolean> SELECTED_DATA = new ElementFormatData<>(Formatter::getConditionMessage, "isElementSelected", Strings.SELECTED);

    public static final ElementFormatData<Boolean> ABSENT_DATA = new ElementFormatData<>(Formatter::getConditionMessage, "isElementAbsent", Strings.ABSENT);
    public static final ElementFormatData<Boolean> HIDDEN_DATA = new ElementFormatData<>(Formatter::getConditionMessage, "isElementHidden", Strings.HIDDEN);
    public static final ElementFormatData<Boolean> DISABLED_DATA = new ElementFormatData<>(Formatter::getConditionMessage, "isElementDisabled", Strings.DISABLED);
    public static final ElementFormatData<Boolean> UNCLICKABLE_DATA = new ElementFormatData<>(Formatter::getConditionMessage, "isElementUnclickable", Strings.UNCLICKABLE);
    public static final ElementFormatData<Boolean> UNSELECTED_DATA = new ElementFormatData<>(Formatter::getConditionMessage, "isElementUnselected", Strings.UNSELECTED);

    public static final ElementFormatData<String> TEXT_DATA = new ElementFormatData<>(Formatter::getElementValueMessage, "getElementText", Strings.TEXT);
    public static final ElementFormatData<String> TAGNAME_DATA = new ElementFormatData<>(Formatter::getElementValueMessage, "getElementTagName", Strings.TAGNAME);
    public static final ElementFormatData<String> ATTRIBUTE_DATA = new ElementFormatData<>(Formatter::getElementValueMessage, "getElementAttribute", Strings.ATTRIBUTE);
    public static final ElementFormatData<String> VALUE_ATTRIBUTE_DATA = new ElementFormatData<>(Formatter::getElementValueMessage, "getElementValueAttribute", Strings.VALUE_ATTRIBUTE);
    public static final ElementFormatData<String> CSS_VALUE_DATA = new ElementFormatData<>(Formatter::getElementValueMessage, "getElementCssValue", Strings.CSS_VALUE);

    public static final ElementConditionParameters<Boolean> PRESENT = new ElementConditionParameters<>(ExecutionCore::validChain, PRESENT_DATA, SeleniumDataValidators::isValidLazyElement, CardinalitiesFunctions::noopBoolean);
    public static final ElementConditionParameters<Boolean> DISPLAYED = new ElementConditionParameters<>(ExecutionCore::validChain, DISPLAYED_DATA, Driver::invokeElementDisplayed, CardinalitiesFunctions::noopBoolean);
    public static final ElementConditionParameters<Boolean> ENABLED = new ElementConditionParameters<>(ExecutionCore::validChain, ENABLED_DATA, Driver::invokeElementEnabled, CardinalitiesFunctions::noopBoolean);
    public static final ElementConditionParameters<Boolean> CLICKABLE = new ElementConditionParameters<>(ExecutionCore::validChain, CLICKABLE_DATA, Driver::invokeElementClickable, CardinalitiesFunctions::noopBoolean);
    public static final ElementConditionParameters<Boolean> SELECTED = new ElementConditionParameters<>(ExecutionCore::validChain, SELECTED_DATA, Driver::invokeElementSelected, CardinalitiesFunctions::noopBoolean);

    public static final ElementConditionParameters<Boolean> ABSENT = new ElementConditionParameters<>(ExecutionCore::nonNullChain, ABSENT_DATA, SeleniumDataValidators::isNotNull, CardinalitiesFunctions::invertBoolean);
    public static final ElementConditionParameters<Boolean> HIDDEN = new ElementConditionParameters<>(ExecutionCore::validChain, HIDDEN_DATA, Driver::invokeElementDisplayed, CardinalitiesFunctions::invertBoolean);
    public static final ElementConditionParameters<Boolean> DISABLED = new ElementConditionParameters<>(ExecutionCore::validChain, DISABLED_DATA, Driver::invokeElementEnabled, CardinalitiesFunctions::invertBoolean);
    public static final ElementConditionParameters<Boolean> UNCLICKABLE = new ElementConditionParameters<>(ExecutionCore::validChain, UNCLICKABLE_DATA, Driver::invokeElementClickable, CardinalitiesFunctions::invertBoolean);
    public static final ElementConditionParameters<Boolean> UNSELECTED = new ElementConditionParameters<>(ExecutionCore::validChain, UNSELECTED_DATA, Driver::invokeElementSelected, CardinalitiesFunctions::invertBoolean);

    public static final ElementStringValueParameters<String> TEXT = new ElementStringValueParameters<>(ExecutionCore::validChain, TEXT_DATA, Driver::invokeGetElementText);
    public static final ElementStringValueParameters<String> TAGNAME = new ElementStringValueParameters<>(ExecutionCore::validChain, TAGNAME_DATA, Driver::invokeGetElementText);
    public static final ElementParameterizedValueParameters<String> ATTRIBUTE = new ElementParameterizedValueParameters<>(ExecutionCore::validChain, ATTRIBUTE_DATA, Driver::invokeGetElementAttribute);
    public static final ElementParameterizedValueParameters<String> VALUE_ATTRIBUTE = new ElementParameterizedValueParameters<>(ExecutionCore::validChain, VALUE_ATTRIBUTE_DATA, Driver::invokeGetElementAttribute);
    public static final ElementParameterizedValueParameters<String> CSS_VALUE = new ElementParameterizedValueParameters<>(ExecutionCore::validChain, CSS_VALUE_DATA, Driver::invokeGetElementCssValue);
}
