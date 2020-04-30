package selenium.constants.scripts;

import selenium.namespaces.scripter.Globals;

public abstract class Properties {
    public static final String FUNCTION_NAME = "GET_STYLES",
            FUNCTION_GROUP = "PROPERTIES",
            IS_EXISTS = Globals.getFunctionExists(FUNCTION_GROUP, FUNCTION_NAME);
}
