package selenium.element.functions;

import core.extensions.interfaces.functional.boilers.IGetMessage;
import data.constants.Strings;
import data.namespaces.Formatter;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static core.extensions.namespaces.NullableFunctions.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

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
        final var isFormatterNull = isNull(formatter);
        final var isMessageBlank = isBlank(message);
        if (isFormatterNull && isMessageBlank) {
            return Formatter.isFormatterNullAndMessageBlank();
        }

        if (isFormatterNull) {
            return Formatter.isFormatterNull(message);
        }

        return isMessageBlank ? Formatter.isMessageBlank(formatter) : Formatter.isFormatterAndMessageValid(formatter, message);
    }
}
