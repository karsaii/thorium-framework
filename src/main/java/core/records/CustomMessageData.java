package core.records;

import core.extensions.interfaces.functional.TriFunction;
import core.extensions.interfaces.functional.boilers.IGetMessage;
import data.constants.Strings;
import data.namespaces.Formatter;

import java.util.Objects;
import java.util.function.Function;

public class CustomMessageData implements IGetMessage {
    public final String prefix;
    public final String suffix;
    public final TriFunction<String, Boolean, String, String> messageFormatter;

    public CustomMessageData(TriFunction<String, Boolean, String, String> messageFormatter, String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.messageFormatter = messageFormatter;
    }

    public CustomMessageData(String prefix, String suffix) {
        this(Formatter::formatPrefixSuffixMessage, prefix, suffix);
    }

    public CustomMessageData() {
        this.prefix = null;
        this.suffix = null;
        this.messageFormatter = null;
    }

    @Override
    public Function<Boolean, String> get() {
        return status -> Objects.isNull(messageFormatter) ? "MessageFormatter was null" + Strings.END_LINE : messageFormatter.apply(prefix, status, suffix);
    }
}
