package selenium.constants;

import core.records.MethodParametersData;
import selenium.namespaces.validators.SeleniumTypeMethods;

public abstract class SeleniumMethodDefaults {
    public static final MethodParametersData FIND_ELEMENT = new MethodParametersData(ElementMethodNameConstants.FIND_ELEMENT, SeleniumTypeMethods::isWebElementMethod, SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS);
}
