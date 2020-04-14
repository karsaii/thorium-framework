package selenium.constants;

import core.constants.CoreDataConstants;
import core.records.TypedEnumKeyData;
import core.extensions.boilers.StringSet;
import selenium.namespaces.extensions.boilers.WebElementList;
import selenium.namespaces.extensions.boilers.DriverFunction;
import org.openqa.selenium.WebElement;
import core.enums.TypeKey;
import selenium.namespaces.DriverFunctionFactoryFunctions;

import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.entry;

public abstract class DriverFunctionConstants {
    public static final DriverFunction<Void> NULL_VOID = DriverFunctionFactoryFunctions.get(CoreDataConstants.NULL_VOID);
    public static final DriverFunction<Object> NULL_OBJECT = DriverFunctionFactoryFunctions.get(CoreDataConstants.NULL_OBJECT);
    public static final DriverFunction<Boolean> NULL_BOOLEAN = DriverFunctionFactoryFunctions.get(CoreDataConstants.NULL_BOOLEAN);
    public static final DriverFunction<String> NULL_STRING = DriverFunctionFactoryFunctions.get(CoreDataConstants.NULL_STRING);
    public static final DriverFunction<Integer> NULL_INTEGER = DriverFunctionFactoryFunctions.get(CoreDataConstants.NULL_INTEGER);
    public static final DriverFunction<StringSet> NULL_STRINGSET = DriverFunctionFactoryFunctions.get(CoreDataConstants.NULL_STRING_SET);
    public static final DriverFunction<Boolean> LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL = DriverFunctionFactoryFunctions.get(SeleniumDataConstants.LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL);
    public static final DriverFunction<Boolean> LAZY_ELEMENT_WAS_NULL = DriverFunctionFactoryFunctions.get(SeleniumDataConstants.LAZY_ELEMENT_WAS_NULL);
    public static final DriverFunction<WebElement> NULL_WEBELEMENT = DriverFunctionFactoryFunctions.get(SeleniumDataConstants.NULL_ELEMENT);
    public static final DriverFunction<WebElementList> NULL_LIST = DriverFunctionFactoryFunctions.get(SeleniumDataConstants.NULL_LIST);

    public static final Map<TypeKey, DriverFunction<?>> FUNCTION_MAP = Collections.unmodifiableMap(
        new EnumMap<>(
            Map.ofEntries(
                entry(TypeKey.BOOLEAN, NULL_BOOLEAN),
                entry(TypeKey.INTEGER, NULL_INTEGER),
                entry(TypeKey.VOID, NULL_VOID),
                entry(TypeKey.WEB_ELEMENT, NULL_WEBELEMENT),
                entry(TypeKey.STRING, NULL_STRING),
                entry(TypeKey.STRING_SET, NULL_STRINGSET),
                entry(TypeKey.WEB_ELEMENT_LIST, NULL_LIST),
                entry(TypeKey.OBJECT, NULL_OBJECT)
            )
        )
    );

    public static final TypedEnumKeyData<Boolean> BOOLEAN_FUNCTION_KEY = new TypedEnumKeyData<>(TypeKey.BOOLEAN, Boolean.class);
    public static final TypedEnumKeyData<Integer> INTEGER_FUNCTION_KEY = new TypedEnumKeyData<>(TypeKey.INTEGER, Integer.class);
    public static final TypedEnumKeyData<String> STRING_FUNCTION_KEY = new TypedEnumKeyData<>(TypeKey.STRING, String.class);
    public static final TypedEnumKeyData<StringSet> STRING_SET_FUNCTION_KEY = new TypedEnumKeyData<>(TypeKey.STRING_SET, StringSet.class);
    public static final TypedEnumKeyData<Object> OBJECT_FUNCTION_KEY = new TypedEnumKeyData<>(TypeKey.OBJECT, Object.class);
    public static final TypedEnumKeyData<Void> VOID_FUNCTION_KEY = new TypedEnumKeyData<>(TypeKey.VOID, Void.class);
    public static final TypedEnumKeyData<WebElement> WEB_ELEMENT_FUNCTION_KEY = new TypedEnumKeyData<>(TypeKey.WEB_ELEMENT, WebElement.class);
    public static final TypedEnumKeyData<WebElementList> WEB_ELEMENT_LIST_FUNCTION_KEY = new TypedEnumKeyData<>(TypeKey.WEB_ELEMENT_LIST, WebElementList.class);

    public static final Map<Class<?>, TypeKey> TYPE_MAP = Collections.unmodifiableMap(
        new LinkedHashMap<>(
            Map.ofEntries(
                entry(String.class, TypeKey.STRING),
                entry(Boolean.class, TypeKey.BOOLEAN)
            )
        )
    );
}
