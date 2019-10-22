package data.tuples;

/**
 * A class containing the parameters needed to do an Action:
 * - An object,
 * - A methodName (to use in WebDriver reflection search),
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

    public ActionData(T data, String methodName, String message) {
        this(data, methodName, message, "");
    }
}
