package core.records;

import selenium.constants.ElementMethodNameConstants;
import validators.TypeMethod;

public abstract class MethodDefaults {
    public static final MethodParametersData IS_DISPLAYED = new MethodParametersData(ElementMethodNameConstants.IS_DISPLAYED, TypeMethod::isBooleanMethod);
    public static final MethodParametersData IS_ENABLED = new MethodParametersData(ElementMethodNameConstants.IS_ENABLED, TypeMethod::isBooleanMethod);
    public static final MethodParametersData IS_SELECTED = new MethodParametersData(ElementMethodNameConstants.IS_SELECTED, TypeMethod::isBooleanMethod);

    public static final MethodParametersData GET_TEXT = new MethodParametersData(ElementMethodNameConstants.GET_TEXT, TypeMethod::isStringMethod);
    public static final MethodParametersData GET_TAG_NAME = new MethodParametersData(ElementMethodNameConstants.GET_TAG_NAME, TypeMethod::isStringMethod);
    public static final MethodParametersData GET_ATTRIBUTE = new MethodParametersData(ElementMethodNameConstants.GET_ATTRIBUTE, TypeMethod::isStringMethod);
    public static final MethodParametersData GET_CSS_VALUE = new MethodParametersData(ElementMethodNameConstants.GET_CSS_VALUE, TypeMethod::isStringMethod);

    public static final MethodParametersData FIND_ELEMENT = new MethodParametersData(ElementMethodNameConstants.FIND_ELEMENT, TypeMethod::isWebElementMethod);

    public static final MethodParametersData CLICK = new MethodParametersData(ElementMethodNameConstants.CLICK, TypeMethod::isVoidMethod);
    public static final MethodParametersData CLEAR = new MethodParametersData(ElementMethodNameConstants.CLEAR, TypeMethod::isVoidMethod);
    public static final MethodParametersData SEND_KEYS = new MethodParametersData(ElementMethodNameConstants.SEND_KEYS, TypeMethod::isVoidMethod);
}
