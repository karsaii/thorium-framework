package core.extensions.namespaces;

import core.constants.CardinalityDefaults;
import core.records.ActionData;
import core.records.CardinalityData;
import org.apache.commons.lang3.StringUtils;
import core.constants.CoreConstants;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public interface CoreUtilities {
    static boolean isEqual(Object a, Object b) {
        return Objects.equals(a, b);
    }

    static boolean isNotEqual(Object a, Object b) {
        return !isEqual(a, b);
    }

    static boolean isException(Exception ex) {
        final var exception = CoreConstants.EXCEPTION;
        return NullableFunctions.isNotNull(ex) && isNotEqual(exception, ex) && isNotEqual(exception.getMessage(), ex.getMessage());
    }

    static boolean isNonException(Exception ex) {
        final var exception = CoreConstants.EXCEPTION;
        return NullableFunctions.isNotNull(ex) && (isEqual(exception, ex) || isEqual(exception.getMessage(), ex.getMessage()));
    }

    static <T> boolean isBoolean(T object) {
        return NullableFunctions.isNotNull(object) && Objects.equals(Boolean.class.getTypeName(), object.getClass().getTypeName());
    }

    static <T> boolean isNotBoolean(T object) {
        return !isBoolean(object);
    }

    static <T> boolean isConditionCore(T object, Boolean value) {
        return isBoolean(object) && NullableFunctions.isNotNull(value) && Objects.equals(value, object);
    }

    static <T> boolean isTrue(T object) {
        return isConditionCore(object, Boolean.TRUE);
    }

    static <T> boolean isFalse(T object) {
        return isConditionCore(object, Boolean.FALSE);
    }

    @SafeVarargs
    static <T> boolean are(Predicate<T> condition, CardinalityData conditionData, T... objects) {
        if (NullableFunctions.isNull(condition) || ArrayFunctions.isNullOrEmptyArray(objects)) {
            return false;
        }

        final boolean guardValue = conditionData.guardValue,
            finalValue = conditionData.finalValue;
        final Function<Predicate<T>, Predicate<T>> inverter = CardinalitiesFunctions.getPredicate(conditionData.invert);
        final var checker = inverter.apply(condition);
        final var length = objects.length;
        var index = 0;
        if (length == 1) {
            return checker.test(objects[index]) ? guardValue : finalValue;
        }

        for(; index < length; ++index) {
            if (checker.test(objects[index])) {
                return guardValue;
            }
        }

        return finalValue;
    }

    @SafeVarargs
    static <T> boolean areAll(Predicate<T> condition, T... objects) {
        return are(condition, CardinalityDefaults.all, objects);
    }

    @SafeVarargs
    static <T> boolean areAny(Predicate<T> condition, T... objects) {
        return are(condition, CardinalityDefaults.any, objects);
    }

    @SafeVarargs
    static <T> boolean areNone(Predicate<T> condition, T... objects) {
        return are(condition, CardinalityDefaults.none, objects);
    }

    @SafeVarargs
    static <T> boolean areNotNull(T... objects) {
        return areAll(NullableFunctions::isNotNull, objects);
    }

    @SafeVarargs
    static <T> boolean areNull(T... objects) {
        return areAll(NullableFunctions::isNull, objects);
    }

    @SafeVarargs
    static <T> boolean areAnyNull(T... objects) {
        return areAny(NullableFunctions::isNull, objects);
    }

    static boolean areNotBlank(String... strings) {
        return areAll(StringUtils::isNotBlank, strings);
    }

    static boolean areAnyBlank(String... strings) {
        return areAny(StringUtils::isBlank, strings);
    }

    static boolean isNullOrEmptyList(List<?> list) {
        return NullableFunctions.isNull(list) || list.isEmpty();
    }

    static <T, U> Boolean isNullSpecficTypeList(List<T> list, Class<U> clazz) {
        return isNullOrEmptyList(list) || !clazz.isInstance(list.get(0));
    }

    static <T, U> Boolean isNotNullSpecificTypeList(List<T> list, Class<U> clazz) {
        return !isNullSpecficTypeList(list, clazz);
    }

    static <T> Object[] toSingleElementArray(T object, Predicate<T> guard) {
        if (!guard.test(object)) {
            return CoreConstants.EMPTY_OBJECT_ARRAY;
        }

        final var parameters = new Object[1];
        parameters[0] = object;

        return parameters;
    }

    static <T> Object[] toSingleElementArray(T object) {
        return toSingleElementArray(object, NullableFunctions::isNotNull);
    }

    static boolean castToBoolean(Object o) {
        return o instanceof Boolean ? (boolean)o : NullableFunctions.isNotNull(o);
    }

    static Boolean isStringMatchesPattern(String string, String regex) {
        return Pattern.matches(regex, string);
    }

    static Boolean Uncontains(String object, String expected) {
        return !StringUtils.contains(object, expected);
    }

    static String getIncrementalUUID(AtomicInteger counter) {
        return "" + counter.getAndIncrement() + UUID.randomUUID().toString();
    }

    static boolean isNullOrFalseActionData(ActionData actionData) {
        return areAnyNull(actionData, actionData.data) || areAnyBlank(actionData.message, actionData.methodName);
    }
}
