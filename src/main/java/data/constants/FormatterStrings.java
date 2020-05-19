package data.constants;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.entry;

public abstract class FormatterStrings {
    public static final String IS = "is";
    public static final String ISN_T = "isn't";
    public static final String ELEMENT = "Element ";
    public static final String WAITING_FAILED = "Waiting failed" + Strings.COLON_NEWLINE;
    public static final String EXTENSION = ".png";
    public static final String NAME_START = "ss";
    public static final String SS_NAME_SEPARATOR = "-";

    public static final Map<String, String> isMessageMap = Collections.unmodifiableMap(
        new LinkedHashMap<>(
            Map.ofEntries(
                entry("true", IS),
                entry("false", ISN_T),
                entry("nottrue", ISN_T),
                entry("notfalse", IS)
            )
        )
    );
}
