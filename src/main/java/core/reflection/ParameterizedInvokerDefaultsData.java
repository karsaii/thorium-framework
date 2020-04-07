package core.reflection;

import core.extensions.interfaces.functional.boilers.MethodFunction;
import core.records.Data;
import core.records.HandleResultData;
import core.records.caster.BasicCastData;
import core.reflection.abstracts.BaseInvokerDefaultsData;

import java.util.function.Function;
import java.util.function.Predicate;

public class ParameterizedInvokerDefaultsData<ParameterType, ReturnType> extends BaseInvokerDefaultsData<
    ParameterType,
    InvokerParameterizedParametersFieldData<ParameterType>,
    ReturnType
> {
    public ParameterizedInvokerDefaultsData(
        Function<InvokerParameterizedParametersFieldData<ParameterType>, MethodFunction<Function<ParameterType, Object>>> constructor,
        Predicate<InvokerParameterizedParametersFieldData<ParameterType>> guard,
        BasicCastData<ReturnType> castData,
        Function<HandleResultData<ParameterType, ReturnType>, Data<ReturnType>> castHandler
    ) {
        super(constructor, guard, castData, castHandler);
    }
}
