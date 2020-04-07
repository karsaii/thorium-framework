package selenium.records.scripter;

import core.extensions.interfaces.DriverFunction;
import core.records.ExecuteCommonData;
import core.records.HandleResultData;
import core.records.caster.WrappedCastData;
import org.openqa.selenium.JavascriptExecutor;
import selenium.abstracts.BaseFunctionalData;
import selenium.abstracts.ExecuteBaseData;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class ExecutorData<HandlerType, ParameterType, MessageParameterType, ReturnType> extends
        BaseFunctionalData<JavascriptExecutor, HandlerType, ParameterType, MessageParameterType, ExecuteCommonData<ParameterType>, ExecuteBaseData<ParameterType, Function<String, Object>>, ReturnType> {
    public ExecutorData(
            DriverFunction<JavascriptExecutor> getter,
            BiFunction<ExecuteCommonData<ParameterType>, HandlerType, ExecuteBaseData<ParameterType, Function<String, Object>>> constructor,
            Predicate<HandlerType> guard,
            WrappedCastData<ReturnType> castData,
            ExecutorWrappedResultFunctionsData<HandleResultData<ParameterType, ReturnType>, MessageParameterType, ReturnType> resultHandlers
    ) {
        super(getter, constructor, guard, castData, resultHandlers);
    }
}