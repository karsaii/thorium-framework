package expectedConditions;
import data.constants.FormatterStrings;
import data.constants.Strings;
import data.Data;
import data.DriverFunction;
import data.enums.SingleGetter;
import data.LazyElement;
import data.constants.DataDefaults;
import drivers.Driver;
import utilities.utils;
import formatter.Formatter;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static data.ExecutionCore.ifDriver;
import static utilities.utils.*;

public class EC {
    public static final DriverFunction<String> GET_TITLE = Driver.getTitle();

    private static <T, V> DriverFunction<T> isCoreData(Data<T> data, T expected, BiFunction<T, T, V> checker) {
        final var localData = new Data<>(expected, false, "Driver was null.");
        if (isNullOrFalseDataOrDataObject(data) || areAnyNull(expected, checker)) {
            return new DriverFunction<T>(localData);
        }

        return new DriverFunction<T>(driver -> {
            if (isNull(driver)) {
                return localData;
            }

            final var object = data.object;
            var status = data.status;
            final var result = status ? object : expected;
            status = status && (Boolean) checker.apply(object, expected);
            return new Data<T>(result, status, "Checker with value(\"" + result + "\") against expected(\"" + expected + "\") was " + status + ".");
        });
    }

    private static <T, V> DriverFunction<T> isCore(DriverFunction<T> getter, T expected, BiFunction<T, T, V> checker) {
        return new DriverFunction<T>(driver -> (
            areNotNull(driver, expected, getter, checker)
        ) ? isCoreData(getter.apply(driver), expected, checker).apply(driver)
          : new Data<T>(expected, false, "Parameter issue(s)."));
    }

    public static <T, V> DriverFunction<T> is(Data<T> data, T expected, BiFunction<T, T, V> checker) {
        return ifLambda(
            isNotNullOrFalseDataOrDataObject(data) && areNotNull(expected, checker),
            isCoreData(data, expected, checker),
            new Data<T>(expected, false, "expected guard"),
            "Is: "
        );
    }

    public static <T, V> DriverFunction<T> is(DriverFunction<T> getter, T expected, BiFunction<T, T, V> checker) {
        return ifLambda(
            areNotNull(expected, getter, checker),
            isCore(getter, expected, checker),
            new Data<T>(expected, false, "expected guard"),
            "Is: "
        );
    }

    public static DriverFunction<Boolean> isValuesData(String descriptor, String expected, Data<String> data, BiFunction<String, String, Boolean> checker, String conditionDescriptor) {
        return ifDriver(
            "isValuesData",
            isNotNullOrFalseDataOrDataObject(data) &&
            areNotNull(expected, checker) &&
            areNotBlank(descriptor, conditionDescriptor),
            new DriverFunction<Boolean>(driver -> {
                final var result = is(data, expected, checker).apply(driver);
                final var status = result.status;
                final var messageData = Formatter.getIsValuesMessage(FormatterStrings.isMessageMap, result, expected, status, descriptor, conditionDescriptor);
                return DataDefaults.getSimpleBooleanData(status, messageData.message.getMessage());
            }),
            replaceMessage(DataDefaults.NULL_BOOLEAN_DATA, "Expected was: \"" + expected +"\" and actual was: \"" + data.message + Strings.END_LINE)
        );
    }

    public static DriverFunction<Boolean> isValuesData(String descriptor, String expected, DriverFunction<String> getter, BiFunction<String, String, Boolean> checker, String conditionDescriptor) {
        return ifDriver(
            "isValuesData",
            areNotNull(expected, getter, checker) && areNotBlank(descriptor, conditionDescriptor),
            new DriverFunction<Boolean>(driver -> {
                final var result = is(getter, expected, checker).apply(driver);
                final var status = result.status;
                final var messageData = Formatter.getIsValuesMessage(FormatterStrings.isMessageMap, result, expected, status, descriptor, conditionDescriptor);
                return DataDefaults.getSimpleBooleanData(status, messageData.message.getMessage());
            }),
            replaceMessage(DataDefaults.NULL_BOOLEAN_DATA, "isValuesData: ", "Expected was: \"" + expected +"\" and actual was: \"" + getter + Strings.END_LINE)
        );
    }

    public static DriverFunction<Boolean> isValuesEqualData(String descriptor, String expected, DriverFunction<String> getter) {
        return isValuesData(descriptor, expected, getter, Objects::equals, "equal");
    }

    public static DriverFunction<Boolean> isStringContainsExpectedData(String descriptor, String expected, DriverFunction<String> getter) {
        return isValuesData(descriptor, expected, getter, StringUtils::contains, "contain");
    }

    public static DriverFunction<Boolean> isValuesNotEqualData(String descriptor, String expected, DriverFunction<String> getter) {
        return isValuesData(descriptor, expected, getter, utils::isNotEqual, "unequal");
    }

