package selenium.namespaces;

import core.constants.CoreDataConstants;
import core.constants.InvokeConstants;
import core.extensions.namespaces.CollectionPredicateFunctions;
import core.namespaces.StringUtilities;
import core.namespaces.validators.MethodParametersDataValidators;
import selenium.constants.SeleniumInvokeFunctionDefaults;
import core.extensions.DecoratedList;
import core.extensions.boilers.StringSet;
import selenium.namespaces.extensions.boilers.WebElementList;
import selenium.constants.FactoryConstants;
import selenium.namespaces.extensions.boilers.DriverFunction;
import core.extensions.interfaces.ISizable;
import core.extensions.namespaces.BasicPredicateFunctions;
import core.extensions.namespaces.CardinalitiesFunctions;
import core.extensions.namespaces.CoreUtilities;
import core.extensions.namespaces.NullableFunctions;
import core.namespaces.DataFactoryFunctions;
import core.namespaces.ExceptionHandlers;
import core.namespaces.Executor;
import core.namespaces.InvokeFunctions;
import core.namespaces.repositories.MethodRepository;
import core.records.Data;
import core.records.ExecuteCommonData;
import core.records.HandleResultData;
import core.records.MethodData;
import core.records.MethodDefaults;
import core.records.MethodMessageData;
import core.records.MethodParametersData;
import core.records.reflection.InvokerParameterizedParametersFieldData;
import core.abstracts.reflection.BaseInvokerDefaultsData;
import core.constants.CoreConstants;
import core.records.reflection.message.InvokeCommonMessageParametersData;
import core.reflection.message.ParameterizedMessageData;
import core.reflection.message.RegularMessageData;
import data.constants.FormatterStrings;
import data.constants.Strings;
import data.namespaces.Formatter;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import selector.records.SelectorKeySpecificityData;
import selenium.constants.AdjusterConstants;
import selenium.constants.SeleniumDataConstants;
import selenium.constants.DriverFunctionConstants;
import selenium.constants.ElementFinderConstants;
import selenium.constants.ExecuteCoreDataConstants;
import selenium.constants.ExecuteCoreFunctionDataConstants;
import selenium.constants.GetOrderConstants;
import selenium.constants.SeleniumCoreConstants;
import selenium.constants.SeleniumMethodDefaults;
import selenium.enums.ManyGetter;
import selenium.enums.SingleGetter;
import selenium.javascriptCommands.Execute;
import selenium.namespaces.repositories.ElementRepository;
import selenium.namespaces.repositories.FunctionRepository;
import selenium.namespaces.repositories.LocatorRepository;
import selenium.namespaces.validators.ElementGetterValidators;
import selenium.namespaces.validators.InvokeCoreValidator;
import selenium.namespaces.validators.MessageFormatter;
import selenium.namespaces.validators.SeleniumDataValidators;
import selenium.records.ElementCondition;
import selenium.records.ElementFilterParameters;
import selenium.records.ExecuteCoreData;
import selenium.records.ExecuteCoreFunctionData;
import selenium.records.ExternalElementData;
import selenium.records.ExternalSelectorData;
import selenium.records.GetWithData;
import selenium.records.InternalSelectorData;
import selenium.records.LazyElement;
import selenium.namespaces.extensions.boilers.LazyLocatorList;
import selenium.records.ParametersFieldDefaultsData;
import selenium.records.ProbabilityData;
import selenium.records.SwitchResultMessageData;
import selenium.records.lazy.LazyElementWithOptionsData;
import selenium.records.lazy.LazyLocator;
import selenium.records.scripter.ExecutorData;
import selenium.records.scripter.ExecutorParametersFieldData;
import validators.ScriptExecutions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static core.extensions.namespaces.CoreUtilities.areAnyNull;
import static core.extensions.namespaces.CoreUtilities.areNotNull;
import static core.extensions.namespaces.NullableFunctions.isNotNull;
import static core.extensions.namespaces.NullableFunctions.isNull;
import static core.namespaces.validators.DataValidators.isValidNonFalse;
import static core.namespaces.validators.DataValidators.isInvalidOrFalse;
import static core.namespaces.DataFactoryFunctions.appendMessage;
import static core.namespaces.DataFactoryFunctions.prependMessage;
import static core.namespaces.DataFactoryFunctions.replaceMessage;
import static core.namespaces.DataFactoryFunctions.replaceName;
import static data.namespaces.Formatter.isBlankMessage;
import static data.namespaces.Formatter.isInvalidOrFalseMessage;
import static data.namespaces.Formatter.isNullLazyDataMessage;
import static data.namespaces.Formatter.isNullLazyElementMessage;
import static data.namespaces.Formatter.isNullMessage;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static selenium.namespaces.ExecutionCore.conditionalChain;
import static selenium.namespaces.ExecutionCore.ifData;
import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.ExecutionCore.ifDriverGuardData;
import static selenium.namespaces.ExecutionCore.validChain;
import static selenium.namespaces.ExecutionCore.validElementChain;
import static selenium.namespaces.SeleniumUtilities.getKeysCopy;
import static selenium.namespaces.SeleniumUtilities.getLazyLocator;
import static selenium.namespaces.SeleniumUtilities.getLocator;
import static selenium.namespaces.SeleniumUtilities.isNotNullLazyData;
import static selenium.namespaces.SeleniumUtilities.isNotNullLazyElement;
import static selenium.namespaces.SeleniumUtilities.isNotNullWebElement;
import static selenium.namespaces.SeleniumUtilities.isNullLazyData;
import static selenium.namespaces.SeleniumUtilities.isNullWebElement;

public interface Driver {
    private static JavascriptExecutor getExecutor(WebDriver driver) {
        return (JavascriptExecutor)driver;
    }

    private static TakesScreenshot getScreenshotter(WebDriver driver) {
        return (TakesScreenshot)driver;
    }

    private static TargetLocator getTargetLocator(WebDriver driver) {
        return driver.switchTo();
    }

    private static <T extends SearchContext> SearchContext getSearchContextOf(T object) {
        return object;
    }

    private static <T, U> Data<U> getSubtypeOf(String dependencyName, T dependency, Function<T, U> getter, U defaultValue) {
        final var lDependencyName = isNotBlank(dependencyName) ? dependencyName : "Dependency";
        final var status = isNotNull(dependency);
        final var object = status ? getter.apply(dependency) : defaultValue;
        final var message = lDependencyName + (status ? Strings.WASNT_NULL : Strings.WAS_NULL);
        return DataFactoryFunctions.getWithMessage(object, status, message);
    }

    static <T extends SearchContext> Data<SearchContext> getSearchContextOf(String dependencyName, Data<T> data) {
        return (isValidNonFalse(data)) ? (
            getSubtypeOf(dependencyName, data.object, Driver::getSearchContextOf, FactoryConstants.NULL_SEARCH_CONTEXT)
        ) : FactoryConstants.NULL_SEARCH_CONTEXT_DATA;
    }

    static Data<SearchContext> getSearchContext(WebElement element) {
        return getSubtypeOf("Search Context (Element)", element, Driver::getSearchContextOf, FactoryConstants.NULL_SEARCH_CONTEXT);
    }

    static Data<SearchContext> getSearchContext(WebDriver driver) {
        return getSubtypeOf("Search Context (Driver)", driver, Driver::getSearchContextOf, FactoryConstants.NULL_SEARCH_CONTEXT);
    }

    private static <T> DriverFunction<T> getSubtypeOfDriver(Function<WebDriver, T> getter, T defaultValue) {
        return driver -> getSubtypeOf("Driver", driver, getter, defaultValue);
    }

    static DriverFunction<JavascriptExecutor> getExecutorData() {
        return getSubtypeOfDriver(Driver::getExecutor, FactoryConstants.NULL_JAVASCRIPT_EXECUTOR);
    }

    static DriverFunction<TakesScreenshot> getScreenshotter() {
        return getSubtypeOfDriver(Driver::getScreenshotter, FactoryConstants.NULL_TAKES_SCREENSHOT);
    }

    static DriverFunction<TargetLocator> getTargetLocator() {
        return getSubtypeOfDriver(Driver::getTargetLocator, FactoryConstants.NULL_TARGET_LOCATOR);
    }

    private static <HandlerType, ReturnType> Data<ReturnType> executeCore(WebDriver driver, ExecutorData<HandlerType, String, Boolean, ReturnType> data, HandlerType handler, String script) {
        final var castData = data.castData;
        final var executor = data.getter.apply(driver);
        final var defaultValue = castData.defaultValue.object;
        if (isInvalidOrFalse(executor)) {
            return DataFactoryFunctions.getWithMessage(defaultValue, false, "Executor" + Strings.WAS_NULL);
        }

        final var parameters = new ExecuteCommonData<>(script, StringUtils::isNotBlank);
        final var exData = data.constructor.apply(parameters, handler);
        final var function = castData.caster.compose(exData.apply(executor.object));
        final var resultFunctions = data.resultHandlers;
        final var result = resultFunctions.castHandler.apply(new HandleResultData<>(function, script, defaultValue));
        final var status = result.status;
        var message = result.message.message;
        if (status) {
            message = resultFunctions.messageHandler.apply(status);
        }
        return DataFactoryFunctions.getWithMessage(result.object, status, message, result.exception);
    }

    private static <T> Data<T> getCore(WebDriver driver, String property, Predicate<T> guard, Function<WebDriver, T> function, T defaultValue) {
        final var result = function.apply(driver);
        return guard.test(result) ? (
            DataFactoryFunctions.getWithMessage(result, true, property + " is: \"" + result + "\"" + Strings.END_LINE)
        ) : DataFactoryFunctions.getWithMessage(defaultValue, false, property + Strings.WAS_NULL);
    }

    private static <HandlerType, ParameterType, ReturnType> Data<ReturnType> invokeCore(
        Data<MethodData> data,
        BaseInvokerDefaultsData<ParameterType, HandlerType, ReturnType> defaults,
        Function<InvokeCommonMessageParametersData, Function<Exception, String>> messageHandler,
        HandlerType handler,
        ParameterType parameter
    ) {
        final var castData = defaults.castData;
        final var methodData = data.object;
        final var method = methodData.method;
        final var function = castData.caster.compose(defaults.constructor.apply(handler).apply(method));
        final var result = defaults.castHandler.apply(new HandleResultData<>(function, parameter, castData.defaultValue));

        final var status = isValidNonFalse(result);
        var message = (!status) ? (
            messageHandler
                .apply(new InvokeCommonMessageParametersData(data.message.toString(), methodData.returnType, methodData.methodParameterTypes))
                .apply(result.exception)
        ) : result.message.toString();

        return DataFactoryFunctions.getWithMessage(result.object, status, message, result.exception);
    }

    private static <HandlerType, ParameterType, ReturnType> Data<ReturnType> invokeCore(
        Data<MethodData> data,
        BaseInvokerDefaultsData<ParameterType, HandlerType, ReturnType> defaults,
        Function<InvokeCommonMessageParametersData, Function<Exception, String>> messageHandler,
        HandlerType handler,
        Data<ParameterType> parameter
    ) {
        return invokeCore(data, defaults, messageHandler, handler, parameter.object);
    }

    private static <ParameterType, HandlerType, ReturnType> Function<Data<ParameterType>, Data<ReturnType>> invokeCore(
        Data<MethodData> data,
        BaseInvokerDefaultsData<ParameterType, HandlerType, ReturnType> defaults,
        Function<InvokeCommonMessageParametersData, Function<Exception, String>> messageHandler,
        HandlerType handler
    ) {
        return parameter -> invokeCore(data, defaults, messageHandler, handler, parameter);
    }

    private static <HandlerType, ReturnType> DriverFunction<ReturnType> executeCore(
        ExecuteCoreFunctionData<HandlerType> functionData,
        DriverFunction<ReturnType> negative,
        ExecutorData<HandlerType, String, Boolean, ReturnType> data,
        String script
    ) {
        final var isFunctionDataNotNull = isNotNull(functionData);
        final var name = isFunctionDataNotNull ? functionData.nameof : Strings.EMPTY;
        final var nameof = isNotBlank(name) ? name : "executeCore";
        return ifDriver(
            nameof,
            isBlank(script) || !(isFunctionDataNotNull && ScriptExecutions.isValidConstructorData(data)),
            driver -> executeCore(driver, data, functionData.handler, script),
            negative
        );
    }

