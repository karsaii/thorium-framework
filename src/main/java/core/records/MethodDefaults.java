package core.records;

import selenium.constants.ElementMethodNameConstants;
import selenium.constants.SeleniumCoreConstants;
import selenium.namespaces.validators.SeleniumTypeMethods;
import validators.TypeMethod;

public abstract class MethodDefaults {
    public static final MethodParametersData IS_DISPLAYED = new MethodParametersData(ElementMethodNameConstants.IS_DISPLAYED, TypeMethod::isBooleanMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);
    public static final MethodParametersData IS_ENABLED = new MethodParametersData(ElementMethodNameConstants.IS_ENABLED, TypeMethod::isBooleanMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);
    public static final MethodParametersData IS_SELECTED = new MethodParametersData(ElementMethodNameConstants.IS_SELECTED, TypeMethod::isBooleanMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);

    public static final MethodParametersData GET_TEXT = new MethodParametersData(ElementMethodNameConstants.GET_TEXT, TypeMethod::isStringMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);
    public static final MethodParametersData GET_TAG_NAME = new MethodParametersData(ElementMethodNameConstants.GET_TAG_NAME, TypeMethod::isStringMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);
    public static final MethodParametersData GET_ATTRIBUTE = new MethodParametersData(ElementMethodNameConstants.GET_ATTRIBUTE, TypeMethod::isStringMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);
    public static final MethodParametersData GET_CSS_VALUE = new MethodParametersData(ElementMethodNameConstants.GET_CSS_VALUE, TypeMethod::isStringMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);

    public static final MethodParametersData FIND_ELEMENT = new MethodParametersData(ElementMethodNameConstants.FIND_ELEMENT, SeleniumTypeMethods::isWebElementMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);

    public static final MethodParametersData CLICK = new MethodParametersData(ElementMethodNameConstants.CLICK, TypeMethod::isVoidMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);
    public static final MethodParametersData CLEAR = new MethodParametersData(ElementMethodNameConstants.CLEAR, TypeMethod::isVoidMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);
    public static final MethodParametersData SEND_KEYS = new MethodParametersData(ElementMethodNameConstants.SEND_KEYS, TypeMethod::isVoidMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);
}
