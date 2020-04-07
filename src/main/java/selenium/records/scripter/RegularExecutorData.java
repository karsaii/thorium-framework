package selenium.records.scripter;

import core.extensions.interfaces.DriverFunction;
import core.extensions.interfaces.functional.boilers.ScriptHandlerFunction;
import core.records.ExecuteCommonData;
import core.records.HandleResultData;
import core.records.caster.WrappedCastData;
import org.openqa.selenium.JavascriptExecutor;
import selenium.abstracts.ExecuteBaseData;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class RegularExecutorData<ReturnType> extends ExecutorData<ScriptHandlerFunction, String, Boolean, ReturnType> {
    public RegularExecutorData(
        DriverFunction<JavascriptExecutor> getter,
        BiFunction<ExecuteCommonData<String>, ScriptHandlerFunction, ExecuteBaseData<String, Function<String, Object>>> constructor,
        Predicate<ScriptHandlerFunction> guard,
        WrappedCastData<ReturnType> castData,
        ExecutorWrappedResultFunctionsData<HandleResultData<String, ReturnType>, Boolean, ReturnType> resultHandlers
    ) {
        super(getter, constructor, guard, castData, resultHandlers);
    }
}
