package selenium.abstracts;

public abstract class AbstractWaitParameters<T> {
    public final T object;
    public final int duration;
    public final int interval;

    public AbstractWaitParameters(T object, int duration, int interval) {
        this.object = object;
        this.duration = duration;
        this.interval = interval;
    }
}
