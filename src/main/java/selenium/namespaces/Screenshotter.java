package selenium.namespaces;

import core.extensions.interfaces.DriverFunction;
import core.namespaces.DataFactoryFunctions;
import core.records.Data;
import data.namespaces.Formatter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import selenium.constants.DataConstants;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

import static core.extensions.namespaces.CoreUtilities.areNotNull;
import static core.namespaces.DataFunctions.appendMessage;
import static core.namespaces.DataFunctions.isInvalidOrFalse;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static selenium.namespaces.ExecutionCore.ifDriver;
import static selenium.namespaces.ExecutionCore.validChain;

public interface Screenshotter {
    private static Data<Boolean> takeScreenshot(TakesScreenshot shotter, String path) {
        final var formattedPath = Formatter.getScreenshotFileName(path);
        var data = DataConstants.NULL_BOOLEAN;
        try {
            FileUtils.copyFile(shotter.getScreenshotAs(OutputType.FILE), new File(formattedPath));
            data = DataFactoryFunctions.getBoolean(true, "Successfully taken screenshot, as: " + formattedPath);
        } catch (IOException ex) {
            data = DataFactoryFunctions.getBoolean(false, "Couldn't take screenshot, exception occurred: " + ex.getMessage() + "\n As: " + formattedPath, ex, ex.getMessage());
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
            validChain(Driver.getScreenshotter(), Screenshotter.takeScreenshot(path), DataConstants.NULL_BOOLEAN),
            DataConstants.NULL_BOOLEAN
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
                var ldata = DataConstants.NULL_BOOLEAN;
                if (isInvalidOrFalse(data)) {
                    ldata = appendMessage(Screenshotter.takeScreenShot(EnvironmentUtilities.getUsersProjectRootDirectory() + path).apply(driver), data.message.getMessage());
                }

                assertion.accept(data);

                return ldata;
            },
            DataConstants.NULL_BOOLEAN
        );
    }
}
