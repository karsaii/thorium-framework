package drivers;

import data.constants.*;
import data.Data;
import data.enums.SingleGetter;
import data.lazy.Adjuster;
import data.lazy.SelectorKeySpecificityData;
import data.lazy.tuples.LazyElementWithOptionsData;
import data.lazy.tuples.LazyIndexedElementParameters;
import data.repositories.ElementRepository;
import data.repositories.MethodRepository;
import data.DriverFunction;
import data.tuples.*;
import data.extensions.DecoratedList;
import data.functions.Executor;
import data.LazyElement;
import data.LazyLocator;
import data.LazyLocatorList;
import data.WebElementList;
import data.StringSet;
import formatter.Formatter;
import javascriptCommands.Execute;
import utilities.Cardinalities;
import utilities.TriFunction;
import utilities.utils;
import validators.ElementParameters;
import validators.Tuples;
import validators.TypeMethod;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver.TargetLocator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.*;

import static data.ExecutionCore.*;
import static utilities.utils.*;
import static org.apache.commons.lang3.StringUtils.*;

public interface Driver {
    static JavascriptExecutor getExecutor(WebDriver driver) {
        return (JavascriptExecutor)driver;
    }

    static TakesScreenshot getScreenshotter(WebDriver driver) {
        return (TakesScreenshot)driver;
    }

    static TargetLocator getTargetLocator(WebDriver driver) {
        return driver.switchTo();
    }

    private static <T> Data<T> getCoreTyped(InputData<T> data, String nameof) {
        final var name = isNotBlank(nameof) ? nameof : "getCoreTyped: ";
        final var errorMessage = Formatter.getMessageIfErrorMessage(name, Tuples.validateInputData(data));

        var object = data.defaultValue;
        if (isNotBlank(errorMessage)) {
            new Data<T>(object, false, name, "Null T value" + Strings.END_LINE);
        }

        var exception = Defaults.EXCEPTION;
        var value = data.object;
        try {
            object = data.clazz.cast(value);
        } catch (Exception ex) {
            exception = ex;
        }

        final var status = isNonException(exception);
        final var message = (status ? "Value is: \"" + value + "\"" : "Null T value") + Strings.END_LINE;
        final var messageData = new MethodMessageData(name, message);
        return status ? (
            new Data<T>(object, status, messageData)
        ) : new Data<T>(object, status, messageData, exception);
    }

    private static Data<String> getTitle(String value) {
        return getCoreTyped(new InputData<>(value, String.class, Strings.EMPTY), "getTitle");
    }

    private static Data<String> getUrl(String value) {
        return getCoreTyped(new InputData<>(value, String.class, Strings.EMPTY), "getCurrentUrl");
    }

    static Data<Integer> getCountOfElements(Data<WebElementList> data) {
        return isNotNullOrFalseDataOrDataObject(data) ? new Data<Integer>(data.object.size(), true, data.message) : DataDefaults.NULL_INTEGER_DATA;
    }


    static DriverFunction<String> getTitle() {
        return ifDriverGuardObject("getTitle", WebDriver::getTitle, Driver::getTitle, Strings.EMPTY);
    }

    static DriverFunction<String> getUrl() {
        return ifDriverGuardObject("getUrl", WebDriver::getCurrentUrl, Driver::getUrl, Strings.EMPTY);
    }

    static Data<Object> executeCore(Function<String, Object> executor, String script) {
        if (isNull(executor) || isBlank(script)) {
            return DataDefaults.WONT_EXECUTE_DATA;
        }

        var exception = Defaults.EXCEPTION;
        var object = DataDefaults.STOCK_OBJECT;
        try {
            object = executor.apply(script);
        } catch (NullPointerException ex) {
            exception = ex;
        }

        final var status = isNonException(exception);
        return status ? (
            new Data<>(object, status, Formatter.getStringExecutionMessage(status))
        ) : new Data<>(object, status, Formatter.getStringExecutionMessage(status), exception);
    }

    static Data<Object> executeParametersCore(BiFunction<String, Object[], Object> executor, String script, Object[] parameters) {
        if (areNull(executor, parameters) || isBlank(script) || (parameters.length < 1)) {
            return DataDefaults.WONT_EXECUTE_DATA;
        }

        var exception = Defaults.EXCEPTION;
        var object = DataDefaults.STOCK_OBJECT;
        try {
            object = executor.apply(script, parameters);
        } catch (NullPointerException ex) {
            exception = ex;
        }

        final var status = isNonException(exception);
        return status ? (
            new Data<>(object, status, Formatter.getStringExecutionMessage(status))
        ) : new Data<>(object, status, Formatter.getStringExecutionMessage(status), exception);
    }

    static Data<Object> execute(Function<String, Object> executor, String script) {
        return executeCore(executor, script);
    }

    static Data<Object> executeData(JavascriptExecutor executor, String script) {
        return execute(executor::executeScript, script);
    }

    static Data<Object> executeAsyncData(JavascriptExecutor executor, String script) {
        return execute(executor::executeAsyncScript, script);
    }

    static Function<JavascriptExecutor, Data<Object>> executeData(String script) {
        return executor -> Driver.executeData(executor, script);
    }

    static Function<JavascriptExecutor, Data<Object>> executeAsyncData(String script) {
        return executor -> Driver.executeAsyncData(executor, script);
    }

    static Data<Object> executeParameters(BiFunction<String, Object[], Object> executor, String script, Object[] parameters) {
        return executeParametersCore(executor, script, parameters);
    }

    static Data<Object> executeParameters(JavascriptExecutor executor, String script, Object[] parameters) {
        return executeParameters(executor::executeScript, script, parameters);
    }

    static Data<Object> executeParametersAsync(JavascriptExecutor executor, String script, Object[] parameters) {
        return executeParameters(executor::executeAsyncScript, script, parameters);
    }

    static Function<JavascriptExecutor, Data<Object>> executeParametersData(String script, Object[] parameters) {
        return execute -> Driver.executeParameters(execute, script, parameters);
    }

    static Function<JavascriptExecutor, Data<Object>> executeParametersAsyncData(String script, Object[] parameters) {
        return execute -> Driver.executeParametersAsync(execute, script, parameters);
    }

    static DriverFunction<Object> executeAsync(String script) {
        return ifDriver("executeAsync", isNotBlank(script), Driver::getExecutor, executeAsyncData(script), DataDefaults.NULL_EXECUTE_OBJECT_DATA);
    }

    static DriverFunction<Object> execute(String script) {
        return ifDriver("execute", isNotBlank(script), Driver::getExecutor, executeData(script), DataDefaults.NULL_EXECUTE_OBJECT_DATA);
    }

    static DriverFunction<Object> executeParameters(String script, Object[] parameters) {
        return ifDriver("executeParameters",isNotBlank(script), Driver::getExecutor, executeParametersData(script, parameters), DataDefaults.NULL_EXECUTE_OBJECT_DATA);
    }

    static DriverFunction<Object> executeParametersAsync(String script, Object[] parameters) {
        return ifDriver("executeParametersAsync", isNotBlank(script), Driver::getExecutor, executeParametersAsyncData(script, parameters), DataDefaults.NULL_EXECUTE_OBJECT_DATA);
    }

    static <T extends Data> DriverFunction<Object> executeParameters(String script, T elements, Function<T, Boolean> guard) {
        return ifDriverExecute(
            "executeParameters",
            areNotNull(guard) && isNotBlank(script) && guard.apply(elements),
            Driver::getExecutor,
            executeParametersData(script, ArrayUtils.toArray(elements.object))
        );
    }

    static <T extends Data> DriverFunction<Object> executeParametersAsync(String script, T elements, Function<T, Boolean> guard) {
        return ifDriverExecute(
            "executeParametersAsync",
            areNotNull(guard) && isNotBlank(script) && guard.apply(elements),
            Driver::getExecutor,
            executeParametersAsyncData(script, ArrayUtils.toArray(elements.object))
        );
    }

    static <T extends Data> DriverFunction<Object> executeParameters(TriFunction<String, T, Function<T, Boolean>, DriverFunction<Object>> executor, String script, T object, Function<T, Boolean> guard) {
        return ifDriverExecute("executeParameters", areNotNull(executor, guard, object) && isNotBlank(script) && guard.apply(object), executor.apply(script, object, guard));
    }

    static <T extends Data> DriverFunction<Object> executeData(String script, T elements, Function<T, Boolean> guard) {
        return executeParameters(Driver::executeParameters, script, elements, guard);
    }

    static <T extends Data> DriverFunction<Object> executeAsyncData(String script, T elements, Function<T, Boolean> guard) {
        return executeParameters(Driver::executeParametersAsync, script, elements, guard);
    }

    static DriverFunction<Object> executeSingleParameter(String script, Data<WebElement> data) {
        return executeParameters(Driver::executeData, script, data, utils::isNotNullOrFalseDataOrDataObject);
    }

    static DriverFunction<Object> executeAsyncSingleParameterData(String script, Data<WebElement> data) {
        return executeParameters(Driver::executeAsyncData, script, data, utils::isNotNullOrFalseDataOrDataObject);
    }

    static <T extends Data> Function<T, DriverFunction<Object>> executeSingleParameter(String script) {
        return data -> executeParameters(Driver::executeData, script, data, utils::isNotNullOrFalseDataOrDataObject);
    }

    static <T extends Data> Function<T, DriverFunction<Object>> executeAsyncSingleParameterData(String script) {
        return data -> executeParameters(Driver::executeAsyncData, script, data, utils::isNotNullOrFalseDataOrDataObject);
    }

    static <T, V> Data<T> invokeMethod(Data<WebElement> element, HashMap<String, Method> methodMap, Class<T> returnType, BiPredicate<Method, String> condition, String methodName, V methodParam, T defaultValue) {
        final var nameof = "invokeMethod: ";
        final var methodData = MethodRepository.getMethod(methodMap, DataDefaults.WEB_ELEMENT_METHOD_LIST, condition, methodName);
        if (isNullOrFalseDataOrDataObject(methodData) || isNullOrFalseDataOrDataObject(element)) {
            return new Data<T>(defaultValue, false, nameof, methodData.message.toString() + element.message.toString());
        }

        final var base = element.object;
        final var method = methodData.object;
        final var hasParameter = isNotNull(methodParam);
        var object = defaultValue;
        var exception = Defaults.EXCEPTION;
        try {
            object = returnType.cast(hasParameter ? method.invoke(base, methodParam) : method.invoke(base));
        } catch (
            IllegalArgumentException |
            NoSuchElementException |
            StaleElementReferenceException |
            IllegalAccessException |
            InvocationTargetException ex
        ) {
            exception = ex;
        }

        final var parameterMessage = (hasParameter ? "Parameter was specified: " + methodParam.toString() : "Parameter wasn't specified") + Strings.END_LINE;
        final var message = Formatter.getInvokeMethodCommonMessage(exception, methodData.message.toString(), returnType.toGenericString(), Arrays.asList(method.getParameterTypes()).toString());
        return isNonException(exception) ? (
            new Data<T>(object, true, nameof, message)
        ) : new Data<T>(object, false, nameof, message + parameterMessage, exception);
    }

    private static <T, V> Function<Data<WebElement>, Data<T>> invokeMethod(HashMap<String, Method> methodMap, Class<T> returnType, BiPredicate<Method, String> condition, String methodName, V methodParam, T defaultValue) {
        return data -> invokeMethod(data, methodMap, returnType, condition, methodName, methodParam, defaultValue);
    }

