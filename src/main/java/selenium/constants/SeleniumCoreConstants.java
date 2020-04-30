package selenium.constants;

import core.constants.CoreDataConstants;
import core.extensions.DecoratedList;
import selenium.namespaces.lazy.LazyElementFactory;
import selenium.namespaces.extensions.boilers.WebElementList;
import core.records.MethodSourceData;
import org.openqa.selenium.WebElement;
import selector.records.SelectorKeySpecificityData;
import core.constants.CoreConstants;
import selenium.namespaces.repositories.ElementRepository;
import selenium.namespaces.utilities.BasicTypeUtilities;
import selenium.records.ExternalElementData;
import selenium.records.lazy.LazyElement;
import selenium.records.lazy.CachedLazyElementData;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static core.extensions.namespaces.CoreUtilities.getIncrementalUUID;

public abstract class SeleniumCoreConstants {
    public static final Method[] WEBELEMENT_METHODS = WebElement.class.getMethods();
    public static final WebElement STOCK_ELEMENT = BasicTypeUtilities.getStockElement();
    public static final WebElementList NULL_ELEMENT_LIST = new WebElementList();
    public static final List<Method> WEB_ELEMENT_METHOD_LIST = Arrays.asList(WEBELEMENT_METHODS);

    public static AtomicInteger ATOMIC_COUNT = new AtomicInteger();
    public static final LazyElement NULL_LAZY_ELEMENT = LazyElementFactory.getWithDefaultLocatorsAndValidator("Null Lazy Element " + getIncrementalUUID(ATOMIC_COUNT));

    public static final Map<String, DecoratedList<SelectorKeySpecificityData>> NULL_CACHED_KEYS = ElementRepository.getInitializedTypeKeysMap();
    public static final CachedLazyElementData NULL_CACHED_LAZY_ELEMENT_DATA = new CachedLazyElementData(NULL_LAZY_ELEMENT, NULL_CACHED_KEYS);
    public static final ExternalElementData NULL_EXTERNAL_ELEMENT_DATA = new ExternalElementData(NULL_CACHED_KEYS, SeleniumDataConstants.NULL_ELEMENT);

    public static final List<Class<?>> CLASSES_OF_GET_MECHANISMS = Arrays.asList(WebElementList.class, WebElement.class);
    public static final MethodSourceData DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS = new MethodSourceData(CoreConstants.METHODS, SeleniumCoreConstants.WEB_ELEMENT_METHOD_LIST, CoreDataConstants.NULL_METHODDATA);
    public static final Function<Object, WebElement> WEB_ELEMENT_CASTER_FUNCTION = WebElement.class::cast;
}