    private static <T> DriverFunction<T> getCore(String property, Predicate<T> guard, Function<WebDriver, T> function, T defaultValue) {
        final var isPropertyNotBlank = isNotBlank(property);
        return ifDriver(
            isPropertyNotBlank ? "get" + property : "getCore",
            isPropertyNotBlank && areNotNull(guard, function, defaultValue),
            driver -> getCore(driver, property, guard, function, defaultValue),
            DataFactoryFunctions.getWithMessage(defaultValue, false, Strings.DRIVER_WAS_NULL)
        );
    }

    private static <ParameterType, HandlerType, ReturnType> Data<ReturnType> invokeCore(
        String name,
        Data<MethodData> data,
        BaseInvokerDefaultsData<ParameterType, HandlerType, ReturnType> defaults,
        Function<InvokeCommonMessageParametersData, Function<Exception, String>> messageHandler,
        HandlerType handler,
        ParameterType parameter
    ) {
        final var nameof = isNotBlank(name) ? name : "invokeCore";
        final var errorMessage = InvokeCoreValidator.isInvalidInvokeCoreParametersMessage(data,defaults, messageHandler, handler, parameter);
        return isBlank(errorMessage) ? (
            replaceName(invokeCore(data, defaults, messageHandler, handler, parameter), nameof)
        ) : DataFactoryFunctions.getWithNameAndMessage(null, false, nameof, errorMessage);
    }

    private static <ParameterType, HandlerType, ReturnType> DriverFunction<ReturnType> invokeCore(
        String name,
        Data<MethodData> data,
        BaseInvokerDefaultsData<ParameterType, HandlerType, ReturnType> defaults,
        Function<InvokeCommonMessageParametersData, Function<Exception, String>> messageHandler,
        HandlerType handler,
        DriverFunction<ParameterType> getter
    ) {
        final var nameof = isNotBlank(name) ? name : "invokeCore";
        final var errorMessage = InvokeCoreValidator.isInvalidInvokeCoreParametersMessage(data, defaults, messageHandler, handler, getter);
        final Data<ReturnType> negative = DataFactoryFunctions.getWithMessage(null, false, errorMessage);
        return ifDriver(nameof, errorMessage, validChain(getter, invokeCore(data, defaults, messageHandler, handler), negative), negative);
    }

    static <T extends ISizable> Data<Integer> getAmount(String nameof, Data<T> sizable, BiFunction<Boolean, Integer, String> messageHandler, Predicate<Integer> condition) {
        final var lNameof = isNotBlank(nameof) ? nameof : "getAmount";
        if (areAnyNull(messageHandler, condition) || isInvalidOrFalse(sizable)) {
            return replaceName(CoreDataConstants.NULL_INTEGER, lNameof);
        }

        final var size = sizable.object.size();
        final var status = condition.test(size);
        return DataFactoryFunctions.getWithNameAndMessage(size, status, nameof, messageHandler.apply(status, size));
    }

    static Data<Integer> getWindowHandleAmountData(Data<StringSet> data) {
        return getAmount("getWindowHandleAmount", data, Formatter::getWindowHandlesMessage, BasicPredicateFunctions::isPositiveNonZero);
    }

    static Data<Integer> getCountOfElements(Data<WebElementList> data) {
        return getAmount("getCountOfElements", data, Formatter::getCountOfElementsMessage, BasicPredicateFunctions::isNonNegative);
    }

    static DriverFunction<Integer> getCountOfElements(DriverFunction<WebElementList> getter) {
        return ifDriver("getCountOfElements", isNotNull(getter), SeleniumDataFunctions.isOfTypeNonEmpty(WebElement.class), getter, Driver::getCountOfElements, CoreDataConstants.NULL_INTEGER_NULL_DRIVER);
    }

    private static DriverFunction<String> getString(String property, Function<WebDriver, String> function) {
        return getCore(property, NullableFunctions::isNotNull, function, Strings.EMPTY);
    }

    static DriverFunction<String> getTitle() {
        return getString("Title", WebDriver::getTitle);
    }

    static DriverFunction<String> getWindowHandle() {
        return getString("WindowHandle", WebDriver::getWindowHandle);
    }

    static DriverFunction<String> getUrl() {
        return getCore("Url", StringUtils::isNotBlank, WebDriver::getCurrentUrl, Strings.EMPTY);
    }

    static DriverFunction<StringSet> getWindowHandles() {
        final var getStringSetOfWindowHandles = conditionalChain(NullableFunctions::isNotNull, WebDriver::getWindowHandles, StringSet::new, CoreConstants.NULL_STRING_SET);
        return getCore("WindowHandles", NullableFunctions::isNotNull, getStringSetOfWindowHandles, CoreConstants.NULL_STRING_SET);
    }

    static DriverFunction<Integer> getWindowHandleAmount() {
        return ifDriverGuardData("getWindowHandleAmount", Driver.getWindowHandles(), Driver::getWindowHandleAmountData, CoreDataConstants.NULL_INTEGER);
    }

    static <HandlerType, ReturnType> DriverFunction<ReturnType> execute(ExecuteCoreFunctionData<HandlerType> functionData, ExecuteCoreData<HandlerType, ReturnType> data, String script) {
        return executeCore(functionData, FunctionRepository.get(data.functionMap, data.negativeKeyData), data.data, script);
    }

    static <ReturnType> DriverFunction<ReturnType> executeParameters(
        ExecuteCoreFunctionData<ParametersFieldDefaultsData> functionData,
        ExecuteCoreData<ExecutorParametersFieldData, ReturnType> data,
        String script,
        Object[] parameters
    ) {
        final var negative = FunctionRepository.get(data.functionMap, data.negativeKeyData);
        final var handlerData = functionData.handler;
        if (isNull(parameters) || !handlerData.validator.test(parameters)) {
            return negative;
        }

        final var fnData = new ExecuteCoreFunctionData<>(functionData.nameof, new ExecutorParametersFieldData(parameters, handlerData));
        return executeCore(fnData, negative, data.data, script);
    }

    static DriverFunction<Object> execute(String script) {
        return execute(ExecuteCoreFunctionDataConstants.EXECUTE, ExecuteCoreDataConstants.EXECUTE_RETURN_OBJECT, script);
    }

    static DriverFunction<Object> executeAsync(String script) {
        return execute(ExecuteCoreFunctionDataConstants.EXECUTE_ASYNC, ExecuteCoreDataConstants.EXECUTE_RETURN_OBJECT, script);
    }

    static DriverFunction<Object> executeParameters(String script, Object[] parameters) {
        return executeParameters(ExecuteCoreFunctionDataConstants.EXECUTE_PARAMETERS, ExecuteCoreDataConstants.EXECUTE_PARAMETERS_RETURN_OBJECT, script, parameters);
    }

    static DriverFunction<Object> executeAsyncParameters(String script, Object[] parameters) {
        return executeParameters(ExecuteCoreFunctionDataConstants.EXECUTE_ASYNC_PARAMETERS, ExecuteCoreDataConstants.EXECUTE_PARAMETERS_RETURN_OBJECT, script, parameters);
    }

    static DriverFunction<Object> executeSingleParameter(String script, Object[] parameter) {
        return executeParameters(ExecuteCoreFunctionDataConstants.EXECUTE_SINGLE_PARAMETER, ExecuteCoreDataConstants.EXECUTE_PARAMETERS_RETURN_OBJECT, script, parameter);
    }

    static DriverFunction<Object> executeAsyncSingleParameter(String script, Object[] parameter) {
        return executeParameters(ExecuteCoreFunctionDataConstants.EXECUTE_ASYNC_SINGLE_PARAMETER, ExecuteCoreDataConstants.EXECUTE_PARAMETERS_RETURN_OBJECT, script, parameter);
    }

    private static Data<WebElement> invokeGetElement(Data<SearchContext> context, By locator) {
        if (isInvalidOrFalse(context) || isNull(locator)) {
            return SeleniumDataConstants.NULL_ELEMENT;
        }

        final var methodData = MethodRepository.getMethod(SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS, SeleniumMethodDefaults.FIND_ELEMENT);
        final var handler = new InvokerParameterizedParametersFieldData<>(CoreUtilities.toSingleElementArray(locator), SeleniumInvokeFunctionDefaults.SEARCH_CONTEXT_SINGLE_PARAMETER);
        final var messageHandler = new ParameterizedMessageData(locator.toString(), Formatter::getInvokeMethodParameterizedMessageFunction);
        return invokeCore("invokeGetElement", methodData, SeleniumInvokeFunctionDefaults.SEARCH_CONTEXT_PARAMETERS, messageHandler, handler, context.object);
    }

    static Function<Data<SearchContext>, Data<WebElement>> invokeGetElement(By locator) {
        final var message = isNullMessage(locator, "Locator");
        return isBlank(message) ? (
            context -> invokeGetElement(context, locator)
        ) : context -> DataFactoryFunctions.getInvalidWithNameAndMessage(SeleniumCoreConstants.STOCK_ELEMENT, "invokeGetElement", message);
    }

    private static DriverFunction<Void> invokeElementVoidMethod(String name, LazyElement element, MethodParametersData parameterData) {
        final var nameof = isNotBlank(name) ? name : "invokeElementVoidMethod";
        final var errorMessage = isNullLazyElementMessage(element) + MethodParametersDataValidators.isValid(parameterData);
        if (isNotBlank(errorMessage)) {
            return driver -> DataFactoryFunctions.getInvalidWithNameAndMessage(null, nameof, errorMessage);
        }

        final var methodData = MethodRepository.getMethod(SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS, parameterData);
        final var messageHandler = new RegularMessageData(Formatter::getInvokeMethodCommonMessageFunction);
        final var result = invokeCore(nameof, methodData, SeleniumInvokeFunctionDefaults.VOID_REGULAR, messageHandler, InvokeFunctions::invoke, element.get());
        return DriverFunctionFactoryFunctions.prependMessage(result, parameterData.methodName + Strings.COLON_SPACE);
    }

    private static DriverFunction<Boolean> invokeElementBooleanMethod(String name, LazyElement element, MethodParametersData parameterData) {
        final var nameof = isNotBlank(name) ? name : "invokeElementBooleanMethod";
        final var errorMessage = isNullLazyElementMessage(element) + MethodParametersDataValidators.isValid(parameterData);
        if (isNotBlank(errorMessage)) {
            return driver -> DataFactoryFunctions.getInvalidWithNameAndMessage(null, nameof, errorMessage);
        }

        final var methodData = MethodRepository.getMethod(SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS, parameterData);
        final var messageHandler = new RegularMessageData(Formatter::getInvokeMethodCommonMessageFunction);
        final var result = invokeCore(nameof, methodData, SeleniumInvokeFunctionDefaults.BOOLEAN_REGULAR, messageHandler, InvokeFunctions::invoke, element.get());
        return DriverFunctionFactoryFunctions.prependMessage(result, parameterData.methodName + Strings.COLON_SPACE);
    }

    private static DriverFunction<String> invokeElementStringMethod(String name, LazyElement element, MethodParametersData parameterData) {
        final var nameof = isNotBlank(name) ? name : "invokeElementStringMethod";
        final var errorMessage = isNullLazyElementMessage(element) + MethodParametersDataValidators.isValid(parameterData);
        if (isNotBlank(errorMessage)) {
            return driver -> DataFactoryFunctions.getInvalidWithNameAndMessage(null, nameof, errorMessage);
        }

        final var methodData = MethodRepository.getMethod(SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS, parameterData);
        final var messageHandler = new RegularMessageData(Formatter::getInvokeMethodCommonMessageFunction);
        final var result = invokeCore(nameof, methodData, SeleniumInvokeFunctionDefaults.STRING_REGULAR, messageHandler, InvokeFunctions::invoke, element.get());
        return DriverFunctionFactoryFunctions.prependMessage(result, parameterData.methodName + Strings.COLON_SPACE);
    }