    private static <T, V> DriverFunction<T> invokeMethod(DriverFunction<WebElement> getter, HashMap<String, Method> methodMap, Class<T> returnType, BiPredicate<Method, String> condition, String methodName, V methodParam, T defaultValue) {
        final var nameof = "invokeMethod: ";
        final var methodData = MethodRepository.getMethod(methodMap, DataDefaults.WEB_ELEMENT_METHOD_LIST, condition, methodName);
        return ifDriverWebElement(
            nameof,
            isNotNullOrFalseDataOrDataObject(methodData) && isNotNull(getter),
            getter,
            invokeMethod(methodMap, returnType, condition, methodName, methodParam, defaultValue),
            new Data<T>(defaultValue, false, nameof, methodData.message.toString())
        );
    }

    private static <T, V> Data<T> invokeMethod(Data<WebElement> data, Class<T> returnType, BiPredicate<Method, String> condition, String methodName, V methodParam, T defaultValue) {
        return invokeMethod(data, Defaults.methods, returnType, condition, methodName, methodParam, defaultValue);
    }

    private static <T, V> Function<Data<WebElement>, Data<T>> invokeMethod(Class<T> returnType, BiPredicate<Method, String> condition, String methodName, V methodParam, T defaultValue) {
        return data -> invokeMethod(data, returnType, condition, methodName, methodParam, defaultValue);
    }

    private static DriverFunction<WebElement> invokeWebElementMethod(DriverFunction<WebElement> data, String methodName, By locator) {
        return invokeMethod(data, Defaults.methods, WebElement.class, TypeMethod::isWebElementMethod, methodName, locator, DataDefaults.STOCK_ELEMENT);
    }

    static <T, V> DriverFunction<T> invokeMethod(DriverFunction<WebElement> element, Class<T> returnType, BiPredicate<Method, String> condition, String methodName, V methodParam, T defaultValue) {
        final var nameof = "invokeMethod";
        return ifDriver(
            nameof,
            Formatter.getMessageIfErrorMessage(nameof, ElementParameters.validateElementMethodParameters(element, returnType, condition, methodName)),
            utils::isNotNullOrFalseDataOrDataObject,
            element,
            invokeMethod(returnType, condition, methodName, methodParam, defaultValue),
            new Data<T>(defaultValue, false, nameof, "Data was null, whatever" + Strings.END_LINE)
        );
    }

    static <T, V> DriverFunction<T> invokeMethod(LazyElement element, Class<T> returnType, BiPredicate<Method, String> condition, String methodName, V methodParam, T defaultValue) {
        return invokeMethod(element.get(), returnType, condition, methodName, methodParam, defaultValue);
    }

    static DriverFunction<String> invokeStringMethod(LazyElement element, String methodName) {
        return invokeMethod(element, String.class, TypeMethod::isStringMethod, methodName, null, "");
    }

    static <T> DriverFunction<String> invokeStringMethod(LazyElement element, String methodName, T parameter) {
        return invokeMethod(element, String.class, TypeMethod::isStringMethod, methodName, parameter, "");
    }

    static DriverFunction<Boolean> invokeBooleanMethod(LazyElement element, String methodName) {
        return invokeMethod(element, Boolean.class, TypeMethod::isBooleanMethod, methodName, null, false);
    }

    static <T> DriverFunction<Void> invokeVoidMethod(DriverFunction<WebElement> element, String methodName, T parameter) {
        return invokeMethod(element, void.class, TypeMethod::isVoidMethod, methodName, parameter, null);
    }

    static DriverFunction<Void> invokeVoidMethod(DriverFunction<WebElement> element, String methodName) {
        return invokeMethod(element, void.class, TypeMethod::isVoidMethod, methodName, null, null);
    }

    static <T> DriverFunction<Void> invokeVoidMethod(LazyElement element, String methodName, T parameter) {
        return invokeVoidMethod(element.get(), methodName, parameter);
    }

    static DriverFunction<WebElement> invokeWebElementMethod(LazyElement element, String methodName, By locator) {
        return invokeMethod(element, WebElement.class, TypeMethod::isWebElementMethod, methodName, locator, DataDefaults.STOCK_ELEMENT);
    }

    static Data<WebElement> invokeGetElement(Data<WebElement> data, By locator) {
        return invokeMethod(data, WebElement.class, TypeMethod::isWebElementMethod, Strings.FIND_ELEMENT, locator, DataDefaults.STOCK_ELEMENT);
    }

    static Function<Data<WebElement>, Data<WebElement>> invokeGetElement(By locator) {
        return data -> invokeGetElement(data, locator);
    }



    static DriverFunction<Void> invokeVoidMethod(LazyElement element, String methodName) {
        return invokeVoidMethod(element.get(), methodName);
    }

    private static DriverFunction<Boolean> invokeElementDisplayed(LazyElement data) {
        return invokeBooleanMethod(data, "isDisplayed");
    }

    private static DriverFunction<Boolean> invokeElementEnabled(LazyElement data) {
        return invokeBooleanMethod(data, "isEnabled");
    }

    private static DriverFunction<Boolean> invokeElementSelected(LazyElement data) {
        return invokeBooleanMethod(data, "isSelected");
    }

    private static DriverFunction<String> invokeGetElementText(LazyElement data) {
        return invokeStringMethod(data, "getText");
    }

    private static DriverFunction<String> invokeGetElementTagname(LazyElement data) {
        return invokeStringMethod(data, "getTagName");
    }

    private static DriverFunction<String> invokeGetElementAttribute(LazyElement data, String attribute) {
        return invokeStringMethod(data, "getAttribute", attribute);
    }

    private static DriverFunction<String> invokeGetElementCssValue(LazyElement data, String cssValue) {
        return invokeStringMethod(data, "getCssValue", cssValue);
    }

    static DriverFunction<Boolean> invokeElementClickable(LazyElement data) {
        final var nameof = "invokeElementClickable";
        return ifDriver(
            nameof,
            Formatter.isNullMessage(data, "data"),
            new DriverFunction<Boolean> (driver -> {
                final var result = Executor.execute(
                    Executor::aggregateMessage,
                    invokeElementDisplayed(data),
                    invokeElementEnabled(data)
                ).apply(driver);

                final var status = result.status;
                return new Data<Boolean>(status, status, nameof, Formatter.getElementClickableMessage(status, result.message.getMessage()));
            }),
            DataDefaults.NULL_BOOLEAN_DATA
        );
    }

    private static Data<Boolean> isElementCondition(String name, Data<WebElement> data, UnaryOperator<Boolean> inverter, String descriptor, String negator) {
        final var status = isNotNullOrFalseDataOrDataObject(data);
        if (isNull(inverter)) {
            return DataDefaults.getSimpleBooleanData(false, "Inverter " + Strings.WAS_NULL);
        }

        final var lStatus = inverter.apply(status);
        final var returnData = Formatter.getConditionMessage(name, FormatterStrings.isMessageMap, lStatus, descriptor, negator);

        return DataDefaults.getSimpleBooleanData(lStatus, name + " " + returnData.message.getMessage() + data.message.getMessage());
    }

    private static <T> DriverFunction<Boolean> isElementCondition(String name, DriverFunction<WebElement> getter, UnaryOperator<Boolean> inverter, String descriptor, String negator) {
        return ifDriver(
            "isElementCondition",
            Formatter.getIsElementConditionMessage(getter, inverter),
            new DriverFunction<Boolean>(driver -> isElementCondition(name, getter.apply(driver), inverter, descriptor, negator)),
            DataDefaults.NULL_BOOLEAN_DATA
        );
    }

    static DriverFunction<Boolean> isElementCondition(ElementCondition data) {
        return isElementConditionDataCore(data.data, data.condition, data.descriptor, Strings.OPTION_EMPTY);
    }

    static DriverFunction<Boolean> isElementNotCondition(ElementCondition data) {
        return isElementConditionDataCore(data.data, data.condition, data.descriptor, Strings.OPTION_NOT);
    }

    private static DriverFunction<Boolean> isElementConditionDataCore(LazyElement element, Function<LazyElement, DriverFunction<Boolean>> condition, String descriptor, String negator) {
        return ifDriver(
            "isElementConditionDataCore",
            areNotNull(condition, negator, element) && isNotBlank(descriptor),
            isElementCondition(element.name, Executor.execute(condition.apply(element), element.get()), Cardinalities::noopBoolean, descriptor, negator),
            new Data<Boolean>(false, false, "Data was null or false." + Strings.END_LINE)
        );
    }

    static DriverFunction<Boolean> isElementAbsent(LazyElement element) {
        return ifDriver(
            "isElementAbsent",
            isNotNull(element),
            isElementCondition(element.name, element.get(), Cardinalities::invertBoolean, Strings.ABSENT, Strings.OPTION_NOT),
            DataDefaults.NULL_BOOLEAN_DATA
        );
    }

    private static DriverFunction<String> getFormattedElementValueData(DriverFunction<String> data, String descriptor) {
        return ifDriver("getFormattedElementValueData", areNotNull(data, descriptor), data, Formatter.getValueMessage(descriptor), DataDefaults.GET_FORMATTED_ELEMENT_VALUE_ERROR_DATA);
    }

    static DriverFunction<Boolean> isNotNullOrFalse(LazyElement data) {
        final var nameof = "isNotNullOrFalse";
        return ifDriver(
            nameof,
            isNotNull(data),
            new DriverFunction<Boolean>(driver -> {
                final var element = data.get().apply(driver);
                final var status = isNotNullOrFalseDataOrDataObject(element);
                final var message = status ? ("Element is okay" + Strings.END_LINE) : ("Element was null or false: " + element.message);
                return DataDefaults.getSimpleBooleanData(status, nameof, message);
            }),
            new Data<Boolean>(false, false, "Passed Data " + Strings.WAS_NULL)
        );
    }

    static DriverFunction<Boolean> isElementPresent(LazyElement data) {
        return isElementCondition(new ElementCondition(data, Driver::isNotNullOrFalse, Strings.PRESENT));
    }

    static DriverFunction<Boolean> isElementLambdaDataCore(
        LazyElement data,
        Function<LazyElement, DriverFunction<Boolean>> condition,
        Function<ElementCondition, DriverFunction<Boolean>> elementMessageFormatter,
        String descriptor
    ) {
        final var nameof = "isElementLambdaDataCore";
        return ifDriver(
            nameof,
            areNotNull(data, condition, elementMessageFormatter) && isNotBlank(descriptor),
            Executor.execute(
                isElementPresent(data),
                elementMessageFormatter.apply(new ElementCondition(data, condition, descriptor))
            ),
            new Data<Boolean>(false, false, nameof, "Parameters " + Strings.WAS_NULL)
        );
    }

    static DriverFunction<String> getElementValueLambdaDataCore(
        LazyElement data,
        Function<LazyElement, DriverFunction<String>> getter,
        BiFunction<DriverFunction<String>, String, DriverFunction<String>> formatter,
        String descriptor
    ) {
        final var nameof = "getElementValueLambdaDataCore";
        return ifDriver(
            nameof,
            areNotNull(data, getter, formatter) && isNotBlank(descriptor),
            Executor.execute(
                isElementPresent(data),
                formatter.apply(getter.apply(data), descriptor)
            ),
            new Data<Boolean>(false, false, nameof, "Parameters " + Strings.WAS_NULL)
        );
    }

