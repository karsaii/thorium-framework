package selenium.element;

import core.constants.CoreDataConstants;
import selenium.namespaces.SeleniumExecutor;
import selenium.namespaces.extensions.boilers.DriverFunction;
import core.extensions.interfaces.functional.TriFunction;
import core.namespaces.DataFactoryFunctions;
import core.namespaces.Executor;
import core.records.Data;
import data.constants.Strings;
import data.namespaces.Formatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.constants.ElementWaitDefaults;
import selenium.enums.SingleGetter;
import selenium.namespaces.Driver;
import selenium.namespaces.DriverFunctionFactoryFunctions;
import selenium.namespaces.Wait;
import selenium.namespaces.repositories.LocatorRepository;
import selenium.records.ActionWhenData;
import selenium.records.ElementWaitParameters;
import selenium.records.LazyElement;
import selenium.records.lazy.LazyElementWaitParameters;

import java.util.function.BiFunction;
import java.util.function.Function;

import static core.extensions.namespaces.CoreUtilities.areNotNull;

import static core.namespaces.validators.DataValidators.isValidNonFalse;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.ExecutionCore.validChain;
import static selenium.namespaces.SeleniumUtilities.isNotNullLazyElementWaitParametersData;

public interface Element {
    private static Data<Boolean> actionCore(Data<Void> data, String message) {
        final var nameof = "action";
        final var status = data.status;
        final var lMessage = Formatter.getActionMessage(message, data.message.getMessage(), status);
        final var exception = data.exception;
        return DataFactoryFunctions.getBoolean(status, nameof, lMessage, exception);
    }

    private static Function<Data<Void>, Data<Boolean>> actionCore(String message) {
        return voidData -> actionCore(voidData, message);
    }

    static DriverFunction<String> getWhenCore(DriverFunction<Boolean> waiter, DriverFunction<String> getter) {
        final var nameof = "getWhenCore";
        return ifDriver(nameof, areNotNull(waiter, getter), SeleniumExecutor.execute(nameof, waiter, getter), CoreDataConstants.NULL_STRING);
    }

    static DriverFunction<String> getWhenCore(
        LazyElementWaitParameters data,
        String input,
        Function<LazyElementWaitParameters, DriverFunction<Boolean>> waiter,
        BiFunction<LazyElement, String, DriverFunction<String>> getter
    ) {
        return isNotNullLazyElementWaitParametersData(data) && areNotNull(waiter, getter) && isNotBlank(input) ? (
            getWhenCore(waiter.apply(data), getter.apply(data.object, input))
        ) : DriverFunctionFactoryFunctions.get(CoreDataConstants.DATA_WAS_NULL_OR_FALSE_STRING);
    }

    static DriverFunction<String> getWhenCore(
        LazyElementWaitParameters data,
        Function<LazyElementWaitParameters, DriverFunction<Boolean>> waiter,
        Function<LazyElement, DriverFunction<String>> getter
    ) {
        return (isNotNullLazyElementWaitParametersData(data) && areNotNull(waiter, getter)) ? (
            getWhenCore(waiter.apply(data), getter.apply(data.object))
        ) : DriverFunctionFactoryFunctions.get(CoreDataConstants.DATA_WAS_NULL_OR_FALSE_STRING);
    }

    static DriverFunction<Boolean> sendKeys(LazyElement element, String input) {
        return ifDriver(
            Formatter.getSendKeysNotSendingMessage(element, input),
            validChain(Driver.invokeElementSendKeys(element, input), Element.actionCore("sent keys to"), CoreDataConstants.NULL_BOOLEAN)
        );
    }

    static DriverFunction<Boolean> click(LazyElement element) {
        return ifDriver(
            Formatter.isNullLazyElementMessage(element),
            validChain(Driver.invokeElementClick(element), Element.actionCore("clicked"), CoreDataConstants.NULL_BOOLEAN)
        );
    }

    static DriverFunction<Boolean> clear(LazyElement element) {
        return ifDriver(
            Formatter.isNullLazyElementMessage(element),
            validChain(Driver.invokeElementClear(element), Element.actionCore("cleared"), CoreDataConstants.NULL_BOOLEAN)
        );
    }

