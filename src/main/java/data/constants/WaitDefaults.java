package data.constants;

import java.time.Clock;
import java.time.Duration;

public class WaitDefaults {
    public static final int DEFAULT_SLEEP_TIMEOUT = 500;
    public static final Duration DEFAULT_WAIT_DURATION = Duration.ofMillis(DEFAULT_SLEEP_TIMEOUT);
    public static final Clock CLOCK = Clock.systemDefaultZone();

    public static final Duration TIMEOUT = DEFAULT_WAIT_DURATION;
    public static final Duration INTERVAL = DEFAULT_WAIT_DURATION;
}
