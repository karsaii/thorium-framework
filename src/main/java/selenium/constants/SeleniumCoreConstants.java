package selenium.constants;

import core.extensions.DecoratedList;
import core.extensions.boilers.WebElementList;
import core.records.MethodData;
import core.records.MethodGetCommonParametersData;
import data.constants.Strings;
import org.openqa.selenium.WebElement;
import selector.records.SelectorKeySpecificityData;
import selenium.enums.CoreConstants;
import selenium.namespaces.repositories.ElementRepository;
import selenium.namespaces.utilities.BasicTypeUtilities;
import selenium.records.ExternalElementData;
import selenium.records.LazyElement;
import selenium.records.lazy.CachedLazyElementData;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static core.extensions.namespaces.CoreUtilities.getIncrementalUUID;

public abstract class SeleniumCoreConstants {
    public static final Method STOCK_WEBELEMENT_METHOD = WebElement.class.getDeclaredMethods()[0];
    public static final Method[] WEBELEMENT_METHODS = WebElement.class.getMethods();
    public static final MethodData STOCK_WEBELEMENT_METHOD_DATA = new MethodData(STOCK_WEBELEMENT_METHOD, CoreConstants.STOCK_METHOD_PARAMETER_TYPES.toString(), Strings.VOID_CLASS_GENERIC);
    public static final WebElement STOCK_ELEMENT = BasicTypeUtilities.getStockElement();
    public static final WebElementList NULL_ELEMENT_LIST = new WebElementList();
    public static final List<Method> WEB_ELEMENT_METHOD_LIST = Arrays.asList(WEBELEMENT_METHODS);

    public static AtomicInteger atomicCount = new AtomicInteger();
    public static final LazyElement NULL_LAZY_ELEMENT = new LazyElement("Null Lazy Element " + getIncrementalUUID(atomicCount));

    public static final Map<String, DecoratedList<SelectorKeySpecificityData>> NULL_CACHED_KEYS = ElementRepository.getInitializedTypeKeysMap();
    public static final CachedLazyElementData NULL_CACHED_LAZY_ELEMENT_DATA = new CachedLazyElementData(NULL_LAZY_ELEMENT, NULL_CACHED_KEYS);
    public static final ExternalElementData NULL_EXTERNAL_ELEMENT_DATA = new ExternalElementData(NULL_CACHED_KEYS, DataConstants.NULL_ELEMENT);

    public static final List<Class<?>> CLASSES_OF_GET_MECHANISMS = Arrays.asList(WebElementList.class, WebElement.class);
    public static final MethodGetCommonParametersData DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS = new MethodGetCommonParametersData(CoreConstants.METHODS, SeleniumCoreConstants.WEB_ELEMENT_METHOD_LIST, DataConstants.NULL_METHODDATA);
}
