package selenium.namespaces;

import core.extensions.interfaces.DriverFunction;
import core.extensions.namespaces.BasicPredicateFunctions;
import core.namespaces.Executor;
import core.namespaces.WaitTimeDataFactory;
import core.records.WaitData;
import selenium.constants.DataConstants;

import static core.extensions.namespaces.CoreUtilities.areAll;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static selenium.namespaces.ExecutionCore.ifDriver;

public interface DriverWaits {
    static DriverFunction<Boolean> waitNavigatedTo(String url, int interval, int timeout) {
        return ifDriver(
                "waitNavigatedTo",
                isNotBlank(url) && areAll(BasicPredicateFunctions::isPositiveNonZero, interval, timeout) && (interval < timeout),
                driver -> Wait.untilCore(new WaitData<>(EC.isUrlContainsData(url), WaitPredicateFunctions::isTruthyData, "Waiting for url", WaitTimeDataFactory.getWithDefaultClock(interval, timeout))).apply(driver),
                DataConstants.PARAMETERS_NULL_BOOLEAN
        );
    }

    static DriverFunction<Boolean> navigateAndWait(String url, int interval, int timeout) {
        return ifDriver(
                "navigateAndWait",
                isNotBlank(url) && areAll(BasicPredicateFunctions::isPositiveNonZero, interval, timeout) && (interval < timeout),
                Executor.execute(Driver.navigate(url), waitNavigatedTo(url, interval, timeout)),
                DataConstants.PARAMETERS_NULL_BOOLEAN
        );
    }
}
