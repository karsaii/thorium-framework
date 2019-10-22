package data.tuples;

import java.time.Clock;
import java.time.Duration;
import java.util.function.Function;

public class WaitData<T, V> {
    public T input;
    public Function<T, V> condition;
    public String conditionMessage;
    public Duration interval;
    public Duration timeout;
    public Clock clock;

    public WaitData(T input, Function<T, V> condition, String conditionMessage, Duration interval, Duration timeout, Clock clock) {
        this.input = input;
        this.condition = condition;
        this.conditionMessage = conditionMessage;
        this.interval = interval;
        this.timeout = timeout;
        this.clock = clock;
    }

    public WaitData(T input, Function<T, V> condition, String conditionMessage, int interval, int timeout, Clock clock) {
        this(input, condition, conditionMessage, Duration.ofMillis(interval), Duration.ofMillis(timeout), clock);
    }

    public WaitData(T input, Function<T, V> condition, String conditionMessage, int interval, int timeout) {
        this(input, condition, conditionMessage, Duration.ofMillis(interval), Duration.ofMillis(timeout), null);
    }

    public WaitData(T input, Function<T, V> condition, String conditionMessage, Duration interval, Duration timeout) {
        this(input, condition, conditionMessage, interval, timeout, null);
    }

    public WaitData(T input, Function<T, V> condition, String conditionMessage, Duration interval) {
        this(input, condition, conditionMessage, interval, null, null);
    }

    public WaitData(T input, Function<T, V> condition, String conditionMessage) {
        this(input, condition, conditionMessage, null, null, null);
    }

    public WaitData(T input, Function<T, V> condition, Duration interval) {
        this(input, condition, null, interval, null, null);
    }

    public WaitData(T input, Function<T, V> condition) {
        this(input, condition, null, null, null);
    }
}