    static DriverFunction<Boolean> sendKeys(Data<LazyElement> data, String input) {
        return ifDriver("sendKeys", isValidNonFalse(data), sendKeys(data.object, input), CoreDataConstants.NULL_BOOLEAN);
    }

    static DriverFunction<Boolean> click(Data<LazyElement> data) {
        return ifDriver("click", isValidNonFalse(data), click(data.object), CoreDataConstants.NULL_BOOLEAN);
    }

    static DriverFunction<Boolean> clear(Data<LazyElement> data) {
        return ifDriver("clear", isValidNonFalse(data), clear(data.object), CoreDataConstants.NULL_BOOLEAN);
    }

    static DriverFunction<Boolean> click(By locator, SingleGetter getter) {
        return ifDriver(Formatter.getLocatorAndGetterErrorMessage(locator, getter), click(LocatorRepository.getIfContains(locator, getter)));
    }

    static DriverFunction<Boolean> clear(By locator, SingleGetter getter) {
        return ifDriver(Formatter.getLocatorAndGetterErrorMessage(locator, getter), clear(LocatorRepository.getIfContains(locator, getter)));
    }

    static DriverFunction<Boolean> click(By locator) {
        return ifDriver(Formatter.getLocatorErrorMessage(locator), click(LocatorRepository.getIfContains(locator)));
    }

    static DriverFunction<Boolean> clear(By locator) {
        return ifDriver(Formatter.getLocatorErrorMessage(locator), clear(LocatorRepository.getIfContains(locator)));
    }

    static DriverFunction<Boolean> sendKeys(By locator, String input, SingleGetter getter) {
        return ifDriver(Formatter.getSendKeysNotSendingMessage(locator, input, getter), sendKeys(LocatorRepository.getIfContains(locator, getter), input));
    }

    static DriverFunction<Boolean> sendKeys(By locator, String input) {
        return ifDriver(Formatter.getSendKeysNotSendingMessage(locator, input), sendKeys(LocatorRepository.getIfContains(locator), input));
    }

    static <T, U> DriverFunction<U> actionWhenCore(ActionWhenData<T, U> data) {
        return SeleniumExecutor.execute(Executor::aggregateMessage, data.condition, data.action);
    }

    static <T, U> DriverFunction<U> actionWhenCore(LazyElement data, Function<LazyElement, DriverFunction<U>> action, Function<LazyElement, DriverFunction<T>> condition) {
        return ifDriver(
            "actionWhenCore",
            Formatter.isNullLazyElementMessage(data),
            SeleniumExecutor.execute(Executor::aggregateMessage, condition.apply(data), action.apply(data)),
            DataFactoryFunctions.getWithMessage(null, false, "")
        );
    }

    static <T, U> DriverFunction<U> actionWhenCore(LazyElementWaitParameters data, Function<LazyElement, DriverFunction<U>> action, Function<LazyElementWaitParameters, DriverFunction<T>> condition) {
        return ifDriver(
            "actionWhenCore",
            isNotNullLazyElementWaitParametersData(data),
            SeleniumExecutor.execute(Executor::aggregateMessage, condition.apply(data), action.apply(data.object)),
            DataFactoryFunctions.getWithMessage(null, false, Strings.LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL)
        );
    }

    static DriverFunction<Boolean> waitPresent(By locator, int interval, int timeout) {
        return Wait.waitConditionCore(locator, Driver::isElementPresent, Strings.OPTION_EMPTY, interval, timeout, Strings.PRESENT);
    }

    static DriverFunction<Boolean> waitDisplayed(By locator, int interval, int timeout) {
        return Wait.waitConditionCore(locator, Driver::isElementDisplayed, Strings.OPTION_EMPTY, interval, timeout, Strings.DISPLAYED);
    }

    static DriverFunction<Boolean> waitEnabled(By locator, int interval, int timeout) {
        return Wait.waitConditionCore(locator, Driver::isElementEnabled, Strings.OPTION_EMPTY, interval, timeout, Strings.ENABLED);
    }

