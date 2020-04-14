package selenium.namespaces;

import core.constants.CoreDataConstants;
import selenium.namespaces.extensions.boilers.DriverFunction;
import core.records.Data;
import data.namespaces.Formatter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

import static core.extensions.namespaces.CoreUtilities.areNotNull;
import static core.namespaces.DataFactoryFunctions.appendMessage;
import static core.namespaces.DataFactoryFunctions.getBoolean;
import static core.namespaces.validators.DataValidators.isInvalidOrFalse;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.ExecutionCore.validChain;

public interface Screenshotter {
    private static Data<Boolean> takeScreenshot(TakesScreenshot shotter, String path) {
        final var formattedPath = Formatter.getScreenshotFileName(path);
        var data = CoreDataConstants.NULL_BOOLEAN;
        try {
            FileUtils.copyFile(shotter.getScreenshotAs(OutputType.FILE), new File(formattedPath));
            data = getBoolean(true, "Successfully taken screenshot, as: " + formattedPath);
        } catch (IOException ex) {
            data = getBoolean(false, "Couldn't take screenshot, exception occurred: " + ex.getMessage() + "\n As: " + formattedPath, ex, ex.getMessage());
        }

        return data;
    }

    private static Data<Boolean> takeScreenshot(Data<TakesScreenshot> shotter, String path) {
        return takeScreenshot(shotter.object, path);
    }

    private static Function<Data<TakesScreenshot>, Data<Boolean>> takeScreenshot(String path) {
        return shotter -> takeScreenshot(shotter, path);
    }

    static DriverFunction<Boolean> takeScreenShot(String path) {
        return ifDriver(
            "takeScreenShot",
            Formatter.isBlankMessage(path, "Path"),
            validChain(Driver.getScreenshotter(), Screenshotter.takeScreenshot(path), CoreDataConstants.NULL_BOOLEAN),
            CoreDataConstants.NULL_BOOLEAN
        );
    }

    static <Actual> Consumer<WebDriver> takeScreenShotOnFailure(Consumer<Data<Actual>> assertion, Data<Actual> data, String path) {
        return driver -> {
            try {
                assertion.accept(data);
            } catch (AssertionError ex) {
                final var ldata = Screenshotter.takeScreenShot(EnvironmentUtilities.getUsersProjectRootDirectory() + path).apply(driver);
                throw new AssertionError("takeScreenShotOnFailure: " + ldata.message + "\nOriginal Exception message: " + ex.getMessage());
            }
        };
    }

    static <Actual> DriverFunction<Boolean> takeScreenShotOnDataFailure(Consumer<Data<Actual>> assertion, Data<Actual> data, String path) {
        return ifDriver(
            "takeScreenShotOnDataFailure",
            areNotNull(assertion, data) && isNotBlank(path),
            driver -> {
                var ldata = CoreDataConstants.NULL_BOOLEAN;
                if (isInvalidOrFalse(data)) {
                    ldata = appendMessage(Screenshotter.takeScreenShot(EnvironmentUtilities.getUsersProjectRootDirectory() + path).apply(driver), data.message.getMessage());
                }

                assertion.accept(data);

                return ldata;
            },
            CoreDataConstants.NULL_BOOLEAN
        );
    }
}
