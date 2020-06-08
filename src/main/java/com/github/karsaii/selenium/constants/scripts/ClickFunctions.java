package com.github.karsaii.selenium.constants.scripts;

import static com.github.karsaii.selenium.namespaces.scripter.General.IF_RETURN;
import static com.github.karsaii.selenium.namespaces.scripter.General.RETURN;

public abstract class ClickFunctions {
    public static final String CLICK_DISPATCHER = (
        IF_RETURN("arguments.length < 1", "false") +
        "arguments[0].dispatchEvent(new MouseEvent('click', {view: window, bubbles:true, cancelable: true}))" +
        RETURN("true")
    );
}
