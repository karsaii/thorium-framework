package core.constants;

import core.extensions.boilers.StringSet;
import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import core.records.MethodData;
import data.constants.Strings;

import java.lang.reflect.Method;

public abstract class CoreDataConstants {
    public static final Data<Boolean> NULL_BOOLEAN = DataFactoryFunctions.getBoolean(false, "nullBoolean", "nullBoolean data" + Strings.END_LINE);
    public static final Data<String> NULL_STRING = DataFactoryFunctions.getWithNameAndMessage(Strings.EMPTY, false, "nullString", "nullString data.");
    public static final Data<Object> NULL_OBJECT = DataFactoryFunctions.getWithNameAndMessage(CoreConstants.STOCK_OBJECT, false, "nullObject", "null object data.");
    public static final Data<MethodData> NULL_METHODDATA = DataFactoryFunctions.getWithNameAndMessage(CoreConstants.STOCK_METHOD_DATA, false, "nullMethodData", "Null methodData data.");
    public static final Data<Void> NULL_VOID = DataFactoryFunctions.getWithNameAndMessage(null, false, "nullVoid", "Void data" + Strings.END_LINE);
    public static final Data<Object[]> NULL_PARAMETER_ARRAY = DataFactoryFunctions.getWithNameAndMessage(CoreConstants.EMPTY_OBJECT_ARRAY, false, "nullParameterArray", "Null Parameter Array.");
    public static final Data<Integer> NULL_INTEGER = DataFactoryFunctions.getWithNameAndMessage(0, false, "nullInteger", "nullInteger data.");
    public static final Data<Integer> NULL_INTEGER_NULL_DRIVER = DataFactoryFunctions.getWithNameAndMessage(0, false, "nullIntegerNullDriver", Strings.DRIVER_WAS_NULL);
    public static final Data<Integer> NO_ELEMENTS_FOUND_DATA_FALSE_OR_NULL = DataFactoryFunctions.getWithNameAndMessage(0, false, "noElementsFoundDataFalseOrNull", "No elements were found, data was false or null" + Strings.END_LINE);
    public static final Data<Integer> NO_ELEMENTS_FOUND = DataFactoryFunctions.getWithNameAndMessage(0, false, "noElementsFound", Strings.ELEMENT_LIST_EMPTY_OR_NULL);
    public static final Data<Boolean> DATA_PARAMETER_WAS_NULL = DataFactoryFunctions.getWithNameAndMessage(false, false, "dataParameterWasNull", "Data parameter" + Strings.WAS_NULL);
    public static final Data<Boolean> PARAMETERS_NULL_BOOLEAN = DataFactoryFunctions.getWithNameAndMessage(false, false, "parametersNullBoolean", "Parameters" + Strings.WAS_NULL);
    public static final Data<StringSet> NULL_STRING_SET = DataFactoryFunctions.getWithNameAndMessage(CoreConstants.NULL_STRING_SET, false, "nullStringSet", "Null String Set data" + Strings.END_LINE);
    public static final Data<String> DATA_WAS_NULL_OR_FALSE_STRING = DataFactoryFunctions.getString(Strings.EMPTY, "dataWasNullOrFalseString", Strings.DATA_NULL_OR_FALSE);
    public static final Data<Boolean> DRIVER_WAS_NULL = DataFactoryFunctions.getBoolean(false, "driverWasNull", Strings.DRIVER_WAS_NULL);
    public static final Data<Boolean> NO_STEPS = DataFactoryFunctions.getBoolean(true, "noSteps", "No Steps were provided" + Strings.END_LINE);
}