    static <T> DriverFunction<String> getElementValueLambdaDataCore(
        LazyElement data,
        T parameter,
        BiFunction<LazyElement, T, DriverFunction<String>> getter,
        BiFunction<DriverFunction<String>, String, DriverFunction<String>> formatter,
        String descriptor
    ) {
        final var nameof = "getElementValueLambdaDataCore";
        return ifDriver(
            nameof,
            areNotNull(data, getter, formatter) && isNotBlank(descriptor),
            Executor.execute(
                isElementPresent(data),
                formatter.apply(getter.apply(data, parameter), descriptor)
            ),
            new Data<Boolean>(false, false, nameof, "Parameters " + Strings.WAS_NULL)
        );
    }

    static DriverFunction<Boolean> isElementConditionLambdaDataCore(LazyElement data, Function<LazyElement, DriverFunction<Boolean>> condition, String descriptor) {
        return isElementLambdaDataCore(data, condition, Driver::isElementCondition, descriptor);
    }

    static DriverFunction<Boolean> isElementNotConditionLambdaDataCore(LazyElement data, Function<LazyElement, DriverFunction<Boolean>> condition, String descriptor) {
        return isElementLambdaDataCore(data, condition, Driver::isElementNotCondition, descriptor);
    }

    static DriverFunction<String> getElementValueData(LazyElement data, Function<LazyElement, DriverFunction<String>> getter, String descriptor) {
        return getElementValueLambdaDataCore(data, getter, Driver::getFormattedElementValueData, descriptor);
    }

    static <T> DriverFunction<String> getElementValueData(LazyElement data, T parameter, BiFunction<LazyElement, T, DriverFunction<String>> getter, String descriptor) {
        return getElementValueLambdaDataCore(data, parameter, getter, Driver::getFormattedElementValueData, descriptor);
    }

    static DriverFunction<Boolean> isElement(Function<LazyElement, DriverFunction<Boolean>> elementCondition, LazyElement data) {
        final var nameof = "isElement";
        return ifDriver(nameof, areNotNull(elementCondition, data), elementCondition.apply(data), new Data<Boolean>(false, false, nameof, "Parameters " + Strings.WAS_NULL));
    }

    static DriverFunction<Boolean> isElementDisplayed(LazyElement data) {
        return isElementConditionLambdaDataCore(data, Driver::invokeElementDisplayed, Strings.DISPLAYED);
    }

    static DriverFunction<Boolean> isElementEnabled(LazyElement data) {
        return isElementConditionLambdaDataCore(data, Driver::invokeElementEnabled, Strings.ENABLED);
    }

    static DriverFunction<Boolean> isElementClickable(LazyElement data) {
        return isElementConditionLambdaDataCore(data, Driver::invokeElementClickable, Strings.CLICKABLE);
    }

    static DriverFunction<Boolean> isElementSelected(LazyElement data) {
        return isElementConditionLambdaDataCore(data, Driver::invokeElementSelected, Strings.SELECTED);
    }

    static DriverFunction<Boolean> isElementHidden(LazyElement data) {
        return isElementNotConditionLambdaDataCore(data, Driver::invokeElementDisplayed, Strings.HIDDEN);
    }

    static DriverFunction<Boolean> isElementDisabled(LazyElement data) {
        return isElementNotConditionLambdaDataCore(data, Driver::invokeElementEnabled, Strings.DISABLED);
    }

    static DriverFunction<Boolean> isElementUnclickable(LazyElement data) {
        return isElementNotConditionLambdaDataCore(data, Driver::invokeElementClickable, Strings.UNCLICKABLE);
    }

    static DriverFunction<Boolean> isElementUnselected(LazyElement data) {
        return isElementNotConditionLambdaDataCore(data, Driver::invokeElementSelected, Strings.UNSELECTED);
    }

    static DriverFunction<String> getElementText(LazyElement data) {
        return getElementValueData(data, Driver::invokeGetElementText, "Text");
    }

    static DriverFunction<String> getElementTagName(LazyElement data) {
        return getElementValueData(data, Driver::invokeGetElementTagname, "Tagname");
    }

    static DriverFunction<String> getElementAttribute(LazyElement data, String attribute) {
        return getElementValueData(data, attribute, Driver::invokeGetElementAttribute, "Attribute(\"" + attribute + "\")");
    }

    static DriverFunction<String> getElementAttributeValue(LazyElement data) {
        return getElementAttribute(data, "value");
    }

    static DriverFunction<String> getElementCssValue(LazyElement data, String cssValue) {
        return getElementValueData(data, cssValue, Driver::invokeGetElementCssValue, "Css value(\"" + cssValue + "\")");
    }

    static DriverFunction<Boolean> isElementPresent(By locator, SingleGetter getter) {
        return isElement(Driver::isElementPresent, new LazyElement(locator, getter));
    }
    static DriverFunction<Boolean> isElementDisplayed(By locator, SingleGetter getter) {
        return isElement(Driver::invokeElementDisplayed, new LazyElement(locator, getter));
    }
    static DriverFunction<Boolean> isElementEnabled(By locator, SingleGetter getter) {
        return isElement(Driver::invokeElementEnabled, new LazyElement(locator, getter));
    }
    static DriverFunction<Boolean> isElementClickable(By locator, SingleGetter getter) {
        return isElement(Driver::isElementClickable, new LazyElement(locator, getter));
    }
    static DriverFunction<Boolean> isElementSelected(By locator, SingleGetter getter) {
        return isElement(Driver::invokeElementSelected, new LazyElement(locator, getter));
    }
    static DriverFunction<Boolean> isElementAbsent(By locator, SingleGetter getter) {
        return isElement(Driver::isElementAbsent, new LazyElement(locator, getter));
    }
    static DriverFunction<Boolean> isElementHidden(By locator, SingleGetter getter) {
        return isElement(Driver::isElementHidden, new LazyElement(locator, getter));
    }
    static DriverFunction<Boolean> isElementDisabled(By locator, SingleGetter getter) {
        return isElement(Driver::isElementDisabled, new LazyElement(locator, getter));
    }
    static DriverFunction<Boolean> isElementUnclickable(By locator, SingleGetter getter) {
        return isElement(Driver::isElementUnclickable, new LazyElement(locator, getter));
    }
    static DriverFunction<Boolean> isElementUnselected(By locator, SingleGetter getter) {
        return isElement(Driver::isElementUnselected, new LazyElement(locator, getter));
    }
    static DriverFunction<String> getElementText(By locator, SingleGetter getter) {
        return getElementText(new LazyElement(locator, getter));
    }
    static DriverFunction<String> getElementTagName(By locator, SingleGetter getter) {
        return getElementTagName(new LazyElement(locator, getter));
    }
    static DriverFunction<String> getElementAttribute(By locator, SingleGetter getter, String attribute) {
        return getElementAttribute(new LazyElement(locator, getter), attribute);
    }
    static DriverFunction<String> getElementAttributeValue(By locator, SingleGetter getter) {
        return getElementAttributeValue(new LazyElement(locator, getter));
    }
    static DriverFunction<String> getElementCssValue(By locator, SingleGetter getter, String cssValue) {
        return getElementCssValue(new LazyElement(locator, getter), cssValue);
    }

    static DriverFunction<Boolean> isElementPresent(By locator) {
        return isElement(Driver::isElementPresent, new LazyElement(locator, SingleGetter.DEFAULT));
    }
    static DriverFunction<Boolean> isElementDisplayed(By locator) {
        return isElement(Driver::isElementDisplayed, new LazyElement(locator, SingleGetter.DEFAULT));
    }
    static DriverFunction<Boolean> isElementEnabled(By locator) {
        return isElement(Driver::isElementEnabled, new LazyElement(locator, SingleGetter.DEFAULT));
    }
    static DriverFunction<Boolean> isElementClickable(By locator) {
        return isElement(Driver::isElementClickable, new LazyElement(locator, SingleGetter.DEFAULT));
    }
    static DriverFunction<Boolean> isElementSelected(By locator) {
        return isElement(Driver::isElementSelected, new LazyElement(locator, SingleGetter.DEFAULT));
    }
    static DriverFunction<Boolean> isElementAbsent(By locator) {
        return isElement(Driver::isElementAbsent, new LazyElement(locator, SingleGetter.DEFAULT));
    }
    static DriverFunction<Boolean> isElementHidden(By locator) {
        return isElement(Driver::isElementHidden, new LazyElement(locator, SingleGetter.DEFAULT));
    }
    static DriverFunction<Boolean> isElementDisabled(By locator) {
        return isElement(Driver::isElementDisabled, new LazyElement(locator, SingleGetter.DEFAULT));
    }
    static DriverFunction<Boolean> isElementUnclickable(By locator) {
        return isElement(Driver::isElementUnclickable, new LazyElement(locator, SingleGetter.DEFAULT));
    }
    static DriverFunction<Boolean> isElementUnselected(By locator) {
        return isElement(Driver::isElementUnselected, new LazyElement(locator, SingleGetter.DEFAULT));
    }

    static DriverFunction<String> getElementText(By locator) {
        return getElementText(locator, SingleGetter.DEFAULT);
    }

    static DriverFunction<String> getElementTagName(By locator) {
        return getElementTagName(locator, SingleGetter.DEFAULT);
    }

    static DriverFunction<String> getElementAttribute(By locator, String attribute) {
        return getElementAttribute(locator, SingleGetter.DEFAULT, attribute);
    }

    static DriverFunction<String> getElementAttributeValue(By locator) {
        return getElementAttributeValue(locator, SingleGetter.DEFAULT);
    }

    static DriverFunction<String> getElementCssValue(By locator, String cssValue) {
        return getElementCssValue(locator, SingleGetter.DEFAULT, cssValue);
    }

    static DriverFunction<Integer> getCountOfElements(DriverFunction<WebElementList> data) {
        return ifDriverList("getCountOfElements", isNotNull(data), utils.isOfTypeNonEmpty(WebElement.class), data, Driver::getCountOfElements, DataDefaults.NULL_INTEGER_NULL_DRIVER_DATA);
    }

    static Data<WebElement> getElementByIndex(Data<WebElementList> data, int index) {
        final var nameof = "getElementByIndexFrom";
        if ((index < 0) || isNullOrFalseDataOrDataObject(data)) {
            return prependMessage(DataDefaults.NULL_ELEMENT_DATA, nameof, "Data or index was null. Index: " + index + " Data: " + data.toString());
        }

        final var object = data.object;
        if (object.isNullOrEmpty()) {
            return prependMessage(DataDefaults.NULL_ELEMENT_DATA, nameof, "List " + Strings.WAS_NULL);
        }

        final var size = object.size();
        final var status = (size > index);
        return new Data<WebElement>((WebElement)object.get(index), status, nameof, "element " + (status ? "" : "not ") + "found by index: " + index + ", list size: " + size + Strings.END_LINE + data.message);
    }



    static Function<Data<WebElementList>, Data<WebElement>> getElementByIndex(int index) {
        return index > -1 ? (
            data -> getElementByIndex(data, index)
        ) : data -> replaceMessage(DataDefaults.NULL_ELEMENT_DATA, "getElementByIndex", "Index(\"" + index +"\") was wrong" + Strings.END_LINE);
    }

