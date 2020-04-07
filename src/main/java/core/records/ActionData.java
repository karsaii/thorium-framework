package core.records;

import java.util.Objects;

/**
 * A class containing the parameters needed to do an Action:
 * - An object,
 * - A methodName (to use in WebDriver core.reflection search),
 * - A message (fragment) describing the action,
 * - An optional input for input using actions, methods.
 *
 * @param <T>
 */
public class ActionData<T> {
    public final T data;
    public final String methodName;
    public final String message;
    public final String input;

    public ActionData(T data, String methodName, String message, String input) {
        this.data = data;
        this.methodName = methodName;
        this.message = message;
        this.input = input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ActionData<?>) o;
        return (
            Objects.equals(data, that.data) &&
            Objects.equals(methodName, that.methodName) &&
            Objects.equals(message, that.message) &&
            Objects.equals(input, that.input)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, methodName, message, input);
    }
}
