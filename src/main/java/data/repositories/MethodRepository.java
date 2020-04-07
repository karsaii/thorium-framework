package data.repositories;

import data.constants.DataDefaults;
import data.Data;
import formatter.Formatter;
import validators.ElementParameters;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiPredicate;

import static utilities.utils.isNotEqual;
import static utilities.utils.replaceMessage;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface MethodRepository {
    private static Data<Method> getMethodFromMap(HashMap<String, Method> methodMap, String methodName, Data<Method> defaultValue) {
        final var status = methodMap.containsKey(methodName);
        final var object = status ? methodMap.get(methodName) : defaultValue.object;
        return new Data<>(object, status, Formatter.getMethodFromMapMessage(methodName, status));
    }

    private static Data<Method> getMethodFromList(HashMap<String, Method> methodMap, List<Method> list, BiPredicate<Method, String> condition, String methodName, Data<Method> defaultValue) {
        final var nameof = "getMethodFromList: ";
        final var message = ElementParameters.validateGetMethodFromList(condition, methodName);
        if (isNotBlank(message)) {
            return replaceMessage(defaultValue, nameof, message);
        }

        var currentMethod = defaultValue.object;
        for (Method method : list) {
            if (!condition.test(method, methodName)) {
                continue;
            }

            method.setAccessible(true);
            methodMap.put(methodName, method);
            currentMethod = method;
            break;
        }

        final var status = isNotEqual(currentMethod, defaultValue);
        return new Data<>(currentMethod, status, nameof, Formatter.getMethodFromListMessage(methodName, status));
    }

    private static Data<Method> getMethodFromMap(HashMap<String, Method> methodMap, String methodName) {
        return getMethodFromMap(methodMap, methodName, DataDefaults.NULL_METHOD_DATA);
    }

    private static Data<Method> getMethodFromList(HashMap<String, Method> methodMap, List<Method> list, BiPredicate<Method, String> condition, String methodName) {
        return getMethodFromList(methodMap, list, condition, methodName, DataDefaults.NULL_METHOD_DATA);
    }

    static Data<Method> getMethod(HashMap<String, Method> methodMap, List<Method> list, BiPredicate<Method, String> condition, String methodName, Data<Method> defaultValue) {
        final var methodData = getMethodFromMap(methodMap, methodName, defaultValue);
        return methodData.status ? methodData : getMethodFromList(methodMap, list, condition, methodName, defaultValue);
    }

    static Data<Method> getMethod(HashMap<String, Method> methodMap, List<Method> list, BiPredicate<Method, String> condition, String methodName) {
        return getMethod(methodMap, list, condition, methodName, DataDefaults.NULL_METHOD_DATA);
    }
}
