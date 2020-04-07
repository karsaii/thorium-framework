package data.tuples;

public class InputData<T> {
    public final T object;
    public final Class<T> clazz;
    public final T defaultValue;

    public InputData(T object, Class<T> clazz, T defaultValue) {
        this.object = object;
        this.clazz = clazz;
        this.defaultValue = defaultValue;
    }
}
