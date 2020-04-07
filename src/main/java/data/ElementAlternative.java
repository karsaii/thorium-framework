package data;

import data.constants.DataDefaults;
import data.constants.Defaults;
import data.constants.Strings;
import data.enums.SingleGetter;
import data.functions.ElementFunctionsData;
import data.tuples.ActionWhenData;
import data.lazy.tuples.LazyElementWaitParameters;
import element.Element;
import utilities.TriFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.function.BiFunction;
import java.util.function.Function;

import static utilities.utils.*;

public class ElementAlternative {
    private final ElementFunctionsData functionData;

    public ElementAlternative(ElementFunctionsData functionData) {
        this.functionData = functionData;
    }

    public ElementAlternative() {
        this.functionData = new ElementFunctionsData();
    }

    public DriverFunction<Boolean> clickWhenCore(LazyElement data, DriverFunction<Boolean> condition) {
        return Element.actionWhenCore(new ActionWhenData<Boolean, Boolean>(condition, functionData.clickData.clickLazy.apply(data)));
    }

    public DriverFunction<Boolean> clearWhenCore(LazyElement data, DriverFunction<Boolean> condition) {
        return Element.actionWhenCore(new ActionWhenData<Boolean, Boolean>(condition, functionData.clearData.clearLazy.apply(data)));
    }

    public DriverFunction<Boolean> clickWhenCore(By locator, Function<By, DriverFunction<Boolean>> condition, SingleGetter getter) {
        return clickWhenCore(new LazyElement(locator, getter), condition.apply(locator));
    }

    public DriverFunction<Boolean> clearWhenCore(By locator, Function<By, DriverFunction<Boolean>> condition, SingleGetter getter) {
        return clearWhenCore(new LazyElement(locator, getter), condition.apply(locator));
    }

    public DriverFunction<Boolean> clickWhenCore(By locator, Function<By, DriverFunction<Boolean>> condition) {
        return Element.actionWhenCore(new ActionWhenData<Boolean, Boolean>(condition.apply(locator), functionData.clickData.clickBy.apply(locator)));
    }

    public DriverFunction<Boolean> clearWhenCore(By locator, BiFunction<By, DriverFunction<WebElement>, DriverFunction<Boolean>> condition, SingleGetter getter) {
        return Element.actionWhenCore(new ActionWhenData<Boolean, Boolean>(condition.apply(locator, Defaults.singleGetterMap.get(getter.name()).apply(getLazyLocatorList(locator))), functionData.clearData.clearByWithGetter.apply(locator, getter)));
    }

    public DriverFunction<Boolean> clearWhenCore(By locator, Function<By, DriverFunction<Boolean>> condition) {
        return Element.actionWhenCore(new ActionWhenData<Boolean, Boolean>(condition.apply(locator), functionData.clearData.clearBy.apply(locator)));
    }

    public DriverFunction<Boolean> inputWhenCore(ActionWhenData<Boolean, Boolean> data) {
        return Element.actionWhenCore(data);
    }

