package data.tuples;

public class SwitchResultMessageData<T> {
    public final T target;
    public final String type,
        nameof;

    public SwitchResultMessageData(T target, String type, String nameof) {
        this.target = target;
        this.type = type;
        this.nameof = nameof;
    }
}
