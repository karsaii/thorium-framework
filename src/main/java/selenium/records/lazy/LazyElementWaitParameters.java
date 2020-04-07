package selenium.records.lazy;

import selenium.abstracts.AbstractWaitParameters;
import selenium.constants.ElementWaitDefaults;
import selenium.records.LazyElement;

public class LazyElementWaitParameters extends AbstractWaitParameters<LazyElement> {
    public LazyElementWaitParameters(LazyElement object, int interval, int duration) {
        super(object, interval, duration);
    }

    public LazyElementWaitParameters(LazyElement object, int duration) {
        super(object, ElementWaitDefaults.INTERVAL, duration);
    }

    public LazyElementWaitParameters(LazyElement object) {
        super(object, ElementWaitDefaults.INTERVAL, ElementWaitDefaults.DURATION);
    }


}