    static DriverFunction<Boolean> waitSelected(By locator, int interval, int timeout) {
        return Wait.waitConditionCore(locator, Driver::isElementSelected, Strings.OPTION_EMPTY, interval, timeout, Strings.SELECTED);
    }

    static DriverFunction<Boolean> waitClickable(By locator, int interval, int timeout) {
        return Wait.waitConditionCore(locator, Driver::isElementClickable, Strings.OPTION_EMPTY, interval, timeout, Strings.CLICKABLE);
    }

    static DriverFunction<Boolean> waitAbsent(By locator, int interval, int timeout) {
        return Wait.waitConditionCore(locator, Driver::isElementAbsent, Strings.OPTION_EMPTY, interval, timeout, Strings.ABSENT);
    }

    static DriverFunction<Boolean> waitHidden(By locator, int interval, int timeout) {
        return Wait.waitConditionCore(locator, Driver::isElementHidden, Strings.OPTION_EMPTY, interval, timeout, Strings.HIDDEN);
    }

    static DriverFunction<Boolean> waitDisabled(By locator, int interval, int timeout) {
        return Wait.waitConditionCore(locator, Driver::isElementDisabled, Strings.OPTION_EMPTY, interval, timeout, Strings.DISABLED);
    }

    static DriverFunction<Boolean> waitUnselected(By locator, int interval, int timeout) {
        return Wait.waitConditionCore(locator, Driver::isElementUnselected, Strings.OPTION_EMPTY, interval, timeout, Strings.UNSELECTED);
    }

    static DriverFunction<Boolean> waitUnclickable(By locator, int interval, int timeout) {
        return Wait.waitConditionCore(locator, Driver::isElementUnclickable, Strings.OPTION_EMPTY, interval, timeout, Strings.UNCLICKABLE);
    }

