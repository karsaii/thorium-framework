package core.extensions.interfaces;

import core.records.Data;
import org.openqa.selenium.WebDriver;

import java.util.function.Function;

@FunctionalInterface
public interface DriverFunction<T> extends Function<WebDriver, Data<T>> {}