    static DriverFunction<WebElement> getElementByIndex(DriverFunction<WebElementList> data, int index) {
        return ifDriver(
            "getElementByIndexFrom",
            (index > -1) || isNotNull(data),
            new DriverFunction<WebElement>(driver -> getElementByIndex(data.apply(driver), index)),
            DataDefaults.NULL_ELEMENT_DATA
        );
    }

    static Data<WebElement> getElementFromSingle(Data<WebElementList> data) {
        final var nameof = "getElementFromSingle";
        return isNotNullOrFalseDataOrDataObject(data) ? getElementByIndex(data, 0) : prependMessage(DataDefaults.NULL_ELEMENT_DATA, nameof, "Data or index was null." + data.toString());
    }

    static DriverFunction<WebElement> getElementFromSingle(DriverFunction<WebElementList> data) {
        return ifDriver("getElementFromSingle", isNotNull(data), data, getElementByIndex(0), DataDefaults.NULL_ELEMENT_DATA);
    }

    static DriverFunction<WebElement> getElementByIndex(By locator, int index) {
        return ifDriver("getElementByIndexFrom", isNotNull(locator) && (index > -1), utils::isNotNullOrFalseDataOrDataObject, getElements(locator), getElementByIndex(index), DataDefaults.NULL_ELEMENT_DATA);
    }

    private static Data<WebElementList> getElements(Data<SearchContext> contextData, LazyLocator locator, String contextName) {
        final var nameof = "getElements: ";

        if (
            isNullLazyData(locator) ||
            isBlank(contextName) ||
            isNullOrFalseDataOrDataObject(contextData)
        ) {
            return new Data<WebElementList>(DataDefaults.NULL_ELEMENT_LIST, false, nameof, "There were parameter issues.");
        }
        final var context = contextData.object;
        if (isNull(context)) {
            return new Data<WebElementList>(DataDefaults.NULL_ELEMENT_LIST, false, nameof, "There were parameter issues.");
        }

        final var lLocator = getLocator(locator);
        if (isNullOrFalseDataOrDataObject(lLocator)) {
            return new Data<WebElementList>(DataDefaults.NULL_ELEMENT_LIST, false, nameof, "There were parameter issues.");
        }

        var list = DataDefaults.NULL_ELEMENT_LIST;
        var exception = Defaults.EXCEPTION;
        try {
            list = new WebElementList(lLocator.object.findElements(context));
        } catch (NoSuchElementException | StaleElementReferenceException ex) {
            exception = ex;
        }

        final var status = list.isNotNullAndNonEmpty();
        final var message = status ? Formatter.getFindElementsMessage(locator, list.size()) : "An Exception(" + exception.getClass() + ") has occurred" + Strings.END_LINE;
        final var messageData = new MethodMessageData(nameof, message);
        return isNonException(exception) ? (
            new Data<WebElementList>(list, status, messageData)
        ) : new Data<WebElementList>(list, status, messageData, exception);
    }

    private static <T> DriverFunction<WebElementList> getElements(DriverFunction<SearchContext> contextGetter, LazyLocator locator, String contextName) {
        final var nameof = "getElements";
        return ifDriver(
            nameof,
            areNotNull(locator, contextGetter) && isNotBlank(contextName),
            new DriverFunction<WebElementList>(driver -> getElements(contextGetter.apply(driver), locator, contextName)),
            replaceMessage(DataDefaults.NULL_LIST_DATA, nameof, "There were parameter issues.")
        );
    }

    private static Data<WebElementList> getElementsAmountData(Data<WebElementList> data, LazyLocator locator, int expected) {
        if (isNullOrFalseDataOrDataObject(data)) {
            return replaceMessage(DataDefaults.NULL_LIST_DATA, "Passed data " + Strings.WAS_NULL);
        }

        final var object = data.object;
        final var size = object.size();
        final var status = size == expected;
        final var messageData = new MethodMessageData("getElementsAmountData", Formatter.getElementsAmountMessage(locator, status, expected, size));
        return isNonException(data.exception) ? (
            new Data<WebElementList>(object, status, messageData)
        ) : new Data<WebElementList>(object, status, messageData, data.exception);
    }

    static DriverFunction<WebElementList> getElements(LazyLocator locator) {
        final var nameof = "getElements";
        return ifDriver(
            nameof,
            isNotNullLazyData(locator),
            getElements(getSearchContext(), locator, "Driver"),
            replaceMessage(DataDefaults.NULL_LIST_DATA, nameof, Strings.LOCATOR_WAS_NULL)
        );
    }

    static DriverFunction<WebElementList> getElements(By locator) {
        final var nameof = "getElements";
        return ifDriver(
            nameof,
            isNotNull(locator),
            getElements(getLazyLocator(locator)),
            replaceMessage(DataDefaults.NULL_LIST_DATA, nameof, Strings.LOCATOR_WAS_NULL)
        );
    }

    static DriverFunction<WebElementList> getElements(LazyLocatorList locators, Function<By, DriverFunction<WebElement>> getter) {
        final var nameof = "getElements: (List locators)";
        return ifDriver(
            nameof,
            locators.isNotNullAndNonEmpty(),
            new DriverFunction<WebElementList>(driver -> {
                final var elementList = new WebElementList();
                final var length = locators.size();
                var message = "";
                Data<WebElement> data;
                LazyLocator locator;
                for(var index = 0; index < length; ++index) {
                    locator = locators.get(index);
                    if (isNull(locator)) {
                        return DataDefaults.NULL_LIST_DATA;
                    }

                    data = getter.apply(getLocator(locator).object).apply(driver);
                    message += index + data.message.toString() + Strings.END_LINE;
                    if (isNullOrFalseDataOrDataObject(data)) {
                        continue;
                    }

                    elementList.add(data.object);
                }

                return new Data<WebElementList>(elementList, elementList.isNotNullAndNonEmpty(), nameof, message);
            }),
            replaceMessage(DataDefaults.NULL_LIST_DATA, nameof, Strings.DRIVER_WAS_NULL)
        );
    }

    static DriverFunction<Integer> getCountOfElements(By by) {
        return ifDriver("getCountOfElements", isNotNull(by), getCountOfElements(getElements(by)), DataDefaults.NO_ELEMENTS_FOUND_DATA);
    }

    static DriverFunction<WebElementList> getElementsAmountData(DriverFunction<WebElementList> getter, LazyLocator locator, int expected) {
        return ifDriver(
            "getElementsAmountData",
            isNotNull(getter) && isNotNullLazyData(locator) && (expected > -1),
            new DriverFunction<WebElementList>(driver -> getElementsAmountData(getter.apply(driver), locator, expected)),
            DataDefaults.NULL_LIST_DATA
        );
    }

    static DriverFunction<WebElementList> getElementsAmount(By locator, int expected) {
        final var lazyLocator = getLazyLocator(locator);
        return ifDriver(
            "getElementsAmount",
            isNotNullLazyData(lazyLocator) && (expected > -1),
            getElementsAmountData(getElements(lazyLocator), lazyLocator, expected),
            DataDefaults.NULL_LIST_DATA
        );
    }

    // TODO: Abstract out get(get())
    static DriverFunction<WebElement> getElement(By locator) {
        return ifDriver("getElement", isNotNull(locator), getElementFromSingle(getElementsAmount(locator, 1)), DataDefaults.NULL_ELEMENT_DATA);
    }

    static DriverFunction<WebElement> getElement(LazyLocator locator) {
        return ifDriver("getElement", Formatter.isNotNullLazyDataMessage(locator), getElementFromSingle(getElementsAmount(getLocator(locator).object, 1)), DataDefaults.NULL_ELEMENT_DATA);
    }

    private static Data<WebElement> getShadowRootElementCore(Data<WebElement> data) {
        final var exception = data.exception;
        final var status = isNotNullOrFalseDataOrDataObject(data);
        final var messageData = new MethodMessageData("getShadowRootElementCore", Formatter.getShadowRootElementMessage(data.message.getMessage(), status));
        return isNonException(exception) ? (
            new Data<WebElement>(data.object, status, messageData)
        ) : new Data<WebElement>(DataDefaults.STOCK_ELEMENT, status, messageData, exception);
    }

    private static DriverFunction<WebElement> getShadowRootElement(Data<WebElement> data) {
        return ifDriver(
            "getShadowRootElement",
            Formatter.isNullOrFalseDataMessage(data),
            Execute.getShadowRoot(data),
            Driver::getShadowRootElementCore,
            DataDefaults.NULL_ELEMENT_DATA
        );
    }
    static DriverFunction<WebElement> getShadowRootElement(DriverFunction<WebElement> getter) {
        return ifDriver("getShadowRootElement: ", Formatter.isNullMessage(getter, "Getter"), Execute.getShadowRoot(getter), DataDefaults.NULL_ELEMENT_DATA);
    }

    static DriverFunction<WebElement> getShadowRootElement(LazyElement data) {
        return getShadowRootElement(data.get());
    }

    static DriverFunction<WebElement> getShadowRootElement(By locator, SingleGetter getter) {
        return ifDriver("getShadowRootElement", areNotNull(locator, getter), getShadowRootElement(new LazyElement(locator, getter)), DataDefaults.NULL_ELEMENT_DATA);
    }

    static DriverFunction<WebElement> getShadowRootElement(By locator) {
        return getShadowRootElement(locator, SingleGetter.DEFAULT);
    }

    static DriverFunction<WebElement> getRootElementByInvokedElement(Data<WebElement> data, By locator) {
        final var nameof = "getRootElementByInvokedElement";
        return ifDriver(
            nameof,
            isNotNull(locator) && isNotNullOrFalseDataOrDataObject(data),
            getShadowRootElement(invokeGetElement(locator).apply(data)),
            replaceMessage(DataDefaults.NULL_ELEMENT_DATA, nameof, "Parameters were wrong: locator or data.")
        );
    }

    static DriverFunction<WebElement> getRootElementByInvokedElement(LazyElement data, By locator) {
        final var nameof = "getRootElementByInvokedElement";
        return ifDriver(
            nameof,
            areNotNull(data, locator),
            getRootElementByInvokedElement(data, getLazyLocator(locator)),
            replaceMessage(DataDefaults.NULL_ELEMENT_DATA, nameof, "Parameters were wrong: locator or data.")
        );
    }

    static DriverFunction<WebElement> getRootElementByInvokedElement(Data<WebElement> data, LazyLocator locator) {
        final var nameof = "getRootElementByInvokedElement";
        return ifDriver(
            nameof,
            isNotNullOrFalseDataOrDataObject(data) && isNotNullLazyData(locator),
            getShadowRootElement(invokeGetElement(getLocator(locator).object).apply(data)),
            replaceMessage(DataDefaults.NULL_ELEMENT_DATA, nameof, "Parameters were wrong: locator or data.")
        );
    }

    static DriverFunction<WebElement> getRootElementByInvokedElement(LazyElement data, LazyLocator locator) {
        final var nameof = "getRootElementByInvokedElement";
        return new DriverFunction<WebElement> (driver -> ifLambda(
            areNotNull(driver, data) && isNotNullLazyData(locator),
            getShadowRootElement(invokeGetElement(getLocator(locator).object).apply(data.get().apply(driver))),
            replaceMessage(DataDefaults.NULL_ELEMENT_DATA, nameof, "Parameters were wrong: locator or data.")
        ).apply(driver));
    }

