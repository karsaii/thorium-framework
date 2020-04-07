package selenium.compatibility;

import org.openqa.selenium.JavascriptExecutor;

public final class NullJavascriptExecutor implements JavascriptExecutor {
    public NullJavascriptExecutor() {}

    @Override
    public Object executeScript(String s, Object... objects) {
        return null;
    }

    @Override
    public Object executeAsyncScript(String s, Object... objects) {
        return null;
    }
}
