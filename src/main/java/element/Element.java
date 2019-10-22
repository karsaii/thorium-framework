package element;

import data.constants.Strings;
import data.Data;
import data.DriverFunction;
import data.enums.SingleGetter;
import data.tuples.ActionData;
import data.tuples.ActionWhenData;
import data.functions.*;
import data.LazyElement;
import data.constants.DataDefaults;
import data.constants.ElementWaitDefaults;
import data.ElementWaitParameters;
import data.lazy.tuples.LazyElementWaitParameters;
import drivers.Driver;
import expectedConditions.EC;
import formatter.Formatter;
import utilities.TriFunction;
import wait.Wait;
import org.openqa.selenium.*;

import java.util.function.BiFunction;
import java.util.function.Function;

import static data.ExecutionCore.ifDriver;
import static utilities.utils.*;
import static org.apache.commons.lang3.StringUtils.*;

public interface Element {
    private static DriverFunction<Boolean> action(DriverFunction<WebElement> getter, String methodName, String message, String input) {
        final var nameof = "action";
        return ifDriver(
            nameof,
            isNotNull(getter) && isNotBlank(methodName),
            new DriverFunction<Boolean>(driver -> {
                final var isInput = isNotBlank(input);
                final var lData = (
                    isInput ? Driver.invokeVoidMethod(getter, methodName, new CharSequence[]{input}) : Driver.invokeVoidMethod(getter, methodName)
                ).apply(driver);
                final var status = lData.status;
                final var lMessage = isInput ? Formatter.getActionWithInputMessage(message, lData.message.getMessage(), input, status) : Formatter.getActionMessage(message, lData.message.getMessage(), status);
                final var exception = lData.exception;
                return isNonException(exception) ? (
                    new Data<Boolean>(status, status, nameof, lMessage)
                ) : new Data<Boolean>(status, status, nameof, lMessage, exception);
            }),
            DataDefaults.DATA_WAS_NULL_OR_FALSE_BOOLEAN_DATA
        );
    }

    static DriverFunction<String> getWhenCore(DriverFunction<Boolean> waiter, DriverFunction<String> getter) {
        final var nameof = "getWhenCore";
        return ifDriver(nameof, areNotNull(waiter, getter), Executor.execute(nameof, waiter, getter), DataDefaults.NULL_STRING_DATA);
    }

    static DriverFunction<String> getWhenCore(LazyElementWaitParameters data, String input, Function<LazyElementWaitParameters, DriverFunction<Boolean>> waiter, BiFunction<LazyElement, String, DriverFunction<String>> getter) {
        return (
            isNotNullLazyElementWaitParametersData(data) &&
            areNotNull(waiter, getter) &&
            isNotBlank(input)
        ) ? (
            getWhenCore(waiter.apply(data), getter.apply(data.object, input))
        ) : new DriverFunction<String>(DataDefaults.DATA_WAS_NULL_OR_FALSE_STRING_DATA);
    }

    static DriverFunction<String> getWhenCore(LazyElementWaitParameters data, Function<LazyElementWaitParameters, DriverFunction<Boolean>> waiter, Function<LazyElement, DriverFunction<String>> getter) {
        return (isNotNullLazyElementWaitParametersData(data) && areNotNull(waiter, getter)) ? (
            getWhenCore(waiter.apply(data), getter.apply(data.object))
        ) : new DriverFunction<String>(DataDefaults.DATA_WAS_NULL_OR_FALSE_STRING_DATA);
    }

    private static DriverFunction<Boolean> action(DriverFunction<WebElement> getter, String methodName, String message) {
        return action(getter, methodName, message, null);
    }

    private static DriverFunction<Boolean> action(ActionData<LazyElement> actionData) {
        return ifDriver(
            "action",
            Formatter.getNullOrFalseActionDataMessage(actionData),
            action(actionData.data.get(), actionData.methodName, actionData.message, actionData.input),
            DataDefaults.NULL_BOOLEAN_DATA
        );
    }

    private static DriverFunction<Boolean> action(LazyElement data, String methodName, String message) {
        return action(new ActionData<>(data, methodName, message));
    }

    private static DriverFunction<Boolean> action(LazyElement data, String methodName, String message, String input) {
        return action(new ActionData<>(data, methodName, message, input));
    }

    static DriverFunction<Boolean> click(DriverFunction<WebElement> getter) {
        return ifBlank(Formatter.getGetterErrorMessage(getter), action(getter, "click", "clicked"));
    }

