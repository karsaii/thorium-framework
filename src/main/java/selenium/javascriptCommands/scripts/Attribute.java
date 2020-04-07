package selenium.javascriptCommands.scripts;

import static selenium.javascriptCommands.General.IF_RETURN;
import static selenium.javascriptCommands.General.RETURN;

public class Attribute {
    public static final String SET_ATTRIBUTE = (
        IF_RETURN("arguments.length < 3", "null") +
        "arguments[0].setAttribute(arguments[1], arguments[2]);" +
        RETURN("arguments[0].getAttribute(arguments[1])")
    );
}
