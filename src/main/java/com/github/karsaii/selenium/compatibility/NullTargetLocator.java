package com.github.karsaii.selenium.compatibility;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class NullTargetLocator implements WebDriver.TargetLocator {
    public NullTargetLocator() {}

    @Override
    public WebDriver frame(int i) {
        return null;
    }

    @Override
    public WebDriver frame(String s) {
        return null;
    }

    @Override
    public WebDriver frame(WebElement webElement) {
        return null;
    }

    @Override
    public WebDriver parentFrame() {
        return null;
    }

    @Override
    public WebDriver window(String s) {
        return null;
    }

    @Override
    public WebDriver defaultContent() {
        return null;
    }

    @Override
    public WebElement activeElement() {
        return null;
    }

    @Override
    public Alert alert() {
        return null;
    }
}