    public static DriverFunction<Boolean> isStringNotContainsExpectedData(String descriptor, String expected, DriverFunction<String> getter) {
        return isValuesData(descriptor, expected, getter, utils::Uncontains, "does not contain");
    }

    public static DriverFunction<Boolean> isValuesEqualData(String descriptor, String expected, Data<String> data) {
        return isValuesData(descriptor, expected, data, Objects::equals, "equal");
    }

    public static DriverFunction<Boolean> isStringContainsExpectedData(String descriptor, String expected, Data<String> data) {
        return isValuesData(descriptor, expected, data, StringUtils::contains, "contain");
    }

    public static DriverFunction<Boolean> isValuesNotEqualData(String descriptor, String expected, Data<String> data) {
        return isValuesData(descriptor, expected, data, utils::isNotEqual, "unequal");
    }

    public static DriverFunction<Boolean> isStringNotContainsExpectedData(String descriptor, String expected, Data<String> data) {
        return isValuesData(descriptor, expected, data, utils::Uncontains, "does not contain");
    }

    public static DriverFunction<Boolean> isTitleData(String expected, BiFunction<String, String, Boolean> checker, String conditionDescriptor) {
        return isValuesData(Strings.TITLE_OF_WINDOW, expected, GET_TITLE, checker, conditionDescriptor);
    }

    public static DriverFunction<Boolean> isUrlData(String expected, BiFunction<String, String, Boolean> checker, String conditionDescriptor) {
        return isValuesData("Current url ", expected, Driver.getUrl(), checker, conditionDescriptor);
    }

    public static DriverFunction<Boolean> isTitleEqualsData(String expected) {
        return isValuesEqualData(Strings.TITLE_OF_WINDOW, expected, GET_TITLE);
    }

    public static DriverFunction<Boolean> isTitleContainsData(String expected) {
        return isStringContainsExpectedData(Strings.TITLE_OF_WINDOW, expected, GET_TITLE);
    }

    public static DriverFunction<Boolean> isUrlEqualsData(String expected) {
        return isUrlData(expected, StringUtils::equals, "equal");
    }

    public static DriverFunction<Boolean> isUrlContainsData(String expected) {
        return isUrlData(expected, StringUtils::contains, "contain");
    }

    public static DriverFunction<Boolean> isUrlEqualsIgnoreCaseData(String expected) {
        return isUrlData(expected, StringUtils::equalsIgnoreCase, "case insensitive equal");
    }

    public static DriverFunction<Boolean> isUrlContainsIgnoreCaseData(String expected) {
        return isUrlData(expected, StringUtils::containsIgnoreCase, "case insensitive contain");
    }

    public static DriverFunction<Boolean> isUrlMatchesData(String pattern) {
        return isUrlData(pattern, utils::isStringMatchesPattern, "match regex");
    }

    public static DriverFunction<Boolean> isElementPresent(LazyElement data) {
        return Driver.isElementPresent(data);
    }
    public static DriverFunction<Boolean> isElementDisplayed(LazyElement data) {
        return Driver.isElementDisplayed(data);
    }
    public static DriverFunction<Boolean> isElementEnabled(LazyElement data) {
        return Driver.isElementEnabled(data);
    }
    public static DriverFunction<Boolean> isElementClickable(LazyElement data) {
        return Driver.isElementClickable(data);
    }
    public static DriverFunction<Boolean> isElementSelected(LazyElement data) {
        return Driver.isElementSelected(data);
    }
    public static DriverFunction<Boolean> isElementAbsent(LazyElement data) {
        return Driver.isElementAbsent(data);
    }
    public static DriverFunction<Boolean> isElementHidden(LazyElement data) {
        return Driver.isElementHidden(data);
    }
    public static DriverFunction<Boolean> isElementDisabled(LazyElement data) {
        return Driver.isElementDisabled(data);
    }
    public static DriverFunction<Boolean> isElementUnclickable(LazyElement data) {
        return Driver.isElementUnclickable(data);
    }
    public static DriverFunction<Boolean> isElementUnselected(LazyElement data) {
        return Driver.isElementUnselected(data);
    }
    public static DriverFunction<String> getElementText(LazyElement data) {
        return Driver.getElementText(data);
    }
    public static DriverFunction<String> getElementTagName(LazyElement data) {
        return Driver.getElementTagName(data);
    }
    public static DriverFunction<String> getElementAttributeValue(LazyElement data) {
        return Driver.getElementAttributeValue(data);
    }