    static DriverFunction<WebElement> getShadowRootElement(Data<WebElement> data, LazyLocatorList locators) {
        return ifDriver(
            "getShadowRootElement",
            isNotNullOrFalseDataOrDataObject(data) && locators.isNotNullAndNonEmpty(),
            new DriverFunction<WebElement>(driver -> {
                if (locators.isSingle()) {
                    return getRootElementByInvokedElement(data, locators.first()).apply(driver);
                }

                var current = data;
                var message = "";
                for (var shadowLocator : locators) {
                    if (isNull(shadowLocator)) {
                        break;
                    }

                    current = getRootElementByInvokedElement(current, shadowLocator).apply(driver);
                    if (isNullOrFalseDataOrDataObject(current)) {
                        break;
                    }

                    message += current.message;
                }

                return replaceMessage(current, message);
            }),
            DataDefaults.NULL_ELEMENT_DATA
        );
    }

    static Function<Data<WebElement>, DriverFunction<WebElement>> getShadowRootElementFunction(LazyLocatorList locators) {
        return locators.isNotNullAndNonEmpty() ? data -> getShadowRootElement(data, locators) : data -> new DriverFunction<WebElement>(DataDefaults.STOCK_ELEMENT, false, Strings.LOCATOR_WAS_NULL);
    }

    static DriverFunction<WebElement> getShadowRootElement(LazyLocatorList locators) {
        final var nameof = "getShadowRootElement: ";
        return ifDriver(
            nameof,
            locators.isNotNullAndNonEmpty() && isNotNullLazyData(locators.first()),
            new DriverFunction<WebElement>(driver -> {
                final var currentBy = locators.first();
                final var current = getShadowRootElement(getLocator(currentBy).object).apply(driver);
                if (isNullOrFalseDataOrDataObject(current)) {
                    return prependMessage(DataDefaults.NULL_ELEMENT_DATA, "Current was null.");
                }

                return locators.isMany() ? getShadowRootElement(current, locators.tail()).apply(driver) : current;
            }),
            prependMessage(DataDefaults.NULL_ELEMENT_DATA, nameof + "Shadow locators list was null.")
        );
    }

    static DriverFunction<WebElement> getShadowRootElement(DriverFunction<WebElement> data, LazyLocatorList locators) {
        return ifDriver(
            "getShadowRootElement",
            isNotNull(data) && locators.isNotNullAndNonEmpty(),
            new DriverFunction<WebElement>(driver -> {
                var current = data.apply(driver);
                return isNotNullOrFalseDataOrDataObject(current) ? getShadowRootElement(current, locators).apply(driver) : DataDefaults.NULL_ELEMENT_DATA;
            }),
            DataDefaults.NULL_ELEMENT_DATA
        );
    }

    static DriverFunction<WebElement> getShadowRootElement(LazyElement data, LazyLocatorList locators) {
        return ifDriver("getShadowRootElement", isNotNull(data) && locators.isNotNullAndNonEmpty(), getShadowRootElement(data.get(), locators), DataDefaults.NULL_ELEMENT_DATA);
    }

    static Function<Data<SearchContext>, Data<WebElement>> getNestedElement(By locator) {
        final var nameof = "getNestedElement: ";
        return isNotNull(locator) ? (context -> {
            if (isNull(context)) {
                return replaceMessage(DataDefaults.NULL_ELEMENT_DATA, nameof, Strings.PASSED_DATA_WAS_NULL);
            }

            final var nestedElement = getNestedElementsAmount(locator, 1).apply(context);
            return isNotNullOrFalseDataWebElementList(nestedElement) ? getElementFromSingle(nestedElement) : replaceMessage(DataDefaults.NULL_ELEMENT_DATA, nameof, "Nested Element was null." + nestedElement.message);
        }) : context -> replaceMessage(DataDefaults.NULL_ELEMENT_DATA, nameof, Strings.LOCATOR_WAS_NULL);
    }

    static Function<Data<SearchContext>, Data<WebElementList>> getNestedElements(By locator) {
        return element -> ifBlank(
            Formatter.getNestedElementsErrorMessage(locator, element),
            getElements(getSearchContext(element), utils.getLazyLocator(locator), "element"),
            "getNestedElements: ",
            DataDefaults.NULL_ELEMENT_LIST
        );
    }

    static Function<Data<SearchContext>, Data<WebElement>> getNestedElement(LazyLocator locator) {
        final var nameof = "getNestedElement: ";
        return isNotNullLazyData(locator) ? getNestedElement(getLocator(locator).object) : data -> replaceMessage(DataDefaults.NULL_ELEMENT_DATA, nameof, Strings.LOCATOR_WAS_NULL);
    }

    static Function<Data<SearchContext>, Data<WebElementList>> getNestedElements(LazyLocator locator) {
        final var nameof = "getNestedElements: ";
        if (isNullLazyData(locator)) {
            return context -> replaceMessage(DataDefaults.NULL_LIST_DATA, nameof + "Lazy locator" + Strings.WAS_NULL);
        }

        final var locatorData = getLocator(locator);
        return isNotNullOrFalseDataOrDataObject(locatorData) ? getNestedElements(locatorData.object) : context -> replaceMessage(DataDefaults.NULL_LIST_DATA, nameof + "Locator data" + Strings.WAS_NULL);
    }

    static Function<Data<SearchContext>, Data<WebElementList>> getNestedElementsAmount(By locator, int count) {
        final var nameof = "getNestedElementsAmount: ";
        return isNotNull(locator) && (count > -1) ? (context -> {
            if (isNull(context)) {
                return replaceMessage(DataDefaults.NULL_LIST_DATA, Strings.PASSED_DATA_WAS_NULL);
            }

            final var result = getNestedElements(locator).apply(context);
            if (isNullOrFalseDataWebElementList(result)) {
                return prependMessage(result, Strings.RESULT_WAS_NULL);
            }
            final var object = result.object;
            final var size = (result.status && isNotNullWebElementList(object)) ? object.size() : 0;
            final var status = size == count;
            final var message = (status ? count : nameof + (size > 0 ? "Wrong amount(" + count + ") of" : "No")) + " Elements" + " found by: " + locator + ".";
            return new Data<WebElementList>(object, status, message);
        }) : context -> replaceMessage(DataDefaults.NULL_LIST_DATA, nameof + "Locator was null, or count was wrong. Locator: " + locator + ", count: " + count);
    }

    static Function<Data<SearchContext>, Data<Integer>> getCountOfNestedElements(By locator) {
        return isNotNull(locator) ? (
            context -> isNotNull(context) ? getCountOfElements(getNestedElements(locator).apply(context)) : DataDefaults.NO_ELEMENTS_FOUND_DATA_FALSE_OR_NULL
        ) : context -> DataDefaults.NO_ELEMENTS_FOUND_DATA;
    }

    private static <T> DriverFunction<T> getShadowNested(Function<LazyLocator, Function<Data<SearchContext>, Data<T>>> getter, LazyLocatorList locators, LazyLocator locator, T defaultValue) {
        final var nameof = "getShadowNested";
        return ifDriver(
            nameof,
            areNotNull(getter, defaultValue) && locators.isNotNullAndNonEmpty() && isNotNullLazyData(locator),
            new DriverFunction<T>(driver -> {
                return isNullOrFalseDataOrDataObject(switchToDefaultContent().apply(driver)) ? (
                    new Data<T>(defaultValue, false, nameof, "Couldn't switch to default content" + Strings.END_LINE)
                ) : getter.apply(locator).apply(getSearchContext(getShadowRootElement(locators).apply(driver).object));
            }),
            new Data<T>(defaultValue, false, "There were parameter issues" + Strings.END_LINE)
        );
    }

    static DriverFunction<WebElement> getShadowNestedElement(LazyLocatorList locators, LazyLocator elementLocator) {
        return getShadowNested(Driver::getNestedElement, locators, elementLocator, DataDefaults.STOCK_ELEMENT);
    }

    static DriverFunction<WebElementList> getShadowNestedElements(LazyLocatorList locators, LazyLocator elementLocator) {
        return getShadowNested(Driver::getNestedElements, locators, elementLocator, DataDefaults.NULL_ELEMENT_LIST);
    }

    static DriverFunction<WebElement> getShadowNestedElement(LazyLocatorList locators, By locator) {
        return getShadowNestedElement(locators, utils.getLazyLocator(locator));
    }

    static DriverFunction<WebElementList> getShadowNestedElements(LazyLocatorList locators, By locator) {
        return getShadowNestedElements(locators, utils.getLazyLocator(locator));
    }

    static <T> Data<Boolean> switchTo(
        TargetLocator locator,
        Function<TargetLocator, T> operation,
        BiFunction<Boolean, SwitchResultMessageData<Void>, String> formatter,
        SwitchResultMessageData<Void> messageData
    ) {
        if (isNull(locator)) {
            new Data<Boolean>(false, false, formatter.apply(false, messageData));
        }

        Exception exception = Defaults.EXCEPTION;
        try {
            operation.apply(locator);
        } catch (NoSuchFrameException ex) {
            exception = ex;
        }

        var status = isNonException(exception);
        return status ? (
            new Data<Boolean>(status, status, formatter.apply(status, messageData))
        ) : new Data<Boolean>(status, status, formatter.apply(status, messageData), exception);
    }

    static <T, U> Data<Boolean> switchTo(
        T target,
        TargetLocator locator,
        boolean guardCondition,
        BiFunction<TargetLocator, T, U> operation,
        BiFunction<Boolean, SwitchResultMessageData<T>, String> formatter,
        SwitchResultMessageData<T> messageData
    ) {
        if (areAnyNull(target, locator) || !guardCondition) {
            new Data<Boolean>(false, false, formatter.apply(false, messageData));
        }

        Exception exception = Defaults.EXCEPTION;
        try {
            operation.apply(locator, target);
        } catch (NoSuchFrameException ex) {
            exception = ex;
        }

        var status = isNonException(exception);
        return status ? (
            new Data<Boolean>(status, status, formatter.apply(status, messageData))
        ) : new Data<Boolean>(status, status, formatter.apply(status, messageData), exception);
    }

    static <T, U> Data<Boolean> switchTo(T target, TargetLocator locator, BiFunction<TargetLocator, T, U> operation, String type, String nameof) {
        return switchTo(target, locator, true, operation, Formatter::getSwitchToMessage, new SwitchResultMessageData<T>(target, type, nameof));
    }

    static <T, U> Function<TargetLocator, Data<Boolean>> switchTo(T target, BiFunction<TargetLocator, T, U> operation, String type, String nameof) {
        return locator -> switchTo(target, locator, operation, type, nameof);
    }

    static Function<TargetLocator, Data<Boolean>> switchToFrame(Data<WebElement> data) {
        final var nameof = "switchToFrame(Data<WebElement> data): ";
        return target -> ifData(
            nameof,
            utils::isNotNull,
            target,
            switchTo(data.object, TargetLocator::frame, "frame", nameof),
            new Data<Boolean>(false, false, nameof, "Couldn't attempt, data was null or false" + Strings.END_LINE)
        );
    }

