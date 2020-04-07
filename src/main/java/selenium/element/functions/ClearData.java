package selenium.element.functions;

import core.extensions.interfaces.DriverFunction;
import org.openqa.selenium.By;
import selenium.element.Element;
import selenium.enums.SingleGetter;
import selenium.records.LazyElement;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ClearData {
    public final Function<LazyElement, DriverFunction<Boolean>> clearLazy;
    public final BiFunction<By, SingleGetter, DriverFunction<Boolean>> clearByWithGetter;
    public final Function<By, DriverFunction<Boolean>> clearBy;

    public ClearData(
        Function<LazyElement, DriverFunction<Boolean>> clearLazy,
        BiFunction<By, SingleGetter, DriverFunction<Boolean>> clearByWithGetter,
        Function<By, DriverFunction<Boolean>> clearBy
    ) {
        this.clearLazy = clearLazy;
        this.clearByWithGetter = clearByWithGetter;
        this.clearBy = clearBy;
    }

    public ClearData() {
        this.clearLazy = Element::clear;
        this.clearByWithGetter = Element::clear;
        this.clearBy = Element::clear;
    }
}
