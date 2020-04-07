package screenshot;

import data.constants.DataDefaults;
import data.Data;
import data.DriverFunction;
import drivers.Driver;
import environment.EnvironmentProperties;
import formatter.Formatter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import static data.ExecutionCore.ifDriver;
import static utilities.utils.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface Screenshotter {
    static DriverFunction<Boolean> takeScreenShot(String path) {
        final var nameof = "takeScreenShot: ";
        return ifDriver(
                nameof,
                isNotBlank(path),
                new DriverFunction<Boolean> (driver -> {
                final var formattedPath = Formatter.getScreenshotFileName(path);

                Data<Boolean> data;
                try {
                    var screenshot = Driver.getScreenshotter(driver).getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(screenshot, new File(formattedPath));
                    data = new Data<Boolean>(true, true, nameof, "Successfully taken screenshot, as: " + formattedPath);
                } catch (IOException ex) {
                    data = new Data<Boolean>(false, false, nameof, "Couldn't take screenshot, exception occurred: " + ex.getMessage() + "\n As: " + formattedPath, ex, ex.getMessage());
                }

                return data;
            }),
            DataDefaults.NULL_BOOLEAN_DATA
        );
    }

    static <Actual> Consumer<WebDriver> takeScreenShotOnFailure(Consumer<Data<Actual>> assertion, Data<Actual> data, String path) {
        return driver -> {
            try {
                assertion.accept(data);
            } catch (AssertionError ex) {
                Data<Boolean> ldata = Screenshotter.takeScreenShot(EnvironmentProperties.getUsersProjectRootDirectory() + path).apply(driver);
                throw new AssertionError("takeScreenShotOnFailure: " + ldata.message + "\nOriginal Exception message: " + ex.getMessage());
            }
        };
    }

    static <Actual> DriverFunction<Boolean> takeScreenShotOnDataFailure(Consumer<Data<Actual>> assertion, Data<Actual> data, String path) {
        return ifDriver(
            "takeScreenShotOnDataFailure",
            areNotNull(assertion, data) && isNotBlank(path),
            new DriverFunction<Boolean> (driver -> {
                Data<Boolean> ldata = DataDefaults.NULL_BOOLEAN_DATA;
                if (isNullOrFalseDataOrDataObject(data)) {
                    ldata = appendMessage(Screenshotter.takeScreenShot(EnvironmentProperties.getUsersProjectRootDirectory() + path).apply(driver), data.message.getMessage());
                }

                assertion.accept(data);

                return ldata;
            }),
            DataDefaults.NULL_BOOLEAN_DATA
        );
    }
}
