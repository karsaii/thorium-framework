package com.github.karsaii.selenium.compatibility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

public final class NullTakesScreenshot implements TakesScreenshot {
    public NullTakesScreenshot() {}

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return null;
    }
}
