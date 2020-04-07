package selenium.element.functions;

import core.extensions.interfaces.functional.boilers.IGetMessage;
import data.constants.Strings;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SimpleMessageData implements IGetMessage {
    public final BiFunction<String, Boolean, String> formatter;
    public final String message;

    public SimpleMessageData(BiFunction<String, Boolean, String> formatter, String message) {
        this.formatter = formatter;
        this.message = message;
    }

    public SimpleMessageData(String message) {
        this(null, message);
    }

    public SimpleMessageData() {
        this(null, null);
    }

    @Override
    public Function<Boolean, String> get() {
        return status -> Objects.isNull(formatter) ? message + " execution status: " + status + Strings.END_LINE : formatter.apply(message, status);
    }
}