    private static DriverFunction<String> invokeElementStringMethod(String name, LazyElement element, String parameter, MethodParametersData parameterData) {
        final var nameof = isNotBlank(name) ? name : "invokeElementStringMethod";
        final var errorMessage = isNullLazyElementMessage(element) + MethodParametersDataValidators.isValid(parameterData) + isBlankMessage(parameter, "Execution parameter value");
        if (isNotBlank(errorMessage)) {
            return driver -> DataFactoryFunctions.getInvalidWithNameAndMessage(null, nameof, errorMessage);
        }

        final var methodData = MethodRepository.getMethod(SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS, parameterData);
        final var handler = new InvokerParameterizedParametersFieldData<>(CoreUtilities.toSingleElementArray(parameter, StringUtils::isNotBlank), SeleniumInvokeFunctionDefaults.SINGLE_PARAMETER);
        final var messageHandler = new ParameterizedMessageData(parameter, Formatter::getInvokeMethodParameterizedMessageFunction);
        final var result = invokeCore(nameof, methodData, SeleniumInvokeFunctionDefaults.STRING_PARAMETERS, messageHandler, handler, element.get());
        return DriverFunctionFactoryFunctions.prependMessage(result, parameterData.methodName + Strings.COLON_SPACE);
    }

    private static DriverFunction<Boolean> invokeElementDisplayed(LazyElement element) {
        return invokeElementBooleanMethod(InvokeConstants.ELEMENT_DISPLAYED, element, MethodDefaults.IS_DISPLAYED);
    }

    private static DriverFunction<Boolean> invokeElementEnabled(LazyElement element) {
        return invokeElementBooleanMethod(InvokeConstants.ELEMENT_ENABLED, element, MethodDefaults.IS_ENABLED);
    }

    private static DriverFunction<Boolean> invokeElementSelected(LazyElement element) {
        return invokeElementBooleanMethod(InvokeConstants.ELEMENT_SELECTED, element, MethodDefaults.IS_SELECTED);
    }

    private static DriverFunction<String> invokeGetElementText(LazyElement element) {
        return invokeElementStringMethod(InvokeConstants.GET_ELEMENT_TEXT, element, MethodDefaults.GET_TEXT);
    }

    private static DriverFunction<String> invokeGetElementTagname(LazyElement element) {
        return invokeElementStringMethod(InvokeConstants.GET_ELEMENT_TAGNAME, element, MethodDefaults.GET_TAG_NAME);
    }

    private static DriverFunction<String> invokeGetElementAttribute(LazyElement element, String attribute) {
        return invokeElementStringMethod(InvokeConstants.GET_ELEMENT_ATTRIBUTE, element, attribute, MethodDefaults.GET_ATTRIBUTE);
    }

    private static DriverFunction<String> invokeGetElementCssValue(LazyElement element, String cssValue) {
        return invokeElementStringMethod(InvokeConstants.GET_ELEMENT_CSS_VALUE, element, cssValue, MethodDefaults.GET_CSS_VALUE);
    }

    private static DriverFunction<Boolean> invokeElementClickable(LazyElement element) {
        return ifDriver(
            InvokeConstants.ELEMENT_CLICKABLE,
            isNullLazyElementMessage(element),
            SeleniumExecutor.execute(Executor::aggregateMessage, invokeElementDisplayed(element), invokeElementEnabled(element)),
            CoreDataConstants.NULL_BOOLEAN
        );
    }

    static DriverFunction<Void> invokeElementClick(LazyElement element) {
        return invokeElementVoidMethod(InvokeConstants.CLICK, element, MethodDefaults.CLICK);
    }

    static DriverFunction<Void> invokeElementClear(LazyElement element) {
        return invokeElementVoidMethod(InvokeConstants.CLEAR, element, MethodDefaults.CLEAR);
    }

    static DriverFunction<Void> invokeElementSendKeys(LazyElement element, String parameter) {
        final var nameof = "invokeElementSendKeys";
        final var errorMessage = isNullLazyElementMessage(element) + isBlankMessage(parameter, "Send keys value");
        if (isNotBlank(errorMessage)) {
            return driver -> DataFactoryFunctions.getInvalidWithNameAndMessage(null, nameof, errorMessage);
        }

        final var methodParameterData = MethodDefaults.SEND_KEYS;
        final var methodData = MethodRepository.getMethod(SeleniumCoreConstants.DEFAULT_WEB_ELEMENT_METHOD_PARAMETERS, methodParameterData);
        final var keyData = new CharSequence[]{parameter};
        final var handler = new InvokerParameterizedParametersFieldData<>(CoreUtilities.toSingleElementArray(keyData, NullableFunctions::isNotNull), SeleniumInvokeFunctionDefaults.SINGLE_PARAMETER);
        final var messageHandler = new ParameterizedMessageData(parameter, Formatter::getInvokeMethodParameterizedMessageFunction);
        final var result = invokeCore(nameof, methodData, SeleniumInvokeFunctionDefaults.VOID_PARAMETERS, messageHandler, handler, element.get());
        return DriverFunctionFactoryFunctions.prependMessage(result, methodParameterData.methodName + Strings.COLON_SPACE);
    }

    private static Data<Boolean> isElementCondition(String name, Data<WebElement> data, UnaryOperator<Boolean> inverter, String descriptor, String negator) {
        final var nameof = isNotBlank(name) ? name : "isElementCondition";
        final var errorMessage = isNullMessage(inverter, "Inverter");
        if (isNotBlank(errorMessage)) {
            return DataFactoryFunctions.getInvalidBooleanWithNameAndMessage(nameof, errorMessage);
        }

        final var status = isNotNullWebElement(data);
        final var lStatus = inverter.apply(status);
        final var returnData = Formatter.getConditionMessage(nameof, FormatterStrings.isMessageMap, lStatus, descriptor, negator);
        var message = returnData.message.getMessage();
        if (CoreUtilities.isFalse(lStatus)) {
            message += data.message.getMessage();
        }

        return DataFactoryFunctions.getBoolean(lStatus, nameof, message);
    }

    private static Function<Data<WebElement>, Data<Boolean>> isElementCondition(String name, UnaryOperator<Boolean> inverter, String descriptor, String negator) {
        return element -> isElementCondition(name, element, inverter, descriptor, negator);
    }

    private static DriverFunction<Boolean> isElementCondition(String name, DriverFunction<WebElement> getter, UnaryOperator<Boolean> inverter, String descriptor, String negator) {
        final var negative = CoreDataConstants.NULL_BOOLEAN;
        return ifDriver(
            "isElementCondition",
            Formatter.getElementConditionMessage(getter, inverter),
            validElementChain(getter, isElementCondition(name, inverter, descriptor, negator), negative),
            negative
        );
    }

    private static DriverFunction<Boolean> isElementConditionDataCore(LazyElement element, Function<LazyElement, DriverFunction<Boolean>> condition, String descriptor, String negator) {
        return ifDriver(
            "isElementConditionDataCore",
            isNotNullLazyElement(element) && areNotNull(condition, negator) && isNotBlank(descriptor),
            isElementCondition(
                element.name,
                SeleniumExecutor.execute(condition.apply(element), element.get()),
                Objects.equals(negator, Strings.EMPTY) ? CardinalitiesFunctions::noopBoolean : CardinalitiesFunctions::invertBoolean,
                descriptor,
                negator
            ),
            CoreDataConstants.DATA_PARAMETER_WAS_NULL
        );
    }

    static DriverFunction<Boolean> isElementCondition(ElementCondition data) {
        return isElementConditionDataCore(data.data, data.condition, data.descriptor, Strings.OPTION_EMPTY);
    }

    static DriverFunction<Boolean> isElementNotCondition(ElementCondition data) {
        return isElementConditionDataCore(data.data, data.condition, data.descriptor, Strings.OPTION_NOT);
    }

    static DriverFunction<Boolean> isElementAbsent(LazyElement element) {
        final var nameof = "isElementAbsent";
        final var errorMessage = isNullLazyElementMessage(element);
        if (isNotBlank(errorMessage)) {
            return driver -> DataFactoryFunctions.getInvalidBooleanWithNameAndMessage(nameof, errorMessage);
        }

        final var negative = CoreDataConstants.NULL_BOOLEAN;
        return DriverFunctionFactoryFunctions.replaceName(
            conditionalChain(NullableFunctions::isNotNull, element.get(), isElementCondition(element.name, CardinalitiesFunctions::invertBoolean, Strings.ABSENT, Strings.OPTION_NOT), negative),
            nameof
        );
    }

    private static DriverFunction<String> getFormattedElementValueData(DriverFunction<String> data, String descriptor) {
        return ifDriver("getFormattedElementValueData", areNotNull(data, descriptor), data, Formatter.getValueMessage(descriptor), CoreDataConstants.GET_FORMATTED_ELEMENT_VALUE_ERROR);
    }


    static DriverFunction<Boolean> isElementPresent(LazyElement element) {
        return isElementCondition(new ElementCondition(element, SeleniumDataValidators::isValidWebElement, Strings.PRESENT));
    }

    static DriverFunction<Boolean> isElementLambdaDataCore(
        LazyElement element,
        Function<LazyElement, DriverFunction<Boolean>> condition,
        Function<ElementCondition, DriverFunction<Boolean>> formatter,
        String descriptor
    ) {
        return ifDriver(
            "isElementLambdaDataCore",
            isNotNullLazyElement(element) && areNotNull(condition, formatter) && isNotBlank(descriptor),
            SeleniumExecutor.execute(isElementPresent(element), formatter.apply(new ElementCondition(element, condition, descriptor))),
            CoreDataConstants.PARAMETERS_NULL_BOOLEAN
        );
    }

    static DriverFunction<String> getElementValueLambdaDataCore(
        LazyElement element,
        Function<LazyElement, DriverFunction<String>> getter,
        BiFunction<DriverFunction<String>, String, DriverFunction<String>> formatter,
        String descriptor
    ) {
        return ifDriver(
            "getElementValueLambdaDataCore",
            isNotNullLazyElement(element) && areNotNull(getter, formatter) && isNotBlank(descriptor),
            SeleniumExecutor.execute(isElementPresent(element), formatter.apply(getter.apply(element), descriptor)),
            CoreDataConstants.PARAMETERS_NULL_STRING
        );
    }

    static <T> DriverFunction<String> getElementValueLambdaDataCore(
        LazyElement element,
        T parameter,
        BiFunction<LazyElement, T, DriverFunction<String>> getter,
        BiFunction<DriverFunction<String>, String, DriverFunction<String>> formatter,
        String descriptor
    ) {
        return ifDriver(
            "getElementValueLambdaDataCore",
            isNotNullLazyElement(element) && areNotNull(getter, formatter) && isNotBlank(descriptor),
            SeleniumExecutor.execute(isElementPresent(element), formatter.apply(getter.apply(element, parameter), descriptor)),
            CoreDataConstants.PARAMETERS_NULL_STRING
        );
    }

    static DriverFunction<Boolean> isElementConditionLambdaDataCore(LazyElement element, Function<LazyElement, DriverFunction<Boolean>> condition, String descriptor) {
        return isElementLambdaDataCore(element, condition, Driver::isElementCondition, descriptor);
    }

    static DriverFunction<Boolean> isElementNotConditionLambdaDataCore(LazyElement element, Function<LazyElement, DriverFunction<Boolean>> condition, String descriptor) {
        return isElementLambdaDataCore(element, condition, Driver::isElementNotCondition, descriptor);
    }

    static DriverFunction<String> getElementValueData(LazyElement element, Function<LazyElement, DriverFunction<String>> getter, String descriptor) {
        return getElementValueLambdaDataCore(element, getter, Driver::getFormattedElementValueData, descriptor);
    }

    static <T> DriverFunction<String> getElementValueData(LazyElement element, T parameter, BiFunction<LazyElement, T, DriverFunction<String>> getter, String descriptor) {
        return getElementValueLambdaDataCore(element, parameter, getter, Driver::getFormattedElementValueData, descriptor);
    }

    static DriverFunction<Boolean> isElement(Function<LazyElement, DriverFunction<Boolean>> elementCondition, LazyElement element) {
        return ifDriver("isElement", isNotNullLazyElement(element) && areNotNull(elementCondition), elementCondition.apply(element), CoreDataConstants.PARAMETERS_NULL_BOOLEAN);
    }

    static DriverFunction<Boolean> isElement(Function<LazyElement, DriverFunction<Boolean>> elementCondition, Data<LazyElement> data) {
        return isValidNonFalse(data) ? isElement(elementCondition, data.object) : DriverFunctionConstants.NULL_BOOLEAN;
    }