    static DriverFunction<Boolean> clear(DriverFunction<WebElement> getter) {
        return ifBlank(Formatter.getGetterErrorMessage(getter), action(getter, "clear", "cleared"));
    }

    static DriverFunction<Boolean> sendKeys(DriverFunction<WebElement> getter, String input) {
        return ifBlank(Formatter.getSendKeysNotSendingMessage(getter, input), action(getter, "sendKeys", "sent keys to", input));
    }

    static DriverFunction<Boolean> sendKeys(LazyElement element, String input) {
        return sendKeys(element.get(), input);
    }

    static DriverFunction<Boolean> click(LazyElement data) {
        final var nameof = "click";
        return ifDriver(nameof, Formatter.isNullLazyElementMessage(data), action(data, nameof, "clicked"), DataDefaults.NULL_BOOLEAN_DATA);
    }

    static DriverFunction<Boolean> clear(LazyElement data) {
        final var nameof = "clear";
        return ifDriver(nameof, Formatter.isNullLazyElementMessage(data), action(data, nameof, "cleared"), DataDefaults.NULL_BOOLEAN_DATA);
    }

    static DriverFunction<Boolean> click(By locator, SingleGetter getter) {
        return ifBlank(Formatter.getLocatorAndGetterErrorMessage(locator, getter), click(new LazyElement(locator, getter)));
    }

    static DriverFunction<Boolean> clear(By locator, SingleGetter getter) {
        return ifBlank(Formatter.getLocatorAndGetterErrorMessage(locator, getter), clear(new LazyElement(locator, getter)));
    }

    static DriverFunction<Boolean> click(By locator) {
        return ifBlank(Formatter.getLocatorErrorMessage(locator), click(new LazyElement(locator, SingleGetter.DEFAULT)));
    }

    static DriverFunction<Boolean> clear(By locator) {
        return ifBlank(Formatter.getLocatorErrorMessage(locator), clear(new LazyElement(locator, SingleGetter.DEFAULT)));
    }

    static DriverFunction<Boolean> sendKeys(By locator, String input, SingleGetter getter) {
        return ifBlank(Formatter.getSendKeysNotSendingMessage(locator, input, getter), sendKeys(new LazyElement(locator, getter), input));
    }

    static DriverFunction<Boolean> sendKeys(By locator, String input) {
        return ifBlank(Formatter.getSendKeysNotSendingMessage(locator, input), sendKeys(new LazyElement(locator, SingleGetter.DEFAULT), input));
    }

    static <T, U> DriverFunction<U> actionWhenCore(ActionWhenData<T, U> data) {
        return Executor.execute(Executor::aggregateMessage, data.condition, data.action);
    }

    static <T, U> DriverFunction<U> actionWhenCore(Function<LazyElement, DriverFunction<U>> action, Function<LazyElement, DriverFunction<T>> condition, LazyElement data) {
        return ifDriver(
            "actionWhenCore",
            Formatter.isNullLazyElementMessage(data),
            Executor.execute(Executor::aggregateMessage, condition.apply(data), action.apply(data)),
            new Data<U>((U)null, false, "")
        );
    }

    static <T, U> DriverFunction<U> actionWhenCore(Function<LazyElement, DriverFunction<U>> action, LazyElementWaitParameters data, Function<LazyElementWaitParameters, DriverFunction<T>> condition) {
        return ifDriver(
            "actionWhenCore",
            isNotNullLazyElementWaitParametersData(data),
            Executor.execute(Executor::aggregateMessage, condition.apply(data), action.apply(data.object)),
            new Data<U>(null, false, Strings.LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL)
        );
    }

    static DriverFunction<Boolean> waitPresentLambda(By locator, int interval, int timeout) {
        return new DriverFunction<Boolean>(Wait.waitConditionCore(locator, EC::isElementPresentData, Strings.OPTION_EMPTY, interval, timeout, Strings.PRESENT));
    }

    static DriverFunction<Boolean> waitDisplayedLambda(By locator, int interval, int timeout) {
        return new DriverFunction<Boolean>(Wait.waitConditionCore(locator, EC::isElementDisplayedData, Strings.OPTION_EMPTY, interval, timeout, Strings.DISPLAYED));
    }

    static DriverFunction<Boolean> waitEnabledLambda(By locator, int interval, int timeout) {
        return Wait.waitConditionCoreF(locator, EC::isElementEnabledData, Strings.OPTION_EMPTY, interval, timeout, Strings.ENABLED);
    }