    public static DriverFunction<Boolean> isElementPresentData(By locator) {
        return isElementPresent(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<Boolean> isElementDisplayedData(By locator) {
        return isElementDisplayed(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<Boolean> isElementEnabledData(By locator) {
        return isElementEnabled(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<Boolean> isElementClickableData(By locator) {
        return isElementClickable(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<Boolean> isElementSelectedData(By locator) {
        return isElementSelected(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<Boolean> isElementAbsentData(By locator) {
        return isElementAbsent(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<Boolean> isElementHiddenData(By locator) {
        return isElementHidden(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<Boolean> isElementDisabledData(By locator) {
        return isElementDisabled(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<Boolean> isElementUnclickableData(By locator) {
        return isElementUnclickable(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<Boolean> isElementUnselectedData(By locator) {
        return isElementUnselected(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<String> getElementTextData(By locator) {
        return getElementText(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<String> getElementTagNameData(By locator) {
        return getElementTagName(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<String> getElementAttributeValueData(By locator) {
        return getElementAttributeValue(new LazyElement(locator, SingleGetter.DEFAULT));
    }
    public static DriverFunction<Boolean> isElementPresentData(String cssSelector) {
        return isElementPresentData(By.cssSelector(cssSelector));
    }
    public static DriverFunction<Boolean> isElementDisplayedData(String cssSelector) {
        return isElementDisplayedData(By.cssSelector(cssSelector));
    }
    public static DriverFunction<Boolean> isElementEnabledData(String cssSelector) {
        return isElementEnabledData(By.cssSelector(cssSelector));
    }
    public static DriverFunction<Boolean> isElementClickableData(String cssSelector) {
        return isElementClickableData(By.cssSelector(cssSelector));
    }
    public static DriverFunction<Boolean> isElementAbsentData(String cssSelector) {
        return isElementAbsentData(By.cssSelector(cssSelector));
    }
    public static DriverFunction<Boolean> isElementHiddenData(String cssSelector) {
        return isElementHiddenData(By.cssSelector(cssSelector));
    }
    public static DriverFunction<Boolean> isElementDisabledData(String cssSelector) {
        return isElementDisabledData(By.cssSelector(cssSelector));
    }
    public static DriverFunction<Boolean> isElementUnclickableData(String cssSelector) {
        return isElementUnclickableData(By.cssSelector(cssSelector));
    }
    public static DriverFunction<String> getElementTextData(String cssSelector) {
        return getElementTextData(By.cssSelector(cssSelector));
    }
    public static DriverFunction<String> getElementTagNameData(String cssSelector) {
        return getElementTagNameData(By.cssSelector(cssSelector));
    }
    public static DriverFunction<String> getElementAttributeValueData(String cssSelector) {
        return getElementAttributeValueData(By.cssSelector(cssSelector));
    }

    public static Function<WebDriver, Boolean> isTitleEquals(String expected) {
        return driver -> isTitleEqualsData(expected).apply(driver).status;
    }

    public static Function<WebDriver, Boolean> isTitleContains(String expected) {
        return driver -> isTitleContainsData(expected).apply(driver).status;
    }

    public static DriverFunction<Boolean> isElementTextEqualData(LazyElement data, String expected) {
        return isValuesEqualData("Element text", expected, Driver.getElementText(data));
    }

    public static DriverFunction<Boolean> isElementTextContainsData(LazyElement data, String expected) {
        return isStringContainsExpectedData("Element text", expected, Driver.getElementText(data));
    }

    public static DriverFunction<Boolean> isElementAttributeValueTextEqualData(LazyElement data, String expected) {
        return isValuesEqualData("Element attribute value", expected, Driver.getElementAttributeValue(data));
    }

    public static DriverFunction<Boolean> isElementAttributeValueContainsData(LazyElement data, String expected) {
        return isStringContainsExpectedData("Element attribute value", expected, Driver.getElementAttributeValue(data));
    }

    public static DriverFunction<Boolean> isElementAttributeValueNotContainsData(LazyElement data, String expected) {
        return isStringContainsExpectedData("Element text", expected, Driver.getElementAttributeValue(data));
    }

    public static DriverFunction<Boolean> isNumberOfWindowsEqualTo(int expected) {
        return ifLambda(
            expected > 0,
            new DriverFunction<Boolean>(driver -> {
                if (isNull(driver)) {
                    return DataDefaults.NULL_BOOLEAN_DATA;
                }

                final var data = Driver.getWindowHandleAmount().apply(driver);
                if (isNullOrFalseDataOrDataObject(data)) {
                    return DataDefaults.NULL_BOOLEAN_DATA;
                }

                final var count = data.object;
                final var status = count == expected;
                return DataDefaults.getSimpleBooleanData(status, Formatter.getNumberOfWindowsEqualToMessage(status, expected, count));
            }),
            DataDefaults.NULL_BOOLEAN_DATA
        );
    }
}
