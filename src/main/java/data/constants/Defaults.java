package data.constants;

import data.*;
import data.extensions.DecoratedList;
import data.lazy.SelectorKeySpecificityData;
import data.lazy.tuples.CachedLazyElementData;
import data.repositories.ElementRepository;
import data.tuples.*;
import drivers.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static utilities.utils.getIncrementalUUID;
import static java.util.Map.entry;

public class Defaults {
    public static final Exception EXCEPTION = new Exception(Strings.NULL_EXCEPTION_MESSAGE);
    public static final int MAXIMUM_COMMAND_LIMIT = 20;
    public static final int MINIMUM_COMMAND_LIMIT = 1;
    public static final CommandRangeData DEFAULT_RANGE = new CommandRangeData(MINIMUM_COMMAND_LIMIT, MAXIMUM_COMMAND_LIMIT);

    public static final Map<String, Function<String, By>> strategyMap = Collections.unmodifiableMap(
        new LinkedHashMap<>(
            Map.ofEntries(
                entry("id", By::id),
                entry("cssSelector", By::cssSelector),
                entry("class", By::className),
                entry("xpath", By::xpath),
                entry("tagName", By::tagName),
                entry("name", By::name),
                entry("partialLinkText", By::partialLinkText),
                entry("linkText", By::linkText)
            )
        )
    );

    public static final String DEFAULT_GETTER_ELEMENT = Strings.DEFAULT_GETTER_ELEMENT;
    public static final String DEFAULT_GETTER_ELEMENTS = Strings.DEFAULT_GETTER_ELEMENTS;

    public static final String EMPTY_STRING = Strings.EMPTY;

    public static final String PRIMARY_STRATEGY = Strings.PRIMARY_STRATEGY;

    public static final ExecuteParametersData DEFAULT_EXECUTION_DATA = new ExecuteParametersData();

    public static final List<Class<?>> CLASSES_OF_GET_MECHANISMS = Arrays.asList(WebElementList.class, WebElement.class);
    public static final Map<String, Function<LazyLocatorList, DriverFunction<WebElement>>> singleGetterMap = Collections.unmodifiableMap(
        new LinkedHashMap<>(
            Map.ofEntries(
                entry("getElement", Driver::getElementFromSingle),
                entry("getRootElement", Driver::getRootElementFromSingle),
                entry("getNestedElement", Driver::getNestedElement),
                entry("getFrameNestedElement", Driver::getFrameNestedElement),
                entry("getShadowRootElement", Driver::getShadowRootElement),
                entry("getShadowNestedElement", Driver::getShadowNestedElement)
            )
        )
    );

    public static final Map<String, Function<LazyLocatorList, DriverFunction<WebElementList>>> manyGetterMap = Collections.unmodifiableMap(
        new LinkedHashMap<>(
            Map.ofEntries(
                entry("getElements", Driver::getElements),
                entry("getNestedElementsFromLast", Driver::getNestedElementsFromLast),
                entry("getFrameNestedElements", Driver::getFrameNestedElementsFromLast),
                entry("getShadowNestedElements", Driver::getShadowNestedElementsFromLast)
            )
        )
    );

    public static final Map<String, Function<LazyLocatorList, DriverFunction<WebElement>>> frameAmountStrategyMap = Collections.unmodifiableMap(
        new LinkedHashMap<>(
            Map.ofEntries(
                entry("true", Driver::getNestedElement),
                entry("false", Driver::getElementFromSingle)
            )
        )
    );

    public static final Map<String, Function<LazyLocatorList, DriverFunction<Boolean>>> frameNestedStrategyMap = Collections.unmodifiableMap(
        new LinkedHashMap<>(
            Map.ofEntries(
                entry("true", Driver::switchToNestedFrame),
                entry("false", Driver::switchToFrameFromSingle)
            )
        )
    );

    public static AtomicInteger atomicCount = new AtomicInteger();

    public static final HashMap<String, Method> methods = new HashMap<>();
    public static final LazyElement NULL_LAZY_ELEMENT = new LazyElement("Null Lazy Element " + getIncrementalUUID(Defaults.atomicCount));
    public static final Map<String, DecoratedList<SelectorKeySpecificityData>> NULL_CACHED_KEYS = ElementRepository.getInitializedTypeKeysMap();
    public static final CachedLazyElementData NULL_CACHED_LAZY_ELEMENT_DATA = new CachedLazyElementData(NULL_LAZY_ELEMENT, NULL_CACHED_KEYS);
    public static final ExternalElementData NULL_EXTERNAL_ELEMENT_DATA = new ExternalElementData(NULL_CACHED_KEYS, DataDefaults.NULL_ELEMENT_DATA);
    public static final HashMap<String, CachedLazyElementData> elements = new HashMap<>();
    public static final Map<By, String> namedLocators = new HashMap<>();

    //TODO: Unmodifiable List - Collections not applicable, copy or roll own. Shame.
    public static final DecoratedList<SelectorKeySpecificityData> NULL_SELECTOR_KEY_SPECIFICS = new DecoratedList<SelectorKeySpecificityData>();

    public static final ExternalSelectorData EXTERNAL_DATA_NULL = new ExternalSelectorData();

    public static final double PROBABILITY_THRESHOLD = 80.0;
    public static final double ADJUST_STEP_AMOUNT = 5.0;
    public static final ProbabilityData PROBABILITY_DATA = new ProbabilityData(ADJUST_STEP_AMOUNT, PROBABILITY_THRESHOLD);
    public static final InternalSelectorData INTERNAL_SELECTOR_DATA = new InternalSelectorData();
    public static final ExternalSelectorData EXTERNAL_SELECTOR_DATA = new ExternalSelectorData();
}
