package com.github.karsaii.selenium.namespaces;

import com.github.karsaii.core.constants.CoreDataConstants;
import com.github.karsaii.core.namespaces.wait.Wait;
import com.github.karsaii.selenium.namespaces.extensions.boilers.DriverFunction;
import com.github.karsaii.core.extensions.namespaces.BasicPredicateFunctions;
import com.github.karsaii.core.namespaces.wait.WaitTimeDataFactory;
import com.github.karsaii.core.records.WaitData;

import static com.github.karsaii.core.extensions.namespaces.CoreUtilities.areAll;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static com.github.karsaii.selenium.namespaces.ExecutionCore.ifDriver;

public interface DriverWaits {
    static DriverFunction<Boolean> waitNavigatedTo(String url, int interval, int timeout) {
        return ifDriver(
            "waitNavigatedTo",
            isNotBlank(url) && areAll(BasicPredicateFunctions::isPositiveNonZero, interval, timeout) && (interval < timeout),
            driver -> Wait.core(new WaitData<>(ExpectedConditions.isUrlContainsData(url), WaitPredicateFunctions::isTruthyData, "Waiting for url", WaitTimeDataFactory.getWithDefaultClock(interval, timeout))).apply(driver),
            CoreDataConstants.PARAMETERS_NULL_BOOLEAN
        );
    }

    static DriverFunction<Boolean> navigateAndWait(String url, int interval, int timeout) {
        return ifDriver(
            "navigateAndWait",
            isNotBlank(url) && areAll(BasicPredicateFunctions::isPositiveNonZero, interval, timeout) && (interval < timeout),
            SeleniumExecutor.execute(Driver.navigate(url), waitNavigatedTo(url, interval, timeout)),
            CoreDataConstants.PARAMETERS_NULL_BOOLEAN
        );
    }
}
