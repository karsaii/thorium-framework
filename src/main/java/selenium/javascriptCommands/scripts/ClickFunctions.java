package selenium.javascriptCommands.scripts;

import static selenium.javascriptCommands.General.IF_RETURN;
import static selenium.javascriptCommands.General.RETURN;

public abstract class ClickFunctions {
    public static final String CLICK_DISPATCHER = (
        IF_RETURN("arguments.length < 3", "false") +
        "arguments[0].dispatchEvent(new MouseEvent('click', {view: window, bubbles:true, cancelable: true}))" +
        RETURN("true")
    );
}
