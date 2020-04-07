package core.extensions.interfaces.functional.boilers;

import java.util.function.Function;

@FunctionalInterface
public interface ScriptHandlerFunction extends ScriptFunction<Function<String, Object>> {
}
