package selenium.records.scripter;

import java.util.Objects;
import java.util.function.Function;

public class ExecutorResultFunctionsData<ParameterType, MessageParameterType, ReturnType> {
    public final Function<MessageParameterType, String> messageHandler;
    public final Function<ParameterType, ReturnType> castHandler;

    public ExecutorResultFunctionsData(Function<MessageParameterType, String> messageHandler, Function<ParameterType, ReturnType> castHandler) {
        this.messageHandler = messageHandler;
        this.castHandler = castHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ExecutorResultFunctionsData<?, ?, ?>) o;
        return Objects.equals(messageHandler, that.messageHandler) && Objects.equals(castHandler, that.castHandler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageHandler, castHandler);
    }
}
