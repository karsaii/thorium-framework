package selenium.constants.scripts;

import static selenium.namespaces.scripter.General.IF_RETURN;
import static selenium.namespaces.scripter.General.RETURN;

public abstract class Attribute {
    public static final String SET_ATTRIBUTE = (
        IF_RETURN("arguments.length < 3", "null") +
        "arguments[0].setAttribute(arguments[1], arguments[2]);" +
        RETURN("arguments[0].getAttribute(arguments[1])")
    );
}
