package core.constants;

import core.extensions.boilers.StringSet;
import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import core.records.MethodData;
import data.constants.Strings;

import java.lang.reflect.Method;

public abstract class CoreDataConstants {
    public static final Data<Boolean> NULL_BOOLEAN = DataFactoryFunctions.getBoolean(false, "nullBoolean data" + Strings.END_LINE);
    public static final Data<String> NULL_STRING = DataFactoryFunctions.getWithMessage(Strings.EMPTY, false, "nullString data.");
    public static final Data<Object> NULL_OBJECT = DataFactoryFunctions.getWithMessage(CoreConstants.STOCK_OBJECT, false, "null object data.");
    public static final Data<Method> NULL_METHOD = DataFactoryFunctions.getWithMessage(CoreConstants.STOCK_METHOD, false, "null method data.");
    public static final Data<MethodData> NULL_METHODDATA = DataFactoryFunctions.getWithMessage(CoreConstants.STOCK_METHOD_DATA, false, "Null methodData data.");
    public static final Data<Void> NULL_VOID = DataFactoryFunctions.getWithMessage(null, false, "Void data" + Strings.END_LINE);
    public static final Data<Object[]> NULL_PARAMETER_ARRAY = DataFactoryFunctions.getWithMessage(CoreConstants.EMPTY_OBJECT_ARRAY, false, "Null Parameter Array.");
    public static final Data<Integer> NULL_INTEGER = DataFactoryFunctions.getWithMessage(0, false, "nullInteger data.");
    public static final Data<Integer> NULL_INTEGER_NULL_DRIVER = DataFactoryFunctions.getWithMessage(0, false, Strings.DRIVER_WAS_NULL);
    public static final Data<Integer> NO_ELEMENTS_FOUND_DATA_FALSE_OR_NULL = DataFactoryFunctions.getWithMessage(0, false, "No elements were found, data was false or null" + Strings.END_LINE);
    public static final Data<Integer> NO_ELEMENTS_FOUND = DataFactoryFunctions.getWithMessage(0, false, Strings.ELEMENT_LIST_EMPTY_OR_NULL);
    public static final Data<String> GET_FORMATTED_ELEMENT_VALUE_ERROR = DataFactoryFunctions.getWithMessage("", false, Strings.GET_FORMATTED_ELEMENT_VALUE_ERROR);
    public static final Data<Boolean> DATA_PARAMETER_WAS_NULL = DataFactoryFunctions.getWithMessage(false, false, "Data parameter" + Strings.WAS_NULL);
    public static final Data<Boolean> PARAMETERS_NULL_BOOLEAN = DataFactoryFunctions.getWithMessage(false, false, "Parameters" + Strings.WAS_NULL);
    public static final Data<String> PARAMETERS_NULL_STRING = DataFactoryFunctions.getWithMessage(Strings.EMPTY, false, "Parameters" + Strings.WAS_NULL);
    public static final Data<Object> WONT_EXECUTE = DataFactoryFunctions.getWithMessage(CoreConstants.STOCK_OBJECT, false, "Won't execute blank, empty or null script" + Strings.END_LINE);
    public static final Data<Object> NULL_EXECUTE_OBJECT = DataFactoryFunctions.getWithMessage(CoreConstants.STOCK_OBJECT, false, "List was empty, or null" + Strings.END_LINE);
    public static final Data<StringSet> NULL_STRING_SET = DataFactoryFunctions.getWithMessage(CoreConstants.NULL_STRING_SET, false, "Null String Set data" + Strings.END_LINE);
    public static final Data<String> DATA_WAS_NULL_OR_FALSE_STRING = DataFactoryFunctions.getString(Strings.EMPTY, Strings.DATA_NULL_OR_FALSE);
    public static final Data<Boolean> DRIVER_WAS_NULL = DataFactoryFunctions.getBoolean(false, Strings.DRIVER_WAS_NULL);
    public static final Data<Boolean> NO_STEPS = DataFactoryFunctions.getBoolean(true, "No Steps were provided" + Strings.END_LINE);
}
