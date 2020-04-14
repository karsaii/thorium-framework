package core.constants;

import core.records.MethodDefaults;
import core.records.reflection.InvokeMethodData;

public abstract class InvokeConstants {
    public static final String INVOKE = "invoke";
    public static final String ELEMENT_PREFIX = INVOKE + "Element";
    public static final String GET_ELEMENT_PREFIX = INVOKE + "GetElement";
    public static final String ELEMENT_DISPLAYED = ELEMENT_PREFIX + "Displayed";
    public static final String ELEMENT_ENABLED = ELEMENT_PREFIX + "Enabled";
    public static final String ELEMENT_SELECTED = ELEMENT_PREFIX + "Selected";
    public static final String ELEMENT_CLICKABLE = ELEMENT_PREFIX + "Clickable";
    public static final String GET_ELEMENT_TEXT = GET_ELEMENT_PREFIX + "Text";
    public static final String GET_ELEMENT_TAGNAME = GET_ELEMENT_PREFIX + "Tagname";
    public static final String GET_ELEMENT_ATTRIBUTE = GET_ELEMENT_PREFIX + "Attribute";
    public static final String GET_ELEMENT_CSS_VALUE = GET_ELEMENT_PREFIX + "CssValue";

    public static final String SEND_KEYS = ELEMENT_PREFIX + "SendKeys";
    public static final String CLICK = ELEMENT_PREFIX + "Click";
    public static final String CLEAR = ELEMENT_PREFIX + "Clear";

    public static final InvokeMethodData DISPLAYED = new InvokeMethodData(MethodDefaults.IS_DISPLAYED, InvokeConstants.ELEMENT_DISPLAYED);
    public static final InvokeMethodData ENABLED = new InvokeMethodData(MethodDefaults.IS_ENABLED, InvokeConstants.ELEMENT_ENABLED);
    public static final InvokeMethodData SELECTED = new InvokeMethodData(MethodDefaults.IS_SELECTED, InvokeConstants.ELEMENT_SELECTED);

}