    static DriverFunction<Boolean> waitPresent(ElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementPresent, Strings.OPTION_EMPTY, Strings.PRESENT);
    }

    static DriverFunction<Boolean> waitDisplayed(ElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementDisplayed, Strings.OPTION_EMPTY, Strings.DISPLAYED);
    }

    static DriverFunction<Boolean> waitEnabled(ElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementEnabled, Strings.OPTION_EMPTY, Strings.ENABLED);
    }

    static DriverFunction<Boolean> waitSelected(ElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementSelected, Strings.OPTION_EMPTY, Strings.SELECTED);
    }

    static DriverFunction<Boolean> waitClickable(ElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementClickable, Strings.OPTION_EMPTY, Strings.CLICKABLE);
    }

    static DriverFunction<Boolean> waitAbsent(ElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementAbsent, Strings.OPTION_EMPTY, Strings.ABSENT);
    }

    static DriverFunction<Boolean> waitHidden(ElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementHidden, Strings.OPTION_EMPTY, Strings.HIDDEN);
    }

    static DriverFunction<Boolean> waitDisabled(ElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementDisabled, Strings.OPTION_EMPTY, Strings.DISABLED);
    }

    static DriverFunction<Boolean> waitUnselected(ElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementUnselected, Strings.OPTION_EMPTY, Strings.UNSELECTED);
    }

    static DriverFunction<Boolean> waitUnclickable(ElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementUnclickable, Strings.OPTION_EMPTY, Strings.UNCLICKABLE);
    }

    static DriverFunction<Boolean> waitPresent(By locator) {
        return waitPresent(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitDisplayed(By locator) {
        return waitDisplayed(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitEnabled(By locator) {
        return waitEnabled(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitSelected(By locator) {
        return waitSelected(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitClickable(By locator) {
        return waitClickable(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitAbsent(By locator) {
        return waitAbsent(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitHidden(By locator) {
        return waitHidden(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitDisabled(By locator) {
        return waitDisabled(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitUnselected(By locator) {
        return waitUnselected(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitUnclickable(By locator) {
        return waitUnclickable(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitPresent(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCore(data, Driver::isElementPresent, Strings.OPTION_EMPTY, interval, timeout, Strings.PRESENT);
    }

    static DriverFunction<Boolean> waitDisplayed(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCore(data, Driver::isElementDisplayed, Strings.OPTION_EMPTY, interval, timeout, Strings.DISPLAYED);
    }

    static DriverFunction<Boolean> waitEnabled(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCore(data, Driver::isElementEnabled, Strings.OPTION_EMPTY, interval, timeout, Strings.ENABLED);
    }

    static DriverFunction<Boolean> waitSelected(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCore(data, Driver::isElementSelected, Strings.OPTION_EMPTY, interval, timeout, Strings.SELECTED);
    }

    static DriverFunction<Boolean> waitClickable(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCore(data, Driver::isElementClickable, Strings.OPTION_EMPTY, interval, timeout, Strings.CLICKABLE);
    }

    static DriverFunction<Boolean> waitAbsent(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCore(data, Driver::isElementAbsent, Strings.OPTION_EMPTY, interval, timeout, Strings.ABSENT);
    }

    static DriverFunction<Boolean> waitHidden(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCore(data, Driver::isElementHidden, Strings.OPTION_NOT, interval, timeout, Strings.HIDDEN);
    }

    static DriverFunction<Boolean> waitDisabled(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCore(data, Driver::isElementDisabled, Strings.OPTION_NOT, interval, timeout, Strings.DISABLED);
    }

    static DriverFunction<Boolean> waitUnselected(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCore(data, Driver::isElementUnselected, Strings.OPTION_NOT, interval, timeout, Strings.UNSELECTED);
    }

    static DriverFunction<Boolean> waitUnclickable(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCore(data, Driver::isElementUnclickable, Strings.OPTION_NOT, interval, timeout, Strings.UNCLICKABLE);
    }

    static DriverFunction<Boolean> waitPresentDefaults(LazyElement data) {
        return waitPresent(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitDisplayedDefaults(LazyElement data) {
        return waitDisplayed(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitEnabledDefaults(LazyElement data) {
        return waitEnabled(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitSelectedDefaults(LazyElement data) {
        return waitSelected(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitClickableDefaults(LazyElement data) {
        return waitClickable(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitAbsentDefaults(LazyElement data) {
        return waitAbsent(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitHiddenDefaults(LazyElement data) {
        return waitHidden(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitDisabledDefaults(LazyElement data) {
        return waitDisabled(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitUnselectedDefaults(LazyElement data) {
        return waitUnselected(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitUnclickableDefaults(LazyElement data) {
        return waitUnclickable(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitPresent(LazyElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementPresent, Strings.OPTION_EMPTY, Strings.PRESENT);
    }

    static DriverFunction<Boolean> waitDisplayed(LazyElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementDisplayed, Strings.OPTION_EMPTY, Strings.DISPLAYED);
    }

    static DriverFunction<Boolean> waitEnabled(LazyElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementEnabled, Strings.OPTION_EMPTY, Strings.ENABLED);
    }

    static DriverFunction<Boolean> waitSelected(LazyElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementSelected, Strings.OPTION_EMPTY, Strings.SELECTED);
    }

    static DriverFunction<Boolean> waitClickable(LazyElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementClickable, Strings.OPTION_EMPTY, Strings.CLICKABLE);
    }

    static DriverFunction<Boolean> waitAbsent(LazyElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementAbsent, Strings.OPTION_EMPTY, Strings.ABSENT);
    }

    static DriverFunction<Boolean> waitHidden(LazyElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementHidden, Strings.OPTION_EMPTY, Strings.HIDDEN);
    }

    static DriverFunction<Boolean> waitDisabled(LazyElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementDisabled, Strings.OPTION_EMPTY, Strings.DISABLED);
    }

    static DriverFunction<Boolean> waitUnselected(LazyElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementUnselected, Strings.OPTION_EMPTY, Strings.UNSELECTED);
    }

    static DriverFunction<Boolean> waitUnclickable(LazyElementWaitParameters data) {
        return Wait.waitConditionCore(data, Driver::isElementUnclickable, Strings.OPTION_EMPTY, Strings.UNCLICKABLE);
    }

    static DriverFunction<String> getText(LazyElement element) {
        return Driver.getElementText(element);
    }

    static DriverFunction<String> getTagName(LazyElement element) {
        return Driver.getElementTagName(element);
    }

    static DriverFunction<String> getAttribute(LazyElement element, String attribute) {
        return Driver.getElementAttribute(element, attribute);
    }

    static DriverFunction<String> getAttributeValue(LazyElement element) {
        return Driver.getElementAttributeValue(element);
    }

    static DriverFunction<String> getCssValue(LazyElement element, String cssValue) {
        return Driver.getElementCssValue(element, cssValue);
    }

    static DriverFunction<String> getTextWhen(LazyElementWaitParameters data, Function<LazyElementWaitParameters, DriverFunction<Boolean>> waiter) {
        return getWhenCore(data, waiter, Element::getText);
    }

    static DriverFunction<String> getTagNameWhen(LazyElementWaitParameters data, Function<LazyElementWaitParameters, DriverFunction<Boolean>> waiter) {
        return getWhenCore(data, waiter, Element::getTagName);
    }

    static DriverFunction<String> getAttributeWhen(LazyElementWaitParameters data, String attribute, Function<LazyElementWaitParameters, DriverFunction<Boolean>> waiter) {
        return getWhenCore(data, attribute, waiter, Element::getAttribute);
    }

    static DriverFunction<String> getAttributeValueWhen(LazyElementWaitParameters data, Function<LazyElementWaitParameters, DriverFunction<Boolean>> waiter) {
        return getWhenCore(data, waiter, Element::getAttributeValue);
    }

    static DriverFunction<String> getCssValueWhen(LazyElementWaitParameters data, String attribute, Function<LazyElementWaitParameters, DriverFunction<Boolean>> waiter) {
        return getWhenCore(data, attribute, waiter, Element::getCssValue);
    }

    /* regular unwrappers */
    static DriverFunction<Boolean> clickWhenCore(LazyElement data, DriverFunction<Boolean> condition) {
        return Alternatives.regular.clickWhenCore(data, condition);
    }

    static DriverFunction<Boolean> clearWhenCore(LazyElement data, DriverFunction<Boolean> condition) {
        return Alternatives.regular.clearWhenCore(data, condition);
    }

    static DriverFunction<Boolean> clickWhenCore(LazyElement data, Function<LazyElement, DriverFunction<Boolean>> condition) {
        return Alternatives.regular.clickWhenCore(data, condition);
    }

    static DriverFunction<Boolean> clearWhenCore(LazyElement data, Function<LazyElement, DriverFunction<Boolean>> condition) {
        return Alternatives.regular.clearWhenCore(data, condition);
    }

    static DriverFunction<Boolean> clickWhenCore(By locator, Function<By, DriverFunction<Boolean>> condition, SingleGetter getter) {
        return Alternatives.regular.clickWhenCore(locator, condition, getter);
    }

    static DriverFunction<Boolean> clearWhenCore(By locator, Function<By, DriverFunction<Boolean>> condition, SingleGetter getter) {
        return Alternatives.regular.clearWhenCore(locator, condition, getter);
    }

    static DriverFunction<Boolean> clickWhenCore(By locator, Function<By, DriverFunction<Boolean>> condition) {
        return Alternatives.regular.clickWhenCore(locator, condition);
    }

    static DriverFunction<Boolean> clearWhenCore(By locator, BiFunction<By, DriverFunction<WebElement>, DriverFunction<Boolean>> condition, SingleGetter getter) {
        return Alternatives.regular.clearWhenCore(locator, condition, getter);
    }

    static DriverFunction<Boolean> clearWhenCore(By locator, Function<By, DriverFunction<Boolean>> condition) {
        return Alternatives.regular.clearWhenCore(locator, condition);
    }

    static DriverFunction<Boolean> inputWhenCore(ActionWhenData<Boolean, Boolean> data) {
        return Alternatives.regular.inputWhenCore(data);
    }

    static DriverFunction<Boolean> inputWhenCore(
        By locator,
        SingleGetter getter,
        String input,
        Function<LazyElement, DriverFunction<Boolean>> condition,
        BiFunction<LazyElement, String, DriverFunction<Boolean>> sender
    ) {
        return Alternatives.regular.inputWhenCore(locator, getter, input, condition, sender);
    }

    static DriverFunction<Boolean> inputWhenCore(
        By locator,
        String input,
        Function<LazyElement, DriverFunction<Boolean>> condition,
        BiFunction<LazyElement, String, DriverFunction<Boolean>> sender
    ) {
        return Alternatives.regular.inputWhenCore(locator, input, condition, sender);
    }

    static DriverFunction<Boolean> inputWhenCore(
        By locator,
        SingleGetter getter,
        String input,
        BiFunction<By, SingleGetter, DriverFunction<Boolean>> condition,
        TriFunction<By, SingleGetter, String, DriverFunction<Boolean>> sender
    ) {
        return Alternatives.regular.inputWhenCore(locator, getter, input, condition, sender);
    }

    static DriverFunction<Boolean> inputWhenCore(
        LazyElement data,
        String input,
        Function<LazyElement, DriverFunction<Boolean>> condition,
        BiFunction<LazyElement, String, DriverFunction<Boolean>> sender
    ) {
        return Alternatives.regular.inputWhenCore(data, input, condition, sender);
    }

    static DriverFunction<Boolean> clickWhenCore(LazyElementWaitParameters data, Function<LazyElementWaitParameters, DriverFunction<Boolean>> condition) {
        return Alternatives.regular.clickWhenCore(data, condition);
    }

    static DriverFunction<Boolean> clearWhenCore(LazyElementWaitParameters data, Function<LazyElementWaitParameters, DriverFunction<Boolean>> condition) {
        return Alternatives.regular.clearWhenCore(data, condition);
    }

    static DriverFunction<Boolean> inputWhenCore(
        LazyElementWaitParameters data,
        String input,
        Function<LazyElementWaitParameters, DriverFunction<Boolean>> action,
        BiFunction<LazyElement, String, DriverFunction<Boolean>> sender
    ) {
        return Alternatives.regular.inputWhenCore(data,input, action, sender);
    }


    static DriverFunction<Boolean> clickWhenPresent(By locator, SingleGetter getter) {
        return Alternatives.regular.clickWhenPresent(locator, getter);
    }

    static DriverFunction<Boolean> clickWhenDisplayed(By locator, SingleGetter getter) {
        return Alternatives.regular.clickWhenDisplayed(locator, getter);
    }

    static DriverFunction<Boolean> clickWhenEnabled(By locator, SingleGetter getter) {
        return Alternatives.regular.clickWhenEnabled(locator, getter);
    }

    static DriverFunction<Boolean> clickWhenSelected(By locator, SingleGetter getter) {
        return Alternatives.regular.clickWhenSelected(locator, getter);
    }

    static DriverFunction<Boolean> clickWhenClickable(By locator, SingleGetter getter) {
        return Alternatives.regular.clickWhenClickable(locator, getter);
    }

    static DriverFunction<Boolean> clickWhenPresent(By locator) {
        return Alternatives.regular.clickWhenPresent(locator);
    }

    static DriverFunction<Boolean> clickWhenDisplayed(By locator) {
        return Alternatives.regular.clickWhenDisplayed(locator);
    }

    static DriverFunction<Boolean> clickWhenEnabled(By locator) {
        return Alternatives.regular.clickWhenEnabled(locator);
    }

    static DriverFunction<Boolean> clickWhenSelected(By locator) {
        return Alternatives.regular.clickWhenSelected(locator);
    }

    static DriverFunction<Boolean> clickWhenClickable(By locator) {
        return Alternatives.regular.clickWhenClickable(locator);
    }

    static DriverFunction<Boolean> clearWhenPresent(By locator, SingleGetter getter) {
        return Alternatives.regular.clearWhenPresent(locator, getter);
    }

    static DriverFunction<Boolean> clearWhenDisplayed(By locator, SingleGetter getter) {
        return Alternatives.regular.clearWhenDisplayed(locator, getter);
    }

    static DriverFunction<Boolean> clearWhenEnabled(By locator, SingleGetter getter) {
        return Alternatives.regular.clearWhenEnabled(locator, getter);
    }

    static DriverFunction<Boolean> clearWhenSelected(By locator, SingleGetter getter) {
        return Alternatives.regular.clearWhenSelected(locator, getter);
    }

    static DriverFunction<Boolean> clearWhenClickable(By locator, SingleGetter getter) {
        return Alternatives.regular.clearWhenClickable(locator, getter);
    }

    static DriverFunction<Boolean> clearWhenPresent(By locator) {
        return Alternatives.regular.clearWhenPresent(locator);
    }

    static DriverFunction<Boolean> clearWhenDisplayed(By locator) {
        return Alternatives.regular.clearWhenDisplayed(locator);
    }

    static DriverFunction<Boolean> clearWhenEnabled(By locator) {
        return Alternatives.regular.clearWhenEnabled(locator);
    }

    static DriverFunction<Boolean> clearWhenSelected(By locator) {
        return Alternatives.regular.clearWhenSelected(locator);
    }

    static DriverFunction<Boolean> clearWhenClickable(By locator) {
        return Alternatives.regular.clearWhenClickable(locator);
    }

    static DriverFunction<Boolean> inputWhenPresent(By locator, String input, SingleGetter getter) {
        return Alternatives.regular.inputWhenPresent(locator, input, getter);
    }

    static DriverFunction<Boolean> inputWhenDisplayed(By locator, String input, SingleGetter getter) {
        return Alternatives.regular.inputWhenDisplayed(locator, input, getter);
    }

    static DriverFunction<Boolean> inputWhenEnabled(By locator, String input, SingleGetter getter) {
        return Alternatives.regular.inputWhenEnabled(locator, input, getter);
    }

    static DriverFunction<Boolean> inputWhenSelected(By locator, String input, SingleGetter getter) {
        return Alternatives.regular.inputWhenSelected(locator, input, getter);
    }

    static DriverFunction<Boolean> inputWhenClickable(By locator, String input, SingleGetter getter) {
        return Alternatives.regular.inputWhenClickable(locator, input, getter);
    }

    static DriverFunction<Boolean> inputWhenPresent(By locator, String input) {
        return Alternatives.regular.inputWhenPresent(locator, input);
    }

    static DriverFunction<Boolean> inputWhenDisplayed(By locator, String input) {
        return Alternatives.regular.inputWhenDisplayed(locator, input);
    }

    static DriverFunction<Boolean> inputWhenEnabled(By locator, String input) {
        return Alternatives.regular.inputWhenEnabled(locator, input);
    }

    static DriverFunction<Boolean> inputWhenSelected(By locator, String input) {
        return Alternatives.regular.inputWhenSelected(locator, input);
    }

    static DriverFunction<Boolean> inputWhenClickable(By locator, String input) {
        return Alternatives.regular.inputWhenClickable(locator, input);
    }

    static DriverFunction<Boolean> clickWhenPresent(LazyElement data) {
        return Alternatives.regular.clickWhenPresent(data);
    }

    static DriverFunction<Boolean> clickWhenDisplayed(LazyElement data) {
        return Alternatives.regular.clickWhenDisplayed(data);
    }

    static DriverFunction<Boolean> clickWhenEnabled(LazyElement data) {
        return Alternatives.regular.clickWhenEnabled(data);
    }

    static DriverFunction<Boolean> clickWhenSelected(LazyElement data) {
        return Alternatives.regular.clickWhenSelected(data);
    }

    static DriverFunction<Boolean> clickWhenClickable(LazyElement data) {
        return Alternatives.regular.clickWhenClickable(data);
    }

    static DriverFunction<Boolean> inputWhenPresent(LazyElement data, String input) {
        return Alternatives.regular.inputWhenPresent(data, input);
    }

    static DriverFunction<Boolean> inputWhenDisplayed(LazyElement data, String input) {
        return Alternatives.regular.inputWhenDisplayed(data, input);
    }

    static DriverFunction<Boolean> inputWhenEnabled(LazyElement data, String input) {
        return Alternatives.regular.inputWhenEnabled(data, input);
    }

    static DriverFunction<Boolean> inputWhenSelected(LazyElement data, String input) {
        return Alternatives.regular.inputWhenSelected(data, input);
    }

    static DriverFunction<Boolean> inputWhenClickable(LazyElement data, String input) {
        return Alternatives.regular.inputWhenClickable(data, input);
    }

    static DriverFunction<Boolean> clearWhenPresent(LazyElement data) {
        return Alternatives.regular.clearWhenPresent(data);
    }

    static DriverFunction<Boolean> clearWhenDisplayed(LazyElement data) {
        return Alternatives.regular.clearWhenDisplayed(data);
    }

    static DriverFunction<Boolean> clearWhenEnabled(LazyElement data) {
        return Alternatives.regular.clearWhenEnabled(data);
    }

    static DriverFunction<Boolean> clearWhenSelected(LazyElement data) {
        return Alternatives.regular.clearWhenSelected(data);
    }

    static DriverFunction<Boolean> clearWhenClickable(LazyElement data) {
        return Alternatives.regular.clearWhenClickable(data);
    }

    static DriverFunction<Boolean> clickWhenPresent(LazyElementWaitParameters data) {
        return Alternatives.regular.clickWhenPresent(data);
    }

    static DriverFunction<Boolean> clickWhenDisplayed(LazyElementWaitParameters data) {
        return Alternatives.regular.clickWhenDisplayed(data);
    }

    static DriverFunction<Boolean> clickWhenEnabled(LazyElementWaitParameters data) {
        return Alternatives.regular.clickWhenEnabled(data);
    }

    static DriverFunction<Boolean> clickWhenSelected(LazyElementWaitParameters data) {
        return Alternatives.regular.clickWhenSelected(data);
    }

    static DriverFunction<Boolean> clickWhenClickable(LazyElementWaitParameters data) {
        return Alternatives.regular.clickWhenClickable(data);
    }

    static DriverFunction<Boolean> inputWhenPresent(LazyElementWaitParameters data, String input) {
        return Alternatives.regular.inputWhenPresent(data, input);
    }

    static DriverFunction<Boolean> inputWhenDisplayed(LazyElementWaitParameters data, String input) {
        return Alternatives.regular.inputWhenDisplayed(data, input);
    }

    static DriverFunction<Boolean> inputWhenEnabled(LazyElementWaitParameters data, String input) {
        return Alternatives.regular.inputWhenEnabled(data, input);
    }

    static DriverFunction<Boolean> inputWhenSelected(LazyElementWaitParameters data, String input) {
        return Alternatives.regular.inputWhenSelected(data, input);
    }

    static DriverFunction<Boolean> inputWhenClickable(LazyElementWaitParameters data, String input) {
        return Alternatives.regular.inputWhenClickable(data, input);
    }

    static DriverFunction<Boolean> clearWhenPresent(LazyElementWaitParameters data) {
        return Alternatives.regular.clearWhenPresent(data);
    }

    static DriverFunction<Boolean> clearWhenDisplayed(LazyElementWaitParameters data) {
        return Alternatives.regular.clearWhenDisplayed(data);
    }

    static DriverFunction<Boolean> clearWhenEnabled(LazyElementWaitParameters data) {
        return Alternatives.regular.clearWhenEnabled(data);
    }

    static DriverFunction<Boolean> clearWhenSelected(LazyElementWaitParameters data) {
        return Alternatives.regular.clearWhenSelected(data);
    }

    static DriverFunction<Boolean> clearWhenClickable(LazyElementWaitParameters data) {
        return Alternatives.regular.clearWhenClickable(data);
    }
}
