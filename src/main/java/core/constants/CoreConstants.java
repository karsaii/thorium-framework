package core.constants;

import core.extensions.boilers.StringSet;
import core.extensions.namespaces.CoreUtilities;
import core.records.MethodData;
import data.constants.Strings;

import java.lang.reflect.Method;
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
    public static final Exception EXCEPTION = new Exception(Strings.NON_EXCEPTION_MESSAGE);

    public static final HashMap<String, MethodData> METHODS = new HashMap<>();



    public static final Function<Object, Object> OBJECT_CASTER_FUNCTION = Object.class::cast;
    public static final Function<Object, String> STRING_CASTER_FUNCTION = String.class::cast;
    public static final Function<Object, Boolean> BOOLEAN_CASTER_FUNCTION = CoreUtilities::castToBoolean;
    public static final Function<Object, Void> VOID_CASTER_FUNCTION = object -> null;
    public static final Function<Object, Object> OBJECT_DEFAULT_FUNCTION = object -> CoreConstants.STOCK_OBJECT;

    public static final Method STOCK_METHOD = Object.class.getDeclaredMethods()[0];
    public static final MethodData STOCK_METHOD_DATA = new MethodData(STOCK_METHOD, CoreConstants.STOCK_METHOD_PARAMETER_TYPES.toString(), Strings.VOID_CLASS_GENERIC);
}