    static DriverFunction<Boolean> waitSelectedLambda(By locator, int interval, int timeout) {
        return Wait.waitConditionCoreF(locator, EC::isElementSelectedData, Strings.OPTION_EMPTY, interval, timeout, Strings.SELECTED);
    }

    static DriverFunction<Boolean> waitClickableLambda(By locator, int interval, int timeout) {
        return Wait.waitConditionCoreF(locator, EC::isElementClickableData, Strings.OPTION_EMPTY, interval, timeout, Strings.CLICKABLE);
    }

    static DriverFunction<Boolean> waitAbsentLambda(By locator, int interval, int timeout) {
        return Wait.waitConditionCoreF(locator, EC::isElementAbsentData, Strings.OPTION_EMPTY, interval, timeout, Strings.ABSENT);
    }

    static DriverFunction<Boolean> waitHiddenLambda(By locator, int interval, int timeout) {
        return Wait.waitConditionCoreF(locator, EC::isElementHiddenData, Strings.OPTION_EMPTY, interval, timeout, Strings.HIDDEN);
    }

    static DriverFunction<Boolean> waitDisabledLambda(By locator, int interval, int timeout) {
        return Wait.waitConditionCoreF(locator, EC::isElementDisabledData, Strings.OPTION_EMPTY, interval, timeout, Strings.DISABLED);
    }

    static DriverFunction<Boolean> waitUnselectedLambda(By locator, int interval, int timeout) {
        return Wait.waitConditionCoreF(locator, EC::isElementUnselectedData, Strings.OPTION_EMPTY, interval, timeout, Strings.UNSELECTED);
    }

    static DriverFunction<Boolean> waitUnclickableLambda(By locator, int interval, int timeout) {
        return Wait.waitConditionCoreF(locator, EC::isElementUnclickableData, Strings.OPTION_EMPTY, interval, timeout, Strings.UNCLICKABLE);
    }

