package com.github.karsaii.selenium.constants;

import com.github.karsaii.core.records.MethodParametersData;
import com.github.karsaii.selenium.namespaces.validators.SeleniumTypeMethods;

public abstract class SeleniumMethodDefaults {
    public static final MethodParametersData FIND_ELEMENT = new MethodParametersData(ElementMethodNameConstants.FIND_ELEMENT, SeleniumTypeMethods::isWebElementMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);
}