    static Function<TargetLocator, Data<Boolean>> switchToFrameSingleList(Data<WebElementList> data) {
        final var nameof = "switchToFrame(Data<WebElement> data): ";
        if (isNullOrFalseDataOrDataObject(data)) {
            return target -> DataDefaults.NULL_BOOLEAN_DATA;
        }
        final var list = data.object;
        final var element = new Data<WebElement>(list.first(), list.isSingle(), nameof, data.message.message);
        return utils.isNotNullOrFalseDataOrDataObject(data) ? switchToFrame(element) : target -> DataDefaults.NULL_BOOLEAN_DATA;
    }

    static DriverFunction<Boolean> switchToFrame(DriverFunction<WebElement> data) {
        final var nameof = "switchToFrame";
        return ifDriver(
            nameof,
            isNotNull(data),
            new DriverFunction<Boolean>(driver -> switchToFrame(data.apply(driver)).apply(driver.switchTo())),
            new Data<Boolean>(false, false, nameof, "Data parameter " + Strings.WAS_NULL)
        );
    }

    static DriverFunction<Boolean> switchToFrame(By locator, Function<By, DriverFunction<WebElement>> getter) {
        final var nameof = "switchToFrame";
        return ifDriver(
            nameof,
            areNotNull(locator, getter),
            switchToFrame(getter.apply(locator)),
            replaceMessage(DataDefaults.NULL_BOOLEAN_DATA, nameof, "Couldn't attempt switchToFrame.")
        );
    }

    static DriverFunction<Boolean> switchToFrame(Data<By> locator, Function<By, DriverFunction<WebElement>> getter) {
        final var nameof = "switchToFrame";
        return ifDriver(
            nameof,
            isNotNullOrFalseDataOrDataObject(locator) && isNotNull(getter),
            switchToFrame(getter.apply(locator.object)),
            replaceMessage(DataDefaults.NULL_BOOLEAN_DATA, nameof, "Couldn't attempt switchToFrame.")
        );
    }

    static DriverFunction<Boolean> switchToFrame(By locator) {
        return switchToFrame(locator, Driver::getElement);
    }

    static DriverFunction<Boolean> switchToFrame(LazyLocator locator) {
        return switchToFrame(getLocator(locator), Driver::getElement);
    }

    static DriverFunction<Boolean> switchToFrame(LazyLocatorList locator) {
        final var nameof = "switchToFrame";
        return ifDriver(
            nameof,
            locator.isSingle(),
            switchToFrame(getLocator(locator.first()), Driver::getElement),
            replaceMessage(DataDefaults.NULL_BOOLEAN_DATA, nameof, "Couldn't attempt switchToFrame.")
        );
    }

    static DriverFunction<Boolean> switchToFrameFromSingle(LazyLocatorList locators) {
        final var nameof = "switchToFrameFromSingle";
        return ifDriver(
            nameof,
            locators.isSingle(),
            switchToFrame(locators.first()),
            replaceMessage(DataDefaults.NULL_BOOLEAN_DATA, nameof, "Couldn't attempt switchToFrame. Non-singular list used in function")
        );
    }

    static DriverFunction<Boolean> switchToFrame(int target) {
        return new DriverFunction<Boolean>(driver -> switchTo(
            target,
            driver.switchTo(),
            target > -1,
            TargetLocator::frame,
            Formatter::getSwitchToMessage,
            new SwitchResultMessageData<Integer>(target, "frame", "switchToFrame(int frameLocator): ")
        ));
    }

    static DriverFunction<Boolean> switchToWindow(String target) {
        return new DriverFunction<Boolean>(driver -> switchTo(
            target,
            driver.switchTo(),
            isNotBlank(target),
            TargetLocator::frame,
            Formatter::getSwitchToMessage,
            new SwitchResultMessageData<String>(target, "window", "switchToWindow(String target): ")
        ));
    }

    static DriverFunction<Boolean> switchToParentFrame() {
        return new DriverFunction<Boolean> (driver -> switchTo(driver.switchTo(), TargetLocator::parentFrame, Formatter::getSwitchToMessage, new SwitchResultMessageData<Void>(null, "parent frame", "switchToParentFrame: ")));
    }

    static DriverFunction<Boolean> switchToAlert() {
        return new DriverFunction<Boolean> (driver -> switchTo(driver.switchTo(), TargetLocator::alert, Formatter::getSwitchToMessage, new SwitchResultMessageData<Void>(null, "alert.", "switchToAlert: ")));
    }

    static DriverFunction<Boolean> switchToDefaultContent() {
        return new DriverFunction<Boolean> (driver -> switchTo(driver.switchTo(), TargetLocator::defaultContent, Formatter::getSwitchToMessage, new SwitchResultMessageData<Void>(null, "default content.", "switchToDefaultContent: ")));
    }

    static <T> DriverFunction<T> switchToDefaultContentWith(DriverFunction<T> action) {
        return Executor.execute(
            switchToDefaultContent(),
            action
        );
    }


    static DriverFunction<Boolean> switchToNestedFrame(LazyLocatorList locators) {
        return ifDriver(
            "switchToNestedFrame",
            locators.isNotNullAndNonEmpty(),
            new DriverFunction<Boolean>(driver -> {
                if (isNullOrFalseDataOrDataObject(switchToDefaultContent().apply(driver))) {
                    return replaceMessage(DataDefaults.NULL_BOOLEAN_DATA, "Couldn't switch to default content.");
                }

                final var length = locators.size();
                var message = "";
                var index = 0;
                LazyLocator locator;
                Data<?> data;
                for(; index < length; ++index) {
                    locator = locators.get(index);
                    message += index + ".: ";
                    if (isNull(locator)) {
                        message += (Strings.LOCATOR_WAS_NULL);
                        break;
                    }

                    data = switchToFrame(Driver.getElement(locator)).apply(driver);
                    message += data.message;
                    if (isNullOrFalseDataOrDataObject(data)) {
                        break;
                    }
                }

                return DataDefaults.getSimpleBooleanData(index == length, message);
            }),
            replaceMessage(DataDefaults.NULL_BOOLEAN_DATA, "Locators were empty.")
        );
    }

    static DriverFunction<WebElementList> getFrameNestedElements(LazyLocatorList locators) {
        return ifDriver(
            "getFrameNestedElements",
            locators.isNotNullAndNonEmpty(),
            new DriverFunction<WebElementList>(driver -> {
                if (!locators.isMany()) {
                    return Driver.getElements(locators.first()).apply(driver);
                }

                final var data = switchToNestedFrame(locators.initials()).apply(driver);
                return isNotNullOrFalseDataOrDataObject(data) ? Driver.getElements(locators.last()).apply(driver) : DataDefaults.NULL_LIST_DATA;
            }),
            DataDefaults.NULL_LIST_DATA
        );
    }

    static DriverFunction<WebElement> getFrameNestedElement(LazyLocatorList locators, Function<By, DriverFunction<WebElement>> getter) {
        return ifDriver(
            "getFrameNestedElement",
            isNotNull(getter) && locators.isMany() && !locators.hasMoreThan(2),
            new DriverFunction<WebElement>(driver -> {
                final var frameData = locators.first();
                if (isNullLazyData(frameData)) {
                    return DataDefaults.NULL_ELEMENT_DATA;
                }

                final var frameLocator = getLocator(frameData).object;
                if (isNullOrFalseDataOrDataObject(getter.apply(frameLocator).apply(driver))) {
                    return DataDefaults.NULL_ELEMENT_DATA;
                }

                final var elementData = locators.last();
                if (isNullLazyData(elementData)) {
                    return DataDefaults.NULL_ELEMENT_DATA;
                }

                final var elementLocator = getLocator(elementData).object;
                if (isNullOrFalseDataOrDataObject(getter.apply(frameLocator).apply(driver))) {
                    return DataDefaults.NULL_ELEMENT_DATA;
                }

                final var data = switchToFrame(getter.apply(frameLocator).apply(driver)).apply(driver.switchTo());
                return isNotNullOrFalseDataOrDataObject(data) ? getter.apply(elementLocator).apply(driver) : DataDefaults.NULL_ELEMENT_DATA;
            }),
            DataDefaults.NULL_ELEMENT_DATA
        );
    }

    static Data<String> getWindowHandleData(String handle) {
        final var status = isNotBlank(handle);
        return new Data<>(handle, status, Formatter.getWindowHandleMessage(status, handle));
    }

    static DriverFunction<String> getWindowHandle() {
        return ifDriverGuardData("getWindowHandle", WebDriver::getWindowHandle, Driver::getWindowHandleData, DataDefaults.NULL_STRING_DATA);
    }

    static Data<Integer> getWindowHandleAmountData(Set<String> handles) {
        final var size = handles.size();
        final var status = size > 0;
        return new Data<Integer>(size, status, "getWindowHandleAmount: ", Formatter.getWindowHandlesMessage(status, size));
    }

    static Data<StringSet> getWindowHandlesData(Set<String> handles) {
        final var data = getWindowHandleAmountData(handles);
        final var status = data.status;
        return new Data<StringSet>(status ? new StringSet(handles) : DataDefaults.NULL_STRING_SET, status, "getWindowHandlesData: ", data.message);
    }

    static DriverFunction<StringSet> getWindowHandles() {
        return ifDriverGuardFunction("getWindowHandles", WebDriver::getWindowHandles, Driver::getWindowHandlesData, DataDefaults.NULL_STRINGSET_DF);
    }

    static DriverFunction<Integer> getWindowHandleAmount() {
        return ifDriverGuardFunction("getWindowHandleAmount", WebDriver::getWindowHandles, Driver::getWindowHandleAmountData, DataDefaults.NULL_INTEGER_DF);
    }

    static <T, U> DriverFunction<U> getWithLocator(GetWithData<DecoratedList<T>, T, T, U> data) {
        return ifDriver("getWithLocator", areNotNull(data.locatorGetter, data.getter), data.getter.apply(data.locatorGetter.apply(data.locators)), data.guardData);
    }

    static <T> DriverFunction<T> getWithLazyLocator(GetWithData<LazyLocatorList, LazyLocator, By, T> data) {
        return ifDriver("getWithLazyLocator", areNotNull(data.locatorGetter, data.getter), data.getter.apply(getLocator(data.locatorGetter.apply(data.locators)).object), data.guardData);
    }

    static <WE, BY, BYY extends By, W extends DecoratedList<BY>> DriverFunction<WE> getFromSingle(
        Function<GetWithData<W, BY, BYY, WE>, DriverFunction<WE>> getter,
        GetWithData<W, BY, BYY, WE> data,
        String nameof
    ) {
        return ifDriver(nameof, data.locators.isSingle(), getter.apply(data), data.guardData);
    }

    static DriverFunction<WebElement> getShadowNestedElement(LazyLocatorList locators) {
        if (!locators.hasAtleast(2)) {
            return new DriverFunction<WebElement>(prependMessage(DataDefaults.NULL_ELEMENT_DATA, "Lazy Locator list doesn't have enough items" + Strings.END_LINE));
        }

        final var start = locators.first();
        final var tail = locators.tail();
        return isNotNullLazyData(start) && isNotNull(tail) ? (
            getShadowNestedElement(tail, start)
        ) : new DriverFunction<WebElement>(prependMessage(DataDefaults.NULL_ELEMENT_DATA, "Lazy locator item issues" + Strings.END_LINE));
    }