    static DriverFunction<Boolean> waitPresentLambda(ElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementPresentData, Strings.OPTION_EMPTY, Strings.PRESENT);
    }

    static DriverFunction<Boolean> waitDisplayedLambda(ElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementDisplayedData, Strings.OPTION_EMPTY, Strings.DISPLAYED);
    }

    static DriverFunction<Boolean> waitEnabledLambda(ElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementEnabledData, Strings.OPTION_EMPTY, Strings.ENABLED);
    }

    static DriverFunction<Boolean> waitSelectedLambda(ElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementSelectedData, Strings.OPTION_EMPTY, Strings.SELECTED);
    }

    static DriverFunction<Boolean> waitClickableLambda(ElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementClickableData, Strings.OPTION_EMPTY, Strings.CLICKABLE);
    }

    static DriverFunction<Boolean> waitAbsentLambda(ElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementAbsentData, Strings.OPTION_EMPTY, Strings.ABSENT);
    }

    static DriverFunction<Boolean> waitHiddenLambda(ElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementHiddenData, Strings.OPTION_EMPTY, Strings.HIDDEN);
    }

    static DriverFunction<Boolean> waitDisabledLambda(ElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementDisabledData, Strings.OPTION_EMPTY, Strings.DISABLED);
    }

    static DriverFunction<Boolean> waitUnselectedLambda(ElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementUnselectedData, Strings.OPTION_EMPTY, Strings.UNSELECTED);
    }

    static DriverFunction<Boolean> waitUnclickableLambda(ElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementUnclickableData, Strings.OPTION_EMPTY, Strings.UNCLICKABLE);
    }

    static DriverFunction<Boolean> waitPresentLambda(By locator) {
        return waitPresentLambda(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitDisplayedLambda(By locator) {
        return waitDisplayedLambda(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitEnabledLambda(By locator) {
        return waitEnabledLambda(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitSelectedLambda(By locator) {
        return waitSelectedLambda(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitClickableLambda(By locator) {
        return waitClickableLambda(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitAbsentLambda(By locator) {
        return waitAbsentLambda(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitHiddenLambda(By locator) {
        return waitHiddenLambda(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitDisabledLambda(By locator) {
        return waitDisabledLambda(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitUnselectedLambda(By locator) {
        return waitUnselectedLambda(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitUnclickableLambda(By locator) {
        return waitUnclickableLambda(locator, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitPresentLambda(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCoreF(data, EC::isElementPresent, Strings.OPTION_EMPTY, interval, timeout, Strings.PRESENT);
    }

    static DriverFunction<Boolean> waitDisplayedLambda(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCoreF(data, EC::isElementDisplayed, Strings.OPTION_EMPTY, interval, timeout, Strings.DISPLAYED);
    }

    static DriverFunction<Boolean> waitEnabledLambda(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCoreF(data, EC::isElementEnabled, Strings.OPTION_EMPTY, interval, timeout, Strings.ENABLED);
    }

    static DriverFunction<Boolean> waitSelectedLambda(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCoreF(data, EC::isElementSelected, Strings.OPTION_EMPTY, interval, timeout, Strings.SELECTED);
    }

    static DriverFunction<Boolean> waitClickableLambda(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCoreF(data, EC::isElementClickable, Strings.OPTION_EMPTY, interval, timeout, Strings.CLICKABLE);
    }

    static DriverFunction<Boolean> waitAbsentLambda(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCoreF(data, EC::isElementAbsent, Strings.OPTION_EMPTY, interval, timeout, Strings.ABSENT);
    }

    static DriverFunction<Boolean> waitHiddenLambda(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCoreF(data, EC::isElementHidden, Strings.OPTION_NOT, interval, timeout, Strings.HIDDEN);
    }

    static DriverFunction<Boolean> waitDisabledLambda(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCoreF(data, EC::isElementDisabled, Strings.OPTION_NOT, interval, timeout, Strings.DISABLED);
    }

    static DriverFunction<Boolean> waitUnselectedLambda(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCoreF(data, EC::isElementUnselected, Strings.OPTION_NOT, interval, timeout, Strings.UNSELECTED);
    }

    static DriverFunction<Boolean> waitUnclickableLambda(LazyElement data, int interval, int timeout) {
        return Wait.waitConditionCoreF(data, EC::isElementUnclickable, Strings.OPTION_NOT, interval, timeout, Strings.UNCLICKABLE);
    }

    static DriverFunction<Boolean> waitPresentLambda(LazyElement data) {
        return waitPresentLambda(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitDisplayedLambda(LazyElement data) {
        return waitDisplayedLambda(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitEnabledLambda(LazyElement data) {
        return waitEnabledLambda(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitSelectedLambda(LazyElement data) {
        return waitSelectedLambda(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitClickableLambda(LazyElement data) {
        return waitClickableLambda(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitAbsentLambda(LazyElement data) {
        return waitAbsentLambda(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitHiddenLambda(LazyElement data) {
        return waitHiddenLambda(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitDisabledLambda(LazyElement data) {
        return waitDisabledLambda(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitUnselectedLambda(LazyElement data) {
        return waitUnselectedLambda(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitUnclickableLambda(LazyElement data) {
        return waitUnclickableLambda(data, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }

    static DriverFunction<Boolean> waitPresentLambda(LazyElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementPresent, Strings.OPTION_EMPTY, Strings.PRESENT);
    }

    static DriverFunction<Boolean> waitDisplayedLambda(LazyElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementDisplayed, Strings.OPTION_EMPTY, Strings.DISPLAYED);
    }

    static DriverFunction<Boolean> waitEnabledLambda(LazyElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementEnabled, Strings.OPTION_EMPTY, Strings.ENABLED);
    }

    static DriverFunction<Boolean> waitSelectedLambda(LazyElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementSelected, Strings.OPTION_EMPTY, Strings.SELECTED);
    }

    static DriverFunction<Boolean> waitClickableLambda(LazyElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementClickable, Strings.OPTION_EMPTY, Strings.CLICKABLE);
    }

    static DriverFunction<Boolean> waitAbsentLambda(LazyElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementAbsent, Strings.OPTION_EMPTY, Strings.ABSENT);
    }

    static DriverFunction<Boolean> waitHiddenLambda(LazyElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementHidden, Strings.OPTION_EMPTY, Strings.HIDDEN);
    }

    static DriverFunction<Boolean> waitDisabledLambda(LazyElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementDisabled, Strings.OPTION_EMPTY, Strings.DISABLED);
    }

    static DriverFunction<Boolean> waitUnselectedLambda(LazyElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementUnselected, Strings.OPTION_EMPTY, Strings.UNSELECTED);
    }

    static DriverFunction<Boolean> waitUnclickableLambda(LazyElementWaitParameters data) {
        return Wait.waitConditionCoreF(data, EC::isElementUnclickable, Strings.OPTION_EMPTY, Strings.UNCLICKABLE);
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