    public DriverFunction<Boolean> inputWhenCore(
            By locator,
            SingleGetter getter,
            String input,
            Function<LazyElement, DriverFunction<Boolean>> condition,
            BiFunction<LazyElement, String, DriverFunction<Boolean>> sender
    ) {
        final var data = new LazyElement(locator, getter);
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(condition.apply(data), sender.apply(data, input)));
    }

    public DriverFunction<Boolean> inputWhenCore(
            By locator,
            String input,
            Function<LazyElement, DriverFunction<Boolean>> condition,
            BiFunction<LazyElement, String, DriverFunction<Boolean>> sender
    ) {
        final var data = new LazyElement(locator, SingleGetter.DEFAULT);
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(condition.apply(data), sender.apply(data, input)));
    }

    public DriverFunction<Boolean> inputWhenCore(
            By locator,
            SingleGetter getter,
            String input,
            BiFunction<By, SingleGetter, DriverFunction<Boolean>> condition,
            TriFunction<By, SingleGetter, String, DriverFunction<Boolean>> sender
    ) {
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(condition.apply(locator, getter), sender.apply(locator, getter, input)));
    }

    public DriverFunction<Boolean> clickWhenCore(LazyElement data, Function<LazyElement, DriverFunction<Boolean>> condition) {
        return Element.actionWhenCore(functionData.clickData.clickLazy, condition, data);
    }

    public DriverFunction<Boolean> clearWhenCore(LazyElement data, Function<LazyElement, DriverFunction<Boolean>> condition) {
        return Element.actionWhenCore(functionData.clearData.clearLazy, condition, data);
    }

    public DriverFunction<Boolean> inputWhenCore(
            LazyElement data,
            String input,
            Function<LazyElement, DriverFunction<Boolean>> condition,
            BiFunction<LazyElement, String, DriverFunction<Boolean>> sender
    ) {
        return new DriverFunction<Boolean>(driver -> prependMessage(inputWhenCore(new ActionWhenData<Boolean, Boolean>(condition.apply(data), sender.apply(data, input))).apply(driver), Strings.ELEMENT + data.name));
    }

    public DriverFunction<Boolean> clickWhenCore(LazyElementWaitParameters data, Function<LazyElementWaitParameters, DriverFunction<Boolean>> condition) {
        return Element.actionWhenCore(functionData.clickData.clickLazy, data, condition);
    }

    public DriverFunction<Boolean> clearWhenCore(LazyElementWaitParameters data, Function<LazyElementWaitParameters, DriverFunction<Boolean>> condition) {
        return Element.actionWhenCore(functionData.clearData.clearLazy, data, condition);
    }

    public DriverFunction<Boolean> inputWhenCore(
            LazyElementWaitParameters data,
            String input,
            Function<LazyElementWaitParameters, DriverFunction<Boolean>> action,
            BiFunction<LazyElement, String, DriverFunction<Boolean>> sender
    ) {
        return new DriverFunction<Boolean>(driver -> {
            if (isNullLazyElementWaitParametersData(data)) {
                return DataDefaults.LAZY_ELEMENT_WAIT_PARAMETERS_WERE_NULL_DATA;
            }

            final var object = data.object;
            return prependMessage(Element.actionWhenCore(new ActionWhenData<Boolean, Boolean>(action.apply(data), sender.apply(object, input))).apply(driver), Strings.ELEMENT + object.name);
        });
    }


    public DriverFunction<Boolean> clickWhenPresent(By locator, SingleGetter getter) {
        return clickWhenCore(locator, Element::waitPresentLambda, getter);
    }

    public DriverFunction<Boolean> clickWhenDisplayed(By locator, SingleGetter getter) {
        return clickWhenCore(locator, Element::waitDisplayedLambda, getter);
    }

    public DriverFunction<Boolean> clickWhenEnabled(By locator, SingleGetter getter) {
        return clickWhenCore(locator, Element::waitEnabledLambda, getter);
    }

    public DriverFunction<Boolean> clickWhenSelected(By locator, SingleGetter getter) {
        return clickWhenCore(locator, Element::waitSelectedLambda, getter);
    }

    public DriverFunction<Boolean> clickWhenClickable(By locator, SingleGetter getter) {
        return clickWhenCore(locator, Element::waitClickableLambda, getter);
    }

    public DriverFunction<Boolean> clickWhenPresent(By locator) {
        return clickWhenCore(locator, Element::waitPresentLambda);
    }

    public DriverFunction<Boolean> clickWhenDisplayed(By locator) {
        return clickWhenCore(locator, Element::waitDisplayedLambda);
    }

    public DriverFunction<Boolean> clickWhenEnabled(By locator) {
        return clickWhenCore(locator, Element::waitEnabledLambda);
    }

    public DriverFunction<Boolean> clickWhenSelected(By locator) {
        return clickWhenCore(locator, Element::waitSelectedLambda);
    }

    public DriverFunction<Boolean> clickWhenClickable(By locator) {
        return clickWhenCore(locator, Element::waitClickableLambda);
    }

    public DriverFunction<Boolean> clearWhenPresent(By locator, SingleGetter getter) {
        return clearWhenCore(locator, Element::waitPresentLambda, getter);
    }

    public DriverFunction<Boolean> clearWhenDisplayed(By locator, SingleGetter getter) {
        return clearWhenCore(locator, Element::waitDisplayedLambda, getter);
    }

    public DriverFunction<Boolean> clearWhenEnabled(By locator, SingleGetter getter) {
        return clearWhenCore(locator, Element::waitEnabledLambda, getter);
    }

    public DriverFunction<Boolean> clearWhenSelected(By locator, SingleGetter getter) {
        return clearWhenCore(locator, Element::waitSelectedLambda, getter);
    }

    public DriverFunction<Boolean> clearWhenClickable(By locator, SingleGetter getter) {
        return clearWhenCore(locator, Element::waitClickableLambda, getter);
    }

    public DriverFunction<Boolean> clearWhenPresent(By locator) {
        return clearWhenCore(locator, Element::waitPresentLambda);
    }

    public DriverFunction<Boolean> clearWhenDisplayed(By locator) {
        return clearWhenCore(locator, Element::waitDisplayedLambda);
    }

    public DriverFunction<Boolean> clearWhenEnabled(By locator) {
        return clearWhenCore(locator, Element::waitEnabledLambda);
    }

    public DriverFunction<Boolean> clearWhenSelected(By locator) {
        return clearWhenCore(locator, Element::waitSelectedLambda);
    }

    public DriverFunction<Boolean> clearWhenClickable(By locator) {
        return clearWhenCore(locator, Element::waitClickableLambda);
    }

    public DriverFunction<Boolean> inputWhenPresent(By locator, String input, SingleGetter getter) {
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(clickWhenPresent(locator, getter), functionData.sendKeysData.sendKeysByWithGetter.apply(locator, input, getter)));
    }

    public DriverFunction<Boolean> inputWhenDisplayed(By locator, String input, SingleGetter getter) {
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(clickWhenDisplayed(locator, getter), functionData.sendKeysData.sendKeysByWithGetter.apply(locator, input, getter)));
    }

    public DriverFunction<Boolean> inputWhenEnabled(By locator, String input, SingleGetter getter) {
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(clickWhenEnabled(locator, getter), functionData.sendKeysData.sendKeysByWithGetter.apply(locator, input, getter)));
    }

    public DriverFunction<Boolean> inputWhenSelected(By locator, String input, SingleGetter getter) {
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(clickWhenSelected(locator, getter), functionData.sendKeysData.sendKeysByWithGetter.apply(locator, input, getter)));
    }

    public DriverFunction<Boolean> inputWhenClickable(By locator, String input, SingleGetter getter) {
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(clickWhenClickable(locator, getter), functionData.sendKeysData.sendKeysByWithGetter.apply(locator, input, getter)));
    }

    public DriverFunction<Boolean> inputWhenPresent(By locator, String input) {
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(clickWhenPresent(locator), functionData.sendKeysData.sendKeysBy.apply(locator, input)));
    }

    public DriverFunction<Boolean> inputWhenDisplayed(By locator, String input) {
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(clickWhenDisplayed(locator), functionData.sendKeysData.sendKeysBy.apply(locator, input)));
    }

    public DriverFunction<Boolean> inputWhenEnabled(By locator, String input) {
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(clickWhenEnabled(locator), functionData.sendKeysData.sendKeysBy.apply(locator, input)));
    }

    public DriverFunction<Boolean> inputWhenSelected(By locator, String input) {
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(clickWhenSelected(locator), functionData.sendKeysData.sendKeysBy.apply(locator, input)));
    }

    public DriverFunction<Boolean> inputWhenClickable(By locator, String input) {
        return inputWhenCore(new ActionWhenData<Boolean, Boolean>(clickWhenClickable(locator), functionData.sendKeysData.sendKeysBy.apply(locator, input)));
    }

    public DriverFunction<Boolean> clickWhenPresent(LazyElement data) {
        return clickWhenCore(data, Element::waitPresentLambda);
    }

    public DriverFunction<Boolean> clickWhenDisplayed(LazyElement data) {
        return clickWhenCore(data, Element::waitDisplayedLambda);
    }

    public DriverFunction<Boolean> clickWhenEnabled(LazyElement data) {
        return clickWhenCore(data, Element::waitEnabledLambda);
    }

    public DriverFunction<Boolean> clickWhenSelected(LazyElement data) {
        return clickWhenCore(data, Element::waitSelectedLambda);
    }

    public DriverFunction<Boolean> clickWhenClickable(LazyElement data) {
        return clickWhenCore(data, Element::waitClickableLambda);
    }

    public DriverFunction<Boolean> inputWhenPresent(LazyElement data, String input) {
        return inputWhenCore(data, input, this::clickWhenPresent, functionData.sendKeysData.sendKeysLazy);
    }

    public DriverFunction<Boolean> inputWhenDisplayed(LazyElement data, String input) {
        return inputWhenCore(data, input, this::clickWhenDisplayed, functionData.sendKeysData.sendKeysLazy);
    }

    public DriverFunction<Boolean> inputWhenEnabled(LazyElement data, String input) {
        return inputWhenCore(data, input, this::clickWhenEnabled, functionData.sendKeysData.sendKeysLazy);
    }

    public DriverFunction<Boolean> inputWhenSelected(LazyElement data, String input) {
        return inputWhenCore(data, input, this::clickWhenSelected, functionData.sendKeysData.sendKeysLazy);
    }

    public DriverFunction<Boolean> inputWhenClickable(LazyElement data, String input) {
        return inputWhenCore(data, input, this::clickWhenClickable, functionData.sendKeysData.sendKeysLazy);
    }

    public DriverFunction<Boolean> clearWhenPresent(LazyElement data) {
        return clearWhenCore(data, Element::waitPresentLambda);
    }

    public DriverFunction<Boolean> clearWhenDisplayed(LazyElement data) {
        return clearWhenCore(data, Element::waitDisplayedLambda);
    }

    public DriverFunction<Boolean> clearWhenEnabled(LazyElement data) {
        return clearWhenCore(data, Element::waitEnabledLambda);
    }

    public DriverFunction<Boolean> clearWhenSelected(LazyElement data) {
        return clearWhenCore(data, Element::waitSelectedLambda);
    }

    public DriverFunction<Boolean> clearWhenClickable(LazyElement data) {
        return clearWhenCore(data, Element::waitClickableLambda);
    }

    public DriverFunction<Boolean> clickWhenPresent(LazyElementWaitParameters data) {
        return clickWhenCore(data, Element::waitPresentLambda);
    }

    public DriverFunction<Boolean> clickWhenDisplayed(LazyElementWaitParameters data) {
        return clickWhenCore(data, Element::waitDisplayedLambda);
    }

    public DriverFunction<Boolean> clickWhenEnabled(LazyElementWaitParameters data) {
        return clickWhenCore(data, Element::waitEnabledLambda);
    }

    public DriverFunction<Boolean> clickWhenSelected(LazyElementWaitParameters data) {
        return clickWhenCore(data, Element::waitSelectedLambda);
    }

    public DriverFunction<Boolean> clickWhenClickable(LazyElementWaitParameters data) {
        return clickWhenCore(data, Element::waitClickableLambda);
    }

    public DriverFunction<Boolean> inputWhenPresent(LazyElementWaitParameters data, String input) {
        return inputWhenCore(data, input, this::clickWhenPresent, functionData.sendKeysData.sendKeysLazy);
    }

    public DriverFunction<Boolean> inputWhenDisplayed(LazyElementWaitParameters data, String input) {
        return inputWhenCore(data, input, this::clickWhenDisplayed, functionData.sendKeysData.sendKeysLazy);
    }

    public DriverFunction<Boolean> inputWhenEnabled(LazyElementWaitParameters data, String input) {
        return inputWhenCore(data, input, this::clickWhenEnabled, functionData.sendKeysData.sendKeysLazy);
    }

    public DriverFunction<Boolean> inputWhenSelected(LazyElementWaitParameters data, String input) {
        return inputWhenCore(data, input, this::clickWhenSelected, functionData.sendKeysData.sendKeysLazy);
    }

    public DriverFunction<Boolean> inputWhenClickable(LazyElementWaitParameters data, String input) {
        return inputWhenCore(data, input, this::clickWhenClickable, functionData.sendKeysData.sendKeysLazy);
    }

    public DriverFunction<Boolean> clearWhenPresent(LazyElementWaitParameters data) {
        return clearWhenCore(data, Element::waitPresentLambda);
    }

    public DriverFunction<Boolean> clearWhenDisplayed(LazyElementWaitParameters data) {
        return clearWhenCore(data, Element::waitDisplayedLambda);
    }

    public DriverFunction<Boolean> clearWhenEnabled(LazyElementWaitParameters data) {
        return clearWhenCore(data, Element::waitEnabledLambda);
    }

    public DriverFunction<Boolean> clearWhenSelected(LazyElementWaitParameters data) {
        return clearWhenCore(data, Element::waitSelectedLambda);
    }

    public DriverFunction<Boolean> clearWhenClickable(LazyElementWaitParameters data) {
        return clearWhenCore(data, Element::waitClickableLambda);
    }
}
