package com.github.karsaii.selenium.constants;

import com.github.karsaii.core.extensions.namespaces.CardinalitiesFunctions;
import com.github.karsaii.selenium.constants.validators.SeleniumFormatterConstants;
import com.github.karsaii.core.namespaces.validators.CoreFormatter;
import com.github.karsaii.selenium.namespaces.Driver;
import com.github.karsaii.selenium.namespaces.ExecutionCore;
import com.github.karsaii.selenium.namespaces.validators.SeleniumDataValidators;
import com.github.karsaii.selenium.records.element.is.ElementFormatData;
import com.github.karsaii.selenium.records.element.is.ElementParameterizedValueParameters;
import com.github.karsaii.selenium.records.element.is.ElementConditionParameters;
import com.github.karsaii.selenium.records.element.is.ElementStringValueParameters;

public abstract class ElementFunctionConstants {
    public static final ElementFormatData<Boolean> PRESENT_DATA = new ElementFormatData<>(CoreFormatter::getConditionMessage, "isElementPresent", SeleniumFormatterConstants.PRESENT);
    public static final ElementFormatData<Boolean> DISPLAYED_DATA = new ElementFormatData<>(CoreFormatter::getConditionMessage, "isElementDisplayed", SeleniumFormatterConstants.DISPLAYED);
    public static final ElementFormatData<Boolean> ENABLED_DATA = new ElementFormatData<>(CoreFormatter::getConditionMessage, "isElementEnabled", SeleniumFormatterConstants.ENABLED);
    public static final ElementFormatData<Boolean> CLICKABLE_DATA = new ElementFormatData<>(CoreFormatter::getConditionMessage, "isElementClickable", SeleniumFormatterConstants.CLICKABLE);
    public static final ElementFormatData<Boolean> SELECTED_DATA = new ElementFormatData<>(CoreFormatter::getConditionMessage, "isElementSelected", SeleniumFormatterConstants.SELECTED);

    public static final ElementFormatData<Boolean> ABSENT_DATA = new ElementFormatData<>(CoreFormatter::getConditionMessage, "isElementAbsent", SeleniumFormatterConstants.ABSENT);
    public static final ElementFormatData<Boolean> HIDDEN_DATA = new ElementFormatData<>(CoreFormatter::getConditionMessage, "isElementHidden", SeleniumFormatterConstants.HIDDEN);
    public static final ElementFormatData<Boolean> DISABLED_DATA = new ElementFormatData<>(CoreFormatter::getConditionMessage, "isElementDisabled", SeleniumFormatterConstants.DISABLED);
    public static final ElementFormatData<Boolean> UNCLICKABLE_DATA = new ElementFormatData<>(CoreFormatter::getConditionMessage, "isElementUnclickable", SeleniumFormatterConstants.UNCLICKABLE);
    public static final ElementFormatData<Boolean> UNSELECTED_DATA = new ElementFormatData<>(CoreFormatter::getConditionMessage, "isElementUnselected", SeleniumFormatterConstants.UNSELECTED);

    public static final ElementFormatData<String> TEXT_DATA = new ElementFormatData<>(CoreFormatter::getElementValueMessage, "getElementText", SeleniumFormatterConstants.TEXT);
    public static final ElementFormatData<String> TAGNAME_DATA = new ElementFormatData<>(CoreFormatter::getElementValueMessage, "getElementTagName", SeleniumFormatterConstants.TAGNAME);
    public static final ElementFormatData<String> ATTRIBUTE_DATA = new ElementFormatData<>(CoreFormatter::getElementValueMessage, "getElementAttribute", SeleniumFormatterConstants.ATTRIBUTE);
    public static final ElementFormatData<String> VALUE_ATTRIBUTE_DATA = new ElementFormatData<>(CoreFormatter::getElementValueMessage, "getElementValueAttribute", SeleniumFormatterConstants.VALUE_ATTRIBUTE);
    public static final ElementFormatData<String> CSS_VALUE_DATA = new ElementFormatData<>(CoreFormatter::getElementValueMessage, "getElementCssValue", SeleniumFormatterConstants.CSS_VALUE);

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
