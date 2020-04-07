package selenium.enums;

import core.extensions.boilers.StringSet;
import core.extensions.namespaces.CoreUtilities;
import core.records.MethodData;
import data.constants.Strings;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

public abstract class CoreConstants {
    public static final Object STOCK_OBJECT = new Object();
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final List<Class<?>> STOCK_METHOD_PARAMETER_TYPES = Collections.singletonList(Void.class);
    public static final StringSet NULL_STRING_SET = new StringSet(new HashSet<>());
    public static final Exception NULL_EXCEPTION = new Exception(Strings.NULL_EXCEPTION_MESSAGE);

    public static final HashMap<String, MethodData> METHODS = new HashMap<>();

    public static final Function<Object, Object> STOCK_OBJECT_FUNCTION = object -> CoreConstants.STOCK_OBJECT;

    public static final Function<Object, Object> OBJECT_CASTER_FUNCTION = Object.class::cast;
    public static final Function<Object, String> STRING_CASTER_FUNCTION = String.class::cast;
    public static final Function<Object, Boolean> BOOLEAN_CASTER_FUNCTION = CoreUtilities::castToBoolean;
    public static final Function<Object, Void> VOID_CASTER_FUNCTION = object -> null;
    public static final Function<Object, WebElement> WEB_ELEMENT_CASTER_FUNCTION = WebElement.class::cast;
}
