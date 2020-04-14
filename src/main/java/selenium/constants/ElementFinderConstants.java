package selenium.constants;

import selenium.namespaces.extensions.boilers.WebElementList;
import selenium.namespaces.extensions.boilers.DriverFunction;
import org.openqa.selenium.WebElement;
import selenium.enums.ManyGetter;
import selenium.enums.SingleGetter;
import selenium.namespaces.Driver;
import selenium.records.LazyLocatorList;

import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

public abstract class ElementFinderConstants {
    public static final Map<SingleGetter, Function<LazyLocatorList, DriverFunction<WebElement>>> singleGetterMap = Collections.unmodifiableMap(
        new EnumMap<>(
            Map.ofEntries(
                    entry(SingleGetter.GET_ELEMENT, Driver::getElementFromSingle),
                    entry(SingleGetter.GET_ROOT_ELEMENT, Driver::getRootElementFromSingle),
                    entry(SingleGetter.GET_NESTED_ELEMENT, Driver::getNestedElement),
                    entry(SingleGetter.GET_FRAME_NESTED_ELEMENT, Driver::getFrameNestedElement),
                    entry(SingleGetter.GET_SHADOW_ROOT_ELEMENT, Driver::getShadowRootElement),
                    entry(SingleGetter.GET_SHADOW_ROOT_NESTED_ELEMENT, Driver::getShadowNestedElement)
            )
        )
    );

    public static final Map<ManyGetter, Function<LazyLocatorList, DriverFunction<WebElementList>>> manyGetterMap = Collections.unmodifiableMap(
        new EnumMap<>(
            Map.ofEntries(
                entry(ManyGetter.GET_ELEMENTS, Driver::getElements),
                entry(ManyGetter.GET_NESTED_ELEMENTS_FROM_LAST, Driver::getNestedElementsFromLast),
                entry(ManyGetter.GET_FRAME_NESTED_ELEMENTS, Driver::getFrameNestedElementsFromLast),
                entry(ManyGetter.GET_SHADOW_NESTED_ELEMENTS, Driver::getShadowNestedElementsFromLast)
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
}