    static DriverFunction<String> getElementText(Data<LazyElement> data) {
        return ifDriver("getElementText", isValidNonFalse(data), getElementValueData(data.object, Driver::invokeGetElementText, "Text"), CoreDataConstants.NULL_STRING);
    }

    static DriverFunction<String> getElementTagName(Data<LazyElement> data) {
        return ifDriver("getElementTagName", isValidNonFalse(data), getElementValueData(data.object, Driver::invokeGetElementTagname, "Tagname"), CoreDataConstants.NULL_STRING);
    }

    static DriverFunction<String> getElementAttribute(Data<LazyElement> data, String attribute) {
        return ifDriver(
            "getElementAttribute",
            Formatter.getElementAttributeMessage(data, attribute, "attribute"),
            getElementValueData(data.object, attribute, Driver::invokeGetElementAttribute, "Attribute(\"" + attribute + "\")"),
            CoreDataConstants.NULL_STRING
        );
    }

    static DriverFunction<String> getElementAttributeValue(Data<LazyElement> data) {
        return getElementAttribute(data, "value");
    }

    static DriverFunction<String> getElementCssValue(Data<LazyElement> data, String cssValue) {
        return ifDriver(
            "getElementCssValue",
            Formatter.getElementAttributeMessage(data, cssValue, "CSS value"),
            getElementValueData(data.object, cssValue, Driver::invokeGetElementCssValue, "Css value(\"" + cssValue + "\")"),
            CoreDataConstants.NULL_STRING
        );
    }

    static DriverFunction<Boolean> isElementDisplayed(LazyElement element) {
        return isElementConditionLambdaDataCore(element, Driver::invokeElementDisplayed, Strings.DISPLAYED);
    }

    static DriverFunction<Boolean> isElementEnabled(LazyElement element) {
        return isElementConditionLambdaDataCore(element, Driver::invokeElementEnabled, Strings.ENABLED);
    }

    static DriverFunction<Boolean> isElementClickable(LazyElement element) {
        return isElementConditionLambdaDataCore(element, Driver::invokeElementClickable, Strings.CLICKABLE);
    }

    static DriverFunction<Boolean> isElementSelected(LazyElement element) {
        return isElementConditionLambdaDataCore(element, Driver::invokeElementSelected, Strings.SELECTED);
    }

    static DriverFunction<Boolean> isElementHidden(LazyElement element) {
        return isElementNotConditionLambdaDataCore(element, Driver::invokeElementDisplayed, Strings.HIDDEN);
    }

    static DriverFunction<Boolean> isElementDisabled(LazyElement element) {
        return isElementNotConditionLambdaDataCore(element, Driver::invokeElementEnabled, Strings.DISABLED);
    }

    static DriverFunction<Boolean> isElementUnclickable(LazyElement element) {
        return isElementNotConditionLambdaDataCore(element, Driver::invokeElementClickable, Strings.UNCLICKABLE);
    }

    static DriverFunction<Boolean> isElementUnselected(LazyElement element) {
        return isElementNotConditionLambdaDataCore(element, Driver::invokeElementSelected, Strings.UNSELECTED);
    }

    static DriverFunction<String> getElementText(LazyElement element) {
        return getElementValueData(element, Driver::invokeGetElementText, "Text");
    }

    static DriverFunction<String> getElementTagName(LazyElement element) {
        return getElementValueData(element, Driver::invokeGetElementTagname, "Tagname");
    }

    static DriverFunction<String> getElementAttribute(LazyElement element, String attribute) {
        return getElementValueData(element, attribute, Driver::invokeGetElementAttribute, "Attribute(\"" + attribute + "\")");
    }

    static DriverFunction<String> getElementAttributeValue(LazyElement element) {
        return getElementAttribute(element, "value");
    }

    static DriverFunction<String> getElementCssValue(LazyElement element, String cssValue) {
        return getElementValueData(element, cssValue, Driver::invokeGetElementCssValue, "Css value(\"" + cssValue + "\")");
    }

