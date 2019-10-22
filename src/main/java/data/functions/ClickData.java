package data.functions;

import data.DriverFunction;
import data.enums.SingleGetter;
import data.LazyElement;
import element.Element;
import org.openqa.selenium.By;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ClickData {
    public final Function<LazyElement, DriverFunction<Boolean>> clickLazy;
    public final BiFunction<By, SingleGetter, DriverFunction<Boolean>> clickByWithGetter;
    public final Function<By, DriverFunction<Boolean>> clickBy;

    public ClickData(
        Function<LazyElement, DriverFunction<Boolean>> clickLazy,
        BiFunction<By, SingleGetter, DriverFunction<Boolean>> clickByWithGetter,
        Function<By, DriverFunction<Boolean>> clickBy
    ) {
        this.clickLazy = clickLazy;
        this.clickByWithGetter = clickByWithGetter;
        this.clickBy = clickBy;
    }

    public ClickData() {
        this.clickLazy = Element::click;
        this.clickByWithGetter = Element::click;
        this.clickBy = Element::click;
    }
}
