package core.extensions.interfaces.functional.boilers;

import org.openqa.selenium.JavascriptExecutor;

import java.util.function.Function;

@FunctionalInterface
public interface ScriptFunction<T> extends Function<JavascriptExecutor, T> {
    @Override
    T apply(JavascriptExecutor executor);
}
