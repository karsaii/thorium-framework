package selenium.constants;

import java.time.Clock;
import java.time.Duration;

public abstract class WaitConstants {
    public static final int DEFAULT_SLEEP_TIMEOUT = 3000;
    public static final int DEFAULT_SLEEP_INTERVAL = 300;
    public static final Clock CLOCK = Clock.systemDefaultZone();

    public static final Duration DEFAULT_WAIT_DURATION = Duration.ofMillis(DEFAULT_SLEEP_TIMEOUT),
        DEFAULT_WAIT_INTERVAL = Duration.ofMillis(DEFAULT_SLEEP_INTERVAL),
        TIMEOUT = DEFAULT_WAIT_DURATION,
        INTERVAL = DEFAULT_WAIT_INTERVAL;
}