    static DriverFunction<WebElement> getElementFromSingle(LazyLocatorList locator) {
        return getFromSingle(
            Driver::getWithLazyLocator,
            new GetWithData<LazyLocatorList, LazyLocator, By, WebElement>(locator, LazyLocatorList::first, Driver::getElement, DataDefaults.NULL_ELEMENT_DATA),
            "getElementFromSingle: "
        );
    }

    static DriverFunction<WebElement> getRootElementFromSingle(LazyLocatorList locator) {
        return getFromSingle(
            Driver::getWithLazyLocator,
            new GetWithData<LazyLocatorList, LazyLocator, By, WebElement>(locator, LazyLocatorList::first, Driver::getShadowRootElement, DataDefaults.NULL_ELEMENT_DATA),
            "getRootElementFromSingle: "
        );
    }

    static DriverFunction<WebElement> getNestedElement(LazyLocatorList locators) {
        final var nameof = "getNestedElement: ";
        return ifDriver(
            nameof,
            locators.hasAtleast(2) && isNotNullLazyData(locators.first()),
            new DriverFunction<WebElement>(driver -> {
                if (isNullOrFalseDataOrDataObject(switchToDefaultContent().apply(driver))) {
                    return new Data<WebElement>(DataDefaults.STOCK_ELEMENT, false, nameof, "Driver was null or couldn't switch to default content" + Strings.END_LINE);
                }

                var locator = locators.first();
                var data = Driver.getElement(locator).apply(driver);
                if (isNullOrFalseDataOrDataObject(data)) {
                    return new Data<WebElement>(DataDefaults.STOCK_ELEMENT, false, nameof + data.message);
                }

                final var sublist = locators.tail();
                final var length = sublist.size();
                var index = 0;
                for(; index < length; ++index) {
                    locator = sublist.get(index);
                    if (isNullLazyData(locator)) {
                        break;
                    }

                    data = getNestedElement(locator).apply(getSearchContext(data.object));
                    if (isNullOrFalseDataOrDataObject(data)) {
                        break;
                    }
                }

                return (index == length) ? data : appendMessage(DataDefaults.NULL_ELEMENT_DATA, nameof);
            }),
            DataDefaults.NULL_ELEMENT_DATA
        );
    }

    static <T> DriverFunction<T> getFrameNested(Function<LazyLocator, DriverFunction<T>> getter, LazyLocatorList locators, Data<T> defaultValue, String nameof) {
        return ifDriver(
            nameof,
            locators.hasAtleast(2) && isNotNullLazyData(locators.first()),
            new DriverFunction<T>(driver -> {
                if (isNullOrFalseDataOrDataObject(switchToDefaultContent().apply(driver))) {
                    return replaceMessage(defaultValue, nameof, "Driver was null or couldn't switch to default content" + Strings.END_LINE);
                }

                final var function = Defaults.frameNestedStrategyMap.get("" + locators.hasMoreThan(2));
                if (isNull(function)) {
                    return replaceMessage(defaultValue, nameof, "Function from Frame nested Strategy map " + Strings.WAS_NULL);
                }

                final var data = function.apply(locators.initials()).apply(driver);
                if (isNullOrFalseDataOrDataObject(data)) {
                    return replaceMessage(defaultValue, nameof, "Couldn't switch into frame. By list length: " + locators.size());
                }

                final var locator = locators.last();
                return isNotNull(locator) ? getter.apply(locator).apply(driver) : replaceMessage(defaultValue, "Locator was null" + Strings.END_LINE);
            }),
            replaceMessage(defaultValue, nameof, "Couldn't switch into frame. Guard.")
        );
    }

    static DriverFunction<WebElement> getFrameNestedElement(LazyLocatorList locators) {
        return getFrameNested(Driver::getElement, locators, DataDefaults.NULL_ELEMENT_DATA, "getFrameNestedElement");
    }

    static DriverFunction<WebElementList> getFrameNestedElementsFromLast(LazyLocatorList locators) {
        return getFrameNested(Driver::getElements, locators, DataDefaults.NULL_LIST_DATA, "getFrameNestedElementsFromLast");
    }

    static DriverFunction<WebElementList> getNestedElementsFromLast(LazyLocatorList locators) {
        final var nameof = "getNestedElementsFromLast";
        final var nested = locators.hasAtleast(2);
        return ifDriver(
            nameof,
            nested && isNotNullLazyData(locators.first()), //TODO: Move to formatter, and do blank check instead
            new DriverFunction<WebElementList>(driver -> {
                if (isNullOrFalseDataOrDataObject(switchToDefaultContent().apply(driver))) {
                    return replaceMessage(DataDefaults.NULL_LIST_DATA, nameof, "Couldn't switch to default content" + Strings.END_LINE);
                }

                final var function = Defaults.frameAmountStrategyMap.get("" + locators.hasMoreThan(2));
                final var element = function.apply(locators.initials()).apply(driver);
                if (isNullOrFalseDataOrDataObject(element)) {
                    return replaceMessage(DataDefaults.NULL_LIST_DATA, nameof, "Failed, nested element issue - sublist length(" + locators.initials().size() + "): " + element.message);
                }

                final var locator = locators.last();
                if (isNullLazyData(locator)) {
                    return replaceMessage(DataDefaults.NULL_LIST_DATA, nameof, "Locator was null.");
                }

                final var data = getNestedElements(locator).apply(getSearchContext(element.object));
                return isNotNullOrFalseDataOrDataObject(data) ? data : prependMessage(data, nameof, (nested ? "Nested " : "") + "Elements weren't found by locator: " + locator);
            }),
            replaceMessage(DataDefaults.NULL_LIST_DATA, nameof, "Locators list was null or empty.")
        );
    }

    static DriverFunction<WebElementList> getShadowNestedElementsFromLast(LazyLocatorList locators) {
        return ifDriver(
            "getShadowNestedElementsFromLast",
            locators.isNotNullAndNonEmpty() && isNotNullLazyData(locators.last()),
            getShadowNestedElements(locators.initials(), locators.last()),
            replaceMessage(DataDefaults.NULL_LIST_DATA, "Locators were null" + Strings.END_LINE)
        );
    }

    static DriverFunction<WebElementList> getElements(LazyLocatorList locators) {
        final var nameof = "getElements: ";
        return ifDriver(
            nameof,
            locators.isNotNullAndNonEmpty() && locators.isSingle(),
            new DriverFunction<WebElementList>(driver -> {
                if (isNullOrFalseDataOrDataObject(switchToDefaultContent().apply(driver))) {
                    return replaceMessage(DataDefaults.NULL_LIST_DATA, nameof, "Couldn't switch to default content" + Strings.END_LINE);
                }

                final var lazyLocator = locators.first();
                if (isNullLazyData(lazyLocator)) {
                    return replaceMessage(DataDefaults.NULL_LIST_DATA, "Lazy Locator" + Strings.WAS_NULL);
                }

                final var locator = getLocator(lazyLocator);
                return isNotNull(locator) ? getElements(locator.object).apply(driver) : appendMessage(DataDefaults.NULL_LIST_DATA, nameof, "\n" + Strings.LOCATOR_WAS_NULL);
            }),
            replaceMessage(DataDefaults.NULL_LIST_DATA, nameof, "Locators was null or wrong size" + Strings.END_LINE)
        );
    }

    static DriverFunction<WebElement> getIndexedElement(LazyLocatorList locators, Map<String, Function<LazyLocatorList, DriverFunction<WebElementList>>> getterMap, String getter, int index) {
        return ifDriver("getIndexedElement via LazyElement parameters", Formatter.getGetterErrorMessage(getterMap, getter), getterMap.get(getter).apply(locators), Driver.getElementByIndex(index), DataDefaults.NULL_ELEMENT_DATA);
    }

    static DriverFunction<WebElement> getElement(LazyLocatorList locators, Map<String, Function<LazyLocatorList, DriverFunction<WebElement>>> getterMap, String getter) {
        return ifDriver("getElement via LazyElement parameters", Formatter.getGetterErrorMessage(getterMap, getter), getterMap.get(getter).apply(locators), DataDefaults.NULL_ELEMENT_DATA);
    }

    static <T> DriverFunction<ExternalElementData> getLazyElementByExternal(LazyElement element, ExternalSelectorData externalData, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys) {
        //TODO: Validate all the parameters and provide overloads for defaults.
        final var nameof = "getLazyElementByExternal";
        final var defaultValue = DataDefaults.NULL_EXTERNAL_ELEMENT_DATA;
        return ifDriver(
            nameof,
            Formatter.getExternalSelectorDataErrorMessage(element, externalData, nameof),
            new DriverFunction<ExternalElementData>(driver -> {
                final var types = new ArrayList<>(typeKeys.keySet());
                LazyLocator locator;
                var selector = externalData.defaultSelector;
                var parameterKey = "";
                var selectorType = externalData.selectorType;
                var currentElement = DataDefaults.NULL_ELEMENT_DATA;

                final var failedSelectors = new DecoratedList<String>();
                final var length = externalData.limit;
                var index = 0;
                var message = replaceMessage(DataDefaults.NULL_STRING_DATA, nameof, "");
                var lep = new LazyIndexedElementParameters(false, 0, new LazyLocator(""));
                var getSelector = externalData.getSelector;
                for(; index < length; ++index) {
                    switchToDefaultContent().apply(driver);
                    selector = getSelector.apply(externalData.preferredProperties, failedSelectors.list).apply(driver);
                    if (isNullOrFalseDataOrDataObject(selector)) {
                        continue;
                    }

                    if (isBlank(selector.object)) {
                        appendMessage(message, "Empty selector returned, attempt: " + index + Strings.END_LINE);
                        continue;
                    }

                    locator = new LazyLocator(selector.object, selectorType);
                    parameterKey = Formatter.getUniqueGeneratedName(selectorType);
                    lep = new LazyIndexedElementParameters(false, 0, locator);
                    currentElement = getElement(lep.lazyLocators, Defaults.singleGetterMap, SingleGetter.DEFAULT.getName()).apply(driver);
                    if (isNotNullOrFalseDataOrDataObject(currentElement)) {
                        break;
                    }

                    failedSelectors.addNullSafe(selector.object);
                }

                element.parameters.putIfAbsent(parameterKey, lep);
                final var update = ElementRepository.updateTypeKeys(lep.lazyLocators, typeKeys, types, parameterKey);
                return isNotNullOrFalseDataOrDataObject(currentElement) ? (
                    new Data<ExternalElementData>(new ExternalElementData(typeKeys, currentElement), true, nameof, "External function yielded an element" + Strings.END_LINE)
                ) : replaceMessage(defaultValue, nameof, "All(\"" + length + "\") approaches were tried" + Strings.END_LINE + currentElement.message.toString());
            }),
            defaultValue
        );
    }

    static Data<WebElement> cacheNonNullAndNotFalseLazyElement(LazyElement element, Data<ExternalElementData> regular, Data<ExternalElementData> external) {
        final var nameof = "cacheNonNullAndNotFalseLazyElement";
        if (isNotBlank(Formatter.isNullLazyElementMessage(element)) || areAnyNull(regular, external)) {
            return replaceMessage(DataDefaults.NULL_ELEMENT_DATA, nameof, Strings.PARAMETER_ISSUES + Strings.WAS_NULL);
        }

        final var regularStatus = isNotNullOrFalseDataOrDataObject(regular);
        final var externalStatus = isNotNullOrFalseDataOrDataObject(external);
        if (!regularStatus && !externalStatus) {
            return DataDefaults.NULL_ELEMENT_DATA;
        }

        final var externalElement = (externalStatus ? external : regular).object;
        final var currentElement = externalElement.found;
        return isNotNullOrFalseDataOrDataObject(currentElement) ? (
            appendMessage(currentElement, ElementRepository.cacheIfAbsent(element, utils.getKeysCopy(externalElement.typeKeys)))
        ) : prependMessage(currentElement, "All approaches were tried" + Strings.END_LINE);
    }

