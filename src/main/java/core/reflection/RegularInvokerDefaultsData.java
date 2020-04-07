package core.reflection;

import core.extensions.interfaces.functional.boilers.MethodFunction;
import core.records.Data;
import core.records.HandleResultData;
import core.records.caster.BasicCastData;
import core.reflection.abstracts.BaseInvokerDefaultsData;

import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class RegularInvokerDefaultsData<ParameterType, ReturnType> extends BaseInvokerDefaultsData<
    ParameterType,
    BiFunction<Method, ParameterType, Object>,
    ReturnType
> {
    public RegularInvokerDefaultsData(
        Function<BiFunction<Method, ParameterType, Object>, MethodFunction<Function<ParameterType, Object>>> constructor,
        Predicate<BiFunction<Method, ParameterType, Object>> guard,
        BasicCastData<ReturnType> castData,
        Function<HandleResultData<ParameterType, ReturnType>, Data<ReturnType>> castHandler) {
        super(constructor, guard, castData, castHandler);
    }
}