    static DriverFunction<Boolean> isElementPresent(By locator, SingleGetter getter) {
        return isElement(Driver::isElementPresent, LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<Boolean> isElementDisplayed(By locator, SingleGetter getter) {
        return isElement(Driver::invokeElementDisplayed, LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<Boolean> isElementEnabled(By locator, SingleGetter getter) {
        return isElement(Driver::invokeElementEnabled, LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<Boolean> isElementClickable(By locator, SingleGetter getter) {
        return isElement(Driver::isElementClickable, LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<Boolean> isElementSelected(By locator, SingleGetter getter) {
        return isElement(Driver::invokeElementSelected, LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<Boolean> isElementAbsent(By locator, SingleGetter getter) {
        return isElement(Driver::isElementAbsent, LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<Boolean> isElementHidden(By locator, SingleGetter getter) {
        return isElement(Driver::isElementHidden, LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<Boolean> isElementDisabled(By locator, SingleGetter getter) {
        return isElement(Driver::isElementDisabled, LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<Boolean> isElementUnclickable(By locator, SingleGetter getter) {
        return isElement(Driver::isElementUnclickable, LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<Boolean> isElementUnselected(By locator, SingleGetter getter) {
        return isElement(Driver::isElementUnselected, LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<String> getElementText(By locator, SingleGetter getter) {
        return getElementText(LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<String> getElementTagName(By locator, SingleGetter getter) {
        return getElementTagName(LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<String> getElementAttribute(By locator, SingleGetter getter, String attribute) {
        return getElementAttribute(LocatorRepository.getIfContains(locator, getter), attribute);
    }
    static DriverFunction<String> getElementAttributeValue(By locator, SingleGetter getter) {
        return getElementAttributeValue(LocatorRepository.getIfContains(locator, getter));
    }
    static DriverFunction<String> getElementCssValue(By locator, SingleGetter getter, String cssValue) {
        return getElementCssValue(LocatorRepository.getIfContains(locator, getter), cssValue);
    }

    static DriverFunction<Boolean> isElementPresent(By locator) {
        return isElement(Driver::isElementPresent, LocatorRepository.getIfContains(locator));
    }
    static DriverFunction<Boolean> isElementDisplayed(By locator) {
        return isElement(Driver::isElementDisplayed, LocatorRepository.getIfContains(locator));
    }
    static DriverFunction<Boolean> isElementEnabled(By locator) {
        return isElement(Driver::isElementEnabled, LocatorRepository.getIfContains(locator));
    }
    static DriverFunction<Boolean> isElementClickable(By locator) {
        return isElement(Driver::isElementClickable, LocatorRepository.getIfContains(locator));
    }
    static DriverFunction<Boolean> isElementSelected(By locator) {
        return isElement(Driver::isElementSelected, LocatorRepository.getIfContains(locator));
    }
    static DriverFunction<Boolean> isElementAbsent(By locator) {
        return isElement(Driver::isElementAbsent, LocatorRepository.getIfContains(locator));
    }
    static DriverFunction<Boolean> isElementHidden(By locator) {
        return isElement(Driver::isElementHidden, LocatorRepository.getIfContains(locator));
    }
    static DriverFunction<Boolean> isElementDisabled(By locator) {
        return isElement(Driver::isElementDisabled, LocatorRepository.getIfContains(locator));
    }
    static DriverFunction<Boolean> isElementUnclickable(By locator) {
        return isElement(Driver::isElementUnclickable, LocatorRepository.getIfContains(locator));
    }
    static DriverFunction<Boolean> isElementUnselected(By locator) {
        return isElement(Driver::isElementUnselected, LocatorRepository.getIfContains(locator));
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

    private static Data<WebElementList> getElements(SearchContext context, By locator) {
        final var nameof = "getElements";
        final Function<SearchContext, List<WebElement>> function = locator::findElements;
        final Function<SearchContext, WebElementList> composed = function.andThen(WebElementList::new);

        final var result = ExceptionHandlers.findElementsHandler(new HandleResultData<>(composed, context, SeleniumCoreConstants.NULL_ELEMENT_LIST));
        final var list = result.object;
        final var status = result.status;
        final var exception = result.exception;
        final var message = status ? Formatter.getFindElementsMessage(locator.toString(), list.size()) : "An Exception(" + exception.getClass() + ") has occurred" + Strings.END_LINE;
        final var messageData = new MethodMessageData(nameof, message);
        return DataFactoryFunctions.getWithMethodMessage(list, status, messageData, exception);
    }

    private static Data<WebElementList> getElements(Data<SearchContext> contextData, LazyLocator locator) {
        final var nameof = "getElements";
        final var negative = SeleniumCoreConstants.NULL_ELEMENT_LIST;
        var errorMessage = isNullLazyDataMessage(locator) + isInvalidOrFalseMessage(contextData);
        if (isNotBlank(errorMessage)) {
            return DataFactoryFunctions.getInvalidWithNameAndMessage(negative, nameof, errorMessage);
        }

        final var context = contextData.object;
        errorMessage = isNullMessage(context, "Context");
        if (isNotBlank(errorMessage)) {
            return DataFactoryFunctions.getInvalidWithNameAndMessage(negative, nameof, errorMessage);
        }

        final var lLocator = getLocator(locator);
        errorMessage = isInvalidOrFalseMessage(lLocator);
        if (isNotBlank(errorMessage)) {
            return DataFactoryFunctions.getInvalidWithNameAndMessage(negative, nameof, errorMessage);
        }

        return getElements(context, lLocator.object);
    }

    private static Function<Data<SearchContext>, Data<WebElementList>> getElementsL(LazyLocator locator) {
        return context -> getElements(context, locator);
    }

    private static DriverFunction<WebElementList> getElements(DriverFunction<SearchContext> contextGetter, LazyLocator locator) {
        final var negative = replaceMessage(SeleniumDataConstants.NULL_LIST, "There were parameter issues.");
        return ifDriver("getElements", areNotNull(locator, contextGetter), validChain(contextGetter, getElementsL(locator), negative), negative);
    }

    static Data<WebElement> getElementByIndex(Data<WebElementList> data, int index) {
        final var nameof = "getElementByIndex";
        if (isInvalidOrFalse(data) || BasicPredicateFunctions.isNegative(index)) {
            return prependMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, "Data or index was null. Index: " + index + " Data: " + data.toString());
        }

        final var object = data.object;
        if (object.isNullOrEmpty()) {
            return prependMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, "List " + Strings.WAS_NULL);
        }

        final var size = object.size();
        final var status = (size > index);
        final var message = "Element was" + (status ? "" : "n't") + " found by index(\"" + index + "\"), list size: " + size + Strings.END_LINE + data.message;
        return DataFactoryFunctions.getWithNameAndMessage(object.get(index), status, nameof, message);
    }

    private static Function<Data<WebElementList>, Data<WebElement>> getElementIndexWasNegative(int index) {
        return listData -> MessageFormatter.getInvalidIndexMessageFunction(index);
    }

    static Function<Data<WebElementList>, Data<WebElement>> getElementByIndex(int index) {
        return BasicPredicateFunctions.isNonNegative(index) ? data -> getElementByIndex(data, index) : getElementIndexWasNegative(index);
    }

    static DriverFunction<WebElement> getElementByIndex(DriverFunction<WebElementList> getter, int index) {
        return ifDriver(
            "getElementByIndexFrom",
            isNotNull(getter) && BasicPredicateFunctions.isNonNegative(index),
            validChain(getter, getElementByIndex(index), SeleniumDataConstants.NULL_ELEMENT),
            SeleniumDataConstants.NULL_ELEMENT
        );
    }

    static Data<WebElement> getElementByContainedText(Data<WebElementList> data, String text) {
        final var nameof = "getElementByContainedText";
        final var errorMessage = ElementGetterValidators.isInvalidElementByTextParameters(data, text);
        if (isNotBlank(errorMessage)) {
            return prependMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, errorMessage);
        }

        final var object = data.object;
        if (object.isNullOrEmpty()) {
            return prependMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, "List " + Strings.WAS_NULL);
        }

        final var length = object.size();
        WebElement current = SeleniumCoreConstants.STOCK_ELEMENT;
        var index = 0;
        for (; StringUtilities.uncontains(current.getText(), text) && (index < length); ++index) {
            current = object.get(index);
        }

        final var status = isNotNullWebElement(current) && (index < length);
        final var message = "Element was" + (status ? "" : "n't") + " found by text(\"" + text + "\"), list size: " + length + Strings.END_LINE + data.message;
        return DataFactoryFunctions.getWithNameAndMessage(current, status, nameof, message);
    }

    private static Function<Data<WebElementList>, Data<WebElement>> getElementTextWasBlank(String message) {
        return listData -> MessageFormatter.getInvalidTextMessageFunction(message);
    }

    static Function<Data<WebElementList>, Data<WebElement>> getElementByContainedText(String message) {
        return isNotBlank(message) ? data -> getElementByContainedText(data, message) : getElementTextWasBlank(message);
    }

    static DriverFunction<WebElement> getElementByContainedText(DriverFunction<WebElementList> getter, String message) {
        return ifDriver(
            "getElementByContainedText",
            isNotNull(getter) && isNotBlank(message),
            validChain(getter, getElementByContainedText(message), SeleniumDataConstants.NULL_ELEMENT),
            SeleniumDataConstants.NULL_ELEMENT
        );
    }

    static DriverFunction<WebElement> getElementByContainedText(By locator, String message) {
        return ifDriver(
            "getElementByContainedText",
            isNotNull(locator) && isNotBlank(message),
            validChain(getElements(locator), getElementByContainedText(message), SeleniumDataConstants.NULL_ELEMENT),
            SeleniumDataConstants.NULL_ELEMENT
        );
    }

    static Data<WebElement> getElementFromSingle(Data<WebElementList> data) {
        final var nameof = "getElementFromSingle";
        return isValidNonFalse(data) ? getElementByIndex(data, 0) : prependMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, "Data or index" + Strings.WAS_NULL + data.toString());
    }

    static DriverFunction<WebElement> getElementFromSingle(DriverFunction<WebElementList> getter) {
        return ifDriver("getElementFromSingle", isNotNull(getter), validChain(getter, getElementByIndex(0), SeleniumDataConstants.NULL_ELEMENT), SeleniumDataConstants.NULL_ELEMENT);
    }

    static DriverFunction<WebElement> getElementByIndex(By locator, int index) {
        return ifDriver(
            "getElementByIndexFrom",
            isNotNull(locator) && BasicPredicateFunctions.isNonNegative(index),
            validChain(getElements(locator), getElementByIndex(index), SeleniumDataConstants.NULL_ELEMENT),
            SeleniumDataConstants.NULL_ELEMENT
        );
    }

    private static Data<WebElementList> getElementsAmountData(Data<WebElementList> data, LazyLocator locator, int expected) {
        if (isInvalidOrFalse(data)) {
            return replaceMessage(SeleniumDataConstants.NULL_LIST, "Passed data " + Strings.WAS_NULL);
        }

        final var object = data.object;
        final var size = object.size();
        final var status = size == expected;
        final var messageData = new MethodMessageData("getElementsAmountData", Formatter.getElementsAmountMessage(locator, status, expected, size));
        final var exception = data.exception;
        return new Data<>(object, status, messageData, exception, exception.getMessage());
    }

    static DriverFunction<WebElementList> getElements(LazyLocator locator) {
        return ifDriver("getElements", Formatter.isNullLazyDataMessage(locator), getElements(Driver::getSearchContext, locator), SeleniumDataConstants.LOCATOR_WAS_NULL_LIST);
    }

    static DriverFunction<WebElementList> getElements(By locator) {
        return ifDriver("getElements", isNullMessage(locator, "Locator"), getElements(getLazyLocator(locator)), SeleniumDataConstants.LOCATOR_WAS_NULL_LIST);
    }

    static DriverFunction<WebElementList> getElements(LazyLocatorList locators, Function<By, DriverFunction<WebElement>> getter) {
        final var nameof = "getElements: (List locators)";
        return ifDriver(
            nameof,
            areNotNull(locators, getter) && locators.isNotNullAndNonEmpty(),
            driver -> {
                final var elementList = new WebElementList();
                final var length = locators.size();
                var message = "";
                Data<WebElement> data;
                LazyLocator locator;
                for(var index = 0; index < length; ++index) {
                    locator = locators.get(index);
                    if (isNull(locator)) {
                        return SeleniumDataConstants.NULL_LIST;
                    }

                    data = getter.apply(getLocator(locator).object).apply(driver);
                    message += index + data.message.toString() + Strings.END_LINE;
                    if (isNullWebElement(data)) {
                        continue;
                    }

                    elementList.add(data.object);
                }

                return DataFactoryFunctions.getWithNameAndMessage(elementList, elementList.isNotNullAndNonEmpty(), nameof, message);
            },
            replaceMessage(SeleniumDataConstants.NULL_LIST, nameof, Strings.DRIVER_WAS_NULL)
        );
    }

    static DriverFunction<Integer> getCountOfElements(By by) {
        return ifDriver("getCountOfElements", isNullMessage(by, "By locator"), getCountOfElements(getElements(by)), CoreDataConstants.NO_ELEMENTS_FOUND);
    }

    static DriverFunction<WebElementList> getElementsAmountData(DriverFunction<WebElementList> getter, LazyLocator locator, int expected) {
        return ifDriver(
            "getElementsAmountData",
            isNotNull(getter) && isNotNullLazyData(locator) && BasicPredicateFunctions.isNonNegative(expected),
            driver -> getElementsAmountData(getter.apply(driver), locator, expected),
            SeleniumDataConstants.NULL_LIST
        );
    }

    static DriverFunction<WebElementList> getElementsAmount(By locator, int expected) {
        final var lazyLocator = getLazyLocator(locator);
        return ifDriver(
            "getElementsAmount",
            isNotNullLazyData(lazyLocator) && BasicPredicateFunctions.isNonNegative(expected),
            getElementsAmountData(getElements(lazyLocator), lazyLocator, expected),
            SeleniumDataConstants.NULL_LIST
        );
    }

    static DriverFunction<WebElementList> getElementsAmount(LazyLocator locator, int expected) {
        return ifDriver(
            "getElementsAmount",
            isNotNullLazyData(locator) && BasicPredicateFunctions.isNonNegative(expected),
            getElementsAmountData(getElements(locator), locator, expected),
            SeleniumDataConstants.NULL_LIST
        );
    }

    static DriverFunction<WebElementList> getSingleElementList(By locator) {
        return getElementsAmount(locator, 1);
    }

    static DriverFunction<WebElementList> getSingleElementList(LazyLocator locator) {
        return getElementsAmount(locator, 1);
    }

    static DriverFunction<WebElement> getElement(By locator) {
        return ifDriver("getElement", isNullMessage(locator), getElementFromSingle(getSingleElementList(locator)), SeleniumDataConstants.NULL_ELEMENT);
    }

    static DriverFunction<WebElement> getElement(LazyLocator locator) {
        return ifDriver("getElement", Formatter.isNullLazyDataMessage(locator), getElementFromSingle(getSingleElementList(locator)), SeleniumDataConstants.NULL_ELEMENT);
    }

    private static Data<WebElement> getShadowRootElementCore(Data<WebElement> data) {
        final var exception = data.exception;
        final var status = isNotNullWebElement(data);
        final var messageData = new MethodMessageData("getShadowRootElementCore", Formatter.getShadowRootElementMessage(data.message.getMessage(), status));
        return CoreUtilities.isNonException(exception) ? (
            DataFactoryFunctions.getWithMethodMessage(data.object, status, messageData)
        ) : DataFactoryFunctions.getWithMethodMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, messageData, exception);
    }

    private static DriverFunction<WebElement> getShadowRootElement(Data<WebElement> data) {
        return ifDriver(
            "getShadowRootElement",
            isInvalidOrFalseMessage(data),
            validChain(Execute.getShadowRootElement(data), Driver::getShadowRootElementCore, SeleniumDataConstants.NULL_ELEMENT),
            SeleniumDataConstants.NULL_ELEMENT
        );
    }
    static DriverFunction<WebElement> getShadowRootElement(DriverFunction<WebElement> getter) {
        return ifDriver("getShadowRootElement", isNullMessage(getter, "Getter"), Execute.getShadowRoot(getter), SeleniumDataConstants.NULL_ELEMENT);
    }

    static DriverFunction<WebElement> getShadowRootElement(LazyElement element) {
        return getShadowRootElement(element.get());
    }

    static DriverFunction<WebElement> getShadowRootElement(By locator, SingleGetter getter) {
        return ifDriver("getShadowRootElement", areNotNull(locator, getter), getShadowRootElement(LocatorRepository.getIfContains(locator, getter).object), SeleniumDataConstants.NULL_ELEMENT);
    }

    static DriverFunction<WebElement> getShadowRootElement(By locator) {
        return getShadowRootElement(locator, SingleGetter.DEFAULT);
    }

    static DriverFunction<WebElement> getRootElementByInvokedElement(Data<WebElement> data, By locator) {
        final var nameof = "getRootElementByInvokedElement";
        return ifDriver(
            nameof,
            isNotNull(locator) && isValidNonFalse(data),
            getShadowRootElement(invokeGetElement(locator).apply(getSearchContextOf("Element data", data))),
            replaceMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, "Parameters were wrong: locator or data.")
        );
    }

    static DriverFunction<WebElement> getRootElementByInvokedElement(LazyElement element, By locator) {
        final var nameof = "getRootElementByInvokedElement";
        return ifDriver(
            nameof,
            isNotNullLazyElement(element) && isNotNull(locator),
            getRootElementByInvokedElement(element, getLazyLocator(locator)),
            replaceMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, "Parameters were wrong: locator or data.")
        );
    }

    static DriverFunction<WebElement> getRootElementByInvokedElement(Data<WebElement> data, LazyLocator locator) {
        final var nameof = "getRootElementByInvokedElement";
        return ifDriver(
            nameof,
            isNotNullWebElement(data) && isNotNullLazyData(locator),
            getShadowRootElement(invokeGetElement(getLocator(locator).object).apply(getSearchContextOf("Element data", data))),
            replaceMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, "Parameters were wrong: locator or data.")
        );
    }

    static DriverFunction<WebElement> getRootElementByInvokedElement(LazyElement element, LazyLocator locator) {
        return ifDriver(
            "getRootElementByInvokedElement",
            isNotNullLazyElement(element) && isNotNullLazyData(locator),
            driver -> getShadowRootElement(invokeGetElement(getLocator(locator).object).apply(getSearchContextOf("Element data", element.get().apply(driver)))).apply(driver),
            SeleniumDataConstants.NULL_ELEMENT//, nameof, "Parameters were wrong: locator or data.")
        );
    }

    static DriverFunction<WebElement> getShadowRootElement(Data<WebElement> data, LazyLocatorList locators) {
        return ifDriver(
            "getShadowRootElement",
            isNotNullWebElement(data) && isNotNull(locators) && locators.isNotNullAndNonEmpty(),
            driver -> {
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
                    if (isNullWebElement(current)) {
                        break;
                    }

                    message += current.message;
                }

                return replaceMessage(current, message);
            },
            SeleniumDataConstants.NULL_ELEMENT
        );
    }

    static Function<Data<WebElement>, DriverFunction<WebElement>> getShadowRootElementFunction(LazyLocatorList locators) {
        return isNotNull(locators) && locators.isNotNullAndNonEmpty() ? (
            data -> getShadowRootElement(data, locators)
        ) : data -> DriverFunctionFactoryFunctions.getWithMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, Strings.LOCATOR_WAS_NULL);
    }

    static DriverFunction<WebElement> getShadowRootElement(LazyLocatorList locators) {
        final var nameof = "getShadowRootElement: ";
        return ifDriver(
            nameof,
            isNotNull(locators) && locators.isNotNullAndNonEmpty() && isNotNullLazyData(locators.first()),
            driver -> {
                final var currentBy = locators.first();
                final var current = getShadowRootElement(getLocator(currentBy).object).apply(driver);
                if (isNullWebElement(current)) {
                    return prependMessage(SeleniumDataConstants.NULL_ELEMENT, "Current " + Strings.WAS_NULL + current.message.toString());
                }

                return locators.isMany() ? getShadowRootElement(current, locators.tail()).apply(driver) : current;
            },
            prependMessage(SeleniumDataConstants.NULL_ELEMENT, nameof + "Shadow locators list was null.")
        );
    }

    static DriverFunction<WebElement> getShadowRootElement(DriverFunction<WebElement> data, LazyLocatorList locators) {
        return ifDriver(
            "getShadowRootElement",
            areNotNull(data, locators) && locators.isNotNullAndNonEmpty(),
            driver -> {
                var current = data.apply(driver);
                return isNotNullWebElement(current) ? getShadowRootElement(current, locators).apply(driver) : SeleniumDataConstants.NULL_ELEMENT;
            },
            SeleniumDataConstants.NULL_ELEMENT
        );
    }

    static DriverFunction<WebElement> getShadowRootElement(LazyElement element, LazyLocatorList locators) {
        return ifDriver("getShadowRootElement", areNotNull(element, locators) && locators.isNotNullAndNonEmpty(), getShadowRootElement(element.get(), locators), SeleniumDataConstants.NULL_ELEMENT);
    }

    static Function<Data<SearchContext>, Data<WebElement>> getNestedElement(By locator) {
        final var nameof = "getNestedElement: ";
        return isNotNull(locator) ? (context -> {
            if (isNull(context)) {
                return replaceMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, Strings.PASSED_DATA_WAS_NULL);
            }

            final var nestedElement = getNestedElementsAmount(locator, 1).apply(context);
            if (isInvalidOrFalse(nestedElement)) {
                return replaceMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, "Nested Element" + Strings.WAS_NULL);
            }

            final var object = nestedElement.object;
            return isNotNull(object) && object.isNotNullAndNonEmpty() ? (
                getElementFromSingle(nestedElement)
            ) : replaceMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, "Nested Element" + Strings.WAS_NULL + nestedElement.message);
        }) : context -> replaceMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, Strings.LOCATOR_WAS_NULL);
    }

    static Function<Data<SearchContext>, Data<WebElementList>> getNestedElements(By locator) {
        return context -> {
            final var nameof = "getNestedElements";
            final var message = Formatter.getNestedElementsErrorMessage(locator, context);
            return isBlank(message) ? (
                replaceName(getElements(getSearchContextOf("Search Context", context), getLazyLocator(locator)), nameof)
            ) : replaceMessage(SeleniumDataConstants.NULL_LIST, nameof, message);
        };
    }

    static Function<Data<SearchContext>, Data<WebElement>> getNestedElement(LazyLocator locator) {
        final var nameof = "getNestedElement";
        return isNotNullLazyData(locator) ? getNestedElement(getLocator(locator).object) : data -> replaceMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, Strings.LOCATOR_WAS_NULL);
    }

    static Function<Data<SearchContext>, Data<WebElementList>> getNestedElements(LazyLocator locator) {
        final var nameof = "getNestedElements";
        if (isNullLazyData(locator)) {
            return context -> replaceMessage(SeleniumDataConstants.NULL_LIST, nameof + "Lazy locator" + Strings.WAS_NULL);
        }

        final var locatorData = getLocator(locator);
        return isValidNonFalse(locatorData) ? getNestedElements(locatorData.object) : context -> replaceMessage(SeleniumDataConstants.NULL_LIST, nameof + "Locator data" + Strings.WAS_NULL);
    }

    static Function<Data<SearchContext>, Data<WebElementList>> getNestedElementsAmount(By locator, int count) {
        final var nameof = "getNestedElementsAmount";
        return isNotNull(locator) && BasicPredicateFunctions.isNonNegative(count) ? (context -> {
            if (isNull(context)) {
                return replaceMessage(SeleniumDataConstants.NULL_LIST, Strings.PASSED_DATA_WAS_NULL);
            }

            final var result = getNestedElements(locator).apply(context);
            if (isInvalidOrFalse(result)) {
                return replaceMessage(SeleniumDataConstants.NULL_LIST, nameof, "Nested Element" + Strings.WAS_NULL);
            }

            final var object = result.object;
            if (isNull(object) || object.isNullOrEmpty()) {
                return prependMessage(result, Strings.RESULT_WAS_NULL);
            }
            final var size = (
                result.status &&
                CollectionPredicateFunctions.isNonEmptyAndOfType(object, WebElement.class) &&
                CoreUtilities.isNotEqual(SeleniumDataConstants.NULL_ELEMENT.object, object.first())
            ) ? object.size() : 0;
            final var status = size == count;
            final var message = (status ? count : nameof + (BasicPredicateFunctions.isPositiveNonZero(size) ? "Wrong amount(" + count + ") of" : "No")) + " Elements" + " found by: " + locator + ".";
            return DataFactoryFunctions.getWithMessage(object, status, message);
        }) : context -> replaceMessage(SeleniumDataConstants.NULL_LIST, nameof + "Locator was null, or count was wrong. Locator: " + locator + ", count: " + count);
    }

    static Function<Data<SearchContext>, Data<Integer>> getCountOfNestedElements(By locator) {
        return isNotNull(locator) ? (
            context -> isNotNull(context) ? getCountOfElements(getNestedElements(locator).apply(context)) : CoreDataConstants.NO_ELEMENTS_FOUND_DATA_FALSE_OR_NULL
        ) : context -> CoreDataConstants.NO_ELEMENTS_FOUND;
    }

    private static <T> DriverFunction<T> getShadowNested(Function<LazyLocator, Function<Data<SearchContext>, Data<T>>> getter, LazyLocatorList locators, LazyLocator locator, T defaultValue) {
        final var nameof = "getShadowNested";
        return ifDriver(
            nameof,
            areNotNull(getter, locators, defaultValue) && locators.isNotNullAndNonEmpty() && isNotNullLazyData(locator),
            driver -> isValidNonFalse(switchToDefaultContent().apply(driver)) ? (
                getter.apply(locator).apply(getSearchContext(getShadowRootElement(locators).apply(driver).object))
            ) : DataFactoryFunctions.getInvalidWithNameAndMessage(defaultValue, nameof, "Couldn't switch to default content" + Strings.END_LINE),
            DataFactoryFunctions.getWithMessage(defaultValue, false, "There were parameter issues" + Strings.END_LINE)
        );
    }

    static DriverFunction<WebElement> getShadowNestedElement(LazyLocatorList locators, LazyLocator elementLocator) {
        return getShadowNested(Driver::getNestedElement, locators, elementLocator, SeleniumCoreConstants.STOCK_ELEMENT);
    }

    static DriverFunction<WebElementList> getShadowNestedElements(LazyLocatorList locators, LazyLocator elementLocator) {
        return getShadowNested(Driver::getNestedElements, locators, elementLocator, SeleniumCoreConstants.NULL_ELEMENT_LIST);
    }

    static DriverFunction<WebElement> getShadowNestedElement(LazyLocatorList locators, By locator) {
        return getShadowNestedElement(locators, getLazyLocator(locator));
    }

    static DriverFunction<WebElementList> getShadowNestedElements(LazyLocatorList locators, By locator) {
        return getShadowNestedElements(locators, getLazyLocator(locator));
    }

    static <T> Data<Boolean> switchTo(
        TargetLocator locator,
        Function<TargetLocator, T> operation,
        BiFunction<Boolean, SwitchResultMessageData<Void>, String> formatter,
        SwitchResultMessageData<Void> messageData
    ) {
        if (isNull(locator)) {
            DataFactoryFunctions.getBoolean(false, formatter.apply(false, messageData));
        }

        var exception = CoreConstants.EXCEPTION;
        try {
            operation.apply(locator);
        } catch (NoSuchFrameException ex) {
            exception = ex;
        }

        var status = CoreUtilities.isNonException(exception);
        return status ? (
            DataFactoryFunctions.getBoolean(status, formatter.apply(status, messageData))
        ) : DataFactoryFunctions.getBoolean(status, formatter.apply(status, messageData), exception);
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
            DataFactoryFunctions.getBoolean(false, formatter.apply(false, messageData));
        }

        var exception = CoreConstants.EXCEPTION;
        try {
            operation.apply(locator, target);
        } catch (NoSuchFrameException ex) {
            exception = ex;
        }

        var status = CoreUtilities.isNonException(exception);
        return status ? (
            DataFactoryFunctions.getBoolean(status, formatter.apply(status, messageData))
        ) : DataFactoryFunctions.getBoolean(status, formatter.apply(status, messageData), exception);
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
            NullableFunctions::isNotNull,
            target,
            switchTo(data.object, TargetLocator::frame, "frame", nameof),
            DataFactoryFunctions.getBoolean(false, nameof, "Couldn't attempt, data was null or false" + Strings.END_LINE)
        );
    }

    static Function<TargetLocator, Data<Boolean>> switchToFrameSingleList(Data<WebElementList> data) {
        final var nameof = "switchToFrame(Data<WebElement> data): ";
        if (isInvalidOrFalse(data)) {
            return SeleniumDataConstants.SWITCH_TO_NEGATIVE;
        }
        final var list = data.object;
        final var element = DataFactoryFunctions.getWithNameAndMessage(list.first(), list.isSingle(), nameof, data.message.message);
        return isValidNonFalse(data) ? switchToFrame(element) : SeleniumDataConstants.SWITCH_TO_NEGATIVE;
    }

    static DriverFunction<Boolean> switchToFrame(DriverFunction<WebElement> data) {
        final var nameof = "switchToFrame";
        return ifDriver(
            nameof,
            isNotNull(data),
            driver -> switchToFrame(data.apply(driver)).apply(driver.switchTo()),
            DataFactoryFunctions.getBoolean(false, nameof, "Data parameter " + Strings.WAS_NULL)
        );
    }

    static DriverFunction<Boolean> switchToFrame(By locator, Function<By, DriverFunction<WebElement>> getter) {
        final var nameof = "switchToFrame";
        return ifDriver(
            nameof,
            areNotNull(locator, getter),
            switchToFrame(getter.apply(locator)),
            replaceMessage(CoreDataConstants.NULL_BOOLEAN, nameof, "Couldn't attempt switchToFrame.")
        );
    }

    static DriverFunction<Boolean> switchToFrame(Data<By> locator, Function<By, DriverFunction<WebElement>> getter) {
        final var nameof = "switchToFrame";
        return ifDriver(
            nameof,
            isValidNonFalse(locator) && isNotNull(getter),
            switchToFrame(getter.apply(locator.object)),
            replaceMessage(CoreDataConstants.NULL_BOOLEAN, nameof, "Couldn't attempt switchToFrame.")
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
            isNotNull(locator) && locator.isSingle(),
            switchToFrame(getLocator(locator.first()), Driver::getElement),
            replaceMessage(CoreDataConstants.NULL_BOOLEAN, nameof, "Couldn't attempt switchToFrame.")
        );
    }

    static DriverFunction<Boolean> switchToFrameFromSingle(LazyLocatorList locators) {
        final var nameof = "switchToFrameFromSingle";
        return ifDriver(
            nameof,
            isNotNull(locators) && locators.isSingle(),
            switchToFrame(locators.first()),
            replaceMessage(CoreDataConstants.NULL_BOOLEAN, nameof, "Couldn't attempt switchToFrame. Non-singular list used in function")
        );
    }

    static DriverFunction<Boolean> switchToFrame(int target) {
        return driver -> switchTo(
            target,
            driver.switchTo(),
            BasicPredicateFunctions.isNonNegative(target),
            TargetLocator::frame,
            Formatter::getSwitchToMessage,
            new SwitchResultMessageData<Integer>(target, "frame", "switchToFrame(int frameLocator): ")
        );
    }

    static DriverFunction<Boolean> switchToWindow(String target) {
        return driver -> switchTo(
            target,
            driver.switchTo(),
            isNotBlank(target),
            TargetLocator::frame,
            Formatter::getSwitchToMessage,
            new SwitchResultMessageData<String>(target, "window", "switchToWindow(String target): ")
        );
    }

    static DriverFunction<Boolean> switchToParentFrame() {
        return driver -> switchTo(driver.switchTo(), TargetLocator::parentFrame, Formatter::getSwitchToMessage, new SwitchResultMessageData<Void>(null, "parent frame", "switchToParentFrame: "));
    }

    static DriverFunction<Boolean> switchToAlert() {
        return driver -> switchTo(driver.switchTo(), TargetLocator::alert, Formatter::getSwitchToMessage, new SwitchResultMessageData<Void>(null, "alert.", "switchToAlert: "));
    }

    static DriverFunction<Boolean> switchToDefaultContent() {
        return driver -> switchTo(driver.switchTo(), TargetLocator::defaultContent, Formatter::getSwitchToMessage, new SwitchResultMessageData<Void>(null, "default content.", "switchToDefaultContent: "));
    }

    static <T> DriverFunction<T> switchToDefaultContentWith(DriverFunction<T> action) {
        return SeleniumExecutor.execute(switchToDefaultContent(), action);
    }


    static DriverFunction<Boolean> switchToNestedFrame(LazyLocatorList locators) {
        return ifDriver(
            "switchToNestedFrame",
            isNotNull(locators) && locators.isNotNullAndNonEmpty(),
            driver -> {
                if (isInvalidOrFalse(switchToDefaultContent().apply(driver))) {
                    return replaceMessage(CoreDataConstants.NULL_BOOLEAN, "Couldn't switch to default content.");
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
                    if (isValidNonFalse(data)) {
                        break;
                    }
                }

                return DataFactoryFunctions.getBoolean(index == length, message);
            },
            replaceMessage(CoreDataConstants.NULL_BOOLEAN, "Locators were empty.")
        );
    }

    static DriverFunction<WebElementList> getFrameNestedElements(LazyLocatorList locators) {
        return ifDriver(
            "getFrameNestedElements",
            isNotNull(locators) && locators.isNotNullAndNonEmpty(),
            driver -> {
                if (locators.isSingle()) {
                    return Driver.getElements(locators.first()).apply(driver);
                }

                final var data = switchToNestedFrame(locators.initials()).apply(driver);
                return isValidNonFalse(data) ? Driver.getElements(locators.last()).apply(driver) : SeleniumDataConstants.NULL_LIST;
            },
            SeleniumDataConstants.NULL_LIST
        );
    }

    static DriverFunction<WebElement> getFrameNestedElement(LazyLocatorList locators, Function<By, DriverFunction<WebElement>> getter) {
        return ifDriver(
            "getFrameNestedElement",
            areNotNull(getter, locators) && locators.isMany() && !locators.hasMoreThan(2),
            driver -> {
                final var frameData = locators.first();
                if (isNullLazyData(frameData)) {
                    return SeleniumDataConstants.NULL_ELEMENT;
                }

                final var frameLocator = getLocator(frameData).object;
                if (isInvalidOrFalse(getter.apply(frameLocator).apply(driver))) {
                    return SeleniumDataConstants.NULL_ELEMENT;
                }

                final var elementData = locators.last();
                if (isNullLazyData(elementData)) {
                    return SeleniumDataConstants.NULL_ELEMENT;
                }

                final var elementLocator = getLocator(elementData).object;
                if (isInvalidOrFalse(getter.apply(frameLocator).apply(driver))) {
                    return SeleniumDataConstants.NULL_ELEMENT;
                }

                final var data = switchToFrame(getter.apply(frameLocator).apply(driver)).apply(driver.switchTo());
                return isValidNonFalse(data) ? getter.apply(elementLocator).apply(driver) : SeleniumDataConstants.NULL_ELEMENT;
            },
            SeleniumDataConstants.NULL_ELEMENT
        );
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
        if (isNull(locators) || !locators.hasAtleast(2)) {
            return DriverFunctionFactoryFunctions.get(prependMessage(SeleniumDataConstants.NULL_ELEMENT, "Lazy Locator list doesn't have enough items" + Strings.END_LINE));
        }

        final var start = locators.first();
        final var tail = locators.tail();
        return isNotNullLazyData(start) && isNotNull(tail) ? (
            getShadowNestedElement(tail, start)
        ) : DriverFunctionFactoryFunctions.get(prependMessage(SeleniumDataConstants.NULL_ELEMENT, "Lazy locator item issues" + Strings.END_LINE));
    }

    static DriverFunction<WebElement> getElementFromSingle(LazyLocatorList locator) {
        return getFromSingle(
            Driver::getWithLazyLocator,
            new GetWithData<LazyLocatorList, LazyLocator, By, WebElement>(locator, LazyLocatorList::first, Driver::getElement, SeleniumDataConstants.NULL_ELEMENT),
            "getElementFromSingle"
        );
    }

    static DriverFunction<WebElement> getRootElementFromSingle(LazyLocatorList locator) {
        return getFromSingle(
            Driver::getWithLazyLocator,
            new GetWithData<LazyLocatorList, LazyLocator, By, WebElement>(locator, LazyLocatorList::first, Driver::getShadowRootElement, SeleniumDataConstants.NULL_ELEMENT),
            "getRootElementFromSingle"
        );
    }

    static DriverFunction<WebElement> getNestedElement(LazyLocatorList locators) {
        final var nameof = "getNestedElement";
        return ifDriver(
            nameof,
            isNotNull(locators) && locators.hasAtleast(2) && isNotNullLazyData(locators.first()),
            driver -> {
                if (isInvalidOrFalse(switchToDefaultContent().apply(driver))) {
                    return DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, nameof, "Driver was null or couldn't switch to default content" + Strings.END_LINE);
                }

                var locator = locators.first();
                var data = Driver.getElement(locator).apply(driver);
                if (isNullWebElement(data)) {
                    return DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.STOCK_ELEMENT, false, nameof, data.message.toString());
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
                    if (isNullWebElement(data)) {
                        break;
                    }
                }

                return (index == length) ? data : appendMessage(SeleniumDataConstants.NULL_ELEMENT, nameof);
            },
            SeleniumDataConstants.NULL_ELEMENT
        );
    }

    static <T> DriverFunction<T> getFrameNested(Function<LazyLocator, DriverFunction<T>> getter, LazyLocatorList locators, Data<T> defaultValue, String nameof) {
        return ifDriver(
            nameof,
            areNotNull(getter, locators, defaultValue, nameof) && locators.hasAtleast(2) && isNotNullLazyData(locators.first()),
            driver -> {
                if (isInvalidOrFalse(switchToDefaultContent().apply(driver))) {
                    return replaceMessage(defaultValue, nameof, "Driver was null or couldn't switch to default content" + Strings.END_LINE);
                }

                final var function = ElementFinderConstants.frameNestedStrategyMap.get("" + locators.hasMoreThan(2));
                if (isNull(function)) {
                    return replaceMessage(defaultValue, nameof, "Function from Frame nested Strategy map " + Strings.WAS_NULL);
                }

                final var data = function.apply(locators.initials()).apply(driver);
                if (isInvalidOrFalse(data)) {
                    return replaceMessage(defaultValue, nameof, "Couldn't switch into frame. By list length: " + locators.size());
                }

                final var locator = locators.last();
                return isNotNull(locator) ? getter.apply(locator).apply(driver) : replaceMessage(defaultValue, "Locator was null" + Strings.END_LINE);
            },
            replaceMessage(defaultValue, nameof, "Couldn't switch into frame. Guard.")
        );
    }

    static DriverFunction<WebElement> getFrameNestedElement(LazyLocatorList locators) {
        return getFrameNested(Driver::getElement, locators, SeleniumDataConstants.NULL_ELEMENT, "getFrameNestedElement");
    }

    static DriverFunction<WebElementList> getFrameNestedElementsFromLast(LazyLocatorList locators) {
        return getFrameNested(Driver::getElements, locators, SeleniumDataConstants.NULL_LIST, "getFrameNestedElementsFromLast");
    }

    static DriverFunction<WebElementList> getNestedElementsFromLast(LazyLocatorList locators) {
        return ifDriver(
            "getNestedElementsFromLast",
            isNotNull(locators) && locators.hasAtleast(2) && isNotNullLazyData(locators.first()), //TODO: Move to formatter, and do blank check instead
            driver -> {
                if (isInvalidOrFalse(switchToDefaultContent().apply(driver))) {
                    return replaceMessage(SeleniumDataConstants.NULL_LIST, "Couldn't switch to default content" + Strings.END_LINE);
                }

                final var function = ElementFinderConstants.frameAmountStrategyMap.get("" + locators.hasMoreThan(2));
                final var element = function.apply(locators.initials()).apply(driver);
                if (isInvalidOrFalse(element)) {
                    return replaceMessage(SeleniumDataConstants.NULL_LIST, "Failed, nested selenium.element issue - sublist length(" + locators.initials().size() + "): " + element.message);
                }

                final var locator = locators.last();
                if (isNullLazyData(locator)) {
                    return replaceMessage(SeleniumDataConstants.NULL_LIST, "Locator was null" + Strings.END_LINE);
                }

                final var data = getNestedElements(locator).apply(getSearchContext(element.object));
                final var nested = locators.hasAtleast(2);
                return isValidNonFalse(data) ? data : prependMessage(data, (nested ? "Nested " : "") + "Elements weren't found by locator: " + locator);
            },
            replaceMessage(SeleniumDataConstants.NULL_LIST, "Locators list was null or empty.")
        );
    }

    static DriverFunction<WebElementList> getShadowNestedElementsFromLast(LazyLocatorList locators) {
        return ifDriver(
            "getShadowNestedElementsFromLast",
            isNotNull(locators) && locators.isNotNullAndNonEmpty() && isNotNullLazyData(locators.last()),
            getShadowNestedElements(locators.initials(), locators.last()),
            replaceMessage(SeleniumDataConstants.NULL_LIST, "Locators were null" + Strings.END_LINE)
        );
    }

    static DriverFunction<WebElementList> getElements(LazyLocatorList locators) {
        final var nameof = "getElements: ";
        return ifDriver(
            nameof,
            isNotNull(locators) && locators.isSingle(),
            driver -> {
                if (isInvalidOrFalse(switchToDefaultContent().apply(driver))) {
                    return replaceMessage(SeleniumDataConstants.NULL_LIST, nameof, "Couldn't switch to default content" + Strings.END_LINE);
                }

                final var lazyLocator = locators.first();
                if (isNullLazyData(lazyLocator)) {
                    return replaceMessage(SeleniumDataConstants.NULL_LIST, "Lazy Locator" + Strings.WAS_NULL);
                }

                final var locator = getLocator(lazyLocator);
                return isNotNull(locator) ? getElements(locator.object).apply(driver) : appendMessage(SeleniumDataConstants.NULL_LIST, nameof, "\n" + Strings.LOCATOR_WAS_NULL);
            },
            replaceMessage(SeleniumDataConstants.NULL_LIST, nameof, "Locators was null or wrong size" + Strings.END_LINE)
        );
    }

    static <T> DriverFunction<ExternalElementData> getLazyElementByExternal(LazyElement element, ExternalSelectorData externalData, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys) {
        //TODO: Validate all the parameters and provide overloads for defaults.
        final var nameof = "getLazyElementByExternal";
        final var defaultValue = SeleniumDataConstants.NULL_EXTERNAL_ELEMENT;
        return ifDriver(
            nameof,
            Formatter.getExternalSelectorDataErrorMessage(element, externalData, nameof),
            driver -> {
                final var types = new ArrayList<>(typeKeys.keySet());
                LazyLocator locator;
                var selector = externalData.defaultSelector;
                var parameterKey = "";
                var selectorType = externalData.selectorType;
                var currentElement = SeleniumDataConstants.NULL_ELEMENT;

                final var failedSelectors = new DecoratedList<String>();
                final var length = externalData.limit;
                var index = 0;
                var message = replaceMessage(CoreDataConstants.NULL_STRING, nameof, "");
                var lep = LazyIndexedElementFactory.getWithFilterParametersAndLocator(false, 0, new LazyLocator(""));
                var getSelector = externalData.getSelector;
                for(; index < length; ++index) {
                    switchToDefaultContent().apply(driver);
                    selector = getSelector.apply(externalData.preferredProperties, failedSelectors.list).apply(driver);
                    if (isInvalidOrFalse(selector)) {
                        continue;
                    }

                    if (isBlank(selector.object)) {
                        appendMessage(message, "Empty selector returned, attempt: " + index + Strings.END_LINE);
                        continue;
                    }

                    locator = new LazyLocator(selector.object, selectorType);
                    parameterKey = Formatter.getUniqueGeneratedName(selectorType);
                    lep = LazyIndexedElementFactory.getWithFilterParametersAndLocator(false, 0, locator);
                    currentElement = ElementFilterFunctions.getElement(lep.lazyLocators, ElementFinderConstants.singleGetterMap, SingleGetter.DEFAULT).apply(driver);
                    if (isNullWebElement(currentElement)) {
                        break;
                    }

                    failedSelectors.addNullSafe(selector.object);
                }

                element.parameters.putIfAbsent(parameterKey, lep);
                final var update = ElementRepository.updateTypeKeys(lep.lazyLocators, typeKeys, types, parameterKey);
                return isNotNullWebElement(currentElement) ? (
                    DataFactoryFunctions.getWithNameAndMessage(new ExternalElementData(typeKeys, currentElement), true, nameof, "External function yielded an selenium.element" + Strings.END_LINE)
                ) : replaceMessage(defaultValue, nameof, "All(\"" + length + "\") approaches were tried" + Strings.END_LINE + currentElement.message.toString());
            },
            defaultValue
        );
    }

    static Data<WebElement> cacheNonNullAndNotFalseLazyElement(LazyElement element, Data<ExternalElementData> regular, Data<ExternalElementData> external) {
        final var nameof = "cacheNonNullAndNotFalseLazyElement";
        if (isNotBlank(isNullLazyElementMessage(element)) || areAnyNull(regular, external)) {
            return replaceMessage(SeleniumDataConstants.NULL_ELEMENT, nameof, Strings.PARAMETER_ISSUES + Strings.WAS_NULL);
        }

        final var regularStatus = isValidNonFalse(regular);
        final var externalStatus = isValidNonFalse(external);
        if (!(regularStatus || externalStatus)) {
            return SeleniumDataConstants.NULL_ELEMENT;
        }

        final var externalElement = (externalStatus ? external : regular).object;
        final var currentElement = externalElement.found;
        return isNotNullWebElement(currentElement) ? (
            appendMessage(currentElement, ElementRepository.cacheIfAbsent(element, getKeysCopy(externalElement.typeKeys)))
        ) : prependMessage(currentElement, "All approaches were tried" + Strings.END_LINE);
    }

    static <T> Data<Integer> getNextCachedKey(Map<String, T> parameterMap, Iterator<String> getOrder, Map<String, DecoratedList<SelectorKeySpecificityData>> typeKeys, int parameterIndex) {
        final var nameof = "getNextCachedKey";
        var type = typeKeys.getOrDefault(getOrder.next(), null);
        if (isNull(type)) {
            return replaceMessage(CoreDataConstants.NULL_INTEGER, nameof, "Type" + Strings.WAS_NULL);
        }

        var index = parameterIndex;
        if (!type.hasIndex(index)) {
            if (!getOrder.hasNext()) {
                return replaceMessage(CoreDataConstants.NULL_INTEGER, nameof, "GetOrder doesn't have more entries" + Strings.END_LINE);
            }

            type = typeKeys.get(getOrder.next());
            index = 0;
        }
        final var key = type.get(index).selectorKey;
        return (isNotNull(key) && parameterMap.containsKey(key) ? (
            DataFactoryFunctions.getWithMessage(index, true, key)
        ) : replaceMessage(CoreDataConstants.NULL_INTEGER, nameof, "The parameter map didn't contain an indexed selector-type it should have" + Strings.END_LINE));
    }

    static Data<Integer> getNextKey(DecoratedList<String> keys, int parameterIndex) {
        return isNotNull(keys) && BasicPredicateFunctions.isNonNegative(parameterIndex) && keys.hasIndex(parameterIndex) ? (
            DataFactoryFunctions.getWithMessage(0, true, keys.get(parameterIndex))
        ) : replaceMessage(CoreDataConstants.NULL_INTEGER, "getNextKey", "The parameter map didn't contain an indexed selector-type it should have" + Strings.END_LINE);
    }

    static <T> DriverFunction<WebElement> getLazyElement(LazyElementWithOptionsData data) {
        final var nameof = "getLazyElement";
        return ifDriver(
            nameof,
            Formatter.getLazyElementWithOptionsMessage(data, nameof),
            driver -> {
                final var getOrder = data.getOrder.iterator();
                final var dataElement = data.element;
                final var name = dataElement.name;
                final var cached2 = ElementRepository.containsElement(dataElement.name);
                if (isInvalidOrFalse(cached2)) {
                    return SeleniumDataConstants.NULL_ELEMENT;
                }

                final var isCached = cached2.object;
                final var getResult = ElementRepository.getIfContains(dataElement);
                final var localElement = isCached ? getResult.object.element : dataElement;
                final var typeKeys = isCached ? getResult.object.typeKeys : ElementRepository.getInitializedTypeKeysMap();
                final var parameterMap = localElement.parameters;
                final var parameterKeys = new DecoratedList<>(parameterMap.keySet(), String.class);
                final var types = new DecoratedList<>(typeKeys.keySet(), String.class);
                var message = CoreDataConstants.NULL_STRING;
                var currentElement = DataFactoryFunctions.getWithNameAndMessage(SeleniumCoreConstants.STOCK_ELEMENT, true, nameof, "Internal null element" + Strings.END_LINE);
                var parameterIndex = 0;
                var index = 0;
                var switchData = CoreDataConstants.NULL_BOOLEAN;
                final var length = data.internalData.limit;
                for (; isValidNonFalse(currentElement) && (index < length); ++index, ++parameterIndex) {
                    switchData = switchToDefaultContent().apply(driver);
                    if (isInvalidOrFalse(switchData)) {
                        return replaceMessage(currentElement, nameof, switchData.message.toString());
                    }

                    var keyData = isCached ? getNextCachedKey(parameterMap, getOrder, typeKeys, parameterIndex) : getNextKey(parameterKeys, parameterIndex);
                    if (isInvalidOrFalse(keyData)) {
                        return replaceMessage(currentElement, nameof, "Parameter key wasn't found in " + (isCached ? "cached" : "") + " keys" + Strings.END_LINE);
                    }
                    parameterIndex = isCached ? keyData.object : parameterIndex;
                    var key = keyData.message.message;
                    var parameters = parameterMap.get(key);
                    if (isNull(parameters) || parameters.lazyLocators.isNullOrEmpty()) {
                        continue;
                    }

                    var locators = parameters.lazyLocators;
                    var update = ElementRepository.updateTypeKeys(name, locators, typeKeys, types, key);
                    if (isInvalidOrFalse(update)) {
                        continue;
                    }


                    var getter = parameters.getter;
                    var indexData = parameters.filterData;
                    currentElement = (
                        indexData.isFiltered ? (
                            indexData.apply(new ElementFilterParameters(locators, ElementFinderConstants.manyGetterMap, ManyGetter.getValueOf(getter)))
                        ) : ElementFilterFunctions.getElement(locators, ElementFinderConstants.singleGetterMap, SingleGetter.getValueOf(getter))
                    ).apply(driver);
                    message = appendMessage(message, currentElement.message.toString());
                    message = appendMessage(message, Adjuster.adjustProbability(parameters, typeKeys, key, isValidNonFalse(currentElement), data.probabilityData).message.toString());

                }

                if (SeleniumUtilities.isNullWebElement(currentElement)) {
                    currentElement = SeleniumDataConstants.NULL_ELEMENT;
                }

                final var externalData = data.externalData;
                return cacheNonNullAndNotFalseLazyElement(
                    dataElement,
                    DataFactoryFunctions.getWithMessage(new ExternalElementData(typeKeys, currentElement), isValidNonFalse(currentElement), message.message.toString()),
                    isBlank(Formatter.getExternalSelectorDataMessage(externalData)) ? getLazyElementByExternal(dataElement, externalData, typeKeys).apply(driver) : SeleniumDataConstants.NULL_EXTERNAL_ELEMENT
                );
            },
            SeleniumDataConstants.NULL_ELEMENT
        );
    }

    static DriverFunction<WebElement> getLazyElement(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData, DecoratedList<String> getOrder, ProbabilityData probabilityData) {
        return getLazyElement(new LazyElementWithOptionsData(element, internalData, externalData, getOrder, probabilityData));
    }

    static DriverFunction<WebElement> getLazyElement(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData, DecoratedList<String> getOrder) {
        return getLazyElement(new LazyElementWithOptionsData(element, internalData, externalData, getOrder, AdjusterConstants.PROBABILITY_DATA));
    }

    static DriverFunction<WebElement> getLazyElement(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData, ProbabilityData probabilityData) {
        return getLazyElement(new LazyElementWithOptionsData(element, internalData, externalData, GetOrderConstants.DEFAULT, probabilityData));
    }

    static DriverFunction<WebElement> getLazyElement(LazyElement element, InternalSelectorData internalData, ExternalSelectorData externalData) {
        return getLazyElement(new LazyElementWithOptionsData(element, internalData, externalData, GetOrderConstants.DEFAULT, AdjusterConstants.PROBABILITY_DATA));
    }

    static DriverFunction<WebElement> getLazyElement(LazyElement element) {
        return getLazyElement(new LazyElementWithOptionsData(element));
    }

    static DriverFunction<WebElementList> getLazyElements(LazyElement element) {
        final var nameof = "getLazyElements";
        final var defaultValue = SeleniumDataConstants.NULL_LIST;
        final var getOrder = GetOrderConstants.DEFAULT;
        return ifDriver(
            nameof,
            Formatter.getLazyParameterErrorMessage(element, nameof),
            driver -> {
                final var switchData = switchToDefaultContent().apply(driver);
                if (isInvalidOrFalse(switchData)) {
                    return prependMessage(defaultValue, nameof, switchData.message.toString());
                }

                final var parameterMap = element.parameters;
                if (getOrder.isNullOrEmpty()) {
                    return replaceMessage(defaultValue, nameof, "getOrder length was less than 1.");
                }

                boolean isIndexed;
                var currentElements = SeleniumDataConstants.NULL_LIST;
                for (var currentStrategy : getOrder) {
                    if (isNull(currentStrategy)) {
                        continue;
                    }

                    var parameters = parameterMap.get(currentStrategy);
                    if (isNull(parameters)) {
                        continue;
                    }

                    var indexData = parameters.filterData;
                    isIndexed = indexData.isFiltered;

                    var locatorList = parameters.lazyLocators;
                    if (locatorList.isNullOrEmpty()) {
                        continue;
                    }

                    if (isIndexed) {
                        return replaceMessage(defaultValue, nameof, "List return type is incompatible with indexing" + Strings.END_LINE);
                    }

                    final var getterMap = ElementFinderConstants.manyGetterMap;
                    final var getterParameter = parameters.getter;
                    final var errorMessage = Formatter.getManyGetterErrorMessage(getterMap, ManyGetter.getValueOf(getterParameter));
                    if (isNotBlank(errorMessage)) {
                        continue;
                    }

                    currentElements = getterMap.get(ManyGetter.getValueOf(getterParameter)).apply(locatorList).apply(driver);
                    if (isValidNonFalse(currentElements)) {
                        break;
                    }
                }

                return currentElements;
            },
            defaultValue
        );
    }

    static DriverFunction<Boolean> quitDriver() {
        return ifDriver(
            "quitDriver",
            driver -> {
                Data<Boolean> data;
                try {
                    driver.quit();
                    data = DataFactoryFunctions.getBoolean(true, "Driver quit successfully" + Strings.END_LINE);
                } catch (NullPointerException ex) {
                    final var exMessage = ex.getMessage();
                    data = DataFactoryFunctions.getBoolean(false, "Exception occurred while closing Driver. Exception:" + ex.getClass() + " Message: " +  exMessage, ex, exMessage);
                }

                return data;
            },
            CoreDataConstants.DRIVER_WAS_NULL
        );
    }

    static DriverFunction<Boolean> navigate(String url) {
        return ifDriver(
            "navigate",
            isBlankMessage(url, "url"),
            driver -> {
                Data<Boolean> data;
                try {
                    driver.get(url);
                    data = DataFactoryFunctions.getBoolean(true, "Driver navigated successfully to \"" + url + "\"" + Strings.END_LINE);
                } catch (NullPointerException ex) {
                    final var exMessage = ex.getMessage();
                    data = DataFactoryFunctions.getBoolean(false, "Exception occurred while navigating. Exception:" + ex.getClass() + " Message: " +  exMessage, ex, exMessage);
                }

                return data;
            },
            CoreDataConstants.DRIVER_WAS_NULL
        );
    }
}