    static <T> Data<Integer> getNextCachedKey(Map<String, T> parameterMap, Iterator<String> getOrder, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, int parameterIndex) {
        final var nameof = "getNextCachedKey";
        var type = typeKeys.getOrDefault(getOrder.next(), null);
        if (isNull(type)) {
            return replaceMessage(DataDefaults.NULL_INTEGER_DATA, nameof, "Type" + Strings.WAS_NULL);
        }

        var index = parameterIndex;
        if (!type.hasIndex(index)) {
            if (!getOrder.hasNext()) {
                return replaceMessage(DataDefaults.NULL_INTEGER_DATA, nameof, "GetOrder doesn't have more entries" + Strings.END_LINE);
            }

            type = typeKeys.get(getOrder.next());
            index = 0;
        }
        final var key = type.get(index).selectorKey;
        return (isNotNull(key) && parameterMap.containsKey(key)) ? (
            new Data<Integer>(index, true, key)
        ) : replaceMessage(DataDefaults.NULL_INTEGER_DATA, nameof, "The parameter map didn't contain an indexed selector-type it should have" + Strings.END_LINE);
    }

    static Data<Integer> getNextKey(DecoratedList<String> keys, int parameterIndex) {
        return (parameterIndex > -1) && keys.hasIndex(parameterIndex) ? (
            new Data<Integer>(0, true, keys.get(parameterIndex))
        ) : replaceMessage(DataDefaults.NULL_INTEGER_DATA, "getNextKey", "The parameter map didn't contain an indexed selector-type it should have" + Strings.END_LINE);
    }

    static DriverFunction<WebElement> getLazyElement(LazyElementWithOptionsData data) {
        final var nameof = "getLazyElement";
        return ifDriver(
            nameof,
            Formatter.getLazyElementWithOptionsMessage(data, nameof),
            new DriverFunction<WebElement>(driver -> {
                final var getOrder = data.getOrder.iterator();
                final var dataElement = data.element;
                final var name = dataElement.name;
                final var cached2 = ElementRepository.containsElement(dataElement.name);
                if (isNullOrFalseDataOrDataObject(cached2)) {
                    return DataDefaults.NULL_ELEMENT_DATA;
                }

                final var cached = cached2.object;
                final var getResult = ElementRepository.getIfContains(dataElement);
                final var localElement = cached ? getResult.object.element : dataElement;
                final var typeKeys = cached ? getResult.object.typeKeys : ElementRepository.getInitializedTypeKeysMap();
                final var parameterMap = localElement.parameters;
                final var parameterKeys = new DecoratedList<String>(parameterMap.keySet(), String.class);
                final var types = new DecoratedList<String>(typeKeys.keySet(), String.class);
                var message = DataDefaults.NULL_STRING_DATA;
                var currentElement = DataDefaults.NULL_ELEMENT_DATA;
                var parameterIndex = 0;
                final var length = data.internalData.limit;
                for (var index = 0; isNullOrFalseDataOrDataObject(currentElement) && (index < length); ++index, ++parameterIndex) {
                    switchToDefaultContent().apply(driver);
                    var keyData = cached ? getNextCachedKey(parameterMap, getOrder, typeKeys, parameterIndex) : getNextKey(parameterKeys, parameterIndex);
                    if (isNullOrFalseDataOrDataObject(keyData)) {
                        return replaceMessage(currentElement, nameof, "Parameter key wasn't found in " + (cached ? "cached" : "") + " keys" + Strings.END_LINE);
                    }
                    parameterIndex = cached ? keyData.object : parameterIndex;
                    var key = keyData.message.message;
                    var parameters = parameterMap.get(key);
                    if (isNull(parameters) || parameters.lazyLocators.isNullOrEmpty()) {
                        continue;
                    }

                    var locators = parameters.lazyLocators;
                    var update = ElementRepository.updateTypeKeys(name, locators, typeKeys, types, key);
                    if (isNullOrFalseDataOrDataObject(update)) {
                        continue;
                    }


                    var getter = parameters.getter;
                    var indexData = parameters.indexData;
                    currentElement = (
                        indexData.isIndexed ? getIndexedElement(locators, Defaults.manyGetterMap, getter, indexData.index) : getElement(locators, Defaults.singleGetterMap, getter)
                    ).apply(driver);
                    message = appendMessage(message, currentElement.message.toString());
                    message = appendMessage(message, Adjuster.adjustProbability(parameters, typeKeys, key, isNullOrFalseDataOrDataObject(currentElement), data.probabilityData).message.toString());

                }

                final var externalData = data.externalData;
                return cacheNonNullAndNotFalseLazyElement(
                    dataElement,
                    new Data<>(new ExternalElementData(typeKeys, currentElement), isNotNullOrFalseDataOrDataObject(currentElement), message.message.toString()),
                    isBlank(Formatter.getExternalSelectorDataMessage(externalData)) ? getLazyElementByExternal(dataElement, externalData, typeKeys).apply(driver) : DataDefaults.NULL_EXTERNAL_ELEMENT_DATA
                );
            }),
            DataDefaults.NULL_ELEMENT_DATA
        );
    }

    static DriverFunction<WebElement> getLazyElement(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData, DecoratedList<String> getOrder, ProbabilityData probabilityData) {
        return getLazyElement(new LazyElementWithOptionsData(element, internalData, externalData, getOrder, probabilityData));
    }

    static DriverFunction<WebElement> getLazyElement(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData, DecoratedList<String> getOrder) {
        return getLazyElement(new LazyElementWithOptionsData(element, internalData, externalData, getOrder, Defaults.PROBABILITY_DATA));
    }

    static DriverFunction<WebElement> getLazyElement(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData, ProbabilityData probabilityData) {
        return getLazyElement(new LazyElementWithOptionsData(element, internalData, externalData, GetOrder.DEFAULT, probabilityData));
    }

    static DriverFunction<WebElement> getLazyElement(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData) {
        return getLazyElement(new LazyElementWithOptionsData(element, internalData, externalData, GetOrder.DEFAULT, Defaults.PROBABILITY_DATA));
    }

    static DriverFunction<WebElement> getLazyElement(LazyElement element) {
        //return getLazyElement(new LazyElementWithOptionsData(element));
        final var nameof = "getLazyElement";
        final var defaultValue = DataDefaults.NULL_ELEMENT_DATA;
        return ifDriver(
                nameof,
                Formatter.getLazyParameterErrorMessage(element, nameof),
                new DriverFunction<WebElement>(driver -> {
                    final var switchData = switchToDefaultContent().apply(driver);
                    if (isNullOrFalseDataOrDataObject(switchData)) {
                        return prependMessage(defaultValue, nameof, switchData.message.toString());
                    }

                    final var parameterMap = element.parameters;
                    final var parameterKeys = new DecoratedList<String>(parameterMap.keySet(), String.class);
                    final var length = parameterKeys.size();
                    if (isNull(parameterKeys) || (length < 1)) {
                        return replaceMessage(defaultValue, nameof, "Parameter Keys(getOrder) length was less than 1.");
                    }
                    var currentElement = defaultValue;
                    var index = 0;
                    var key = "";
                    for (; isNullOrFalseDataOrDataObject(currentElement) && (index < length); ++index) {
                        key = parameterKeys.get(index);
                        if (isNull(key)) {
                            continue;
                        }

                        var parameters = parameterMap.get(key);
                        if (isNull(parameters)) {
                            continue;
                        }

                        var locatorList = parameters.lazyLocators;
                        if (locatorList.isNullOrEmpty()) {
                            continue;
                        }

                        var indexData = parameters.indexData;
                        var locators = parameters.lazyLocators;
                        var getter = parameters.getter;
                        currentElement = (indexData.isIndexed ? (getIndexedElement(locators, Defaults.manyGetterMap, getter, indexData.index)) : getElement(locators, Defaults.singleGetterMap, getter)).apply(driver);
                    }

                    return isNotNullOrFalseDataOrDataObject(currentElement) ? currentElement : replaceMessage(currentElement, "All(\"" + length + "\") approaches were tried" + Strings.END_LINE);
                }),
                defaultValue
        );
    }

    static DriverFunction<WebElementList> getLazyElements(LazyElement element) {
        final var nameof = "getLazyElements";
        final var defaultValue = DataDefaults.NULL_LIST_DATA;
        final var getOrder = GetOrder.DEFAULT;
        return ifDriver(
            nameof,
            Formatter.getLazyParameterErrorMessage(element, nameof),
            new DriverFunction<WebElementList>(driver -> {
                final var switchData = switchToDefaultContent().apply(driver);
                if (isNullOrFalseDataOrDataObject(switchData)) {
                    return prependMessage(defaultValue, nameof, switchData.message.toString());
                }

                final var parameterMap = element.parameters;
                if (getOrder.isNullOrEmpty()) {
                    return replaceMessage(defaultValue, nameof, "getOrder length was less than 1.");
                }

                boolean isIndexed;
                var currentElements = DataDefaults.NULL_LIST_DATA;
                for (var currentStrategy : getOrder) {
                    if (isNull(currentStrategy)) {
                        continue;
                    }

                    var parameters = parameterMap.get(currentStrategy);
                    if (isNull(parameters)) {
                        continue;
                    }

                    var indexData = parameters.indexData;
                    isIndexed = indexData.isIndexed;

                    var locatorList = parameters.lazyLocators;
                    if (locatorList.isNullOrEmpty()) {
                        continue;
                    }

                    if (isIndexed) {
                        return replaceMessage(defaultValue, nameof, "List return type is incompatible with indexing" + Strings.END_LINE);
                    }

                    final var getterMap = Defaults.manyGetterMap;
                    final var getterParameter = parameters.getter;
                    final var errorMessage = Formatter.getGetterErrorMessage(getterMap, getterParameter);
                    if (isNotBlank(errorMessage)) {
                        continue;
                    }

                    currentElements = getterMap.get(getterParameter).apply(locatorList).apply(driver);
                    if (isNotNullOrFalseDataOrDataObject(currentElements)) {
                        break;
                    }
                }

                return currentElements;
            }),
            defaultValue
        );
    }

    static DriverFunction<Boolean> quitDriver() {
        return ifDriver(
            "quitDriver",
            new DriverFunction<Boolean>(driver -> {
                Data<Boolean> data;
                try {
                    driver.quit();
                    data = DataDefaults.getSimpleBooleanData(true, "Driver was closed successfully.");
                } catch (NullPointerException ex) {
                    final var exMessage = ex.getMessage();
                    data = DataDefaults.getBooleanData(false, "Exception occurred while closing Driver. Exception:" + ex.getClass() + " Message: " +  exMessage, ex, exMessage);
                }

                return data;
            }),
            DataDefaults.DRIVER_WAS_NULL_DATA
        );
    }
}
