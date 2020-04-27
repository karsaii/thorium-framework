package selenium.constants;

import core.extensions.namespaces.CardinalitiesFunctions;
import data.constants.Strings;
import selenium.namespaces.Driver;
import selenium.namespaces.validators.SeleniumDataValidators;
import selenium.records.ElementConditionParameters;

public class ElementConditionConstants {
    public static final ElementConditionParameters PRESENT = new ElementConditionParameters(SeleniumDataValidators::isValidLazyElement, CardinalitiesFunctions::noopBoolean, "isElementPresent", Strings.PRESENT);
    public static final ElementConditionParameters DISPLAYED = new ElementConditionParameters(Driver::invokeElementDisplayed, CardinalitiesFunctions::noopBoolean, "isElementDisplayed", Strings.DISPLAYED);
    public static final ElementConditionParameters ENABLED = new ElementConditionParameters(Driver::invokeElementEnabled, CardinalitiesFunctions::noopBoolean, "isElementEnabled", Strings.ENABLED);
    public static final ElementConditionParameters CLICKABLE = new ElementConditionParameters(Driver::invokeElementClickable, CardinalitiesFunctions::noopBoolean, "isElementClickable", Strings.CLICKABLE);
    public static final ElementConditionParameters SELECTED = new ElementConditionParameters(Driver::invokeElementSelected, CardinalitiesFunctions::noopBoolean, "isElementSelected", Strings.SELECTED);

    public static final ElementConditionParameters ABSENT = new ElementConditionParameters(SeleniumDataValidators::isNotNull, CardinalitiesFunctions::invertBoolean, "isElementAbsent", Strings.ABSENT);
    public static final ElementConditionParameters HIDDEN = new ElementConditionParameters(Driver::invokeElementDisplayed, CardinalitiesFunctions::invertBoolean, "isElementHidden", Strings.HIDDEN);
    public static final ElementConditionParameters DISABLED = new ElementConditionParameters(Driver::invokeElementEnabled, CardinalitiesFunctions::noopBoolean, "isElementDisabled", Strings.ENABLED);
    public static final ElementConditionParameters UNCLICKABLE = new ElementConditionParameters(Driver::invokeElementClickable, CardinalitiesFunctions::noopBoolean, "isElementUnclickable", Strings.UNCLICKABLE);
    public static final ElementConditionParameters UNSELECTED = new ElementConditionParameters(Driver::invokeElementSelected, CardinalitiesFunctions::noopBoolean, "isElementUnselected", Strings.SELECTED);
}
