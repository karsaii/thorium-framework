package utilities;

import data.constants.CardinalityDefaults;
import data.constants.Defaults;
import data.constants.Strings;
import data.constants.DataDefaults;
import data.Data;
import data.extensions.DecoratedList;
import data.DriverFunction;
import data.lazy.SelectorKeySpecificityData;
import data.lazy.tuples.LazyElementWaitParameters;
import data.lazy.tuples.LazyIndexedElementParameters;
import data.tuples.*;
import data.LazyElement;
import data.LazyLocator;
import data.LazyLocatorList;
import data.AbstractLazyElement;
import data.WebElementList;
import data.AbstractWaitParameters;
import data.ElementWaitParameters;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.regex.Pattern;

import static java.util.Map.entry;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface utils {
    static boolean isNotStockElement(WebElement element) {
        return isNotEqual(element.getAttribute("id"), Strings.NULL_ELEMENT_ID);
    }

    static boolean isEqual(Object a, Object b) {
        return Objects.equals(a, b);
    }

    static boolean isNotEqual(Object a, Object b) {
        return !isEqual(a, b);
    }

    static <T> boolean isNull(T object) {
        return Objects.isNull(object);
    }

    static <T> boolean isNullOrEmptyArray(T[] array) {
        if (isNull(array)) {
            return true;
        }

        final var length = array.length;
        final var expectedSize = 1;
        return (length < expectedSize) || ((length == expectedSize) && isNull(array[0]));
    }

    static boolean isNullData(Data<?> data) {
        return isNull(data) || Objects.equals(data.message.toString(), Strings.EMPTY);
    }

    static boolean isFalseData(Data<?> data) {
        return isNull(data) || !data.status;
    }

    static boolean isNotFalseData(Data<?> data) {
        return !isFalseData(data);
    }

    static boolean isNullOrFalseDataOrDataObject(Data<?> data) {
        return isNullData(data) || !data.status/* || isNull(data.object)*/;
    }

    static boolean isNotNullOrFalseDataOrDataObject(Data<?> data) {
        return !isNullOrFalseDataOrDataObject(data);
    }

    static boolean isNullOrFalseActionData(ActionData actionData) {
        return (
            areAnyNull(actionData, actionData.data) ||
            areAnyBlank(actionData.message, actionData.methodName)
        );
    }

    static boolean isNotNullOrFalseActionData(ActionData actionData) {
        return (
            areNotNull(actionData, actionData.data) &&
            areNotBlank(actionData.message, actionData.methodName)
        );
    }

    static <T> boolean isNotNull(T object) {
        return !isNull(object);
    }

    static boolean isException(Exception ex) {
        final var exception = Defaults.EXCEPTION;
        return isNotNull(ex) && isNotEqual(exception, ex) && isNotEqual(exception.getMessage(), ex.getMessage());
    }

    static boolean isNonException(Exception ex) {
        final var exception = Defaults.EXCEPTION;
        return (
            isNotNull(ex) && (
                isEqual(exception, ex) ||
                isEqual(exception.getMessage(), ex.getMessage())
            )
        );
    }

    static <T> boolean isBoolean(T object) {
        return isNotNull(object) && Objects.equals(Boolean.class.getTypeName(), object.getClass().getTypeName());
    }

    static <T> boolean isNotBoolean(T object) {
        return !isBoolean(object);
    }

    static <T> boolean isConditionCore(T object, Boolean value) {
        return isBoolean(object) && isNotNull(value) && Objects.equals(value, object);
    }

    static <T> boolean isTrue(T object) {
        return isConditionCore(object, Boolean.TRUE);
    }

    static <T> boolean isFalse(T object) {
        return isConditionCore(object, Boolean.FALSE);
    }

    static <T> boolean isFalsyData(T object) {
        return (
            ((object instanceof Data) && (isNullOrFalseDataOrDataObject((Data) object))) ||
            ((object instanceof LazyElement) && (isNullLazyElement((LazyElement)object)))
        ) || isFalse(object);
    }

    static <T> boolean isTruthyData(T object) {
        return (
            ((object instanceof Data) && (isNotNullOrFalseDataOrDataObject((Data) object))) ||
            ((object instanceof LazyElement) && (isNotNullLazyElement((LazyElement)object)))
        ) || isTrue(object);
    }

    static <T> String getMessageFromData(T object) {
        return (object instanceof Data) ? ((Data) object).message.getMessage() : String.valueOf(object);
    }

    @SafeVarargs
    static <T> boolean are(Predicate<T> condition, Cardinality conditionData, T... objects) {
        if (isNull(condition) || isNullOrEmptyArray(objects)) {
            return false;
        }

        final boolean guardValue = conditionData.guardValue,
            finalValue = conditionData.finalValue;
        final Function<Predicate<T>, Predicate<T>> inverter = Cardinalities.getPredicate(conditionData.invert);
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
        return areAll(utils::isNotNull, objects);
    }

    @SafeVarargs
    static <T> boolean areNull(T... objects) {
        return areAll(utils::isNull, objects);
    }

    @SafeVarargs
    static <T> boolean areAnyNull(T... objects) {
        return areAny(utils::isNull, objects);
    }

    static boolean areNotBlank(String... strings) {
        return areAll(StringUtils::isNotBlank, strings);
    }

    static boolean areAnyBlank(String... strings) {
        return areAny(StringUtils::isBlank, strings);
    }

    static boolean isNullLazyData(LazyLocator data) {
        return isNull(data) || areAnyBlank(data.locator, data.strategy);
    }

    static boolean areNullLazyData(LazyLocator... data) {
        return areAll(utils::isNullLazyData, data);
    }

    static boolean areNullLazyData(List<LazyLocator> data) {
        return areNullLazyData(data.toArray(new LazyLocator[0]));
    }

    static boolean isNullLazyDataList(LazyLocatorList list) {
        return isNullOrEmptyList(list) || areNullLazyData(list.list);
    }

    static boolean isNotNullLazyData(LazyLocator data) {
        return !isNullLazyData(data);
    }

    static <T> boolean isNullAbstractLazyElementParametersList(Collection<T> data, Predicate<T> validator) {
        for(T params : data) {
            if (validator.test(params)) {
                return false;
            }
        }

        return true;
    }

    static <T> boolean isNotNullAbstractLazyElementParametersList(Collection<T> data, Predicate<T> validator) {
        return !isNullAbstractLazyElementParametersList(data, validator);
    }

    static <T> boolean isNullLazyElement(AbstractLazyElement<T> element) {
        return (
            isNull(element) ||
            isBlank(element.name) ||
            areAnyNull(element.parameters, element.validator) ||
            element.parameters.isEmpty() ||
            isNullAbstractLazyElementParametersList(element.parameters.values(), element.validator)
        );
    }

    static <T> boolean isNotNullLazyElement(AbstractLazyElement<T> data) {
        return !isNullLazyElement(data);
    }

    static boolean isNullOrEmptyList(List<?> list) {
        return isNull(list) || list.isEmpty();
    }

    static <T, U> Boolean isNullSpecficTypeList(List<T> list, Class<U> clazz) {
        return isNullOrEmptyList(list) || !clazz.isInstance(list.get(0));
    }

    static <T, U> Boolean isNotNullSpecificTypeList(List<T> list, Class<U> clazz) {
        return !isNullSpecficTypeList(list, clazz);
    }

    static Boolean isNullWebElement(WebElement element) {
        return isNull(element) || Objects.equals(DataDefaults.NULL_ELEMENT_DATA.object, element);
    }
    static <T> Boolean isNullWebElementList(DecoratedList<T> list) {
        return list.isNullOrEmpty() || Objects.equals(DataDefaults.NULL_ELEMENT_DATA.object, list.get(0));
    }

    static <T> Boolean isNotNullWebElementList(List<T> list) {
         return isNotNullSpecificTypeList(list, WebElement.class) && isNotEqual(DataDefaults.NULL_ELEMENT_DATA.object, list.get(0));
    }

    static Boolean isNullOrFalseDataWebElementList(Data<WebElementList> data) {
        return isNullOrFalseDataOrDataObject(data) || isNullWebElementList(data.object);
    }

    static Boolean isNotNullOrFalseDataWebElementList(Data<WebElementList> data) {
        return !isNullOrFalseDataWebElementList(data);
    }


    static <T> Data<T> replaceName(Data<T> data, String nameof) {
        return new Data<>(data.object, data.status, data.message.getMessage(nameof), data.exception, data.exceptionMessage);
    }

    static <T> Data<T> prependMessage(Data<T> data, String message) {
        return new Data<>(data.object, data.status, message + data.message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> prependMessage(Data<T> data, String nameof, String message) {
        return new Data<>(data.object, data.status, new MethodMessageData(nameof, message + data.message.message), data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceMessage(Data<T> data, String message) {
        return new Data<>(data.object, data.status, message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceMessage(Data<T> data, String nameof, String message) {
        return new Data<>(data.object, data.status, new MethodMessageData(nameof, message), data.exception, data.exceptionMessage);
    }

    static <T> Data<T> appendMessage(Data<T> data, String message) {
        return new Data<>(data.object, data.status, data.message + message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> appendMessage(Data<T> data, String nameof, String message) {
        return new Data<>(data.object, data.status, new MethodMessageData(nameof, data.message.message + message), data.exception, data.exceptionMessage);
    }

    private static <T> DriverFunction<T> guardLambda(Data<T> object, String message) {
        final var suffix = "guardLambda.";
        return new DriverFunction<>(object.object, false, (isNotBlank(message) ? message + suffix : suffix) + "\n Object message: " + object.message);
    }

    private static <T> DriverFunction<T> guardLambda(Data<T> object) {
        return guardLambda(object, Strings.EMPTY);
    }

    static <T> DriverFunction<T> ifLambda(boolean condition, DriverFunction<T> positive, Data<T> guard, String message) {
        return new DriverFunction<T>(driver -> {
            final var result = isNotNull(driver) ? (condition ? positive : guardLambda(guard)).apply(driver) : guard;
            return isNotBlank(message) ? prependMessage(result, message) : result;
        });
    }

    static <T> DriverFunction<T> ifLambda(boolean condition, DriverFunction<T> positive, Data<T> guard) {
        return ifLambda(condition, positive, guard, "");
    }

    static <T> Data<T> ifData(boolean condition, Data<T> positive, Data<T> guard, String message) {
        var result = condition ? positive : guard;
        return isNotBlank(message) ? prependMessage(result, message) : result;
    }

    static Boolean isStringMatchesPattern(String string, String regex) {
        return Pattern.matches(regex, string);
    }

    static <T> boolean isNullCommonWaitParametersData(AbstractWaitParameters<T> data) {
        return isNull(data) || (data.duration < 0) || ( data.interval < 0);
    }

    static boolean isNullElementWaitParametersData(ElementWaitParameters data) {
        return isNullCommonWaitParametersData(data) || isNull(data.object);
    }

    static boolean isNotNullElementWaitParametersData(ElementWaitParameters data) {
        return !isNullElementWaitParametersData(data);
    }

    static boolean isNullLazyElementWaitParametersData(LazyElementWaitParameters data) {
        return isNullCommonWaitParametersData(data) || isNullLazyElement(data.object);
    }

    static boolean isNotNullLazyElementWaitParametersData(LazyElementWaitParameters data) {
        return !isNullLazyElementWaitParametersData(data);
    }

    static Boolean Uncontains(String object, String expected) {
        return !StringUtils.contains(object, expected);
    }

    static DriverFunction<Boolean> ifBlank(String message, DriverFunction<Boolean> positive) {
        return isBlank(message) ? new DriverFunction<Boolean>(positive) : DataDefaults.getErrorFunction(message, false);
    }

    static boolean isEmpty(Object obj) {
        return (
            isNull(obj) ||
            ((obj instanceof Collection) && ((Collection)obj).isEmpty()) ||
            ((obj instanceof Map) && ((Map)obj).isEmpty()) ||
            ((obj instanceof Optional) && ((Optional) obj).isEmpty()) ||
            ((obj instanceof CharSequence) && (((CharSequence)obj).length() == 0)) ||
            (obj.getClass().isArray() && (Array.getLength(obj) == 0))
        );
    }

    static <T> Data<T> ifBlank(String message, Data<T> positive, String nameof, T defaultValue) {
        return isBlank(message) ? positive : new Data<T>(defaultValue, false, message);
    }

    static <T, U> Predicate<Data<WebElementList>> isOfTypeNonEmpty(Class<U> clazz) {
        return list -> isNotNullOrFalseDataOrDataObject(list) && isOfTypeNonEmpty(list.object, clazz);
    }

    static <T, U> boolean isOfTypeNonEmpty(DecoratedList<T> list, Class<U> clazz) {
        return (
            list.isNotNullAndNonEmpty() &&
            Objects.equals(clazz.getTypeName(), list.getType()) &&
            !Objects.equals(DataDefaults.NULL_ELEMENT_DATA, list.first())
        );
    }

    private static <T extends SearchContext> Data<SearchContext> getSearchContextCore(T object) {
        final var status = isNotNull(object);
        return new Data<SearchContext>(object, status, status ? "Driver isn't null" + Strings.END_LINE : Strings.DRIVER_WAS_NULL);
    }

    static Data<SearchContext> getSearchContext(WebDriver driver) {
        return getSearchContextCore(driver);
    }

    static DriverFunction<SearchContext> getSearchContext() {
        return new DriverFunction<SearchContext>(driver -> getSearchContextCore(driver));
    }

    static Data<SearchContext> getSearchContext(WebElement element) {
        return getSearchContextCore(element);
    }

    static Data<SearchContext> getSearchContext(SearchContext context) {
        return getSearchContextCore(context);
    }

    static Data<SearchContext> getSearchContext(Data<SearchContext> data) {
        return utils.isNotNullOrFalseDataOrDataObject(data) ? getSearchContext(data.object) : new Data<SearchContext>(null, false, "Parameter was null");
    }

    static String getStrategy(String locator) {
        if (isBlank(locator) || locator.length() < 4) {
            return Strings.EMPTY;
        }

        final var colonIndex = locator.indexOf(':');
        return locator.substring(3, colonIndex);
    }

    static String getLocator(String locator) {
        if (isBlank(locator) || locator.length() < 4) {
            return Strings.EMPTY;
        }
        final var locatorStartIndex = locator.indexOf(':') + 2;
        return locator.substring(locatorStartIndex);
    }

    static LazyLocator getLazyLocator(By locator) {
        if (isNull(locator)) {
            return new LazyLocator();
        }

        final var locatorString = locator.toString();
        return new LazyLocator(getLocator(locatorString), getStrategy(locatorString));
    }

    static LazyLocatorList getLazyLocatorList(By locator) {
        return new LazyLocatorList(getLazyLocator(locator));
    }

    static <T> Map.Entry<String, T> getEntry(TriFunction<Boolean, LazyLocator, String, T> constructor, By locator, String getter, boolean isIndexed) {
        final var lazyLocator = getLazyLocator(locator);
        return entry(lazyLocator.strategy, constructor.apply(false, lazyLocator, getter));
    }

    static <T> Map.Entry<String, T> getEntryIndexed(TriFunction<IndexedData, LazyLocator, String, T> constructor, IndexedData indexedData, By locator, String getter) {
        final var lazyLocator = getLazyLocator(locator);
        return entry(lazyLocator.strategy, constructor.apply(indexedData, lazyLocator, getter));
    }

    static String getIncrementalUUID(AtomicInteger counter) {
        return "" + counter.getAndIncrement() + UUID.randomUUID().toString();
    }

    static void throwIfException(Data<?> data) throws Exception {
        if (isNull(data)) {
            return;
        }

        final var exception = data.exception;
        if (isException(exception)) {
            throw exception;
        }
    }

    static Data<By> getLocator(Map<String, Function<String, By>> strategyMap, LazyLocator data) {
        if (isNullLazyData(data)) {
            return DataDefaults.NULL_BY_DATA;
        }

        final var locator = data.locator;
        final var strategy = data.strategy;
        return new Data<>(strategyMap.get(strategy).apply(locator), true, "Locator: By " + strategy + " with locator: " + locator);
    }

    static Data<By> getLocator(LazyLocator data) {
        return getLocator(Defaults.strategyMap, data);
    }

    static Map<String, LazyIndexedElementParameters> getParametersCopy(Map<String, LazyIndexedElementParameters> source) {
        final var keys = source.keySet().iterator();
        final var values = source.values().iterator();

        final var map = Collections.synchronizedMap(new LinkedHashMap<String, LazyIndexedElementParameters>());
        LazyIndexedElementParameters lep;
        while(keys.hasNext() && values.hasNext()) {
            lep = values.next();
            map.putIfAbsent(keys.next(), new LazyIndexedElementParameters(lep.indexData, lep.probability, lep.lazyLocators, lep.getter));
        }

        return map;
    }

    static Map<String, DecoratedList<SelectorKeySpecificityData>> getKeysCopy(Map<String, DecoratedList<SelectorKeySpecificityData>> source) {
        final var keys = source.keySet().iterator();
        final var values = source.values().iterator();

        final var map = Collections.synchronizedMap(new LinkedHashMap<String, DecoratedList<SelectorKeySpecificityData>>());
        while(keys.hasNext() && values.hasNext()) {
            map.putIfAbsent(keys.next(), new DecoratedList<SelectorKeySpecificityData>(values.next(), SelectorKeySpecificityData.class));
        }

        return map;
    }
}
