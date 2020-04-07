package selenium.namespaces;

import core.extensions.interfaces.DriverFunction;
import core.extensions.namespaces.BasicPredicateFunctions;
import core.namespaces.DataFunctions;
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
            driver -> {
                final var result = Driver.navigate(url).apply(driver);
                return DataFunctions.isValidNonFalse(result) ? Wait.untilCore(
                    new WaitData<>(
                        EC.isUrlContainsData(url),
                        WaitPredicateFunctions::isTruthyData,
                        "Waiting for url",
                        WaitTimeDataFactory.getWithDefaultClock(interval, timeout)
                    )
                ).apply(driver) : result;
            },
            DataConstants.PARAMETERS_NULL_BOOLEAN
        );
    }
}
